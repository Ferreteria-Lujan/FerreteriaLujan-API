package com.ferreteria.ventas.mapper;

import com.ferreteria.commons.dto.PresupuestoDetalleDTO;
import com.ferreteria.entity.general.Producto;
import com.ferreteria.entity.ventas.Presupuesto;
import com.ferreteria.entity.ventas.PresupuestoDetalle;
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
public class PresupuestoDetalleMapperImpl implements PresupuestoDetalleMapper {

    @Override
    public List<PresupuestoDetalleDTO> toDtoList(List<PresupuestoDetalle> entities) {
        if ( entities == null ) {
            return null;
        }

        List<PresupuestoDetalleDTO> list = new ArrayList<PresupuestoDetalleDTO>( entities.size() );
        for ( PresupuestoDetalle presupuestoDetalle : entities ) {
            list.add( toDto( presupuestoDetalle ) );
        }

        return list;
    }

    @Override
    public List<PresupuestoDetalle> toEntityList(List<PresupuestoDetalleDTO> dtos) {
        if ( dtos == null ) {
            return null;
        }

        List<PresupuestoDetalle> list = new ArrayList<PresupuestoDetalle>( dtos.size() );
        for ( PresupuestoDetalleDTO presupuestoDetalleDTO : dtos ) {
            list.add( toEntity( presupuestoDetalleDTO ) );
        }

        return list;
    }

    @Override
    public PresupuestoDetalleDTO toDto(PresupuestoDetalle entity) {
        if ( entity == null ) {
            return null;
        }

        PresupuestoDetalleDTO presupuestoDetalleDTO = new PresupuestoDetalleDTO();

        presupuestoDetalleDTO.setIdPresupuesto( entityPresupuestoIdPresupuesto( entity ) );
        presupuestoDetalleDTO.setIdProducto( entityProductoIdProducto( entity ) );
        presupuestoDetalleDTO.setIdPresupuestoDetalle( entity.getIdPresupuestoDetalle() );
        if ( entity.getCantidad() != null ) {
            presupuestoDetalleDTO.setCantidad( entity.getCantidad().doubleValue() );
        }
        if ( entity.getPrecioUnitario() != null ) {
            presupuestoDetalleDTO.setPrecioUnitario( entity.getPrecioUnitario().doubleValue() );
        }

        return presupuestoDetalleDTO;
    }

    @Override
    public PresupuestoDetalle toEntity(PresupuestoDetalleDTO dto) {
        if ( dto == null ) {
            return null;
        }

        PresupuestoDetalle presupuestoDetalle = new PresupuestoDetalle();

        presupuestoDetalle.setPresupuesto( presupuestoFromId( dto.getIdPresupuesto() ) );
        presupuestoDetalle.setProducto( productoFromId( dto.getIdProducto() ) );
        if ( dto.getCantidad() != null ) {
            presupuestoDetalle.setCantidad( BigDecimal.valueOf( dto.getCantidad() ) );
        }
        if ( dto.getPrecioUnitario() != null ) {
            presupuestoDetalle.setPrecioUnitario( BigDecimal.valueOf( dto.getPrecioUnitario() ) );
        }

        return presupuestoDetalle;
    }

    @Override
    public void updateEntityFromDto(PresupuestoDetalleDTO dto, PresupuestoDetalle entity) {
        if ( dto == null ) {
            return;
        }

        entity.setPresupuesto( presupuestoFromId( dto.getIdPresupuesto() ) );
        entity.setProducto( productoFromId( dto.getIdProducto() ) );
        if ( dto.getCantidad() != null ) {
            entity.setCantidad( BigDecimal.valueOf( dto.getCantidad() ) );
        }
        else {
            entity.setCantidad( null );
        }
        if ( dto.getPrecioUnitario() != null ) {
            entity.setPrecioUnitario( BigDecimal.valueOf( dto.getPrecioUnitario() ) );
        }
        else {
            entity.setPrecioUnitario( null );
        }
    }

    private Long entityPresupuestoIdPresupuesto(PresupuestoDetalle presupuestoDetalle) {
        Presupuesto presupuesto = presupuestoDetalle.getPresupuesto();
        if ( presupuesto == null ) {
            return null;
        }
        return presupuesto.getIdPresupuesto();
    }

    private Long entityProductoIdProducto(PresupuestoDetalle presupuestoDetalle) {
        Producto producto = presupuestoDetalle.getProducto();
        if ( producto == null ) {
            return null;
        }
        return producto.getIdProducto();
    }
}
