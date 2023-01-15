package com.unknown.cfdi.mappers.pagos;

import com.unknown.cfdi.mappers.IgnoreUnmappedMapperConfig;
import com.unknown.cfdi.modelos.complementos.pagos.DocumentoRelacionado;
import com.unknown.models.generated.Pagos;
import org.mapstruct.Mapper;

@Mapper(config = IgnoreUnmappedMapperConfig.class)
public interface DocumentoRelacionadoMapper {

  Pagos.Pago.DoctoRelacionado documentoRelacionadoToDoctoRelacionado(
      DocumentoRelacionado documentoRelacionado);

  DocumentoRelacionado doctoRelacionadoToDocumentoRelacionado(
      Pagos.Pago.DoctoRelacionado doctoRelacionado);
}
