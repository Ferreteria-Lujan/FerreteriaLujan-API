package com.ferreteria.compras.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ferreteria.compras.dto.DetallePedidoCompraAltaRequest;
import com.ferreteria.compras.dto.DetallePedidoCompraResponse;

public interface DetallePedidoCompraService {

    Page<DetallePedidoCompraResponse> findAll(Long idPedido, Pageable pageable);

    DetallePedidoCompraResponse findById(Long id);

    DetallePedidoCompraResponse create(DetallePedidoCompraAltaRequest request);
}
