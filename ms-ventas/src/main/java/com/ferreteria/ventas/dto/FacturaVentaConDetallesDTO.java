package com.ferreteria.ventas.dto;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

/**
 * DTO compuesto para crear una factura de venta junto con todas sus lineas en una sola peticion.
 * El servidor calcula nroFactura, importeTotal, iva e idEstado de la cabecera, por eso la cabecera
 * es un record propio con solo lo que decide el cliente (el FacturaVentaDTO de commons exige nroFactura).
 * Cada linea trae solo lo que decide el cliente (producto, cantidad, precio); el id de la
 * factura lo completa el servidor despues de guardar la cabecera, por eso no es un
 * DetalleFacturaVentaDTO completo (ese exige idFactura, que todavia no existe al validar el body).
 */
public record FacturaVentaConDetallesDTO(
        @NotNull @Valid Cabecera cabecera,
        @NotEmpty List<@Valid Linea> detalles) {

    public record Cabecera(
            @NotNull Long idCliente,
            Long idPresupuesto,
            Double timbrado,
            LocalDate fechaEmision,
            LocalDate fechaVencimiento,
            Boolean esCredito) {
    }

    public record Linea(
            @NotNull Long idProducto,
            @NotNull @Positive Integer cantidad,
            @NotNull @PositiveOrZero Double precioUnitario) {
    }
}