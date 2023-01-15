package com.unknown.cfdi.mappers.nomina;

import com.unknown.cfdi.mappers.IgnoreUnmappedMapperConfig;
import com.unknown.cfdi.modelos.complementos.nomina.Deducciones;
import com.unknown.models.generated.Nomina;
import org.mapstruct.Mapper;

@Mapper(config = IgnoreUnmappedMapperConfig.class)
public interface DeduccionesMapper {
  Deducciones deduccionesGeneratedToDeducciones(Nomina.Deducciones deducciones);

  Nomina.Deducciones deduccionesTODeduccionesGenerated(Deducciones deducciones);
}
