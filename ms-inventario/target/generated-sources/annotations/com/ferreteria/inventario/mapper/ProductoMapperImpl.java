package com.ferreteria.inventario.mapper;

import com.ferreteria.commons.dto.ProductoDTO;
import com.ferreteria.entity.general.CategoriaProducto;
import com.ferreteria.entity.general.Marca;
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
public class ProductoMapperImpl implements ProductoMapper {

    @Override
    public List<ProductoDTO> toDtoList(List<Producto> entities) {
        if ( entities == null ) {
            return null;
        }

        List<ProductoDTO> list = new ArrayList<ProductoDTO>( entities.size() );
        for ( Producto producto : entities ) {
            list.add( toDto( producto ) );
        }

        return list;
    }

    @Override
    public List<Producto> toEntityList(List<ProductoDTO> dtos) {
        if ( dtos == null ) {
            return null;
        }

        List<Producto> list = new ArrayList<Producto>( dtos.size() );
        for ( ProductoDTO productoDTO : dtos ) {
            list.add( toEntity( productoDTO ) );
        }

        return list;
    }

    @Override
    public ProductoDTO toDto(Producto entity) {
        if ( entity == null ) {
            return null;
        }

        ProductoDTO productoDTO = new ProductoDTO();

        productoDTO.setIdMarca( entityMarcaIdMarca( entity ) );
        productoDTO.setIdCategoria( entityCategoriaIdCategoria( entity ) );
        productoDTO.setIdProducto( entity.getIdProducto() );
        productoDTO.setNombre( entity.getNombre() );
        productoDTO.setCodigo( entity.getCodigo() );
        productoDTO.setDescripcion( entity.getDescripcion() );
        if ( entity.getPrecioCompra() != null ) {
            productoDTO.setPrecioCompra( entity.getPrecioCompra().doubleValue() );
        }
        if ( entity.getPrecioVenta() != null ) {
            productoDTO.setPrecioVenta( entity.getPrecioVenta().doubleValue() );
        }

        return productoDTO;
    }

    @Override
    public Producto toEntity(ProductoDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Producto producto = new Producto();

        producto.setMarca( marcaFromId( dto.getIdMarca() ) );
        producto.setCategoria( categoriaFromId( dto.getIdCategoria() ) );
        producto.setNombre( dto.getNombre() );
        producto.setCodigo( dto.getCodigo() );
        producto.setDescripcion( dto.getDescripcion() );
        if ( dto.getPrecioCompra() != null ) {
            producto.setPrecioCompra( BigDecimal.valueOf( dto.getPrecioCompra() ) );
        }
        if ( dto.getPrecioVenta() != null ) {
            producto.setPrecioVenta( BigDecimal.valueOf( dto.getPrecioVenta() ) );
        }

        return producto;
    }

    @Override
    public void updateEntityFromDto(ProductoDTO dto, Producto entity) {
        if ( dto == null ) {
            return;
        }

        entity.setMarca( marcaFromId( dto.getIdMarca() ) );
        entity.setCategoria( categoriaFromId( dto.getIdCategoria() ) );
        entity.setNombre( dto.getNombre() );
        entity.setCodigo( dto.getCodigo() );
        entity.setDescripcion( dto.getDescripcion() );
        if ( dto.getPrecioCompra() != null ) {
            entity.setPrecioCompra( BigDecimal.valueOf( dto.getPrecioCompra() ) );
        }
        else {
            entity.setPrecioCompra( null );
        }
        if ( dto.getPrecioVenta() != null ) {
            entity.setPrecioVenta( BigDecimal.valueOf( dto.getPrecioVenta() ) );
        }
        else {
            entity.setPrecioVenta( null );
        }
    }

    private Long entityMarcaIdMarca(Producto producto) {
        Marca marca = producto.getMarca();
        if ( marca == null ) {
            return null;
        }
        return marca.getIdMarca();
    }

    private Long entityCategoriaIdCategoria(Producto producto) {
        CategoriaProducto categoria = producto.getCategoria();
        if ( categoria == null ) {
            return null;
        }
        return categoria.getIdCategoria();
    }
}
