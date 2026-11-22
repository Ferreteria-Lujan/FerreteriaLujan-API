package com.ferreteria.inventario.mapper;

import com.ferreteria.commons.dto.TransferenciaDTO;
import com.ferreteria.entity.Inventario.Deposito;
import com.ferreteria.entity.Inventario.Transferencia;
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
public class TransferenciaMapperImpl implements TransferenciaMapper {

    @Override
    public List<TransferenciaDTO> toDtoList(List<Transferencia> entities) {
        if ( entities == null ) {
            return null;
        }

        List<TransferenciaDTO> list = new ArrayList<TransferenciaDTO>( entities.size() );
        for ( Transferencia transferencia : entities ) {
            list.add( toDto( transferencia ) );
        }

        return list;
    }

    @Override
    public List<Transferencia> toEntityList(List<TransferenciaDTO> dtos) {
        if ( dtos == null ) {
            return null;
        }

        List<Transferencia> list = new ArrayList<Transferencia>( dtos.size() );
        for ( TransferenciaDTO transferenciaDTO : dtos ) {
            list.add( toEntity( transferenciaDTO ) );
        }

        return list;
    }

    @Override
    public TransferenciaDTO toDto(Transferencia entity) {
        if ( entity == null ) {
            return null;
        }

        TransferenciaDTO transferenciaDTO = new TransferenciaDTO();

        transferenciaDTO.setIdDepositoOrigen( entityDepositoOrigenIdDeposito( entity ) );
        transferenciaDTO.setIdDepositoDestino( entityDepositoDestinoIdDeposito( entity ) );
        transferenciaDTO.setIdTransferencia( entity.getIdTransferencia() );
        transferenciaDTO.setFechaCreacion( entity.getFechaCreacion() );
        transferenciaDTO.setFechaEstimada( entity.getFechaEstimada() );
        transferenciaDTO.setCreador( entity.getCreador() );

        return transferenciaDTO;
    }

    @Override
    public Transferencia toEntity(TransferenciaDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Transferencia transferencia = new Transferencia();

        transferencia.setDepositoOrigen( depositoFromId( dto.getIdDepositoOrigen() ) );
        transferencia.setDepositoDestino( depositoFromId( dto.getIdDepositoDestino() ) );
        transferencia.setFechaCreacion( dto.getFechaCreacion() );
        transferencia.setFechaEstimada( dto.getFechaEstimada() );
        transferencia.setCreador( dto.getCreador() );

        return transferencia;
    }

    @Override
    public void updateEntityFromDto(TransferenciaDTO dto, Transferencia entity) {
        if ( dto == null ) {
            return;
        }

        entity.setDepositoOrigen( depositoFromId( dto.getIdDepositoOrigen() ) );
        entity.setDepositoDestino( depositoFromId( dto.getIdDepositoDestino() ) );
        entity.setFechaCreacion( dto.getFechaCreacion() );
        entity.setFechaEstimada( dto.getFechaEstimada() );
        entity.setCreador( dto.getCreador() );
    }

    private Long entityDepositoOrigenIdDeposito(Transferencia transferencia) {
        Deposito depositoOrigen = transferencia.getDepositoOrigen();
        if ( depositoOrigen == null ) {
            return null;
        }
        return depositoOrigen.getIdDeposito();
    }

    private Long entityDepositoDestinoIdDeposito(Transferencia transferencia) {
        Deposito depositoDestino = transferencia.getDepositoDestino();
        if ( depositoDestino == null ) {
            return null;
        }
        return depositoDestino.getIdDeposito();
    }
}
