package com.unknown.cfdi.mappers;

import com.unknown.cfdi.modelos.CfdiRelacionado;
import com.unknown.models.generated.Comprobante;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = IgnoreUnmappedMapperConfig.class)
public interface CfdiRelacionadoMapper {

  @Mapping(source = "uuid", target = "UUID")
  Comprobante.CfdiRelacionados.CfdiRelacionado map(CfdiRelacionado value);

  @Mapping(target = "uuid", source = "UUID")
  CfdiRelacionado mapCfdi(Comprobante.CfdiRelacionados.CfdiRelacionado value);

  List<Comprobante.CfdiRelacionados.CfdiRelacionado> mapList(List<CfdiRelacionado> value);
}
