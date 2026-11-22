package com.ferreteria.compras.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record DetallePedidoCompraRequest(
        @NotNull Long idProducto,
        @NotNull @Positive Integer cantidad,
        @NotNull @DecimalMin("0.0") BigDecimal precioUnitario) {
}
