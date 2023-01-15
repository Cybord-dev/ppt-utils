package com.unknown.cfdi.mappers.nomina;

import com.unknown.cfdi.mappers.IgnoreUnmappedMapperConfig;
import com.unknown.cfdi.modelos.complementos.nomina.AccionesOTitulos;
import com.unknown.models.generated.Nomina;
import org.mapstruct.Mapper;

@Mapper(config = IgnoreUnmappedMapperConfig.class)
public interface AccionesOTitulosMapper {
  AccionesOTitulos accionesOTitulosGeneratedToAccionesOTitulos(
      Nomina.Percepciones.Percepcion.AccionesOTitulos accionesOTitulos);

  Nomina.Percepciones.Percepcion.AccionesOTitulos accionesOTitulosToAccionesOTitulosGenerated(
      AccionesOTitulos accionesOTitulos);
}
