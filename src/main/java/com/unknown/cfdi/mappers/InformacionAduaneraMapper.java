package com.unknown.cfdi.mappers;

import com.unknown.cfdi.modelos.InformacionAduanera;
import com.unknown.models.generated.Comprobante;
import org.mapstruct.Mapper;

@Mapper(config = IgnoreUnmappedMapperConfig.class)
public interface InformacionAduaneraMapper {

  InformacionAduanera informacionAduaneraConceptoToCfdiInformacionAduanera(
      Comprobante.Conceptos.Concepto.Parte.InformacionAduanera informacionAduanera);
}
