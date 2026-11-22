package com.ferreteria.compras.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ferreteria.compras.dto.PedidoCompraRequest;
import com.ferreteria.compras.dto.PedidoCompraResponse;
import com.ferreteria.compras.service.PedidoCompraService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/pedidos-compras")
@Tag(name = "Pedidos de compra", description = "Pedidos, recepción, facturación e ingreso de stock")
public class PedidoCompraController {

    private final PedidoCompraService service;

    public PedidoCompraController(PedidoCompraService service) {
        this.service = service;
    }

    @GetMapping
    public Page<PedidoCompraResponse> findAll(
            @PageableDefault(size = 20, sort = "fechaEmision") Pageable pageable) {
        return service.findAll(pageable);
    }

    @GetMapping("/{id}")
    public PedidoCompraResponse findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoCompraResponse create(@Valid @RequestBody PedidoCompraRequest request) {
        return service.create(request);
    }

    @PostMapping("/{id}/pagar")
    public PedidoCompraResponse marcarPagado(@PathVariable Long id) {
        return service.marcarPagado(id);
    }

    @PostMapping("/{id}/recibir")
    public PedidoCompraResponse recibir(@PathVariable Long id) {
        return service.recibir(id);
    }
}
