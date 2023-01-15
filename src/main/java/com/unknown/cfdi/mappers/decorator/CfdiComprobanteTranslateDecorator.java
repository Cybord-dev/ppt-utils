package com.unknown.cfdi.mappers.decorator;

import com.unknown.cfdi.mappers.CfdiMapper;
import com.unknown.cfdi.mappers.RetencionImpuestoMapper;
import com.unknown.cfdi.mappers.TimbreFiscalDigitalMapper;
import com.unknown.cfdi.mappers.TrasladoImpuestoMapper;
import com.unknown.cfdi.mappers.pagos.PagosMapper;
import com.unknown.cfdi.modelos.Cfdi;
import com.unknown.cfdi.modelos.ObjectImp;
import com.unknown.cfdi.modelos.RetencionConcepto;
import com.unknown.cfdi.modelos.TrasladoConcepto;
import com.unknown.models.generated.CTipoFactor;
import com.unknown.models.generated.Comprobante;
import com.unknown.models.generated.Pagos;
import com.unknown.models.generated.TimbreFiscalDigital;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;

@Slf4j
public class CfdiComprobanteTranslateDecorator implements CfdiMapper {

  public final CfdiMapper delegate;
  public static final RetencionImpuestoMapper retencionMapper =
      Mappers.getMapper(RetencionImpuestoMapper.class);
  public static final TrasladoImpuestoMapper trasladoMapper =
      Mappers.getMapper(TrasladoImpuestoMapper.class);
  public static final TimbreFiscalDigitalMapper timbreFiscalDigitalMapper =
      Mappers.getMapper(TimbreFiscalDigitalMapper.class);
  public static final PagosMapper pagosMapper = Mappers.getMapper(PagosMapper.class);

  public CfdiComprobanteTranslateDecorator(CfdiMapper delegate) {
    this.delegate = delegate;
  }

