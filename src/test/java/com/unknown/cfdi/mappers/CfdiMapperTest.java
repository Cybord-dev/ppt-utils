package com.unknown.cfdi.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.unknown.cfdi.modelos.ACuentaTerceros;
import com.unknown.cfdi.modelos.Cfdi;
import com.unknown.cfdi.modelos.CfdiRelacionados;
import com.unknown.cfdi.modelos.Concepto;
import com.unknown.cfdi.modelos.Emisor;
import com.unknown.cfdi.modelos.Parte;
import com.unknown.cfdi.modelos.Receptor;
import com.unknown.cfdi.modelos.RetencionConcepto;
import com.unknown.cfdi.modelos.TrasladoConcepto;
import com.unknown.models.generated.CTipoDeComprobante;
import com.unknown.models.generated.Comprobante;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

@Slf4j
public class CfdiMapperTest {

  private Cfdi cfdi;
  private ObjectMapper objectMapper = new ObjectMapper();
  public static final CfdiMapper mapper = Mappers.getMapper(CfdiMapper.class);

  @BeforeEach
  public void init() {
    cfdi =
        Cfdi.builder()
            .serie("123456")
            .formaPago("Efectivo")
            .subtotal(BigDecimal.ONE)
            .descuento(BigDecimal.ZERO)
            .total(BigDecimal.ONE)
            .moneda("MXN")
            .tipoDeComprobante(CTipoDeComprobante.E.value())
            .exportacion("No")
            .lugarExpedicion("Zacatlan Puebla")
            .emisor(
                Emisor.builder().rfc("EASC").nombre("Janios").regimenFiscal("Asalariado").build())
            .receptor(
                Receptor.builder()
                    .rfc("EASC")
                    .nombre("Janios")
                    .domicilioFiscalReceptor("Toluca")
                    .regimenFiscalReceptor("Arrendamiento")
                    .usoCfdi("G01")
                    .build())
            .conceptos(ImmutableList.of())
            .impuestos(ImmutableList.of())
            .build();
  }

  @Test
  public void retencionImpuestoMapperTest() throws IOException {
    Concepto concepto =
        objectMapper.readValue(new File("src/test/resources/json/concepto.json"), Concepto.class);
    Concepto concepto2 =
        objectMapper.readValue(new File("src/test/resources/json/concepto2.json"), Concepto.class);

    cfdi.setConceptos(ImmutableList.of(concepto, concepto2));

    Comprobante comprobante = mapper.cfdiToComprobante(cfdi);

    assertEquals(comprobante.getConceptos().getConcepto().size(), cfdi.getConceptos().size());

    log.debug("Conceptos a probar {}", cfdi.getConceptos().size());
    for (int i = 0; i < cfdi.getConceptos().size(); i++) {
      if (Objects.nonNull(
          comprobante.getConceptos().getConcepto().get(i).getImpuestos().getRetenciones())) {
        List<Comprobante.Conceptos.Concepto.Impuestos.Retenciones.Retencion>
            retencionComprobanteList =
                comprobante
                    .getConceptos()
                    .getConcepto()
                    .get(i)
                    .getImpuestos()
                    .getRetenciones()
                    .getRetencion();
        List<RetencionConcepto> retencionCfdiList =
            cfdi.getConceptos().get(i).getImpuestos().get(0).getRetenciones();

        assertEquals(retencionComprobanteList.size(), retencionCfdiList.size());
        for (int retencion = 0; retencion < retencionComprobanteList.size(); retencion++) {
          Comprobante.Conceptos.Concepto.Impuestos.Retenciones.Retencion retencionComprobante =
              retencionComprobanteList.get(retencion);
          RetencionConcepto retencionCfdi = retencionCfdiList.get(retencion);

          assertEquals(retencionComprobante.getBase(), retencionCfdi.getBase());
          assertEquals(retencionComprobante.getImporte(), retencionCfdi.getImporte());
          assertEquals(retencionComprobante.getTasaOCuota(), retencionCfdi.getTasaOCuota());
          assertEquals(retencionComprobante.getImpuesto(), retencionCfdi.getImpuesto());
          assertEquals(retencionComprobante.getTipoFactor().value(), retencionCfdi.getTipoFactor());
        }
      }
    }
  }

