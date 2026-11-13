package com.ferreteria.ventas.repository;

import org.springframework.data.jpa.repository.Query;

import com.ferreteria.entity.ventas.FacturaVenta;

/**
 * Repositorio de FacturaVenta. Hereda CRUD, paginacion y Specification de {@link BaseRepository}.
 */
public interface FacturaVentaRepository extends BaseRepository<FacturaVenta, Long> {

    /** Mayor nro de factura con formato 001-001-0000000 (el relleno con ceros hace que el orden de texto sea el numerico). */
    @Query("select max(f.nroFactura) from FacturaVenta f where f.nroFactura like '___-___-_______'")
    String findUltimoNroFactura();
}