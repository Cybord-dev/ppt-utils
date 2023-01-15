package com.unknown.cfdi.mappers.nomina;

import com.unknown.cfdi.mappers.IgnoreUnmappedMapperConfig;
import com.unknown.cfdi.modelos.complementos.nomina.CompensacionSaldosAFavor;
import com.unknown.models.generated.Nomina;
import org.mapstruct.Mapper;

@Mapper(config = IgnoreUnmappedMapperConfig.class)
public interface CompensacionSaldosAFavorMapper {
  CompensacionSaldosAFavor compensacionSaldosAFavorGeneratedToCompensacionSaldosAFavor(
      Nomina.OtrosPagos.OtroPago.CompensacionSaldosAFavor compensacionSaldosAFavor);

  Nomina.OtrosPagos.OtroPago.CompensacionSaldosAFavor
      compensacionSaldosAFavorToCompensacionSaldosAFavorGenerated(
          CompensacionSaldosAFavor compensacionSaldosAFavor);
}
