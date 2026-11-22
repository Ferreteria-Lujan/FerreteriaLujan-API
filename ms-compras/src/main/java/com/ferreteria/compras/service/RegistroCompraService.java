package com.ferreteria.compras.service;

import com.ferreteria.commons.dto.FacturaCompraDTO;
import com.ferreteria.compras.dto.RegistroCompraRequest;

public interface RegistroCompraService {

    FacturaCompraDTO registrar(RegistroCompraRequest request);
}
