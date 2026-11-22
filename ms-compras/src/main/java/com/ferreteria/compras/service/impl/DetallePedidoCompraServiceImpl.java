package com.ferreteria.compras.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ferreteria.compras.dto.DetallePedidoCompraAltaRequest;
import com.ferreteria.compras.dto.DetallePedidoCompraResponse;
import com.ferreteria.compras.exception.BusinessRuleException;
import com.ferreteria.compras.exception.ResourceNotFoundException;
import com.ferreteria.compras.service.DetallePedidoCompraService;

@Service
public class DetallePedidoCompraServiceImpl implements DetallePedidoCompraService {

    private static final String SELECT = """
            SELECT d.id_detalle_pedido, d.id_pedido, d.id_producto, p.codigo,
                   COALESCE(p.nombre, p.descripcion, 'Producto ' || d.id_producto) AS descripcion,
                   d.cantidad, d.precio_unitario, d.subtotal
              FROM detalles_pedidos_compras d
              JOIN productos p ON p.id_producto = d.id_producto
            """;

    private final JdbcTemplate jdbc;

    public DetallePedidoCompraServiceImpl(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DetallePedidoCompraResponse> findAll(Long idPedido, Pageable pageable) {
        String filtro = idPedido == null ? "" : " WHERE d.id_pedido = ?";
        String countSql = "SELECT COUNT(*) FROM detalles_pedidos_compras d" + filtro;
        Long total = idPedido == null
                ? jdbc.queryForObject(countSql, Long.class)
                : jdbc.queryForObject(countSql, Long.class, idPedido);
        String sql = SELECT + filtro + " ORDER BY d.id_detalle_pedido LIMIT ? OFFSET ?";
        List<DetallePedidoCompraResponse> filas = idPedido == null
                ? jdbc.query(sql, this::map, pageable.getPageSize(), pageable.getOffset())
                : jdbc.query(sql, this::map, idPedido, pageable.getPageSize(), pageable.getOffset());
        return new PageImpl<>(filas, pageable, total == null ? 0 : total);
    }

    @Override
    @Transactional(readOnly = true)
    public DetallePedidoCompraResponse findById(Long id) {
        List<DetallePedidoCompraResponse> filas = jdbc.query(
                SELECT + " WHERE d.id_detalle_pedido = ?", this::map, id);
        if (filas.isEmpty()) {
            throw new ResourceNotFoundException("Detalle de pedido no encontrado: " + id);
        }
        return filas.getFirst();
    }

    @Override
    @Transactional
    public DetallePedidoCompraResponse create(DetallePedidoCompraAltaRequest request) {
        validarPedidoEditable(request.idPedido());
        validarProducto(request.idProducto());
        BigDecimal subtotal = request.precioUnitario()
                .multiply(BigDecimal.valueOf(request.cantidad()));
        Long id = jdbc.queryForObject("""
                INSERT INTO detalles_pedidos_compras
                    (id_pedido, id_producto, cantidad, precio_unitario, subtotal)
                VALUES (?, ?, ?, ?, ?)
                RETURNING id_detalle_pedido
                """, Long.class, request.idPedido(), request.idProducto(),
                request.cantidad(), request.precioUnitario(), subtotal);
        jdbc.update("""
                UPDATE pedidos_compras
                   SET total = (
                       SELECT COALESCE(SUM(subtotal), 0)
                         FROM detalles_pedidos_compras
                        WHERE id_pedido = ?
                   )
                 WHERE id_pedido = ?
                """, request.idPedido(), request.idPedido());
        return findById(id);
    }

    private void validarPedidoEditable(Long idPedido) {
        List<String> estados = jdbc.query("""
                SELECT COALESCE(e.nombre, '')
                  FROM pedidos_compras p
                  LEFT JOIN estados e ON e.id_estado = p.id_estado
                 WHERE p.id_pedido = ?
                 FOR UPDATE OF p
                """, (rs, row) -> rs.getString(1), idPedido);
        if (estados.isEmpty()) {
            throw new ResourceNotFoundException("Pedido de compra no encontrado: " + idPedido);
        }
        if ("Recibido".equalsIgnoreCase(estados.getFirst())) {
            throw new BusinessRuleException("No se pueden agregar productos a un pedido recibido");
        }
    }

    private void validarProducto(Long idProducto) {
        Long cantidad = jdbc.queryForObject(
                "SELECT COUNT(*) FROM productos WHERE id_producto = ?",
                Long.class, idProducto);
        if (cantidad == null || cantidad == 0) {
            throw new ResourceNotFoundException("Producto no encontrado: " + idProducto);
        }
    }

    private DetallePedidoCompraResponse map(java.sql.ResultSet rs, int row) throws java.sql.SQLException {
        return new DetallePedidoCompraResponse(
                rs.getLong("id_detalle_pedido"),
                rs.getLong("id_pedido"),
                rs.getLong("id_producto"),
                rs.getString("codigo"),
                rs.getString("descripcion"),
                rs.getInt("cantidad"),
                rs.getBigDecimal("precio_unitario"),
                rs.getBigDecimal("subtotal"));
    }
}
