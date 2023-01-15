package com.unknown.cfdi.mappers;

import com.unknown.cfdi.mappers.auxiliars.ImpuestosAuxiliarMapper;
import com.unknown.cfdi.modelos.TrasladoConcepto;
import com.unknown.models.generated.Comprobante;
import org.mapstruct.Mapper;

@Mapper(config = IgnoreUnmappedMapperConfig.class, uses = ImpuestosAuxiliarMapper.class)
public interface TrasladoImpuestoMapper {

  Comprobante.Impuestos.Traslados.Traslado mapTrasladoCfdiToComprobante(
      TrasladoConcepto trasladoConcepto);

  TrasladoConcepto mapTrasladoComprobanteToCfdi(
      Comprobante.Impuestos.Traslados.Traslado trasladoConcepto);
}