  @Test
  public void trasladoImpuestoMapperTest() throws IOException {
    Concepto concepto =
        objectMapper.readValue(new File("src/test/resources/json/concepto.json"), Concepto.class);
    Concepto concepto2 =
        objectMapper.readValue(new File("src/test/resources/json/concepto2.json"), Concepto.class);

    cfdi.setConceptos(ImmutableList.of(concepto, concepto2));

    Comprobante comprobante = mapper.cfdiToComprobante(cfdi);

    assertEquals(comprobante.getConceptos().getConcepto().size(), cfdi.getConceptos().size());

    for (int i = 0; i < cfdi.getConceptos().size(); i++) {

      List<Comprobante.Conceptos.Concepto.Impuestos.Traslados.Traslado> trasladoComprobanteList =
          comprobante
              .getConceptos()
              .getConcepto()
              .get(i)
              .getImpuestos()
              .getTraslados()
              .getTraslado();
      List<TrasladoConcepto> trasladoCfdiList =
          cfdi.getConceptos().get(i).getImpuestos().get(0).getTraslados();

      assertEquals(trasladoComprobanteList.size(), trasladoCfdiList.size());

      for (int traslado = 0; traslado < trasladoComprobanteList.size(); traslado++) {
        Comprobante.Conceptos.Concepto.Impuestos.Traslados.Traslado trasladoComprobante =
            trasladoComprobanteList.get(traslado);

        TrasladoConcepto trasladoCfdi = trasladoCfdiList.get(traslado);

        assertEquals(
            trasladoComprobante.getBase().setScale(2, RoundingMode.HALF_UP),
            trasladoCfdi.getBase());
        assertEquals(
            trasladoComprobante.getImporte().setScale(2, RoundingMode.HALF_UP),
            trasladoCfdi.getImporte());
        assertEquals(trasladoComprobante.getTasaOCuota(), trasladoCfdi.getTasaOCuota());
        assertEquals(trasladoComprobante.getImpuesto(), trasladoCfdi.getImpuesto());
        assertEquals(trasladoComprobante.getTipoFactor().value(), trasladoCfdi.getTipoFactor());
      }
    }
  }

  @Test
  public void parteConceptoMapperTest() throws IOException {
    Concepto concepto =
        objectMapper.readValue(new File("src/test/resources/json/concepto.json"), Concepto.class);

    cfdi.setConceptos(ImmutableList.of(concepto));

    Comprobante comprobante = mapper.cfdiToComprobante(cfdi);

    Comprobante.Conceptos.Concepto.Parte parteComprobante =
        comprobante.getConceptos().getConcepto().get(0).getParte().get(0);
    Parte parteCfdi = cfdi.getConceptos().get(0).getParte();

    assertEquals(
        parteComprobante.getInformacionAduanera().get(0).getNumeroPedimento(),
        parteCfdi.getInformacionAduanera().getNumeroPedimento());
    assertEquals(parteComprobante.getNoIdentificacion(), parteCfdi.getNoIdentificacion());
    assertEquals(parteComprobante.getClaveProdServ(), parteCfdi.getClaveProdServ());
    assertEquals(parteComprobante.getCantidad(), parteCfdi.getCantidad());
    assertEquals(parteComprobante.getUnidad(), parteCfdi.getUnidad());
    assertEquals(parteComprobante.getDescripcion(), parteCfdi.getDescripcion());
    assertEquals(parteComprobante.getValorUnitario(), parteCfdi.getValorUnitario());
    assertEquals(parteComprobante.getImporte(), parteCfdi.getImporte());
  }