  @Override
  public Comprobante cfdiToComprobante(Cfdi cfdi) {

    Comprobante comprobante = delegate.cfdiToComprobante(cfdi);

    boolean objetoImpuestoValidation =
        !comprobante.getConceptos().getConcepto().stream()
            .allMatch(a -> ObjectImp.NO_OBJETO_IMPUESTOS.getValor().equals(a.getObjetoImp()));

    List<TrasladoConcepto> listTraslados =
        cfdi.getConceptos().stream()
            .filter(e -> e.getImpuestos() != null)
            .flatMap(e -> e.getImpuestos().stream())
            .filter(e -> e.getTraslados() != null)
            .flatMap(e -> e.getTraslados().stream())
            .peek(
                e -> {
                  e.setBase(e.getBase().setScale(2, RoundingMode.HALF_UP));
                  e.setImporte(e.getImporte().setScale(2, RoundingMode.HALF_UP));
                })
            .collect(Collectors.toList());

    List<RetencionConcepto> listRetenidos =
        cfdi.getConceptos().stream()
            .filter(e -> e.getImpuestos() != null)
            .flatMap(e -> e.getImpuestos().stream())
            .filter(e -> e.getRetenciones() != null)
            .flatMap(e -> e.getRetenciones().stream())
            .peek(
                e -> {
                  e.setBase(e.getBase().setScale(2, RoundingMode.HALF_UP));
                  e.setImporte(e.getImporte().setScale(2, RoundingMode.HALF_UP));
                })
            .collect(Collectors.toList());

    if (objetoImpuestoValidation) {

      comprobante.setImpuestos(new Comprobante.Impuestos());

      BigDecimal totalTraslados =
          listTraslados.stream()
              .map(e -> e.getImporte())
              .reduce(BigDecimal.ZERO, BigDecimal::add)
              .setScale(2, RoundingMode.HALF_UP);

      comprobante.getImpuestos().setTotalImpuestosTrasladados(totalTraslados);

      log.debug(
          "Impuestos Trasladados {} Total importe Traslados {} ",
          listTraslados.size(),
          totalTraslados);

      BigDecimal totalRetenidos =
          listRetenidos.stream()
              .map(e -> e.getImporte())
              .reduce(BigDecimal.ZERO, BigDecimal::add)
              .setScale(2, RoundingMode.HALF_UP);

      comprobante.getImpuestos().setTotalImpuestosRetenidos(totalRetenidos);
      log.debug(
          "Impuestos Retenidos {} Total importe retenidos {}",
          listRetenidos.size(),
          totalRetenidos);

      if (!listTraslados.isEmpty()) {

        comprobante.getImpuestos().setTraslados(new Comprobante.Impuestos.Traslados());
        List<Comprobante.Impuestos.Traslados.Traslado> trasladosComprobante =
            comprobante.getImpuestos().getTraslados().getTraslado();

        Map<String, List<Comprobante.Impuestos.Traslados.Traslado>> trasladoMap =
            listTraslados.stream()
                .map(e -> trasladoMapper.mapTrasladoCfdiToComprobante(e))
                .collect(
                    Collectors.groupingBy(Comprobante.Impuestos.Traslados.Traslado::getImpuesto));
        trasladoMap.keySet().stream()
            .forEach(
                e -> {
                  Comprobante.Impuestos.Traslados.Traslado currentTraslado =
                      new Comprobante.Impuestos.Traslados.Traslado();
                  currentTraslado.setBase(
                      trasladoMap.get(e).stream()
                          .map(impuesto -> impuesto.getBase())
                          .reduce(BigDecimal.ZERO, BigDecimal::add)
                          .setScale(2, RoundingMode.HALF_UP));
                  currentTraslado.setTipoFactor(
                      trasladoMap.get(e).stream()
                          .map(impuesto -> impuesto.getTipoFactor())
                          .findFirst()
                          .orElse(CTipoFactor.TASA));
                  currentTraslado.setTasaOCuota(
                      trasladoMap.get(e).stream()
                          .map(impuesto -> impuesto.getTasaOCuota())
                          .findFirst()
                          .orElse(BigDecimal.ZERO)
                          .setScale(2, RoundingMode.HALF_UP));
                  currentTraslado.setImpuesto(e);
                  currentTraslado.setImporte(
                      trasladoMap.get(e).stream()
                          .map(Comprobante.Impuestos.Traslados.Traslado::getImporte)
                          .reduce(BigDecimal.ZERO, BigDecimal::add)
                          .setScale(2, RoundingMode.HALF_UP));
                  trasladosComprobante.add(currentTraslado);
                });
      }
      if (!listRetenidos.isEmpty()) {
        comprobante.getImpuestos().setRetenciones(new Comprobante.Impuestos.Retenciones());
        List<Comprobante.Impuestos.Retenciones.Retencion> retencionComprobante =
            comprobante.getImpuestos().getRetenciones().getRetencion();

        Map<String, List<Comprobante.Impuestos.Retenciones.Retencion>> retencionMap =
            listRetenidos.stream()
                .map(e -> retencionMapper.mapRetencionCfdiToComprobante(e))
                .collect(
                    Collectors.groupingBy(
                        Comprobante.Impuestos.Retenciones.Retencion::getImpuesto));
        retencionMap.keySet().stream()
            .forEach(
                e -> {
                  Comprobante.Impuestos.Retenciones.Retencion currentRetencion =
                      new Comprobante.Impuestos.Retenciones.Retencion();
                  currentRetencion.setImpuesto(e);
                  currentRetencion.setImporte(
                      retencionMap.get(e).stream()
                          .map(Comprobante.Impuestos.Retenciones.Retencion::getImporte)
                          .reduce(BigDecimal.ZERO, BigDecimal::add)
                          .setScale(2, RoundingMode.HALF_UP));
                  retencionComprobante.add(currentRetencion);
                });
      }
    }
    BigDecimal subtotal =
        cfdi.getConceptos().stream()
            .map(concepto -> concepto.getImporte())
            .reduce(BigDecimal.ZERO, BigDecimal::add)
            .setScale(2, RoundingMode.HALF_UP);
    comprobante.setSubTotal(BigDecimal.ZERO.compareTo(subtotal) == 0 ? BigDecimal.ZERO : subtotal);
    boolean hasDescuento = cfdi.getConceptos().stream().allMatch(a -> a.getDescuento() != null);
    BigDecimal descuento =
        hasDescuento
            ? cfdi.getConceptos().stream()
                .map(concepto -> concepto.getDescuento())
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP)
            : null;
    comprobante.setDescuento(
        Objects.isNull(descuento) || BigDecimal.ZERO.compareTo(descuento) == 0 ? null : descuento);
    BigDecimal total =
        comprobante
            .getSubTotal()
            .subtract(
                Objects.isNull(comprobante.getImpuestos())
                    ? BigDecimal.ZERO
                    : comprobante.getImpuestos().getTotalImpuestosRetenidos())
            .subtract(
                Objects.isNull(comprobante.getDescuento())
                    ? BigDecimal.ZERO
                    : comprobante.getDescuento())
            .add(
                Objects.isNull(comprobante.getImpuestos())
                    ? BigDecimal.ZERO
                    : comprobante.getImpuestos().getTotalImpuestosTrasladados())
            .setScale(2, RoundingMode.HALF_UP);
    comprobante.setTotal(BigDecimal.ZERO.compareTo(total) == 0 ? BigDecimal.ZERO : total);
    return comprobante;
  }

  @Override
  public Cfdi comprobanteToCfdi(Comprobante combrobante) {
    Cfdi cfdi = delegate.comprobanteToCfdi(combrobante);
    if (Objects.nonNull(combrobante.getComplemento())
        && combrobante.getComplemento().getAny().size() > 0) {
      List<TimbreFiscalDigital> listTimbreFiscalDigital =
          combrobante.getComplemento().getAny().stream()
              .filter(o -> o instanceof TimbreFiscalDigital)
              .map(o -> (TimbreFiscalDigital) o)
              .collect(Collectors.toList());

      if (!listTimbreFiscalDigital.isEmpty()) {
        listTimbreFiscalDigital.stream()
            .map(t -> timbreFiscalDigitalMapper.mapTimbreFiscalGeneratedToComplementos(t))
            .forEach(e -> cfdi.getComplemento().add(e));
      }

      List<Pagos> listPagos =
          combrobante.getComplemento().getAny().stream()
              .filter(o -> o instanceof Pagos)
              .map(o -> (Pagos) o)
              .collect(Collectors.toList());
      if (!listPagos.isEmpty()) {
        listPagos.stream()
            .map(p -> pagosMapper.pagosGeneratedToPagos(p))
            .forEach(e -> cfdi.getComplemento().add(e));
      }
    }
    return cfdi;
  }
}
