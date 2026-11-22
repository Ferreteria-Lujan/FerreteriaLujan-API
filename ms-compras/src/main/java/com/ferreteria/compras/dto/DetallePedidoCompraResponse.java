package com.ferreteria.compras.dto;

import java.math.BigDecimal;

public record DetallePedidoCompraResponse(
        Long idDetallePedido,
        Long idPedido,
        Long idProducto,
        String codigo,
        String descripcion,
        Integer cantidad,
        BigDecimal precioUnitario,
        BigDecimal subtotal) {
}
