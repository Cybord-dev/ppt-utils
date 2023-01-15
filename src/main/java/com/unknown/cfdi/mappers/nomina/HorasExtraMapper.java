package com.unknown.cfdi.mappers.nomina;

import com.unknown.cfdi.mappers.IgnoreUnmappedMapperConfig;
import com.unknown.cfdi.modelos.complementos.nomina.HorasExtra;
import com.unknown.models.generated.Nomina;
import org.mapstruct.Mapper;

@Mapper(config = IgnoreUnmappedMapperConfig.class)
public interface HorasExtraMapper {
  HorasExtra horasExtraGeneratedToHorasExtra(Nomina.Percepciones.Percepcion.HorasExtra horasExtra);

  Nomina.Percepciones.Percepcion.HorasExtra horasExtraToHorasExtraGenerated(HorasExtra horasExtra);
}
