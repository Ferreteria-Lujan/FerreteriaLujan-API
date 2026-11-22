package com.ferreteria.compras.mapper;

import com.ferreteria.commons.dto.FacturaCompraDTO;
import com.ferreteria.entity.compras.FacturaCompra;
import com.ferreteria.entity.compras.Proveedor;
import com.ferreteria.entity.general.Estado;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-11-22T20:46:36-0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 25.0.1 (Oracle Corporation)"
)
@Component
public class FacturaCompraMapperImpl implements FacturaCompraMapper {

    @Override
    public List<FacturaCompraDTO> toDtoList(List<FacturaCompra> entities) {
        if ( entities == null ) {
            return null;
        }

        List<FacturaCompraDTO> list = new ArrayList<FacturaCompraDTO>( entities.size() );
        for ( FacturaCompra facturaCompra : entities ) {
            list.add( toDto( facturaCompra ) );
        }

        return list;
    }

    @Override
    public List<FacturaCompra> toEntityList(List<FacturaCompraDTO> dtos) {
        if ( dtos == null ) {
            return null;
        }

        List<FacturaCompra> list = new ArrayList<FacturaCompra>( dtos.size() );
        for ( FacturaCompraDTO facturaCompraDTO : dtos ) {
            list.add( toEntity( facturaCompraDTO ) );
        }

        return list;
    }

    @Override
    public FacturaCompraDTO toDto(FacturaCompra entity) {
        if ( entity == null ) {
            return null;
        }

        FacturaCompraDTO facturaCompraDTO = new FacturaCompraDTO();

        facturaCompraDTO.setIdProveedor( entityProveedorIdPersona( entity ) );
        facturaCompraDTO.setIdEstado( entityEstadoIdEstado( entity ) );
        facturaCompraDTO.setIdFacturaCompra( entity.getIdFacturaCompra() );
        if ( entity.getTimbrado() != null ) {
            facturaCompraDTO.setTimbrado( entity.getTimbrado().doubleValue() );
        }
        facturaCompraDTO.setNroFactura( entity.getNroFactura() );
        facturaCompraDTO.setFechaEmision( entity.getFechaEmision() );
        if ( entity.getImporteTotal() != null ) {
            facturaCompraDTO.setImporteTotal( entity.getImporteTotal().doubleValue() );
        }
        facturaCompraDTO.setFechaVencimiento( entity.getFechaVencimiento() );
        facturaCompraDTO.setCodigoFactura( entity.getCodigoFactura() );

        return facturaCompraDTO;
    }

    @Override
    public FacturaCompra toEntity(FacturaCompraDTO dto) {
        if ( dto == null ) {
            return null;
        }

        FacturaCompra facturaCompra = new FacturaCompra();

        facturaCompra.setProveedor( proveedorFromId( dto.getIdProveedor() ) );
        facturaCompra.setEstado( estadoFromId( dto.getIdEstado() ) );
        if ( dto.getTimbrado() != null ) {
            facturaCompra.setTimbrado( BigDecimal.valueOf( dto.getTimbrado() ) );
        }
        facturaCompra.setNroFactura( dto.getNroFactura() );
        facturaCompra.setFechaEmision( dto.getFechaEmision() );
        facturaCompra.setFechaVencimiento( dto.getFechaVencimiento() );
        facturaCompra.setCodigoFactura( dto.getCodigoFactura() );

        return facturaCompra;
    }

    @Override
    public void updateEntityFromDto(FacturaCompraDTO dto, FacturaCompra entity) {
        if ( dto == null ) {
            return;
        }

        entity.setProveedor( proveedorFromId( dto.getIdProveedor() ) );
        if ( dto.getIdEstado() != null ) {
            entity.setEstado( estadoFromId( dto.getIdEstado() ) );
        }
        if ( dto.getTimbrado() != null ) {
            entity.setTimbrado( BigDecimal.valueOf( dto.getTimbrado() ) );
        }
        else {
            entity.setTimbrado( null );
        }
        entity.setNroFactura( dto.getNroFactura() );
        entity.setFechaEmision( dto.getFechaEmision() );
        entity.setFechaVencimiento( dto.getFechaVencimiento() );
        entity.setCodigoFactura( dto.getCodigoFactura() );
    }

    private Long entityProveedorIdPersona(FacturaCompra facturaCompra) {
        Proveedor proveedor = facturaCompra.getProveedor();
        if ( proveedor == null ) {
            return null;
        }
        return proveedor.getIdPersona();
    }

    private Long entityEstadoIdEstado(FacturaCompra facturaCompra) {
        Estado estado = facturaCompra.getEstado();
        if ( estado == null ) {
            return null;
        }
        return estado.getIdEstado();
    }
}
