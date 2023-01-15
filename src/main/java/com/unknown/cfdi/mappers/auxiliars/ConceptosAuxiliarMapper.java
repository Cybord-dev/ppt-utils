package com.unknown.cfdi.mappers.auxiliars;

import com.google.common.collect.ImmutableList;
import com.unknown.cfdi.mappers.AcuentaTercerosMapper;
import com.unknown.cfdi.mappers.InformacionAduaneraMapper;
import com.unknown.cfdi.mappers.ParteMapper;
import com.unknown.cfdi.mappers.RetencionConceptoMapper;
import com.unknown.cfdi.mappers.TrasladoConceptoMapper;
import com.unknown.cfdi.modelos.ACuentaTerceros;
import com.unknown.cfdi.modelos.CuentaPredial;
import com.unknown.cfdi.modelos.ImpuestoConcepto;
import com.unknown.cfdi.modelos.InformacionAduanera;
import com.unknown.cfdi.modelos.Parte;
import com.unknown.cfdi.modelos.RetencionConcepto;
import com.unknown.cfdi.modelos.TrasladoConcepto;
import com.unknown.models.generated.CTipoFactor;
import com.unknown.models.generated.Comprobante;
import java.math.RoundingMode;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.mapstruct.factory.Mappers;

public class ConceptosAuxiliarMapper {

  public static final RetencionConceptoMapper retencionConceptoMapper =
      Mappers.getMapper(RetencionConceptoMapper.class);
  public static final TrasladoConceptoMapper trasladoConceptoMapper =
      Mappers.getMapper(TrasladoConceptoMapper.class);
  public static final ParteMapper parteMapper = Mappers.getMapper(ParteMapper.class);
  public static final AcuentaTercerosMapper acuentaTercerosMapper =
      Mappers.getMapper(AcuentaTercerosMapper.class);
  public static final InformacionAduaneraMapper informacionAduaneraMapper =
      Mappers.getMapper(InformacionAduaneraMapper.class);

  public Comprobante.Conceptos.Concepto.Impuestos map(List<ImpuestoConcepto> value) {
    if (Objects.isNull(value)) {
      return null;
    }
    Comprobante.Conceptos.Concepto.Impuestos impuestos =
        new Comprobante.Conceptos.Concepto.Impuestos();

    if (Objects.nonNull(value.stream().findFirst().get().getRetenciones())) {
      impuestos.setRetenciones(new Comprobante.Conceptos.Concepto.Impuestos.Retenciones());
      List<Comprobante.Conceptos.Concepto.Impuestos.Retenciones.Retencion> retencion =
          impuestos.getRetenciones().getRetencion();
      retencion.addAll(
          value.stream()
              .flatMap(e -> e.getRetenciones().stream())
              .map(e -> retencionConceptoMapper.mapRetencionConceptoCfdiToComprobante(e))
              .peek(
                  e -> {
                    e.setBase(e.getBase().setScale(2, RoundingMode.HALF_UP));
                    e.setImporte(e.getImporte().setScale(2, RoundingMode.HALF_UP));
                  })
              .collect(Collectors.toList()));
    }
    if (Objects.nonNull(value.stream().findFirst().get().getTraslados())) {
      impuestos.setTraslados(new Comprobante.Conceptos.Concepto.Impuestos.Traslados());
      List<Comprobante.Conceptos.Concepto.Impuestos.Traslados.Traslado> traslado =
          impuestos.getTraslados().getTraslado();
      traslado.addAll(
          value.stream()
              .flatMap(e -> e.getTraslados().stream())
              .map(e -> trasladoConceptoMapper.mapTrasladoConceptoCfdiToComprobante(e))
              .peek(
                  e -> {
                    e.setBase(e.getBase().setScale(2, RoundingMode.HALF_UP));
                    e.setImporte(e.getImporte().setScale(2, RoundingMode.HALF_UP));
                  })
              .collect(Collectors.toList()));
    }
    return impuestos;
  }

  public List<Comprobante.Conceptos.Concepto.InformacionAduanera> map(InformacionAduanera value) {
    if (Objects.nonNull(value)) {
      Comprobante.Conceptos.Concepto.InformacionAduanera aduanera =
          new Comprobante.Conceptos.Concepto.InformacionAduanera();

      aduanera.setNumeroPedimento(value.getNumeroPedimento());
      List<Comprobante.Conceptos.Concepto.InformacionAduanera> aduaneraList =
          ImmutableList.of(aduanera);
      return aduaneraList;
    }
    return null;
  }

