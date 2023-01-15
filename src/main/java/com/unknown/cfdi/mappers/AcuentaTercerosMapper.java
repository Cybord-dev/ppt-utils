package com.unknown.cfdi.mappers;

import com.unknown.cfdi.modelos.ACuentaTerceros;
import com.unknown.models.generated.Comprobante;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(config = IgnoreUnmappedMapperConfig.class)
public interface AcuentaTercerosMapper {

  @Mappings({
    @Mapping(source = "rfcAcuentaTerceros", target = "rfcACuentaTerceros"),
    @Mapping(source = "nombreAcuentaTerceros", target = "nombreACuentaTerceros"),
    @Mapping(source = "regimenFiscalAcuentaTerceros", target = "regimenFiscalACuentaTerceros"),
    @Mapping(source = "domicilioFiscalAcuentaTerceros", target = "domicilioFiscalACuentaTerceros"),
  })
  Comprobante.Conceptos.Concepto.ACuentaTerceros mapAcuentaCfdiToAcuentaComprobante(
      ACuentaTerceros aCuentaTerceros);

  @Mappings({
    @Mapping(target = "rfcAcuentaTerceros", source = "rfcACuentaTerceros"),
    @Mapping(target = "nombreAcuentaTerceros", source = "nombreACuentaTerceros"),
    @Mapping(target = "regimenFiscalAcuentaTerceros", source = "regimenFiscalACuentaTerceros"),
    @Mapping(target = "domicilioFiscalAcuentaTerceros", source = "domicilioFiscalACuentaTerceros"),
  })
  ACuentaTerceros mapAcuentaComprobanteToAcuentaCfdi(
      Comprobante.Conceptos.Concepto.ACuentaTerceros aCuentaTerceros);
}
