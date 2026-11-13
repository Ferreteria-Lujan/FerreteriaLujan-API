package com.ferreteria.ventas.service;

import com.ferreteria.entity.general.Estado;

/**
 * Servicio de apoyo (sin controller): resuelve el estado "inactivo" que usan los borrados logicos
 * de presupuestos y facturas. Es el unico lugar que consulta la tabla estados.
 */
public interface EstadoService {
    Long getInactivoId();
    Estado getInactivo();

    /** Id de un estado por su nombre (sin distinguir mayusculas); 400 si no existe. */
    Long getIdPorNombre(String nombre);
}
