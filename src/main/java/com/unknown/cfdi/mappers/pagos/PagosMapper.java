package com.unknown.cfdi.mappers.pagos;

import com.unknown.cfdi.mappers.IgnoreUnmappedMapperConfig;
import com.unknown.cfdi.mappers.decorator.PagosTranslateDecorator;
import com.unknown.cfdi.modelos.complementos.pagos.Pagos;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

@Mapper(config = IgnoreUnmappedMapperConfig.class)
@DecoratedWith(PagosTranslateDecorator.class)
public interface PagosMapper {

  com.unknown.models.generated.Pagos pagosToGeneratedPagos(Pagos pagos);

  Pagos pagosGeneratedToPagos(com.unknown.models.generated.Pagos pagos);
}
