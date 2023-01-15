package com.unknown.cfdi.mappers.decorator;

import com.unknown.cfdi.mappers.pagos.PagoMapper;
import com.unknown.cfdi.mappers.pagos.PagosMapper;
import com.unknown.cfdi.mappers.pagos.TotalesMapper;
import com.unknown.cfdi.modelos.complementos.pagos.Pagos;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;

@Slf4j
public class PagosTranslateDecorator implements PagosMapper {

  public final PagosMapper delegate;

  public static final PagoMapper pagoMapper = Mappers.getMapper(PagoMapper.class);
  public static final TotalesMapper totalesMapper = Mappers.getMapper(TotalesMapper.class);

  public PagosTranslateDecorator(PagosMapper delegate) {
    this.delegate = delegate;
  }

  @Override
  public com.unknown.models.generated.Pagos pagosToGeneratedPagos(Pagos pago) {
    com.unknown.models.generated.Pagos pagosGenerated = delegate.pagosToGeneratedPagos(pago);
    pagosGenerated.setTotales(totalesMapper.mapTotalesToGenerated(pago.getTotales()));
    pagosGenerated
        .getPago()
        .addAll(
            pago.getPagos().stream()
                .map(e -> pagoMapper.pagoToGeneratedPago(e))
                .collect(Collectors.toList()));
    return pagosGenerated;
  }

  @Override
  public Pagos pagosGeneratedToPagos(com.unknown.models.generated.Pagos pagosGenerated) {
    Pagos pagos = delegate.pagosGeneratedToPagos(pagosGenerated);
    pagos.setTotales(totalesMapper.mapGeneratedToTotales(pagosGenerated.getTotales()));
    pagos
        .getPagos()
        .addAll(
            pagosGenerated.getPago().stream()
                .map(e -> pagoMapper.pagoGenerateToPago(e))
                .collect(Collectors.toList()));
    return pagos;
  }
}
