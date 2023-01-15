package com.unknown.cfdi.mappers;

import com.unknown.cfdi.mappers.auxiliars.ParteAuxiliarMapper;
import com.unknown.cfdi.modelos.Parte;
import com.unknown.models.generated.Comprobante;
import org.mapstruct.Mapper;

@Mapper(config = IgnoreUnmappedMapperConfig.class, uses = ParteAuxiliarMapper.class)
public interface ParteMapper {

  Comprobante.Conceptos.Concepto.Parte mapParteCfidToComprobanteParte(Parte parte);

  Parte mapComprobanteParteToCFDIParte(Comprobante.Conceptos.Concepto.Parte parte);
}
