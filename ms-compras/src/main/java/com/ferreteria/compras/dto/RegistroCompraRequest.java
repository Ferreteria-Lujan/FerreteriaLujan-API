package com.ferreteria.compras.dto;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record RegistroCompraRequest(
        @NotNull Long idProveedor,
        @NotBlank String nroFactura,
        @NotNull LocalDate fechaEmision,
        LocalDate fechaVencimiento,
        Long idPedido,
        @NotEmpty List<@Valid DetallePedidoCompraRequest> detalles) {
}
