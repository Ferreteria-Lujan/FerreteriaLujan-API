package com.ferreteria.inventario.mapper;

import com.ferreteria.commons.dto.CategoriaProductoDTO;
import com.ferreteria.entity.general.CategoriaProducto;
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
public class CategoriaProductoMapperImpl implements CategoriaProductoMapper {

    @Override
    public CategoriaProductoDTO toDto(CategoriaProducto entity) {
        if ( entity == null ) {
            return null;
        }

        CategoriaProductoDTO categoriaProductoDTO = new CategoriaProductoDTO();

        categoriaProductoDTO.setIdCategoria( entity.getIdCategoria() );
        categoriaProductoDTO.setNombre( entity.getNombre() );
        categoriaProductoDTO.setDescripcion( entity.getDescripcion() );

        return categoriaProductoDTO;
    }

    @Override
    public List<CategoriaProductoDTO> toDtoList(List<CategoriaProducto> entities) {
        if ( entities == null ) {
            return null;
        }

        List<CategoriaProductoDTO> list = new ArrayList<CategoriaProductoDTO>( entities.size() );
        for ( CategoriaProducto categoriaProducto : entities ) {
            list.add( toDto( categoriaProducto ) );
        }

        return list;
    }

    @Override
    public List<CategoriaProducto> toEntityList(List<CategoriaProductoDTO> dtos) {
        if ( dtos == null ) {
            return null;
        }

        List<CategoriaProducto> list = new ArrayList<CategoriaProducto>( dtos.size() );
        for ( CategoriaProductoDTO categoriaProductoDTO : dtos ) {
            list.add( toEntity( categoriaProductoDTO ) );
        }

        return list;
    }

    @Override
    public CategoriaProducto toEntity(CategoriaProductoDTO dto) {
        if ( dto == null ) {
            return null;
        }

        CategoriaProducto categoriaProducto = new CategoriaProducto();

        categoriaProducto.setNombre( dto.getNombre() );
        categoriaProducto.setDescripcion( dto.getDescripcion() );

        return categoriaProducto;
    }

    @Override
    public void updateEntityFromDto(CategoriaProductoDTO dto, CategoriaProducto entity) {
        if ( dto == null ) {
            return;
        }

        entity.setNombre( dto.getNombre() );
        entity.setDescripcion( dto.getDescripcion() );
    }
}
