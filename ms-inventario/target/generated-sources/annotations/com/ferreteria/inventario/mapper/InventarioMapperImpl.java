package com.ferreteria.inventario.mapper;

import com.ferreteria.commons.dto.InventarioDTO;
import com.ferreteria.entity.Inventario.Deposito;
import com.ferreteria.entity.Inventario.Inventario;
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
public class InventarioMapperImpl implements InventarioMapper {

    @Override
    public List<InventarioDTO> toDtoList(List<Inventario> entities) {
        if ( entities == null ) {
            return null;
        }

        List<InventarioDTO> list = new ArrayList<InventarioDTO>( entities.size() );
        for ( Inventario inventario : entities ) {
            list.add( toDto( inventario ) );
        }

        return list;
    }

    @Override
    public List<Inventario> toEntityList(List<InventarioDTO> dtos) {
        if ( dtos == null ) {
            return null;
        }

        List<Inventario> list = new ArrayList<Inventario>( dtos.size() );
        for ( InventarioDTO inventarioDTO : dtos ) {
            list.add( toEntity( inventarioDTO ) );
        }

        return list;
    }

    @Override
    public InventarioDTO toDto(Inventario entity) {
        if ( entity == null ) {
            return null;
        }

        InventarioDTO inventarioDTO = new InventarioDTO();

        inventarioDTO.setIdDeposito( entityDepositoIdDeposito( entity ) );
        inventarioDTO.setIdProducto( entityProductoIdProducto( entity ) );
        inventarioDTO.setId( entity.getId() );
        if ( entity.getCantidad() != null ) {
            inventarioDTO.setCantidad( entity.getCantidad().doubleValue() );
        }
        if ( entity.getStockMinimo() != null ) {
            inventarioDTO.setStockMinimo( entity.getStockMinimo().doubleValue() );
        }
        if ( entity.getStockMaximo() != null ) {
            inventarioDTO.setStockMaximo( entity.getStockMaximo().doubleValue() );
        }

        return inventarioDTO;
    }

    @Override
    public Inventario toEntity(InventarioDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Inventario inventario = new Inventario();

        inventario.setDeposito( depositoFromId( dto.getIdDeposito() ) );
        inventario.setProducto( productoFromId( dto.getIdProducto() ) );
        if ( dto.getCantidad() != null ) {
            inventario.setCantidad( BigDecimal.valueOf( dto.getCantidad() ) );
        }
        if ( dto.getStockMinimo() != null ) {
            inventario.setStockMinimo( BigDecimal.valueOf( dto.getStockMinimo() ) );
        }
        if ( dto.getStockMaximo() != null ) {
            inventario.setStockMaximo( BigDecimal.valueOf( dto.getStockMaximo() ) );
        }

        return inventario;
    }

    @Override
    public void updateEntityFromDto(InventarioDTO dto, Inventario entity) {
        if ( dto == null ) {
            return;
        }

        entity.setDeposito( depositoFromId( dto.getIdDeposito() ) );
        entity.setProducto( productoFromId( dto.getIdProducto() ) );
        if ( dto.getCantidad() != null ) {
            entity.setCantidad( BigDecimal.valueOf( dto.getCantidad() ) );
        }
        else {
            entity.setCantidad( null );
        }
        if ( dto.getStockMinimo() != null ) {
            entity.setStockMinimo( BigDecimal.valueOf( dto.getStockMinimo() ) );
        }
        else {
            entity.setStockMinimo( null );
        }
        if ( dto.getStockMaximo() != null ) {
            entity.setStockMaximo( BigDecimal.valueOf( dto.getStockMaximo() ) );
        }
        else {
            entity.setStockMaximo( null );
        }
    }

    private Long entityDepositoIdDeposito(Inventario inventario) {
        Deposito deposito = inventario.getDeposito();
        if ( deposito == null ) {
            return null;
        }
        return deposito.getIdDeposito();
    }

    private Long entityProductoIdProducto(Inventario inventario) {
        Producto producto = inventario.getProducto();
        if ( producto == null ) {
            return null;
        }
        return producto.getIdProducto();
    }
}
