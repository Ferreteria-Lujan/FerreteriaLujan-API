package com.ferreteria.compras.service.impl;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ferreteria.compras.dto.DetallePedidoCompraRequest;
import com.ferreteria.compras.dto.DetallePedidoCompraResponse;
import com.ferreteria.compras.dto.PedidoCompraRequest;
import com.ferreteria.compras.dto.PedidoCompraResponse;
import com.ferreteria.compras.dto.RegistroCompraRequest;
import com.ferreteria.compras.exception.BusinessRuleException;
import com.ferreteria.compras.exception.DuplicateResourceException;
import com.ferreteria.compras.exception.ResourceNotFoundException;
import com.ferreteria.compras.service.PedidoCompraService;
import com.ferreteria.compras.service.RegistroCompraService;

@Service
public class PedidoCompraServiceImpl implements PedidoCompraService {

    private static final String PEDIDO_SELECT = """
            SELECT p.id_pedido, p.nro_pedido, p.id_proveedor, p.fecha_emision, p.fecha_recepcion,
                   p.total, p.pagado, p.id_estado, e.nombre AS nombre_estado,
                   f.id_factura_compra, pv.id_persona AS id_proveedor_persona
              FROM pedidos_compras p
              JOIN proveedores pv ON pv.id_proveedor = p.id_proveedor
              LEFT JOIN estados e ON e.id_estado = p.id_estado
              LEFT JOIN facturas_compras f ON f.id_pedido = p.id_pedido
            """;

    private final JdbcTemplate jdbc;
    private final RegistroCompraService registroCompraService;

