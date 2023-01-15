package com.unknown.cfdi.mappers.auxiliars;

import com.google.common.collect.ImmutableList;
import com.unknown.cfdi.mappers.CfdiRelacionadoMapper;
import com.unknown.cfdi.mappers.ConceptoMapper;
import com.unknown.cfdi.mappers.RetencionMapper;
import com.unknown.cfdi.mappers.TimbreFiscalDigitalMapper;
import com.unknown.cfdi.mappers.TrasladoMapper;
import com.unknown.cfdi.mappers.pagos.PagosMapper;
import com.unknown.cfdi.modelos.CfdiRelacionado;
import com.unknown.cfdi.modelos.CfdiRelacionados;
import com.unknown.cfdi.modelos.Concepto;
import com.unknown.cfdi.modelos.Impuesto;
import com.unknown.cfdi.modelos.Retencion;
import com.unknown.cfdi.modelos.Traslado;
import com.unknown.cfdi.modelos.complementos.pagos.Pagos;
import com.unknown.cfdi.modelos.complementos.timbrefiscaldigital.TimbreFiscalDigital;
import com.unknown.models.generated.CUsoCFDI;
import com.unknown.models.generated.Comprobante;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.mapstruct.factory.Mappers;

public class CfdiAuxiliarMapper {

  public static final ConceptoMapper conceptoMapper = Mappers.getMapper(ConceptoMapper.class);
  public static final CfdiRelacionadoMapper relacionadoMapper =
      Mappers.getMapper(CfdiRelacionadoMapper.class);
  public static final RetencionMapper retencionMapper = Mappers.getMapper(RetencionMapper.class);
  public static final TrasladoMapper trasladoMapper = Mappers.getMapper(TrasladoMapper.class);
  public static final TimbreFiscalDigitalMapper timbreFiscalDigitalMapper =
      Mappers.getMapper(TimbreFiscalDigitalMapper.class);
  public static final PagosMapper pagosMapper = Mappers.getMapper(PagosMapper.class);

  public Comprobante.Conceptos mapConceptos(List<Concepto> value) {
    Comprobante.Conceptos concepto = new Comprobante.Conceptos();
    List<Comprobante.Conceptos.Concepto> listaConcepto = concepto.getConcepto();
    listaConcepto.addAll(conceptoMapper.mapConceptosCfdiToComprobanteConceptos(value));
    return concepto;
  }

  // Metodos usados para la compilacion del Mapper
  public Comprobante.Impuestos mapImpuestos(List<Impuesto> value) {
    return null;
  }

  public Comprobante.Complemento mapComplemento(List<Object> value) {
    if (Objects.nonNull(value) && value.size() > 0) {
      Comprobante.Complemento complemento = new Comprobante.Complemento();
      List<TimbreFiscalDigital> listTimbreFiscalDigital =
          value.stream()
              .filter(o -> o instanceof TimbreFiscalDigital)
              .map(o -> (TimbreFiscalDigital) o)
              .collect(Collectors.toList());
      if (!listTimbreFiscalDigital.isEmpty()) {
        listTimbreFiscalDigital.stream()
            .map(t -> timbreFiscalDigitalMapper.mapTimbreFiscalComplementosToGenerated(t))
            .forEach(e -> complemento.getAny().add(e));
      }
      List<Pagos> listPagos =
          value.stream()
              .filter(o -> o instanceof Pagos)
              .map(o -> (Pagos) o)
              .collect(Collectors.toList());
      if (!listPagos.isEmpty()) {
        listPagos.stream()
            .map(p -> pagosMapper.pagosToGeneratedPagos(p))
            .forEach(e -> complemento.getAny().add(e));
      }

      return complemento;
    }
    return null;
  }

  // Metodos usados para la compilacion del Mapper
  public Comprobante.Addenda mapAddenda(List<Object> value) {
    return null;
  }

  public List<Comprobante.CfdiRelacionados> mapRelacionados(CfdiRelacionados value) {
    if (Objects.nonNull(value)) {
      Comprobante.CfdiRelacionados cfdiRelacionados = new Comprobante.CfdiRelacionados();
      cfdiRelacionados.setTipoRelacion(value.getTipoRelacion());
      cfdiRelacionados
          .getCfdiRelacionado()
          .addAll(relacionadoMapper.mapList(value.getCfdiRelacionado()));
      return ImmutableList.of(cfdiRelacionados);
    } else {
      return null;
    }
  }

  public CfdiRelacionados mapComprobanteRelacionados(List<Comprobante.CfdiRelacionados> value) {
    if (Objects.nonNull(value) && !value.isEmpty()) {
      if (!value.isEmpty()) {
        String tipoRelacion = value.stream().findFirst().get().getTipoRelacion();
        return new CfdiRelacionados(
            value.stream()
                .flatMap(e -> e.getCfdiRelacionado().stream())
                .map(
                    a ->
                        CfdiRelacionado.builder()
                            .tipoRelacion(tipoRelacion)
                            .uuid(a.getUUID())
                            .build())
                .collect(Collectors.toList()),
            tipoRelacion);
      }
      return new CfdiRelacionados(new ArrayList<>(), "");
    }
    return null;
  }

  public List<Concepto> map(Comprobante.Conceptos value) {
    if (Objects.nonNull(value) && !value.getConcepto().isEmpty()) {
      return value.getConcepto().stream()
          .map(e -> conceptoMapper.mapComprobanteConceptoToConceptoCfdi(e))
          .collect(Collectors.toList());
    }
    return new ArrayList<>();
  }

  public List<Impuesto> map(Comprobante.Impuestos value) {
    if (Objects.nonNull(value)) {

      List<Retencion> retencionConceptoList = null;
      List<Traslado> trasladoConceptos = null;
      if (Objects.nonNull(value.getRetenciones())
          && Objects.nonNull(value.getRetenciones().getRetencion())) {
        retencionConceptoList =
            value.getRetenciones().getRetencion().stream()
                .map(e -> retencionMapper.mapRetencionComprobanteToCfdi(e))
                .collect(Collectors.toList());
      }
      if (Objects.nonNull(value.getTraslados())
          && Objects.nonNull(value.getTraslados().getTraslado())) {
        trasladoConceptos =
            value.getTraslados().getTraslado().stream()
                .map(e -> trasladoMapper.mapTrasladoComprobanteToCfdi(e))
                .collect(Collectors.toList());
      }
      Impuesto impuesto =
          Impuesto.builder()
              .totalImpuestosRetenidos(value.getTotalImpuestosRetenidos())
              .totalImpuestosTrasladados(value.getTotalImpuestosTrasladados())
              .retenciones(retencionConceptoList)
              .traslados(trasladoConceptos)
              .build();
      return ImmutableList.of(impuesto);
    }
    return new ArrayList<>();
  }

  public List<Object> map(Comprobante.Complemento value) {
    return new ArrayList<>();
  }

  public List<Object> map(Comprobante.Addenda value) {
    return new ArrayList<>();
  }

  public com.unknown.models.generated.CUsoCFDI mapUsoCfDI(String value) {
    return CUsoCFDI.fromValue(value);
  }

  public String mapUsoCfDI(com.unknown.models.generated.CUsoCFDI value) {
    return value.value();
  }
}
