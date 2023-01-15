package com.unknown.cfdi.mappers.nomina;

import com.unknown.cfdi.mappers.IgnoreUnmappedMapperConfig;
import com.unknown.cfdi.modelos.complementos.nomina.Deduccion;
import com.unknown.models.generated.Nomina;
import org.mapstruct.Mapper;

@Mapper(config = IgnoreUnmappedMapperConfig.class)
public interface DeduccionMapper {
  Deduccion deduccionGeneratedToDeduccion(Nomina.Deducciones.Deduccion deduccion);

  Nomina.Deducciones.Deduccion deduccionToDeduccionGenerated(Deduccion deduccion);
}
