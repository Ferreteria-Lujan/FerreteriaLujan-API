package com.ferreteria.compras.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ferreteria.compras.dto.DetallePedidoCompraAltaRequest;
import com.ferreteria.compras.dto.DetallePedidoCompraResponse;
import com.ferreteria.compras.service.DetallePedidoCompraService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/detalles-pedidos-compras")
@Tag(name = "Detalles de pedidos de compra", description = "Líneas asociadas a pedidos de compra")
public class DetallePedidoCompraController {

    private final DetallePedidoCompraService service;

    public DetallePedidoCompraController(DetallePedidoCompraService service) {
        this.service = service;
    }

    @GetMapping
    public Page<DetallePedidoCompraResponse> findAll(
            @RequestParam(required = false) Long idPedido,
            Pageable pageable) {
        return service.findAll(idPedido, pageable);
    }

    @GetMapping("/{id}")
    public DetallePedidoCompraResponse findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DetallePedidoCompraResponse create(
            @Valid @RequestBody DetallePedidoCompraAltaRequest request) {
        return service.create(request);
    }
}
