package com.ferreteria.compras.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ferreteria.compras.dto.PedidoCompraRequest;
import com.ferreteria.compras.dto.PedidoCompraResponse;

public interface PedidoCompraService {

    Page<PedidoCompraResponse> findAll(Pageable pageable);

    PedidoCompraResponse findById(Long id);

    PedidoCompraResponse create(PedidoCompraRequest request);

    PedidoCompraResponse marcarPagado(Long id);

    PedidoCompraResponse recibir(Long id);
}
