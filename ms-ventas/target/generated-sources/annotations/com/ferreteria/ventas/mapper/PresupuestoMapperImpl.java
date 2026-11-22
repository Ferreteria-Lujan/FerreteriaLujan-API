package com.ferreteria.ventas.mapper;

import com.ferreteria.commons.dto.PresupuestoDTO;
import com.ferreteria.entity.general.Estado;
import com.ferreteria.entity.ventas.Cliente;
import com.ferreteria.entity.ventas.Presupuesto;
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
public class PresupuestoMapperImpl implements PresupuestoMapper {

    @Override
    public List<PresupuestoDTO> toDtoList(List<Presupuesto> entities) {
        if ( entities == null ) {
            return null;
        }

        List<PresupuestoDTO> list = new ArrayList<PresupuestoDTO>( entities.size() );
        for ( Presupuesto presupuesto : entities ) {
            list.add( toDto( presupuesto ) );
        }

        return list;
    }

    @Override
    public List<Presupuesto> toEntityList(List<PresupuestoDTO> dtos) {
        if ( dtos == null ) {
            return null;
        }

        List<Presupuesto> list = new ArrayList<Presupuesto>( dtos.size() );
        for ( PresupuestoDTO presupuestoDTO : dtos ) {
            list.add( toEntity( presupuestoDTO ) );
        }

        return list;
    }

    @Override
    public PresupuestoDTO toDto(Presupuesto entity) {
        if ( entity == null ) {
            return null;
        }

        PresupuestoDTO presupuestoDTO = new PresupuestoDTO();

        presupuestoDTO.setIdCliente( entityClienteIdPersona( entity ) );
        presupuestoDTO.setIdEstado( entityEstadoIdEstado( entity ) );
        presupuestoDTO.setIdPresupuesto( entity.getIdPresupuesto() );
        presupuestoDTO.setFecha( entity.getFecha() );

        return presupuestoDTO;
    }

    @Override
    public Presupuesto toEntity(PresupuestoDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Presupuesto presupuesto = new Presupuesto();

        presupuesto.setCliente( clienteFromId( dto.getIdCliente() ) );
        presupuesto.setEstado( estadoFromId( dto.getIdEstado() ) );
        presupuesto.setFecha( dto.getFecha() );

        return presupuesto;
    }

    @Override
    public void updateEntityFromDto(PresupuestoDTO dto, Presupuesto entity) {
        if ( dto == null ) {
            return;
        }

        entity.setCliente( clienteFromId( dto.getIdCliente() ) );
        if ( dto.getIdEstado() != null ) {
            entity.setEstado( estadoFromId( dto.getIdEstado() ) );
        }
        entity.setFecha( dto.getFecha() );
    }

    private Long entityClienteIdPersona(Presupuesto presupuesto) {
        Cliente cliente = presupuesto.getCliente();
        if ( cliente == null ) {
            return null;
        }
        return cliente.getIdPersona();
    }

    private Long entityEstadoIdEstado(Presupuesto presupuesto) {
        Estado estado = presupuesto.getEstado();
        if ( estado == null ) {
            return null;
        }
        return estado.getIdEstado();
    }
}
