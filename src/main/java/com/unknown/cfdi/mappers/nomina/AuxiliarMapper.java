package com.unknown.cfdi.mappers.nomina;

import com.unknown.cfdi.modelos.complementos.nomina.Incapacidad;
import com.unknown.cfdi.modelos.complementos.nomina.OtroPago;
import com.unknown.models.generated.Nomina;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.mapstruct.factory.Mappers;

public class AuxiliarMapper {
  public static final OtroPagoMapper otroPagoMapper = Mappers.getMapper(OtroPagoMapper.class);
  public static final IncapacidadMapper incapacidadMapper =
      Mappers.getMapper(IncapacidadMapper.class);

  public Nomina.OtrosPagos mapOtrosPagos(List<OtroPago> value) {
    Nomina.OtrosPagos otrosPagos = new Nomina.OtrosPagos();
    otrosPagos.getOtroPago().addAll(otroPagoMapper.otrosPagosToOtrosPagosGenerated(value));
    return otrosPagos;
  }

  public List<OtroPago> mapOtrosPagosReverse(Nomina.OtrosPagos value) {
    if (Objects.nonNull(value) && !value.getOtroPago().isEmpty())
      return value.getOtroPago().stream()
          .map(e -> otroPagoMapper.otroPagoGeneratedToOtroPago(e))
          .collect(Collectors.toList());
    else return new ArrayList<>();
  }

  public Nomina.Incapacidades mapIncapacidades(List<Incapacidad> value) {
    Nomina.Incapacidades incapacidades = new Nomina.Incapacidades();
    incapacidades
        .getIncapacidad()
        .addAll(incapacidadMapper.incapacidadesToIncapacidadesGenerated(value));
    return incapacidades;
  }

  public List<Incapacidad> mapIncapacidadesReverse(Nomina.Incapacidades value) {
    if (Objects.nonNull(value) && !value.getIncapacidad().isEmpty())
      return value.getIncapacidad().stream()
          .map(e -> incapacidadMapper.incapacidadGeneratedToIncapacidad(e))
          .collect(Collectors.toList());
    else return new ArrayList<>();
  }
}
