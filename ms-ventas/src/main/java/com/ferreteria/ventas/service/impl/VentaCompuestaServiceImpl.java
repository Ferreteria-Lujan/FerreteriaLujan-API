package com.ferreteria.ventas.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ferreteria.commons.dto.DetalleFacturaVentaDTO;
import com.ferreteria.commons.dto.FacturaVentaDTO;
import com.ferreteria.commons.dto.PresupuestoDTO;
import com.ferreteria.commons.dto.PresupuestoDetalleDTO;
import com.ferreteria.ventas.dto.FacturaVentaConDetallesDTO;
import com.ferreteria.ventas.dto.FacturaVentaCreadaDTO;
import com.ferreteria.ventas.dto.PresupuestoConDetallesDTO;
import com.ferreteria.ventas.dto.PresupuestoCreadoDTO;
import com.ferreteria.ventas.exception.BusinessException;
import com.ferreteria.ventas.filter.PresupuestoDetalleFilter;
import com.ferreteria.ventas.service.DetalleFacturaVentaService;
import com.ferreteria.ventas.service.EstadoService;
import com.ferreteria.ventas.service.FacturaVentaService;
import com.ferreteria.ventas.service.PresupuestoDetalleService;
import com.ferreteria.ventas.service.PresupuestoService;
import com.ferreteria.ventas.repository.FacturaVentaRepository;
import com.ferreteria.ventas.service.VentaCompuestaService;

/**
 * Orquesta altas cabecera + detalle en una sola transaccion. No persiste nada por su cuenta:
 * delega en el service de cada recurso (que mantiene su propia cache y sus propias validaciones) y
 * solo decide el orden de las llamadas y los valores calculados (total, IVA, estado). Al llamar a
 * los services por su interfaz (bean de Spring, no "this"), las anotaciones de cache de cada uno
 * (@Cacheable/@CachePut/@CacheEvict) funcionan normalmente.
 */
@Service
public class VentaCompuestaServiceImpl implements VentaCompuestaService {

    private static final Logger log = LoggerFactory.getLogger(VentaCompuestaServiceImpl.class);

    private final FacturaVentaService facturaService;
    private final DetalleFacturaVentaService detalleFacturaService;
    private final PresupuestoService presupuestoService;
    private final PresupuestoDetalleService presupuestoDetalleService;
    private final EstadoService estadoService;
    private final FacturaVentaRepository facturaRepository;

    private final double ivaPorcentaje;
    private final boolean ivaIncluido;
    private final String estadoPendiente;
    private final String estadoPagada;
    private final String estadoConvertido;

    public VentaCompuestaServiceImpl(
            FacturaVentaService facturaService,
            DetalleFacturaVentaService detalleFacturaService,
            PresupuestoService presupuestoService,
            PresupuestoDetalleService presupuestoDetalleService,
            EstadoService estadoService,
            FacturaVentaRepository facturaRepository,
            @Value("${app.ventas.iva.porcentaje:10}") double ivaPorcentaje,
            @Value("${app.ventas.iva.incluido-en-precio:false}") boolean ivaIncluido,
            @Value("${app.ventas.estado.pendiente-nombre:Pendiente}") String estadoPendiente,
            @Value("${app.ventas.estado.pagada-nombre:Pagada}") String estadoPagada,
            @Value("${app.ventas.estado.convertido-nombre:Convertido}") String estadoConvertido) {
        this.facturaService = facturaService;
        this.detalleFacturaService = detalleFacturaService;
        this.presupuestoService = presupuestoService;
        this.presupuestoDetalleService = presupuestoDetalleService;
        this.estadoService = estadoService;
        this.facturaRepository = facturaRepository;
        this.ivaPorcentaje = ivaPorcentaje;
        this.ivaIncluido = ivaIncluido;
        this.estadoPendiente = estadoPendiente;
        this.estadoPagada = estadoPagada;
        this.estadoConvertido = estadoConvertido;
    }

    @Override
    @Transactional
    public FacturaVentaCreadaDTO crearFacturaConDetalles(FacturaVentaConDetallesDTO dto) {
        FacturaVentaConDetallesDTO.Cabecera c = dto.cabecera();
        FacturaVentaDTO cabecera = new FacturaVentaDTO()
                .idCliente(c.idCliente())
                .idPresupuesto(c.idPresupuesto())
                .timbrado(c.timbrado())
                .fechaEmision(c.fechaEmision() != null ? c.fechaEmision() : LocalDate.now())
                .fechaVencimiento(c.fechaVencimiento())
                .esCredito(c.esCredito());
        return crearFactura(cabecera, dto.detalles());
    }

