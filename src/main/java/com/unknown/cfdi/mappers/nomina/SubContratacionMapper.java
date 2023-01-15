package com.unknown.cfdi.mappers.nomina;

import com.unknown.cfdi.mappers.IgnoreUnmappedMapperConfig;
import com.unknown.cfdi.modelos.complementos.nomina.SubContratacion;
import com.unknown.models.generated.Nomina;
import org.mapstruct.Mapper;

@Mapper(config = IgnoreUnmappedMapperConfig.class)
public interface SubContratacionMapper {
  SubContratacion subContratacionGeneratedToSubContratacion(
      Nomina.Receptor.SubContratacion subContratacion);

  Nomina.Receptor.SubContratacion subContratacionToSubContratacionGenerated(
      SubContratacion subContratacion);
}
