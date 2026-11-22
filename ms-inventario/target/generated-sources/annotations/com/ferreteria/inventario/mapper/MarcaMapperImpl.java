package com.ferreteria.inventario.mapper;

import com.ferreteria.commons.dto.MarcaDTO;
import com.ferreteria.entity.general.Marca;
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
public class MarcaMapperImpl implements MarcaMapper {

    @Override
    public MarcaDTO toDto(Marca entity) {
        if ( entity == null ) {
            return null;
        }

        MarcaDTO marcaDTO = new MarcaDTO();

        marcaDTO.setIdMarca( entity.getIdMarca() );
        marcaDTO.setNombre( entity.getNombre() );

        return marcaDTO;
    }

    @Override
    public List<MarcaDTO> toDtoList(List<Marca> entities) {
        if ( entities == null ) {
            return null;
        }

        List<MarcaDTO> list = new ArrayList<MarcaDTO>( entities.size() );
        for ( Marca marca : entities ) {
            list.add( toDto( marca ) );
        }

        return list;
    }

    @Override
    public List<Marca> toEntityList(List<MarcaDTO> dtos) {
        if ( dtos == null ) {
            return null;
        }

        List<Marca> list = new ArrayList<Marca>( dtos.size() );
        for ( MarcaDTO marcaDTO : dtos ) {
            list.add( toEntity( marcaDTO ) );
        }

        return list;
    }

    @Override
    public Marca toEntity(MarcaDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Marca marca = new Marca();

        marca.setNombre( dto.getNombre() );

        return marca;
    }

    @Override
    public void updateEntityFromDto(MarcaDTO dto, Marca entity) {
        if ( dto == null ) {
            return;
        }

        entity.setNombre( dto.getNombre() );
    }
}
