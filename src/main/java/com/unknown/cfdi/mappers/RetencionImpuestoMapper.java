package com.unknown.cfdi.mappers;

import com.unknown.cfdi.mappers.auxiliars.ImpuestosAuxiliarMapper;
import com.unknown.cfdi.modelos.RetencionConcepto;
import com.unknown.models.generated.Comprobante;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(config = IgnoreUnmappedMapperConfig.class, uses = ImpuestosAuxiliarMapper.class)
public interface RetencionImpuestoMapper {

  Comprobante.Impuestos.Retenciones.Retencion mapRetencionCfdiToComprobante(
      RetencionConcepto retencionConcepto);

  RetencionConcepto mapRetencionComprobanteToCfdi(
      Comprobante.Impuestos.Retenciones.Retencion retencionConcepto);

  List<RetencionConcepto> mapRetencionComprobantesToCfdi(
      List<Comprobante.Impuestos.Retenciones.Retencion> retencionConceptoList);
}