    public PedidoCompraServiceImpl(
            JdbcTemplate jdbc,
            RegistroCompraService registroCompraService) {
        this.jdbc = jdbc;
        this.registroCompraService = registroCompraService;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PedidoCompraResponse> findAll(Pageable pageable) {
        long total = jdbc.queryForObject("SELECT COUNT(*) FROM pedidos_compras", Long.class);
        String direccion = pageable.getSort().stream().findFirst()
                .map(orden -> orden.isAscending() ? "ASC" : "DESC")
                .orElse("DESC");
        String sql = PEDIDO_SELECT + " ORDER BY p.fecha_emision " + direccion + ", p.id_pedido DESC LIMIT ? OFFSET ?";
        List<PedidoCompraResponse> pedidos = jdbc.query(
                sql, pedidoMapper(), pageable.getPageSize(), pageable.getOffset());
        return new PageImpl<>(pedidos, pageable, total);
    }

    @Override
    @Transactional(readOnly = true)
    public PedidoCompraResponse findById(Long id) {
        return buscarPedido(id, false);
    }

    @Override
    @Transactional
    public PedidoCompraResponse create(PedidoCompraRequest request) {
        Long idProveedorDb = idProveedorDb(request.idProveedor());
        Long estadoEspera = idEstado("En espera");
        LocalDate emision = request.fechaEmision() == null ? LocalDate.now() : request.fechaEmision();
        BigDecimal total = request.detalles().stream()
                .map(item -> item.precioUnitario().multiply(BigDecimal.valueOf(item.cantidad())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        try {
            Long id = jdbc.queryForObject("""
                    INSERT INTO pedidos_compras
                        (nro_pedido, id_proveedor, fecha_emision, total, pagado, id_estado)
                    VALUES (?, ?, ?, ?, false, ?)
                    RETURNING id_pedido
                    """, Long.class, request.nroPedido(), idProveedorDb, Date.valueOf(emision), total, estadoEspera);
            for (DetallePedidoCompraRequest detalle : request.detalles()) {
                validarProducto(detalle.idProducto());
                jdbc.update("""
                        INSERT INTO detalles_pedidos_compras
                            (id_pedido, id_producto, cantidad, precio_unitario, subtotal)
                        VALUES (?, ?, ?, ?, ?)
                        """, id, detalle.idProducto(), detalle.cantidad(), detalle.precioUnitario(),
                        detalle.precioUnitario().multiply(BigDecimal.valueOf(detalle.cantidad())));
            }
            return buscarPedido(id, false);
        } catch (DuplicateKeyException ex) {
            throw new DuplicateResourceException("Ya existe el pedido " + request.nroPedido());
        }
    }

    @Override
    @Transactional
    public PedidoCompraResponse marcarPagado(Long id) {
        buscarPedido(id, true);
        jdbc.update("UPDATE pedidos_compras SET pagado = true WHERE id_pedido = ?", id);
        return buscarPedido(id, false);
    }

    @Override
    @Transactional
    public PedidoCompraResponse recibir(Long id) {
        PedidoCompraResponse pedido = buscarPedido(id, true);
        if ("Recibido".equalsIgnoreCase(pedido.nombreEstado())) {
            return pedido;
        }
        if (pedido.detalles().isEmpty()) {
            throw new BusinessRuleException("El pedido no puede recibirse sin productos");
        }

        Long facturaId = pedido.idFacturaCompra();
        if (facturaId == null) {
            List<DetallePedidoCompraRequest> detalles = pedido.detalles().stream()
                    .map(item -> new DetallePedidoCompraRequest(
                            item.idProducto(), item.cantidad(), item.precioUnitario()))
                    .toList();
            facturaId = registroCompraService.registrar(new RegistroCompraRequest(
                    pedido.idProveedor(),
                    "PED-" + pedido.idPedido(),
                    LocalDate.now(),
                    null,
                    pedido.idPedido(),
                    detalles)).getIdFacturaCompra();
        }

        jdbc.update("""
                UPDATE pedidos_compras
                   SET fecha_recepcion = CURRENT_DATE, pagado = true, id_estado = ?
                 WHERE id_pedido = ?
                """, idEstado("Recibido"), id);
        return buscarPedido(id, false);
    }

    private PedidoCompraResponse buscarPedido(Long id, boolean bloquear) {
        String sql = PEDIDO_SELECT + " WHERE p.id_pedido = ?" + (bloquear ? " FOR UPDATE OF p" : "");
        List<PedidoCompraResponse> resultados = jdbc.query(sql, pedidoMapper(), id);
        if (resultados.isEmpty()) {
            throw new ResourceNotFoundException("Pedido de compra no encontrado: " + id);
        }
        return resultados.getFirst();
    }

    private RowMapper<PedidoCompraResponse> pedidoMapper() {
        return (rs, row) -> {
            Long id = rs.getLong("id_pedido");
            return new PedidoCompraResponse(
                    id,
                    rs.getString("nro_pedido"),
                    rs.getLong("id_proveedor_persona"),
                    rs.getDate("fecha_emision").toLocalDate(),
                    localDate(rs, "fecha_recepcion"),
                    rs.getBigDecimal("total"),
                    rs.getBoolean("pagado"),
                    nullableLong(rs, "id_estado"),
                    rs.getString("nombre_estado"),
                    nullableLong(rs, "id_factura_compra"),
                    detalles(id));
        };
    }

    private List<DetallePedidoCompraResponse> detalles(Long idPedido) {
        return jdbc.query("""
                SELECT d.id_detalle_pedido, d.id_pedido, d.id_producto, p.codigo,
                       COALESCE(p.nombre, p.descripcion, 'Producto ' || d.id_producto) AS descripcion,
                       d.cantidad, d.precio_unitario, d.subtotal
                  FROM detalles_pedidos_compras d
                  JOIN productos p ON p.id_producto = d.id_producto
                 WHERE d.id_pedido = ?
                 ORDER BY d.id_detalle_pedido
                """, (rs, row) -> new DetallePedidoCompraResponse(
                rs.getLong("id_detalle_pedido"),
                rs.getLong("id_pedido"),
                rs.getLong("id_producto"),
                rs.getString("codigo"),
                rs.getString("descripcion"),
                rs.getInt("cantidad"),
                rs.getBigDecimal("precio_unitario"),
                rs.getBigDecimal("subtotal")), idPedido);
    }

    private Long idEstado(String nombre) {
        List<Long> ids = jdbc.query(
                "SELECT id_estado FROM estados WHERE lower(nombre) = lower(?) ORDER BY id_estado LIMIT 1",
                (rs, row) -> rs.getLong(1), nombre);
        if (ids.isEmpty()) {
            throw new BusinessRuleException("Falta configurar el estado '" + nombre + "' en la tabla estados");
        }
        return ids.getFirst();
    }

    private Long idProveedorDb(Long idPersona) {
        List<Long> ids = jdbc.query("""
                SELECT id_proveedor
                  FROM proveedores
                 WHERE id_persona = ? OR id_proveedor = ?
                 ORDER BY CASE WHEN id_persona = ? THEN 0 ELSE 1 END
                 LIMIT 1
                """, (rs, row) -> rs.getLong(1), idPersona, idPersona, idPersona);
        if (ids.isEmpty()) {
            throw new ResourceNotFoundException("Proveedor no encontrado: " + idPersona);
        }
        return ids.getFirst();
    }

    private void validarProducto(Long id) {
        validarExiste("SELECT COUNT(*) FROM productos WHERE id_producto = ?", id,
                "Producto no encontrado: " + id);
    }

    private void validarExiste(String sql, Object arg, String mensaje) {
        validarExiste(sql, arg, null, mensaje);
    }

    private void validarExiste(String sql, Object arg1, Object arg2, String mensaje) {
        Long cantidad = arg2 == null
                ? jdbc.queryForObject(sql, Long.class, arg1)
                : jdbc.queryForObject(sql, Long.class, arg1, arg2);
        if (cantidad == null || cantidad == 0) {
            throw new ResourceNotFoundException(mensaje);
        }
    }

    private static Long nullableLong(ResultSet rs, String columna) throws SQLException {
        long valor = rs.getLong(columna);
        return rs.wasNull() ? null : valor;
    }

    private static LocalDate localDate(ResultSet rs, String columna) throws SQLException {
        Date fecha = rs.getDate(columna);
        return fecha == null ? null : fecha.toLocalDate();
    }
}
