package com.unknown.cfdi.mappers.nomina;

import com.unknown.cfdi.mappers.IgnoreUnmappedMapperConfig;
import com.unknown.cfdi.modelos.complementos.nomina.JubilacionPensionRetiro;
import com.unknown.models.generated.Nomina;
import org.mapstruct.Mapper;

@Mapper(config = IgnoreUnmappedMapperConfig.class)
public interface JubilacionPensionRetiroMapper {
  JubilacionPensionRetiro jubilacionPensionRetiroGeneratedToJubilacionPensionRetiro(
      Nomina.Percepciones.JubilacionPensionRetiro jubilacionPensionRetiro);

  Nomina.Percepciones.JubilacionPensionRetiro
      jubilacionPensionRetiroToJubilacionPensionRetiroGenerated(
          JubilacionPensionRetiro jubilacionPensionRetiro);
}
