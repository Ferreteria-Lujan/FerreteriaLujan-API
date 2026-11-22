package com.ferreteria.ventas.mapper;

import com.ferreteria.commons.dto.DetalleFacturaVentaDTO;
import com.ferreteria.entity.general.Producto;
import com.ferreteria.entity.ventas.DetalleFacturaVenta;
import com.ferreteria.entity.ventas.FacturaVenta;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-11-22T20:46:48-0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 25.0.1 (Oracle Corporation)"
)
@Component
public class DetalleFacturaVentaMapperImpl implements DetalleFacturaVentaMapper {

    @Override
    public List<DetalleFacturaVentaDTO> toDtoList(List<DetalleFacturaVenta> entities) {
        if ( entities == null ) {
            return null;
        }

        List<DetalleFacturaVentaDTO> list = new ArrayList<DetalleFacturaVentaDTO>( entities.size() );
        for ( DetalleFacturaVenta detalleFacturaVenta : entities ) {
            list.add( toDto( detalleFacturaVenta ) );
        }

        return list;
    }

    @Override
    public List<DetalleFacturaVenta> toEntityList(List<DetalleFacturaVentaDTO> dtos) {
        if ( dtos == null ) {
            return null;
        }

        List<DetalleFacturaVenta> list = new ArrayList<DetalleFacturaVenta>( dtos.size() );
        for ( DetalleFacturaVentaDTO detalleFacturaVentaDTO : dtos ) {
            list.add( toEntity( detalleFacturaVentaDTO ) );
        }

        return list;
    }

    @Override
    public DetalleFacturaVentaDTO toDto(DetalleFacturaVenta entity) {
        if ( entity == null ) {
            return null;
        }

        DetalleFacturaVentaDTO detalleFacturaVentaDTO = new DetalleFacturaVentaDTO();

        detalleFacturaVentaDTO.setIdProducto( entityProductoIdProducto( entity ) );
        detalleFacturaVentaDTO.setIdFactura( entityFacturaIdFactura( entity ) );
        detalleFacturaVentaDTO.setId( entity.getId() );
        detalleFacturaVentaDTO.setCantidad( entity.getCantidad() );
        if ( entity.getPrecioUnitario() != null ) {
            detalleFacturaVentaDTO.setPrecioUnitario( entity.getPrecioUnitario().doubleValue() );
        }

        return detalleFacturaVentaDTO;
    }

    @Override
    public DetalleFacturaVenta toEntity(DetalleFacturaVentaDTO dto) {
        if ( dto == null ) {
            return null;
        }

        DetalleFacturaVenta detalleFacturaVenta = new DetalleFacturaVenta();

        detalleFacturaVenta.setProducto( productoFromId( dto.getIdProducto() ) );
        detalleFacturaVenta.setFactura( facturaFromId( dto.getIdFactura() ) );
        detalleFacturaVenta.setCantidad( dto.getCantidad() );
        if ( dto.getPrecioUnitario() != null ) {
            detalleFacturaVenta.setPrecioUnitario( BigDecimal.valueOf( dto.getPrecioUnitario() ) );
        }

        return detalleFacturaVenta;
    }

    @Override
    public void updateEntityFromDto(DetalleFacturaVentaDTO dto, DetalleFacturaVenta entity) {
        if ( dto == null ) {
            return;
        }

        entity.setProducto( productoFromId( dto.getIdProducto() ) );
        entity.setFactura( facturaFromId( dto.getIdFactura() ) );
        entity.setCantidad( dto.getCantidad() );
        if ( dto.getPrecioUnitario() != null ) {
            entity.setPrecioUnitario( BigDecimal.valueOf( dto.getPrecioUnitario() ) );
        }
        else {
            entity.setPrecioUnitario( null );
        }
    }

    private Long entityProductoIdProducto(DetalleFacturaVenta detalleFacturaVenta) {
        Producto producto = detalleFacturaVenta.getProducto();
        if ( producto == null ) {
            return null;
        }
        return producto.getIdProducto();
    }

    private Long entityFacturaIdFactura(DetalleFacturaVenta detalleFacturaVenta) {
        FacturaVenta factura = detalleFacturaVenta.getFactura();
        if ( factura == null ) {
            return null;
        }
        return factura.getIdFactura();
    }
}
