package com.ferreteria.compras.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ferreteria.commons.dto.DetalleFacturaCompraDTO;
import com.ferreteria.commons.dto.FacturaCompraDTO;
import com.ferreteria.compras.dto.DetallePedidoCompraRequest;
import com.ferreteria.compras.dto.RegistroCompraRequest;
import com.ferreteria.compras.exception.ResourceNotFoundException;
import com.ferreteria.compras.service.DetalleFacturaCompraService;
import com.ferreteria.compras.service.FacturaCompraService;
import com.ferreteria.compras.service.RegistroCompraService;

@Service
public class RegistroCompraServiceImpl implements RegistroCompraService {

    private final JdbcTemplate jdbc;
    private final FacturaCompraService facturaService;
    private final DetalleFacturaCompraService detalleService;
    private final long depositoPredeterminado;
    private final int porcentajeIva;

    public RegistroCompraServiceImpl(
            JdbcTemplate jdbc,
            FacturaCompraService facturaService,
            DetalleFacturaCompraService detalleService,
            @Value("${app.compras.deposito-predeterminado-id:1}") long depositoPredeterminado,
            @Value("${app.compras.iva.porcentaje-por-defecto:10}") int porcentajeIva) {
        this.jdbc = jdbc;
        this.facturaService = facturaService;
        this.detalleService = detalleService;
        this.depositoPredeterminado = depositoPredeterminado;
        this.porcentajeIva = porcentajeIva;
    }

    @Override
    @Transactional
    public FacturaCompraDTO registrar(RegistroCompraRequest request) {
        validarDeposito();
        FacturaCompraDTO factura = new FacturaCompraDTO();
        factura.setIdProveedor(request.idProveedor());
        factura.setNroFactura(request.nroFactura());
        factura.setFechaEmision(request.fechaEmision());
        factura.setFechaVencimiento(request.fechaVencimiento());
        if (request.idPedido() != null) {
            factura.setCodigoFactura("PED-" + request.idPedido());
        }
        FacturaCompraDTO creada = facturaService.create(factura);

        if (request.idPedido() != null) {
            jdbc.update("""
                    UPDATE facturas_compras
                       SET id_pedido = ?
                     WHERE id_factura_compra = ?
                    """, request.idPedido(), creada.getIdFacturaCompra());
        }

        for (DetallePedidoCompraRequest item : request.detalles()) {
            DetalleFacturaCompraDTO detalle = new DetalleFacturaCompraDTO();
            detalle.setIdFacturaCompra(creada.getIdFacturaCompra());
            detalle.setIdProducto(item.idProducto());
            detalle.setCantidad(item.cantidad());
            detalle.setPrecioUnitario(item.precioUnitario().doubleValue());
            detalle.setPorcentajeIva(porcentajeIva);
            detalleService.create(detalle);
            ingresarStock(item);
        }
        return facturaService.findById(creada.getIdFacturaCompra());
    }

    private void ingresarStock(DetallePedidoCompraRequest item) {
        List<Long> ids = jdbc.query("""
                SELECT id_inventario
                  FROM inventarios
                 WHERE id_deposito = ? AND id_producto = ?
                 ORDER BY id_inventario
                 LIMIT 1
                 FOR UPDATE
                """, (rs, row) -> rs.getLong(1), depositoPredeterminado, item.idProducto());
        if (ids.isEmpty()) {
            jdbc.update("""
                    INSERT INTO inventarios (id_deposito, id_producto, cantidad, stock_minimo, stock_maximo)
                    VALUES (?, ?, ?, 0, 0)
                    """, depositoPredeterminado, item.idProducto(), item.cantidad());
        } else {
            jdbc.update("""
                    UPDATE inventarios
                       SET cantidad = cantidad + ?
                     WHERE id_inventario = ?
                    """, item.cantidad(), ids.getFirst());
        }
    }

    private void validarDeposito() {
        Long cantidad = jdbc.queryForObject(
                "SELECT COUNT(*) FROM depositos WHERE id_deposito = ?",
                Long.class, depositoPredeterminado);
        if (cantidad == null || cantidad == 0) {
            throw new ResourceNotFoundException(
                    "Depósito predeterminado no encontrado: " + depositoPredeterminado);
        }
    }
}
