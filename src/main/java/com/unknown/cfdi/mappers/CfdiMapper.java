package com.unknown.cfdi.mappers;

import com.unknown.cfdi.mappers.auxiliars.CfdiAuxiliarMapper;
import com.unknown.cfdi.mappers.decorator.CfdiComprobanteTranslateDecorator;
import com.unknown.cfdi.modelos.Cfdi;
import com.unknown.models.generated.Comprobante;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(
    config = IgnoreUnmappedMapperConfig.class,
    uses = {CfdiAuxiliarMapper.class})
@DecoratedWith(CfdiComprobanteTranslateDecorator.class)
public interface CfdiMapper {

  @Mappings({
    @Mapping(source = "cfdi.subtotal", target = "subTotal"),
    @Mapping(source = "cfdi.informacionGlobal.anio", target = "informacionGlobal.año"),
    @Mapping(source = "cfdi.receptor.usoCfdi", target = "receptor.usoCFDI")
  })
  Comprobante cfdiToComprobante(Cfdi cfdi);

  @Mappings({
    @Mapping(target = "subtotal", source = "subTotal"),
    @Mapping(target = "informacionGlobal.anio", source = "informacionGlobal.año"),
    @Mapping(target = "receptor.usoCfdi", source = "receptor.usoCFDI")
  })
  Cfdi comprobanteToCfdi(Comprobante combrobante);
}
