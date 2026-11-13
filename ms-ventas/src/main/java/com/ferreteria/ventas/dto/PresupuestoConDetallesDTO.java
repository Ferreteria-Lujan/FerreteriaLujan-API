package com.ferreteria.ventas.dto;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import com.ferreteria.commons.dto.PresupuestoDTO;

/** Igual que FacturaVentaConDetallesDTO, para presupuestos: nace siempre en "Pendiente". */
public record PresupuestoConDetallesDTO(
        @NotNull @Valid PresupuestoDTO cabecera,
        @NotEmpty List<@Valid Linea> detalles) {

    public record Linea(
            @NotNull Long idProducto,
            @NotNull @Positive Integer cantidad,
            @NotNull @PositiveOrZero Double precioUnitario) {
    }
}