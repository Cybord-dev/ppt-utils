package com.unknown.cfdi.mappers.nomina;

import com.unknown.cfdi.mappers.IgnoreUnmappedMapperConfig;
import com.unknown.cfdi.modelos.complementos.nomina.SubsidioAlEmpleo;
import com.unknown.models.generated.Nomina;
import org.mapstruct.Mapper;

@Mapper(config = IgnoreUnmappedMapperConfig.class)
public interface SubsidioAlEmpleoMapper {
  SubsidioAlEmpleo subsidioAlEmpleoGeneratedToSubsidioAlEmpleo(
      Nomina.OtrosPagos.OtroPago.SubsidioAlEmpleo subsidioAlEmpleo);

  Nomina.OtrosPagos.OtroPago.SubsidioAlEmpleo subsidioAlEmpleoToSubsidioAlEmpleoGenerated(
      SubsidioAlEmpleo subsidioAlEmpleo);
}
