package com.unknown.cfdi.mappers;

import com.unknown.cfdi.mappers.auxiliars.ImpuestosAuxiliarMapper;
import com.unknown.cfdi.modelos.Retencion;
import com.unknown.models.generated.Comprobante;
import org.mapstruct.Mapper;

@Mapper(config = IgnoreUnmappedMapperConfig.class, uses = ImpuestosAuxiliarMapper.class)
public interface RetencionMapper {

  Retencion mapRetencionComprobanteToCfdi(Comprobante.Impuestos.Retenciones.Retencion retencion);
}
