package com.unknown.cfdi.mappers;

import com.unknown.cfdi.mappers.auxiliars.ImpuestosAuxiliarMapper;
import com.unknown.cfdi.modelos.Traslado;
import com.unknown.models.generated.Comprobante;
import org.mapstruct.Mapper;

@Mapper(config = IgnoreUnmappedMapperConfig.class, uses = ImpuestosAuxiliarMapper.class)
public interface TrasladoMapper {

  Traslado mapTrasladoComprobanteToCfdi(Comprobante.Impuestos.Traslados.Traslado traslado);
}
