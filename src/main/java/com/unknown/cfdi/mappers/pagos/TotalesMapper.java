package com.unknown.cfdi.mappers.pagos;

import com.unknown.cfdi.mappers.IgnoreUnmappedMapperConfig;
import com.unknown.cfdi.modelos.complementos.pagos.Totales;
import com.unknown.models.generated.Pagos;
import org.mapstruct.Mapper;

@Mapper(config = IgnoreUnmappedMapperConfig.class)
public interface TotalesMapper {

  Pagos.Totales mapTotalesToGenerated(Totales totales);

  Totales mapGeneratedToTotales(Pagos.Totales totales);
}
