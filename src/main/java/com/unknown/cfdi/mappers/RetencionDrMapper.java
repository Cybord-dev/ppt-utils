package com.unknown.cfdi.mappers;

import com.unknown.cfdi.modelos.complementos.pagos.RetencionDR;
import com.unknown.models.generated.Pagos;
import org.mapstruct.Mapper;

@Mapper(config = IgnoreUnmappedMapperConfig.class)
public interface RetencionDrMapper {

  Pagos.Pago.DoctoRelacionado.ImpuestosDR.RetencionesDR.RetencionDR mapRetencionDrToGenerated(
      RetencionDR retencionDR);

  RetencionDR mapGeneratedToRetencionDr(
      Pagos.Pago.DoctoRelacionado.ImpuestosDR.RetencionesDR.RetencionDR retencionDR);
}
