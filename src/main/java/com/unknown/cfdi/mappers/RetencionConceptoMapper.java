package com.unknown.cfdi.mappers;

import com.unknown.cfdi.mappers.auxiliars.ImpuestosAuxiliarMapper;
import com.unknown.cfdi.modelos.RetencionConcepto;
import com.unknown.models.generated.Comprobante;
import org.mapstruct.Mapper;

@Mapper(config = IgnoreUnmappedMapperConfig.class, uses = ImpuestosAuxiliarMapper.class)
public interface RetencionConceptoMapper {

  Comprobante.Conceptos.Concepto.Impuestos.Retenciones.Retencion
      mapRetencionConceptoCfdiToComprobante(RetencionConcepto retencionConcepto);

  RetencionConcepto mapRetencionComprobanteToCfdi(
      Comprobante.Conceptos.Concepto.Impuestos.Retenciones.Retencion retencionConcepto);
}
