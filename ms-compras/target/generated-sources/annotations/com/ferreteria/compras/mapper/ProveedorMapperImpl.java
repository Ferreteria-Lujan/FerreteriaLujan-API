package com.ferreteria.compras.mapper;

import com.ferreteria.commons.dto.ProveedorDTO;
import com.ferreteria.entity.compras.Proveedor;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-11-22T20:46:35-0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 25.0.1 (Oracle Corporation)"
)
@Component
public class ProveedorMapperImpl implements ProveedorMapper {

    @Override
    public ProveedorDTO toDto(Proveedor entity) {
        if ( entity == null ) {
            return null;
        }

        ProveedorDTO proveedorDTO = new ProveedorDTO();

        proveedorDTO.setIdPersona( entity.getIdPersona() );
        proveedorDTO.setNombre( entity.getNombre() );
        proveedorDTO.setApellido( entity.getApellido() );
        proveedorDTO.setRuc( entity.getRuc() );
        proveedorDTO.setDireccion( entity.getDireccion() );
        proveedorDTO.setTelefono( entity.getTelefono() );
        proveedorDTO.setCorreo( entity.getCorreo() );
        proveedorDTO.setTipoPersona( entity.getTipoPersona() );
        proveedorDTO.setFechaNacimiento( entity.getFechaNacimiento() );
        proveedorDTO.setPlazoEntrega( entity.getPlazoEntrega() );

        return proveedorDTO;
    }

    @Override
    public List<ProveedorDTO> toDtoList(List<Proveedor> entities) {
        if ( entities == null ) {
            return null;
        }

        List<ProveedorDTO> list = new ArrayList<ProveedorDTO>( entities.size() );
        for ( Proveedor proveedor : entities ) {
            list.add( toDto( proveedor ) );
        }

        return list;
    }

    @Override
    public List<Proveedor> toEntityList(List<ProveedorDTO> dtos) {
        if ( dtos == null ) {
            return null;
        }

        List<Proveedor> list = new ArrayList<Proveedor>( dtos.size() );
        for ( ProveedorDTO proveedorDTO : dtos ) {
            list.add( toEntity( proveedorDTO ) );
        }

        return list;
    }

    @Override
    public Proveedor toEntity(ProveedorDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Proveedor proveedor = new Proveedor();

        proveedor.setNombre( dto.getNombre() );
        proveedor.setApellido( dto.getApellido() );
        proveedor.setRuc( dto.getRuc() );
        proveedor.setDireccion( dto.getDireccion() );
        proveedor.setTelefono( dto.getTelefono() );
        proveedor.setCorreo( dto.getCorreo() );
        proveedor.setTipoPersona( dto.getTipoPersona() );
        proveedor.setFechaNacimiento( dto.getFechaNacimiento() );
        proveedor.setPlazoEntrega( dto.getPlazoEntrega() );

        return proveedor;
    }

    @Override
    public void updateEntityFromDto(ProveedorDTO dto, Proveedor entity) {
        if ( dto == null ) {
            return;
        }

        entity.setNombre( dto.getNombre() );
        entity.setApellido( dto.getApellido() );
        entity.setRuc( dto.getRuc() );
        entity.setDireccion( dto.getDireccion() );
        entity.setTelefono( dto.getTelefono() );
        entity.setCorreo( dto.getCorreo() );
        entity.setTipoPersona( dto.getTipoPersona() );
        entity.setFechaNacimiento( dto.getFechaNacimiento() );
        entity.setPlazoEntrega( dto.getPlazoEntrega() );
    }
}
