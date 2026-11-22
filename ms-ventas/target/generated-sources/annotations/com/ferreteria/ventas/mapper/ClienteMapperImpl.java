package com.ferreteria.ventas.mapper;

import com.ferreteria.commons.dto.ClienteDTO;
import com.ferreteria.entity.ventas.Cliente;
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
public class ClienteMapperImpl implements ClienteMapper {

    @Override
    public List<ClienteDTO> toDtoList(List<Cliente> entities) {
        if ( entities == null ) {
            return null;
        }

        List<ClienteDTO> list = new ArrayList<ClienteDTO>( entities.size() );
        for ( Cliente cliente : entities ) {
            list.add( toDto( cliente ) );
        }

        return list;
    }

    @Override
    public List<Cliente> toEntityList(List<ClienteDTO> dtos) {
        if ( dtos == null ) {
            return null;
        }

        List<Cliente> list = new ArrayList<Cliente>( dtos.size() );
        for ( ClienteDTO clienteDTO : dtos ) {
            list.add( toEntity( clienteDTO ) );
        }

        return list;
    }

    @Override
    public ClienteDTO toDto(Cliente entity) {
        if ( entity == null ) {
            return null;
        }

        ClienteDTO clienteDTO = new ClienteDTO();

        clienteDTO.setIdPersona( entity.getIdPersona() );
        clienteDTO.setNombre( entity.getNombre() );
        clienteDTO.setApellido( entity.getApellido() );
        clienteDTO.setRuc( entity.getRuc() );
        clienteDTO.setDireccion( entity.getDireccion() );
        clienteDTO.setTelefono( entity.getTelefono() );
        clienteDTO.setCorreo( entity.getCorreo() );
        clienteDTO.setTipoPersona( entity.getTipoPersona() );
        clienteDTO.setFechaNacimiento( entity.getFechaNacimiento() );
        clienteDTO.setCi( entity.getCi() );
        if ( entity.getLimiteSaldo() != null ) {
            clienteDTO.setLimiteSaldo( entity.getLimiteSaldo().doubleValue() );
        }
        if ( entity.getSaldoActual() != null ) {
            clienteDTO.setSaldoActual( entity.getSaldoActual().doubleValue() );
        }

        return clienteDTO;
    }

    @Override
    public Cliente toEntity(ClienteDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Cliente cliente = new Cliente();

        if ( dto.getSaldoActual() != null ) {
            cliente.setSaldoActual( BigDecimal.valueOf( dto.getSaldoActual() ) );
        }
        else {
            cliente.setSaldoActual( new BigDecimal( "0" ) );
        }
        cliente.setNombre( dto.getNombre() );
        cliente.setApellido( dto.getApellido() );
        cliente.setRuc( dto.getRuc() );
        cliente.setDireccion( dto.getDireccion() );
        cliente.setTelefono( dto.getTelefono() );
        cliente.setCorreo( dto.getCorreo() );
        cliente.setTipoPersona( dto.getTipoPersona() );
        cliente.setFechaNacimiento( dto.getFechaNacimiento() );
        if ( dto.getLimiteSaldo() != null ) {
            cliente.setLimiteSaldo( BigDecimal.valueOf( dto.getLimiteSaldo() ) );
        }
        cliente.setCi( dto.getCi() );

        return cliente;
    }

    @Override
    public void updateEntityFromDto(ClienteDTO dto, Cliente entity) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getSaldoActual() != null ) {
            entity.setSaldoActual( BigDecimal.valueOf( dto.getSaldoActual() ) );
        }
        entity.setNombre( dto.getNombre() );
        entity.setApellido( dto.getApellido() );
        entity.setRuc( dto.getRuc() );
        entity.setDireccion( dto.getDireccion() );
        entity.setTelefono( dto.getTelefono() );
        entity.setCorreo( dto.getCorreo() );
        entity.setTipoPersona( dto.getTipoPersona() );
        entity.setFechaNacimiento( dto.getFechaNacimiento() );
        if ( dto.getLimiteSaldo() != null ) {
            entity.setLimiteSaldo( BigDecimal.valueOf( dto.getLimiteSaldo() ) );
        }
        else {
            entity.setLimiteSaldo( null );
        }
        entity.setCi( dto.getCi() );
    }
}
