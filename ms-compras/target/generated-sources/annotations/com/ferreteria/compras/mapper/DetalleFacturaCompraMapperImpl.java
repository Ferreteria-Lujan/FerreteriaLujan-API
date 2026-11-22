package com.ferreteria.compras.mapper;

import com.ferreteria.commons.dto.DetalleFacturaCompraDTO;
import com.ferreteria.entity.compras.DetalleFacturaCompra;
import com.ferreteria.entity.compras.FacturaCompra;
import com.ferreteria.entity.general.Producto;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-11-22T20:46:36-0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 25.0.1 (Oracle Corporation)"
)
@Component
public class DetalleFacturaCompraMapperImpl implements DetalleFacturaCompraMapper {

    @Override
    public List<DetalleFacturaCompraDTO> toDtoList(List<DetalleFacturaCompra> entities) {
        if ( entities == null ) {
            return null;
        }

        List<DetalleFacturaCompraDTO> list = new ArrayList<DetalleFacturaCompraDTO>( entities.size() );
        for ( DetalleFacturaCompra detalleFacturaCompra : entities ) {
            list.add( toDto( detalleFacturaCompra ) );
        }

        return list;
    }

    @Override
    public List<DetalleFacturaCompra> toEntityList(List<DetalleFacturaCompraDTO> dtos) {
        if ( dtos == null ) {
            return null;
        }

        List<DetalleFacturaCompra> list = new ArrayList<DetalleFacturaCompra>( dtos.size() );
        for ( DetalleFacturaCompraDTO detalleFacturaCompraDTO : dtos ) {
            list.add( toEntity( detalleFacturaCompraDTO ) );
        }

        return list;
    }

    @Override
    public DetalleFacturaCompraDTO toDto(DetalleFacturaCompra entity) {
        if ( entity == null ) {
            return null;
        }

        DetalleFacturaCompraDTO detalleFacturaCompraDTO = new DetalleFacturaCompraDTO();

        detalleFacturaCompraDTO.setIdFacturaCompra( entityFacturaCompraIdFacturaCompra( entity ) );
        detalleFacturaCompraDTO.setIdProducto( entityProductoIdProducto( entity ) );
        detalleFacturaCompraDTO.setId( entity.getId() );
        detalleFacturaCompraDTO.setCantidad( entity.getCantidad() );
        if ( entity.getPrecioUnitario() != null ) {
            detalleFacturaCompraDTO.setPrecioUnitario( entity.getPrecioUnitario().doubleValue() );
        }
        detalleFacturaCompraDTO.setPorcentajeIva( entity.getPorcentajeIva() );
        if ( entity.getMontoIva() != null ) {
            detalleFacturaCompraDTO.setMontoIva( entity.getMontoIva().doubleValue() );
        }
        if ( entity.getSubtotal() != null ) {
            detalleFacturaCompraDTO.setSubtotal( entity.getSubtotal().doubleValue() );
        }

        return detalleFacturaCompraDTO;
    }

    @Override
    public DetalleFacturaCompra toEntity(DetalleFacturaCompraDTO dto) {
        if ( dto == null ) {
            return null;
        }

        DetalleFacturaCompra detalleFacturaCompra = new DetalleFacturaCompra();

        detalleFacturaCompra.setFacturaCompra( facturaFromId( dto.getIdFacturaCompra() ) );
        detalleFacturaCompra.setProducto( productoFromId( dto.getIdProducto() ) );
        detalleFacturaCompra.setCantidad( dto.getCantidad() );
        if ( dto.getPrecioUnitario() != null ) {
            detalleFacturaCompra.setPrecioUnitario( BigDecimal.valueOf( dto.getPrecioUnitario() ) );
        }
        detalleFacturaCompra.setPorcentajeIva( dto.getPorcentajeIva() );
        if ( dto.getMontoIva() != null ) {
            detalleFacturaCompra.setMontoIva( BigDecimal.valueOf( dto.getMontoIva() ) );
        }
        if ( dto.getSubtotal() != null ) {
            detalleFacturaCompra.setSubtotal( BigDecimal.valueOf( dto.getSubtotal() ) );
        }

        return detalleFacturaCompra;
    }

    @Override
    public void updateEntityFromDto(DetalleFacturaCompraDTO dto, DetalleFacturaCompra entity) {
        if ( dto == null ) {
            return;
        }

        entity.setFacturaCompra( facturaFromId( dto.getIdFacturaCompra() ) );
        entity.setProducto( productoFromId( dto.getIdProducto() ) );
        entity.setCantidad( dto.getCantidad() );
        if ( dto.getPrecioUnitario() != null ) {
            entity.setPrecioUnitario( BigDecimal.valueOf( dto.getPrecioUnitario() ) );
        }
        else {
            entity.setPrecioUnitario( null );
        }
        entity.setPorcentajeIva( dto.getPorcentajeIva() );
        if ( dto.getMontoIva() != null ) {
            entity.setMontoIva( BigDecimal.valueOf( dto.getMontoIva() ) );
        }
        else {
            entity.setMontoIva( null );
        }
        if ( dto.getSubtotal() != null ) {
            entity.setSubtotal( BigDecimal.valueOf( dto.getSubtotal() ) );
        }
        else {
            entity.setSubtotal( null );
        }
    }

    private Long entityFacturaCompraIdFacturaCompra(DetalleFacturaCompra detalleFacturaCompra) {
        FacturaCompra facturaCompra = detalleFacturaCompra.getFacturaCompra();
        if ( facturaCompra == null ) {
            return null;
        }
        return facturaCompra.getIdFacturaCompra();
    }

    private Long entityProductoIdProducto(DetalleFacturaCompra detalleFacturaCompra) {
        Producto producto = detalleFacturaCompra.getProducto();
        if ( producto == null ) {
            return null;
        }
        return producto.getIdProducto();
    }
}