    @Override
    @Transactional
    public PresupuestoCreadoDTO crearPresupuestoConDetalles(PresupuestoConDetallesDTO dto) {
        List<PresupuestoConDetallesDTO.Linea> lineas = dto.detalles();
        PresupuestoDTO cabecera = dto.cabecera();
        cabecera.fecha(cabecera.getFecha() != null ? cabecera.getFecha() : LocalDate.now());
        cabecera.idEstado(estadoService.getIdPorNombre(estadoPendiente));

        PresupuestoDTO creado = presupuestoService.create(cabecera);
        List<PresupuestoDetalleDTO> detallesCreados = new ArrayList<>();
        for (PresupuestoConDetallesDTO.Linea linea : lineas) {
            PresupuestoDetalleDTO detalle = new PresupuestoDetalleDTO()
                    .idPresupuesto(creado.getIdPresupuesto())
                    .idProducto(linea.idProducto())
                    .cantidad(linea.cantidad().doubleValue())
                    .precioUnitario(linea.precioUnitario());
            detallesCreados.add(presupuestoDetalleService.create(detalle));
        }
        log.info("Presupuesto {} creado con {} linea(s)", creado.getIdPresupuesto(), detallesCreados.size());
        return new PresupuestoCreadoDTO(creado, detallesCreados);
    }

    @Override
    @Transactional
    public FacturaVentaCreadaDTO generarFacturaDesdePresupuesto(Long idPresupuesto) {
        PresupuestoDTO presupuesto = presupuestoService.findById(idPresupuesto); // 404 si no existe o esta inactivo
        Long idConvertido = estadoService.getIdPorNombre(estadoConvertido);
        if (idConvertido.equals(presupuesto.getIdEstado())) {
            throw new BusinessException("El presupuesto ya fue convertido en factura");
        }

        PresupuestoDetalleFilter filtro = new PresupuestoDetalleFilter();
        filtro.setIdPresupuesto(idPresupuesto);
        List<PresupuestoDetalleDTO> lineas = presupuestoDetalleService.search(filtro, Pageable.unpaged()).getContent();
        if (lineas.isEmpty()) {
            throw new BusinessException("El presupuesto no tiene productos cargados");
        }

        FacturaVentaDTO cabecera = new FacturaVentaDTO()
                .idCliente(presupuesto.getIdCliente())
                .idPresupuesto(idPresupuesto)
                .fechaEmision(LocalDate.now())
                .esCredito(false);
        List<FacturaVentaConDetallesDTO.Linea> detallesFactura = lineas.stream()
                .map(l -> {
                    double cant = l.getCantidad();
                    if (cant != Math.rint(cant)) {
                        throw new BusinessException("La cantidad " + cant + " del producto "
                                + l.getIdProducto() + " no es entera y no puede facturarse");
                    }
                    return new FacturaVentaConDetallesDTO.Linea(l.getIdProducto(), (int) cant, l.getPrecioUnitario());
                })
                .toList();

        FacturaVentaCreadaDTO facturaCreada = crearFactura(cabecera, detallesFactura);

        presupuestoService.update(idPresupuesto, new PresupuestoDTO()
                .idCliente(presupuesto.getIdCliente())
                .fecha(presupuesto.getFecha())
                .idEstado(idConvertido));

        log.info("Presupuesto {} convertido en factura {}", idPresupuesto, facturaCreada.factura().getIdFactura());
        return facturaCreada;
    }

    private FacturaVentaCreadaDTO crearFactura(FacturaVentaDTO cabecera, List<FacturaVentaConDetallesDTO.Linea> lineas) {
        double subtotal = lineas.stream().mapToDouble(l -> l.cantidad() * l.precioUnitario()).sum();
        double iva = ivaIncluido ? subtotal * ivaPorcentaje / (100 + ivaPorcentaje) : subtotal * ivaPorcentaje / 100;
        double total = ivaIncluido ? subtotal : subtotal + iva;

        if (cabecera.getNroFactura() == null || cabecera.getNroFactura().isBlank()) {
            cabecera.nroFactura(siguienteNroFactura());
        }
        cabecera.importeTotal(total)
                .iva(iva)
                .idEstado(estadoService.getIdPorNombre(
                        Boolean.TRUE.equals(cabecera.getEsCredito()) ? estadoPendiente : estadoPagada));

        FacturaVentaDTO creada = facturaService.create(cabecera);
        List<DetalleFacturaVentaDTO> detallesCreados = new ArrayList<>();
        for (FacturaVentaConDetallesDTO.Linea linea : lineas) {
            DetalleFacturaVentaDTO detalle = new DetalleFacturaVentaDTO()
                    .idFactura(creada.getIdFactura())
                    .idProducto(linea.idProducto())
                    .cantidad(linea.cantidad())
                    .precioUnitario(linea.precioUnitario());
            detallesCreados.add(detalleFacturaService.create(detalle));
        }
        log.info("Factura {} creada con {} linea(s), total {}", creada.getIdFactura(), detallesCreados.size(), total);
        return new FacturaVentaCreadaDTO(creada, detallesCreados);
    }

    /** Siguiente nro con formato 001-001-0000013: toma el mayor existente y suma 1 al correlativo (inicia en 0000001). */
    private String siguienteNroFactura() {
        String ultimo = facturaRepository.findUltimoNroFactura();
        if (ultimo == null) {
            return "001-001-0000001";
        }
        int corte = ultimo.lastIndexOf('-') + 1;
        long siguiente = Long.parseLong(ultimo.substring(corte)) + 1;
        return ultimo.substring(0, corte) + String.format("%07d", siguiente);
    }
}