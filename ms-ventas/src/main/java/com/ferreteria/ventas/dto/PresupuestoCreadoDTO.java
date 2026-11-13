package com.ferreteria.ventas.dto;

import java.util.List;

import com.ferreteria.commons.dto.PresupuestoDTO;
import com.ferreteria.commons.dto.PresupuestoDetalleDTO;

public record PresupuestoCreadoDTO(PresupuestoDTO presupuesto, List<PresupuestoDetalleDTO> detalles) {
}