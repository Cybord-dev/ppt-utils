package com.unknown.cfdi.mappers.nomina;

import com.unknown.cfdi.mappers.IgnoreUnmappedMapperConfig;
import com.unknown.cfdi.modelos.complementos.nomina.Nomina;
import org.mapstruct.Mapper;

@Mapper(
    config = IgnoreUnmappedMapperConfig.class,
    uses = {AuxiliarMapper.class})
public interface NominaMapper {
  Nomina nominaGeneratedToNomina(com.unknown.models.generated.Nomina nomina);

  com.unknown.models.generated.Nomina nominaToNominaGenerated(Nomina nomina);
}
