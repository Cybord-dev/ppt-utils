package com.unknown.cfdi.mappers.nomina;

import com.unknown.cfdi.mappers.IgnoreUnmappedMapperConfig;
import com.unknown.cfdi.modelos.complementos.nomina.SeparacionIndemnizacion;
import com.unknown.models.generated.Nomina;
import org.mapstruct.Mapper;

@Mapper(config = IgnoreUnmappedMapperConfig.class)
public interface SeparacionIndemnizacionMapper {
  SeparacionIndemnizacion separacionIndemnizacionGeneratedToSeparacionIndemnizacion(
      Nomina.Percepciones.SeparacionIndemnizacion separacionIndemnizacion);

  Nomina.Percepciones.SeparacionIndemnizacion
      separacionIndemnizacionToSeparacionIndemnizacionGenerated(
          SeparacionIndemnizacion separacionIndemnizacion);
}
