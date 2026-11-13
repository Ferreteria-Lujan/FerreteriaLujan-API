package com.ferreteria.ventas.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import com.ferreteria.commons.dto.FacturaVentaDTO;
import com.ferreteria.ventas.dto.FacturaVentaConDetallesDTO;
import com.ferreteria.ventas.dto.FacturaVentaCreadaDTO;
import com.ferreteria.ventas.filter.FacturaVentaFilter;
import com.ferreteria.ventas.service.FacturaVentaService;
import com.ferreteria.ventas.service.VentaCompuestaService;

@RestController
@RequestMapping("/api/facturas-ventas")
@Tag(name = "Facturas de venta", description = "Facturas de venta (el DELETE las pasa a estado inactivo)")
public class FacturaVentaController
        extends AbstractBaseController<FacturaVentaDTO, FacturaVentaFilter, Long, FacturaVentaService> {

    private final VentaCompuestaService compuesta;

    public FacturaVentaController(FacturaVentaService service, VentaCompuestaService compuesta) {
        super(service);
        this.compuesta = compuesta;
    }

    /**
     * Alta cabecera + detalle en una sola peticion: la factura y todas sus lineas se guardan en
     * una unica transaccion. Total, IVA y estado (Pagada/Pendiente segun esCredito) los calcula
     * el servidor; lo que venga en esos campos de la cabecera se ignora.
     */
    @PostMapping("/completa")
    @PreAuthorize("@roleAccess.canWrite(authentication)")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Crear una factura de venta con todas sus lineas en una sola peticion")
    @ApiResponse(responseCode = "201", description = "Factura y lineas creadas")
    @ApiResponse(responseCode = "400", description = "Datos invalidos o sin lineas")
    public FacturaVentaCreadaDTO crearCompleta(@Valid @RequestBody FacturaVentaConDetallesDTO dto) {
        log.info("POST factura completa (cabecera + detalle)");
        return compuesta.crearFacturaConDetalles(dto);
    }
}