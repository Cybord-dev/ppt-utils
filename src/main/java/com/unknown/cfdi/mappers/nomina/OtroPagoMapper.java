package com.unknown.cfdi.mappers.nomina;

import com.unknown.cfdi.mappers.IgnoreUnmappedMapperConfig;
import com.unknown.cfdi.modelos.complementos.nomina.OtroPago;
import com.unknown.models.generated.Nomina;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(
    config = IgnoreUnmappedMapperConfig.class,
    uses = {AuxiliarMapper.class})
public interface OtroPagoMapper {
  Nomina.OtrosPagos.OtroPago otroPagoToOtroPagoGenerated(OtroPago otroPago);

  OtroPago otroPagoGeneratedToOtroPago(Nomina.OtrosPagos.OtroPago otroPago);

  List<Nomina.OtrosPagos.OtroPago> otrosPagosToOtrosPagosGenerated(List<OtroPago> otrosPagos);
}
