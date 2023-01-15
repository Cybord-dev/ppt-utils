package com.unknown.cfdi.mappers;

import com.unknown.cfdi.modelos.complementos.pagos.TrasladoDR;
import com.unknown.models.generated.Pagos;
import org.mapstruct.Mapper;

@Mapper(config = IgnoreUnmappedMapperConfig.class)
public interface TrasladoDrMapper {

  Pagos.Pago.DoctoRelacionado.ImpuestosDR.TrasladosDR.TrasladoDR mapTrasladoDrToGenerated(
      TrasladoDR trasladoDR);

  TrasladoDR mapGeneratedToTrasladoDr(
      Pagos.Pago.DoctoRelacionado.ImpuestosDR.TrasladosDR.TrasladoDR trasladoDR);
}
