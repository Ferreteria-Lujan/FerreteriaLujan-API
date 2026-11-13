package com.ferreteria.ventas.dto;

import java.util.List;

import com.ferreteria.commons.dto.DetalleFacturaVentaDTO;
import com.ferreteria.commons.dto.FacturaVentaDTO;

/** Respuesta de la creacion compuesta: la factura ya guardada junto con sus lineas ya guardadas. */
public record FacturaVentaCreadaDTO(FacturaVentaDTO factura, List<DetalleFacturaVentaDTO> detalles) {
}