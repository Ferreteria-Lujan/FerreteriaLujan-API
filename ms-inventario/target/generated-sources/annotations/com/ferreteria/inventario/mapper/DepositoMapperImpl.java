package com.ferreteria.inventario.mapper;

import com.ferreteria.commons.dto.DepositoDTO;
import com.ferreteria.entity.Inventario.Deposito;
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
public class DepositoMapperImpl implements DepositoMapper {

    @Override
    public DepositoDTO toDto(Deposito entity) {
        if ( entity == null ) {
            return null;
        }

        DepositoDTO depositoDTO = new DepositoDTO();

        depositoDTO.setIdDeposito( entity.getIdDeposito() );
        depositoDTO.setNombre( entity.getNombre() );

        return depositoDTO;
    }

    @Override
    public List<DepositoDTO> toDtoList(List<Deposito> entities) {
        if ( entities == null ) {
            return null;
        }

        List<DepositoDTO> list = new ArrayList<DepositoDTO>( entities.size() );
        for ( Deposito deposito : entities ) {
            list.add( toDto( deposito ) );
        }

        return list;
    }

    @Override
    public List<Deposito> toEntityList(List<DepositoDTO> dtos) {
        if ( dtos == null ) {
            return null;
        }

        List<Deposito> list = new ArrayList<Deposito>( dtos.size() );
        for ( DepositoDTO depositoDTO : dtos ) {
            list.add( toEntity( depositoDTO ) );
        }

        return list;
    }

    @Override
    public Deposito toEntity(DepositoDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Deposito deposito = new Deposito();

        deposito.setNombre( dto.getNombre() );

        return deposito;
    }

    @Override
    public void updateEntityFromDto(DepositoDTO dto, Deposito entity) {
        if ( dto == null ) {
            return;
        }

        entity.setNombre( dto.getNombre() );
    }
}
