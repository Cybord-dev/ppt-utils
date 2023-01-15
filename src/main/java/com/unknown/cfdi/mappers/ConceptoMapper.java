package com.unknown.cfdi.mappers;

import com.unknown.cfdi.mappers.auxiliars.ConceptosAuxiliarMapper;
import com.unknown.cfdi.modelos.Concepto;
import com.unknown.models.generated.Comprobante;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(
    config = IgnoreUnmappedMapperConfig.class,
    uses = {ConceptosAuxiliarMapper.class})
public interface ConceptoMapper {

  Comprobante.Conceptos.Concepto mapConceptoCfdiComprobanteConcepto(Concepto concepto);

  List<Comprobante.Conceptos.Concepto> mapConceptosCfdiToComprobanteConceptos(
      List<Concepto> conceptos);

  Concepto mapComprobanteConceptoToConceptoCfdi(Comprobante.Conceptos.Concepto concepto);
}
