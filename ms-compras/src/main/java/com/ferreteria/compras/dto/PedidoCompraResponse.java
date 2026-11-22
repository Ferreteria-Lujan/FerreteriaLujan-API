package com.ferreteria.compras.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record PedidoCompraResponse(
        Long idPedido,
        String nroPedido,
        Long idProveedor,
        LocalDate fechaEmision,
        LocalDate fechaRecepcion,
        BigDecimal total,
        Boolean pagado,
        Long idEstado,
        String nombreEstado,
        Long idFacturaCompra,
        List<DetallePedidoCompraResponse> detalles) {
}
