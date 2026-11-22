package com.ferreteria.compras.dto;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record PedidoCompraRequest(
        @NotBlank String nroPedido,
        @NotNull Long idProveedor,
        LocalDate fechaEmision,
        String observaciones,
        @NotEmpty List<@Valid DetallePedidoCompraRequest> detalles) {
}