  public List<Comprobante.Conceptos.Concepto.CuentaPredial> map(CuentaPredial value) {
    if (Objects.nonNull(value)) {
      Comprobante.Conceptos.Concepto.CuentaPredial predial =
          new Comprobante.Conceptos.Concepto.CuentaPredial();
      predial.setNumero(value.getNumero());
      List<Comprobante.Conceptos.Concepto.CuentaPredial> predialList = ImmutableList.of(predial);
      return predialList;
    }
    return null;
  }

  public List<Comprobante.Conceptos.Concepto.Parte> map(Parte value) {
    if (Objects.nonNull(value)) {
      List<Comprobante.Conceptos.Concepto.Parte> parteList =
          new Comprobante.Conceptos.Concepto().getParte();
      Comprobante.Conceptos.Concepto.Parte parte =
          parteMapper.mapParteCfidToComprobanteParte(value);
      parteList.add(parte);
      return parteList;
    }
    return null;
  }

  public List<ImpuestoConcepto> mapComprobanteImpuestos(
      Comprobante.Conceptos.Concepto.Impuestos value) {
    if (Objects.isNull(value)) {
      return null;
    }
    List<RetencionConcepto> retencionCfdi = null;
    List<TrasladoConcepto> trasladoCfdi = null;
    if (Objects.nonNull(value.getRetenciones())
        && Objects.nonNull(value.getRetenciones().getRetencion())) {
      retencionCfdi =
          value.getRetenciones().getRetencion().stream()
              .map(e -> retencionConceptoMapper.mapRetencionComprobanteToCfdi(e))
              .collect(Collectors.toList());
    }
    if (Objects.nonNull(value.getTraslados())
        && Objects.nonNull(value.getTraslados().getTraslado())) {
      trasladoCfdi =
          value.getTraslados().getTraslado().stream()
              .map(e -> trasladoConceptoMapper.mapTrasladoComprobanteToCfdi(e))
              .collect(Collectors.toList());
    }
    return ImmutableList.of(new ImpuestoConcepto(trasladoCfdi, retencionCfdi));
  }

  public InformacionAduanera mapComprobanteParteInformacionAduanera(
      List<Comprobante.Conceptos.Concepto.Parte.InformacionAduanera> value) {
    if (Objects.nonNull(value) && !value.isEmpty()) {
      return value.stream()
          .map(
              e ->
                  informacionAduaneraMapper.informacionAduaneraConceptoToCfdiInformacionAduanera(e))
          .findAny()
          .orElseThrow(null);
    }
    return null;
  }

  public InformacionAduanera mapComprobanteInformacionAduanera(
      List<Comprobante.Conceptos.Concepto.InformacionAduanera> value) {
    if (Objects.nonNull(value) && !value.isEmpty()) {
      return value.stream()
          .map(e -> InformacionAduanera.builder().numeroPedimento(e.getNumeroPedimento()).build())
          .findAny()
          .orElseThrow(null);
    }
    return null;
  }

  public CuentaPredial mapComprobanteCuentaPredial(
      List<Comprobante.Conceptos.Concepto.CuentaPredial> value) {
    if (Objects.nonNull(value) && !value.isEmpty()) {
      return value.stream()
          .map(e -> CuentaPredial.builder().numero(e.getNumero()).build())
          .findAny()
          .orElseThrow(null);
    }
    return null;
  }

  public Parte mapComprobanteParte(List<Comprobante.Conceptos.Concepto.Parte> value) {
    if (Objects.nonNull(value) && !value.isEmpty()) {
      return value.stream()
          .map(e -> parteMapper.mapComprobanteParteToCFDIParte(e))
          .findAny()
          .orElseThrow(null);
    }
    return null;
  }

  public com.unknown.models.generated.CTipoFactor map(String value) {
    return CTipoFactor.fromValue(value);
  }

  public String map(com.unknown.models.generated.CTipoFactor value) {
    return value.value();
  }

  public Comprobante.Conceptos.Concepto.ACuentaTerceros map(ACuentaTerceros value) {
    if (Objects.nonNull(value)) {
      return acuentaTercerosMapper.mapAcuentaCfdiToAcuentaComprobante(value);
    }
    return null;
  }

  public ACuentaTerceros map(Comprobante.Conceptos.Concepto.ACuentaTerceros value) {
    if (Objects.nonNull(value)) {
      return acuentaTercerosMapper.mapAcuentaComprobanteToAcuentaCfdi(value);
    }
    return null;
  }
}
