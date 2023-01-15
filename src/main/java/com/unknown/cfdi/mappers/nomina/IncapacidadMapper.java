package com.unknown.cfdi.mappers.nomina;

import com.unknown.cfdi.mappers.IgnoreUnmappedMapperConfig;
import com.unknown.cfdi.modelos.complementos.nomina.Incapacidad;
import com.unknown.models.generated.Nomina;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(
    config = IgnoreUnmappedMapperConfig.class,
    uses = {AuxiliarMapper.class})
public interface IncapacidadMapper {
  Incapacidad incapacidadGeneratedToIncapacidad(Nomina.Incapacidades.Incapacidad incapacidad);

  Nomina.Incapacidades.Incapacidad incapacidadToIncapacidadGenerated(Incapacidad incapacidad);

  List<Nomina.Incapacidades.Incapacidad> incapacidadesToIncapacidadesGenerated(
      List<Incapacidad> incapacidades);
}
