package com.unknown.cfdi.mappers.pagos;

import com.unknown.cfdi.mappers.IgnoreUnmappedMapperConfig;
import com.unknown.cfdi.modelos.complementos.pagos.Pago;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = IgnoreUnmappedMapperConfig.class)
public interface PagoMapper {

  @Mapping(target = "doctoRelacionado", source = "relacionados")
  com.unknown.models.generated.Pagos.Pago pagoToGeneratedPago(Pago pago);

  @Mapping(source = "doctoRelacionado", target = "relacionados")
  Pago pagoGenerateToPago(com.unknown.models.generated.Pagos.Pago pago);
}
