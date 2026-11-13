package com.ferreteria.ventas.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import com.ferreteria.commons.dto.PresupuestoDTO;
import com.ferreteria.ventas.dto.FacturaVentaCreadaDTO;
import com.ferreteria.ventas.dto.PresupuestoConDetallesDTO;
import com.ferreteria.ventas.dto.PresupuestoCreadoDTO;
import com.ferreteria.ventas.filter.PresupuestoFilter;
import com.ferreteria.ventas.service.PresupuestoService;
import com.ferreteria.ventas.service.VentaCompuestaService;

@RestController
@RequestMapping("/api/presupuestos")
@Tag(name = "Presupuestos", description = "Presupuestos de venta (el DELETE los pasa a estado inactivo)")
public class PresupuestoController
        extends AbstractBaseController<PresupuestoDTO, PresupuestoFilter, Long, PresupuestoService> {

    private final VentaCompuestaService compuesta;

    public PresupuestoController(PresupuestoService service, VentaCompuestaService compuesta) {
        super(service);
        this.compuesta = compuesta;
    }

    @PostMapping("/completo")
    @PreAuthorize("@roleAccess.canWrite(authentication)")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Crear un presupuesto con todas sus lineas en una sola peticion")
    @ApiResponse(responseCode = "201", description = "Presupuesto y lineas creados")
    public PresupuestoCreadoDTO crearCompleto(@Valid @RequestBody PresupuestoConDetallesDTO dto) {
        log.info("POST presupuesto completo (cabecera + detalle)");
        return compuesta.crearPresupuestoConDetalles(dto);
    }

    @PostMapping("/{id}/generar-factura")
    @PreAuthorize("@roleAccess.canWrite(authentication)")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Genera la factura de venta a partir de este presupuesto y lo marca Convertido")
    @ApiResponse(responseCode = "201", description = "Factura generada")
    @ApiResponse(responseCode = "400", description = "El presupuesto ya fue convertido o no tiene lineas")
    @ApiResponse(responseCode = "404", description = "El presupuesto no existe o esta inactivo")
    public FacturaVentaCreadaDTO generarFactura(@PathVariable Long id) {
        log.info("POST generar factura desde presupuesto {}", id);
        return compuesta.generarFacturaDesdePresupuesto(id);
    }
}