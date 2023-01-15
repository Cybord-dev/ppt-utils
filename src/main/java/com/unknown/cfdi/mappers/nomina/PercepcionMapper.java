package com.unknown.cfdi.mappers.nomina;

import com.unknown.cfdi.mappers.IgnoreUnmappedMapperConfig;
import com.unknown.cfdi.modelos.complementos.nomina.Percepcion;
import com.unknown.models.generated.Nomina;
import org.mapstruct.Mapper;

@Mapper(config = IgnoreUnmappedMapperConfig.class)
public interface PercepcionMapper {
  Percepcion percepcionGeneratedToPercepcion(Nomina.Percepciones.Percepcion percepcion);

  Nomina.Percepciones.Percepcion percepcionToPercepcionGenerated(Percepcion percepcion);
}
