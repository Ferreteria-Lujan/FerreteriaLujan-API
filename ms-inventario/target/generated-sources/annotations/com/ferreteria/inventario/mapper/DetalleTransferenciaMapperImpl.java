package com.ferreteria.inventario.mapper;

import com.ferreteria.commons.dto.DetalleTransferenciaDTO;
import com.ferreteria.entity.Inventario.Detalle_transferencia;
import com.ferreteria.entity.Inventario.Transferencia;
import com.ferreteria.entity.general.Producto;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-11-22T20:47:06-0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 25.0.1 (Oracle Corporation)"
)
@Component
public class DetalleTransferenciaMapperImpl implements DetalleTransferenciaMapper {

    @Override
    public List<DetalleTransferenciaDTO> toDtoList(List<Detalle_transferencia> entities) {
        if ( entities == null ) {
            return null;
        }

        List<DetalleTransferenciaDTO> list = new ArrayList<DetalleTransferenciaDTO>( entities.size() );
        for ( Detalle_transferencia detalle_transferencia : entities ) {
            list.add( toDto( detalle_transferencia ) );
        }

        return list;
    }

    @Override
    public List<Detalle_transferencia> toEntityList(List<DetalleTransferenciaDTO> dtos) {
        if ( dtos == null ) {
            return null;
        }

        List<Detalle_transferencia> list = new ArrayList<Detalle_transferencia>( dtos.size() );
        for ( DetalleTransferenciaDTO detalleTransferenciaDTO : dtos ) {
            list.add( toEntity( detalleTransferenciaDTO ) );
        }

        return list;
    }

    @Override
    public DetalleTransferenciaDTO toDto(Detalle_transferencia entity) {
        if ( entity == null ) {
            return null;
        }

        DetalleTransferenciaDTO detalleTransferenciaDTO = new DetalleTransferenciaDTO();

        detalleTransferenciaDTO.setIdTransferencia( entityTransferenciaIdTransferencia( entity ) );
        detalleTransferenciaDTO.setIdProducto( entityProductoIdProducto( entity ) );
        detalleTransferenciaDTO.setIdDetTrans( entity.getIdDetTrans() );
        if ( entity.getCantidad() != null ) {
            detalleTransferenciaDTO.setCantidad( entity.getCantidad().doubleValue() );
        }

        return detalleTransferenciaDTO;
    }

    @Override
    public Detalle_transferencia toEntity(DetalleTransferenciaDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Detalle_transferencia detalle_transferencia = new Detalle_transferencia();

        detalle_transferencia.setTransferencia( transferenciaFromId( dto.getIdTransferencia() ) );
        detalle_transferencia.setProducto( productoFromId( dto.getIdProducto() ) );
        if ( dto.getCantidad() != null ) {
            detalle_transferencia.setCantidad( BigDecimal.valueOf( dto.getCantidad() ) );
        }

        return detalle_transferencia;
    }

    @Override
    public void updateEntityFromDto(DetalleTransferenciaDTO dto, Detalle_transferencia entity) {
        if ( dto == null ) {
            return;
        }

        entity.setTransferencia( transferenciaFromId( dto.getIdTransferencia() ) );
        entity.setProducto( productoFromId( dto.getIdProducto() ) );
        if ( dto.getCantidad() != null ) {
            entity.setCantidad( BigDecimal.valueOf( dto.getCantidad() ) );
        }
        else {
            entity.setCantidad( null );
        }
    }

    private Long entityTransferenciaIdTransferencia(Detalle_transferencia detalle_transferencia) {
        Transferencia transferencia = detalle_transferencia.getTransferencia();
        if ( transferencia == null ) {
            return null;
        }
        return transferencia.getIdTransferencia();
    }

    private Long entityProductoIdProducto(Detalle_transferencia detalle_transferencia) {
        Producto producto = detalle_transferencia.getProducto();
        if ( producto == null ) {
            return null;
        }
        return producto.getIdProducto();
    }
}