  @Test
  public void conceptoMapperTest() throws IOException {
    Concepto concepto =
        objectMapper.readValue(new File("src/test/resources/json/concepto.json"), Concepto.class);

    cfdi.setConceptos(ImmutableList.of(concepto));

    Comprobante comprobante = mapper.cfdiToComprobante(cfdi);
    Comprobante.Conceptos.Concepto conceptoComprobante =
        comprobante.getConceptos().getConcepto().get(0);
    Concepto conceptoCfdi = cfdi.getConceptos().get(0);

    assertEquals(conceptoComprobante.getClaveProdServ(), conceptoCfdi.getClaveProdServ());
    assertEquals(conceptoComprobante.getNoIdentificacion(), conceptoCfdi.getNoIdentificacion());
    assertEquals(conceptoComprobante.getCantidad(), conceptoCfdi.getCantidad());
    assertEquals(conceptoComprobante.getClaveUnidad(), conceptoCfdi.getClaveUnidad());
    assertEquals(conceptoComprobante.getUnidad(), conceptoCfdi.getUnidad());
    assertEquals(conceptoComprobante.getDescripcion(), conceptoCfdi.getDescripcion());
    assertEquals(conceptoComprobante.getValorUnitario(), conceptoCfdi.getValorUnitario());
    assertEquals(conceptoComprobante.getImporte(), conceptoCfdi.getImporte());
    assertEquals(conceptoComprobante.getDescuento(), conceptoCfdi.getDescuento());
    assertEquals(conceptoComprobante.getObjetoImp(), conceptoCfdi.getObjetoImp());
    assertEquals(
        conceptoComprobante.getInformacionAduanera().get(0).getNumeroPedimento(),
        conceptoCfdi.getInformacionAduanera().getNumeroPedimento());
    assertEquals(
        conceptoComprobante.getCuentaPredial().get(0).getNumero(),
        conceptoCfdi.getCuentaPredial().getNumero());
  }

  @Test
  public void aCuentaTercerosMapperTest() throws IOException {
    Concepto concepto =
        objectMapper.readValue(new File("src/test/resources/json/concepto.json"), Concepto.class);

    cfdi.setConceptos(ImmutableList.of(concepto));

    Comprobante comprobante = mapper.cfdiToComprobante(cfdi);

    ACuentaTerceros aCuentaTercerosCfdi = cfdi.getConceptos().get(0).getACuentaTerceros();
    Comprobante.Conceptos.Concepto.ACuentaTerceros aCuentaTercerosComprobante =
        comprobante.getConceptos().getConcepto().get(0).getACuentaTerceros();

    assertEquals(
        aCuentaTercerosComprobante.getNombreACuentaTerceros(),
        aCuentaTercerosCfdi.getNombreAcuentaTerceros());
    assertEquals(
        aCuentaTercerosComprobante.getRfcACuentaTerceros(),
        aCuentaTercerosCfdi.getRfcAcuentaTerceros());
    assertEquals(
        aCuentaTercerosComprobante.getRegimenFiscalACuentaTerceros(),
        aCuentaTercerosCfdi.getRegimenFiscalAcuentaTerceros());
    assertEquals(
        aCuentaTercerosComprobante.getDomicilioFiscalACuentaTerceros(),
        aCuentaTercerosCfdi.getDomicilioFiscalAcuentaTerceros());
  }

  @Test
  public void cfdiRelacionadosTest() throws IOException {
    CfdiRelacionados relacionados =
        objectMapper.readValue(
            new File("src/test/resources/json/cfdiRelacionados.json"), CfdiRelacionados.class);

    cfdi.setCfdiRelacionados(relacionados);

    Comprobante comprobante = mapper.cfdiToComprobante(cfdi);
    Comprobante.CfdiRelacionados comprobanteCfiRelacionados =
        comprobante.getCfdiRelacionados().get(0);
    CfdiRelacionados cfdiRelacionados = cfdi.getCfdiRelacionados();

    assertEquals(comprobanteCfiRelacionados.getTipoRelacion(), cfdiRelacionados.getTipoRelacion());
    assertEquals(
        comprobanteCfiRelacionados.getCfdiRelacionado().size(),
        cfdiRelacionados.getCfdiRelacionado().size());

    for (int i = 0; i < cfdiRelacionados.getCfdiRelacionado().size(); i++) {
      assertEquals(
          comprobanteCfiRelacionados.getCfdiRelacionado().get(i).getUUID(),
          cfdiRelacionados.getCfdiRelacionado().get(i).getUuid());
    }
  }

