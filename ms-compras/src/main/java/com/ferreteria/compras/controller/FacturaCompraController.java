package com.ferreteria.compras.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;

import com.ferreteria.commons.dto.FacturaCompraDTO;
import com.ferreteria.compras.filter.FacturaCompraFilter;
import com.ferreteria.compras.service.FacturaCompraService;
import com.ferreteria.compras.dto.RegistroCompraRequest;
import com.ferreteria.compras.service.RegistroCompraService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@RestController
@RequestMapping("/api/facturas-compras")
@Tag(name = "Facturas de compra", description = "Facturas de compra a proveedores (DELETE = borrado logico)")
public class FacturaCompraController
        extends AbstractBaseController<FacturaCompraDTO, FacturaCompraFilter, Long, FacturaCompraService> {

    private final RegistroCompraService registroCompraService;

    public FacturaCompraController(FacturaCompraService service, RegistroCompraService registroCompraService) {
        super(service);
        this.registroCompraService = registroCompraService;
    }

    @PostMapping("/registrar")
    @ResponseStatus(HttpStatus.CREATED)
    public FacturaCompraDTO registrar(@Valid @RequestBody RegistroCompraRequest request) {
        return registroCompraService.registrar(request);
    }
}
