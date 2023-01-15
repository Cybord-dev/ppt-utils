package com.unknown.cfdi.mappers.nomina;

import com.unknown.cfdi.mappers.IgnoreUnmappedMapperConfig;
import com.unknown.cfdi.modelos.complementos.nomina.Emisor;
import com.unknown.models.generated.Nomina;
import org.mapstruct.Mapper;

@Mapper(config = IgnoreUnmappedMapperConfig.class)
public interface EmisorMapper {
  Emisor emisorGeneratedToEmisor(Nomina.Emisor emisor);

  Nomina.Emisor emisorToEmisorGenerated(Emisor emisor);
}
