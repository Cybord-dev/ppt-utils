package com.unknown.cfdi.mappers.nomina;

import com.unknown.cfdi.mappers.IgnoreUnmappedMapperConfig;
import com.unknown.cfdi.modelos.complementos.nomina.EntidadSNCF;
import com.unknown.models.generated.Nomina;
import org.mapstruct.Mapper;

@Mapper(config = IgnoreUnmappedMapperConfig.class)
public interface EntidadSNCFMapper {
  EntidadSNCF entidadSNCFGeneratedToEntidadSNCF(Nomina.Emisor.EntidadSNCF entidadSNCF);

  Nomina.Emisor.EntidadSNCF entidadSNCFToEntidadSNCFGenerated(EntidadSNCF entidadSNCF);
}
