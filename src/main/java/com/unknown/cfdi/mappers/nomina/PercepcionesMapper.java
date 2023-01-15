package com.unknown.cfdi.mappers.nomina;

import com.unknown.cfdi.mappers.IgnoreUnmappedMapperConfig;
import com.unknown.cfdi.modelos.complementos.nomina.Percepciones;
import com.unknown.models.generated.Nomina;
import org.mapstruct.Mapper;

@Mapper(config = IgnoreUnmappedMapperConfig.class)
public interface PercepcionesMapper {
  Percepciones percepcionesGeneratedToPercepciones(Nomina.Percepciones percepciones);

  Nomina.Percepciones percepcionesToPercepcionesGenerated(Percepciones percepciones);
}
