package com.unknown.cfdi.mappers;

import com.unknown.cfdi.modelos.complementos.timbrefiscaldigital.TimbreFiscalDigital;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(config = IgnoreUnmappedMapperConfig.class)
public interface TimbreFiscalDigitalMapper {

  @Mappings({
    @Mapping(source = "UUID", target = "uuid"),
  })
  TimbreFiscalDigital mapTimbreFiscalGeneratedToComplementos(
      com.unknown.models.generated.TimbreFiscalDigital timbreFiscalDigital);

  @Mappings({
    @Mapping(source = "uuid", target = "UUID"),
  })
  com.unknown.models.generated.TimbreFiscalDigital mapTimbreFiscalComplementosToGenerated(
      TimbreFiscalDigital timbreFiscalDigital);
}
