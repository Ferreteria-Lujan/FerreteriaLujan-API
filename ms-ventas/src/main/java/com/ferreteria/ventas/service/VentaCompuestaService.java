package com.ferreteria.ventas.service;

import com.ferreteria.ventas.dto.FacturaVentaConDetallesDTO;
import com.ferreteria.ventas.dto.FacturaVentaCreadaDTO;
import com.ferreteria.ventas.dto.PresupuestoConDetallesDTO;
import com.ferreteria.ventas.dto.PresupuestoCreadoDTO;

/**
 * Operaciones cabecera + detalle que antes armaba el cliente con varias peticiones. Ahora entran
 * en una sola peticion y corren en una unica transaccion: si una linea falla, se deshace todo (no
 * quedan cabeceras huerfanas y no hace falta un DELETE posterior, que en estos recursos es borrado
 * logico y exige rol ADMIN).
 */
public interface VentaCompuestaService {

    /** Crea la factura y todas sus lineas. Total, IVA y estado los calcula el servidor. */
    FacturaVentaCreadaDTO crearFacturaConDetalles(FacturaVentaConDetallesDTO dto);

    /** Crea el presupuesto y todas sus lineas. Nace en estado "Pendiente". */
    PresupuestoCreadoDTO crearPresupuestoConDetalles(PresupuestoConDetallesDTO dto);

    /**
     * Genera la factura de venta a partir de un presupuesto (copia sus lineas) y marca el
     * presupuesto como "Convertido". Falla sin crear nada si ya fue convertido o no tiene lineas.
     */
    FacturaVentaCreadaDTO generarFacturaDesdePresupuesto(Long idPresupuesto);
}