  @Test
  public void cfdiTest() throws IOException {
    Cfdi cfdi = objectMapper.readValue(new File("src/test/resources/json/cfdi.json"), Cfdi.class);

    Comprobante comprobante = mapper.cfdiToComprobante(cfdi);

    assertEquals(comprobante.getSerie(), cfdi.getSerie());
    assertEquals(comprobante.getFolio(), cfdi.getFolio());
    assertEquals(comprobante.getFecha(), cfdi.getFecha());
    assertEquals(comprobante.getSello(), cfdi.getSello());
    assertEquals(comprobante.getFormaPago(), cfdi.getFormaPago());
    assertEquals(comprobante.getMetodoPago().value(), cfdi.getMetodoPago());
    assertEquals(comprobante.getNoCertificado(), cfdi.getNoCertificado());
    assertEquals(comprobante.getCertificado(), cfdi.getCertificado());
    assertEquals(comprobante.getCondicionesDePago(), cfdi.getCondicionesDePago());
    assertEquals(comprobante.getSubTotal(), cfdi.getSubtotal());
    assertEquals(comprobante.getDescuento(), cfdi.getDescuento());
    assertEquals(comprobante.getTotal(), cfdi.getTotal());
    assertEquals(comprobante.getMoneda().value(), cfdi.getMoneda());
    assertEquals(comprobante.getTipoCambio(), cfdi.getTipoCambio());
    assertEquals(comprobante.getExportacion(), cfdi.getExportacion());
    assertEquals(comprobante.getLugarExpedicion(), cfdi.getLugarExpedicion());
    assertEquals(comprobante.getConfirmacion(), cfdi.getConfirmacion());
    assertEquals(
        comprobante.getInformacionGlobal().getPeriodicidad(),
        cfdi.getInformacionGlobal().getPeriodicidad());
    assertEquals(
        comprobante.getInformacionGlobal().getMeses(), cfdi.getInformacionGlobal().getMeses());
    assertEquals(
        comprobante.getInformacionGlobal().getAño(),
        cfdi.getInformacionGlobal().getAnio().shortValue());
    assertEquals(comprobante.getEmisor().getRfc(), cfdi.getEmisor().getRfc());
    assertEquals(comprobante.getEmisor().getNombre(), cfdi.getEmisor().getNombre());
    assertEquals(comprobante.getEmisor().getRegimenFiscal(), cfdi.getEmisor().getRegimenFiscal());
    assertEquals(
        comprobante.getEmisor().getFacAtrAdquirente(), cfdi.getEmisor().getFacAtrAdquirente());
    assertEquals(comprobante.getReceptor().getRfc(), cfdi.getReceptor().getRfc());
    assertEquals(comprobante.getReceptor().getNombre(), cfdi.getReceptor().getNombre());
    assertEquals(
        comprobante.getReceptor().getDomicilioFiscalReceptor(),
        cfdi.getReceptor().getDomicilioFiscalReceptor());
    assertEquals(
        comprobante.getReceptor().getResidenciaFiscal().value(),
        cfdi.getReceptor().getResidenciaFiscal());
    assertEquals(comprobante.getReceptor().getNumRegIdTrib(), cfdi.getReceptor().getNumRegIdTrib());
    assertEquals(
        comprobante.getReceptor().getRegimenFiscalReceptor(),
        cfdi.getReceptor().getRegimenFiscalReceptor());
    assertEquals(comprobante.getReceptor().getUsoCFDI().value(), cfdi.getReceptor().getUsoCfdi());

    log.debug(objectMapper.writeValueAsString(comprobante));
  }

  @Test
  public void impuestosTest() throws IOException {
    Concepto concepto =
        objectMapper.readValue(new File("src/test/resources/json/concepto.json"), Concepto.class);
    Concepto concepto2 =
        objectMapper.readValue(new File("src/test/resources/json/concepto2.json"), Concepto.class);

    cfdi.setConceptos(ImmutableList.of(concepto, concepto2));

    Comprobante comprobante = mapper.cfdiToComprobante(cfdi);

    assertEquals(BigDecimal.valueOf(0.04), comprobante.getImpuestos().getTotalImpuestosRetenidos());
    assertEquals(
        BigDecimal.valueOf(3.20).setScale(2, RoundingMode.HALF_UP),
        comprobante.getImpuestos().getTotalImpuestosTrasladados());
    assertEquals(comprobante.getImpuestos().getRetenciones().getRetencion().size(), 1);
    assertEquals(comprobante.getImpuestos().getTraslados().getTraslado().size(), 1);
    assertEquals(
        BigDecimal.valueOf(3.20).setScale(2, RoundingMode.HALF_UP),
        comprobante.getImpuestos().getTraslados().getTraslado().get(0).getImporte());
  }
}
