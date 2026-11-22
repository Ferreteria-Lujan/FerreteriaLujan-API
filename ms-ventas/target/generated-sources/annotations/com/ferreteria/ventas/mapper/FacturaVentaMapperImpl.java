package com.ferreteria.ventas.mapper;

import com.ferreteria.commons.dto.FacturaVentaDTO;
import com.ferreteria.entity.general.Estado;
import com.ferreteria.entity.ventas.Cliente;
import com.ferreteria.entity.ventas.FacturaVenta;
import com.ferreteria.entity.ventas.Presupuesto;
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
public class FacturaVentaMapperImpl implements FacturaVentaMapper {

    @Override
    public List<FacturaVentaDTO> toDtoList(List<FacturaVenta> entities) {
        if ( entities == null ) {
            return null;
        }

        List<FacturaVentaDTO> list = new ArrayList<FacturaVentaDTO>( entities.size() );
        for ( FacturaVenta facturaVenta : entities ) {
            list.add( toDto( facturaVenta ) );
        }

        return list;
    }

    @Override
    public List<FacturaVenta> toEntityList(List<FacturaVentaDTO> dtos) {
        if ( dtos == null ) {
            return null;
        }

        List<FacturaVenta> list = new ArrayList<FacturaVenta>( dtos.size() );
        for ( FacturaVentaDTO facturaVentaDTO : dtos ) {
            list.add( toEntity( facturaVentaDTO ) );
        }

        return list;
    }

    @Override
    public FacturaVentaDTO toDto(FacturaVenta entity) {
        if ( entity == null ) {
            return null;
        }

        FacturaVentaDTO facturaVentaDTO = new FacturaVentaDTO();

        facturaVentaDTO.setIdCliente( entityClienteIdPersona( entity ) );
        facturaVentaDTO.setIdPresupuesto( entityPresupuestoIdPresupuesto( entity ) );
        facturaVentaDTO.setIdEstado( entityEstadoIdEstado( entity ) );
        facturaVentaDTO.setIdFactura( entity.getIdFactura() );
        facturaVentaDTO.setNroFactura( entity.getNroFactura() );
        if ( entity.getTimbrado() != null ) {
            facturaVentaDTO.setTimbrado( entity.getTimbrado().doubleValue() );
        }
        facturaVentaDTO.setFechaEmision( entity.getFechaEmision() );
        if ( entity.getImporteTotal() != null ) {
            facturaVentaDTO.setImporteTotal( entity.getImporteTotal().doubleValue() );
        }
        if ( entity.getIva() != null ) {
            facturaVentaDTO.setIva( entity.getIva().doubleValue() );
        }
        facturaVentaDTO.setFechaVencimiento( entity.getFechaVencimiento() );
        facturaVentaDTO.setEsCredito( entity.getEsCredito() );
        facturaVentaDTO.setCodigoFactura( entity.getCodigoFactura() );

        return facturaVentaDTO;
    }

    @Override
    public FacturaVenta toEntity(FacturaVentaDTO dto) {
        if ( dto == null ) {
            return null;
        }

        FacturaVenta facturaVenta = new FacturaVenta();

        facturaVenta.setCliente( clienteFromId( dto.getIdCliente() ) );
        facturaVenta.setPresupuesto( presupuestoFromId( dto.getIdPresupuesto() ) );
        facturaVenta.setEstado( estadoFromId( dto.getIdEstado() ) );
        facturaVenta.setNroFactura( dto.getNroFactura() );
        if ( dto.getTimbrado() != null ) {
            facturaVenta.setTimbrado( BigDecimal.valueOf( dto.getTimbrado() ) );
        }
        facturaVenta.setFechaEmision( dto.getFechaEmision() );
        if ( dto.getImporteTotal() != null ) {
            facturaVenta.setImporteTotal( BigDecimal.valueOf( dto.getImporteTotal() ) );
        }
        if ( dto.getIva() != null ) {
            facturaVenta.setIva( BigDecimal.valueOf( dto.getIva() ) );
        }
        facturaVenta.setFechaVencimiento( dto.getFechaVencimiento() );
        facturaVenta.setEsCredito( dto.getEsCredito() );
        facturaVenta.setCodigoFactura( dto.getCodigoFactura() );

        return facturaVenta;
    }

    @Override
    public void updateEntityFromDto(FacturaVentaDTO dto, FacturaVenta entity) {
        if ( dto == null ) {
            return;
        }

        entity.setCliente( clienteFromId( dto.getIdCliente() ) );
        entity.setPresupuesto( presupuestoFromId( dto.getIdPresupuesto() ) );
        if ( dto.getIdEstado() != null ) {
            entity.setEstado( estadoFromId( dto.getIdEstado() ) );
        }
        entity.setNroFactura( dto.getNroFactura() );
        if ( dto.getTimbrado() != null ) {
            entity.setTimbrado( BigDecimal.valueOf( dto.getTimbrado() ) );
        }
        else {
            entity.setTimbrado( null );
        }
        entity.setFechaEmision( dto.getFechaEmision() );
        if ( dto.getImporteTotal() != null ) {
            entity.setImporteTotal( BigDecimal.valueOf( dto.getImporteTotal() ) );
        }
        else {
            entity.setImporteTotal( null );
        }
        if ( dto.getIva() != null ) {
            entity.setIva( BigDecimal.valueOf( dto.getIva() ) );
        }
        else {
            entity.setIva( null );
        }
        entity.setFechaVencimiento( dto.getFechaVencimiento() );
        entity.setEsCredito( dto.getEsCredito() );
        entity.setCodigoFactura( dto.getCodigoFactura() );
    }

    private Long entityClienteIdPersona(FacturaVenta facturaVenta) {
        Cliente cliente = facturaVenta.getCliente();
        if ( cliente == null ) {
            return null;
        }
        return cliente.getIdPersona();
    }

    private Long entityPresupuestoIdPresupuesto(FacturaVenta facturaVenta) {
        Presupuesto presupuesto = facturaVenta.getPresupuesto();
        if ( presupuesto == null ) {
            return null;
        }
        return presupuesto.getIdPresupuesto();
    }

    private Long entityEstadoIdEstado(FacturaVenta facturaVenta) {
        Estado estado = facturaVenta.getEstado();
        if ( estado == null ) {
            return null;
        }
        return estado.getIdEstado();
    }
}
