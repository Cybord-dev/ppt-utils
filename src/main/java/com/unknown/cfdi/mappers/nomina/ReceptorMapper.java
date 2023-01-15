package com.unknown.cfdi.mappers.nomina;

import com.unknown.cfdi.mappers.IgnoreUnmappedMapperConfig;
import com.unknown.cfdi.modelos.complementos.nomina.Receptor;
import com.unknown.models.generated.Nomina;
import org.mapstruct.Mapper;

@Mapper(config = IgnoreUnmappedMapperConfig.class)
public interface ReceptorMapper {
  Receptor receptorGeneratedToReceptor(Nomina.Receptor receptor);

  Nomina.Receptor receptortoReceptorGenerated(Receptor receptor);
}
