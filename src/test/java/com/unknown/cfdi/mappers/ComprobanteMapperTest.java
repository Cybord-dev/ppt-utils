package com.unknown.cfdi.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unknown.cfdi.modelos.ACuentaTerceros;
import com.unknown.cfdi.modelos.Cfdi;
import com.unknown.cfdi.modelos.Concepto;
import com.unknown.cfdi.modelos.ImpuestoConcepto;
import com.unknown.cfdi.modelos.Parte;
import com.unknown.cfdi.modelos.RetencionConcepto;
import com.unknown.cfdi.modelos.TrasladoConcepto;
import com.unknown.models.generated.Comprobante;
import java.io.File;
import java.io.IOException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

@Slf4j
public class ComprobanteMapperTest {

  private ObjectMapper objectMapper = new ObjectMapper();
  public static final ConceptoMapper conceptoMapper = Mappers.getMapper(ConceptoMapper.class);
  public static final CfdiMapper cfdiMapper = Mappers.getMapper(CfdiMapper.class);

  @Test
  public void parteConceptoMapperTest() throws IOException {
    Comprobante.Conceptos.Concepto conceptoComprobante =
        objectMapper
            .readValue(
                new File("src/test/resources/json/comprobanteConceptos.json"),
                Comprobante.Conceptos.class)
            .getConcepto()
            .get(0);
    Concepto conceptoCfdi =
        conceptoMapper.mapComprobanteConceptoToConceptoCfdi(conceptoComprobante);

    Parte parteCfdi = conceptoCfdi.getParte();
    Comprobante.Conceptos.Concepto.Parte parteComprobante = conceptoComprobante.getParte().get(0);

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
  public void aCuentaTercerosMapperTest() throws IOException {
    Comprobante.Conceptos.Concepto conceptoComprobante =
        objectMapper
            .readValue(
                new File("src/test/resources/json/comprobanteConceptos.json"),
                Comprobante.Conceptos.class)
            .getConcepto()
            .get(0);
    Concepto conceptoCfdi =
        conceptoMapper.mapComprobanteConceptoToConceptoCfdi(conceptoComprobante);

    ACuentaTerceros aCuentaTercerosCfdi = conceptoCfdi.getACuentaTerceros();
    Comprobante.Conceptos.Concepto.ACuentaTerceros aCuentaTercerosComprobante =
        conceptoComprobante.getACuentaTerceros();
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
  public void conceptoComprobanteTest() throws IOException {
    Comprobante.Conceptos.Concepto conceptoComprobante =
        objectMapper
            .readValue(
                new File("src/test/resources/json/comprobanteConceptos.json"),
                Comprobante.Conceptos.class)
            .getConcepto()
            .get(0);
    Concepto conceptoCfdi =
        conceptoMapper.mapComprobanteConceptoToConceptoCfdi(conceptoComprobante);

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
  public void impuestosConceptoMapperTest() throws IOException {
    Comprobante.Conceptos conceptosComprobante =
        objectMapper.readValue(
            new File("src/test/resources/json/comprobanteConceptos.json"),
            Comprobante.Conceptos.class);

    log.debug("Conceptos a probar {}", conceptosComprobante.getConcepto().size());
    for (int concepto = 0; concepto < conceptosComprobante.getConcepto().size(); concepto++) {
      ImpuestoConcepto impuestoConcepto =
          conceptoMapper
              .mapComprobanteConceptoToConceptoCfdi(
                  conceptosComprobante.getConcepto().get(concepto))
              .getImpuestos()
              .get(0);

      List<RetencionConcepto> retencionCfdiList = impuestoConcepto.getRetenciones();
      List<TrasladoConcepto> trasladoCfdiList = impuestoConcepto.getTraslados();

      Comprobante.Conceptos.Concepto.Impuestos comprobanteImpuesto =
          conceptosComprobante.getConcepto().get(concepto).getImpuestos();

      for (int retencion = 0;
          retencion < comprobanteImpuesto.getRetenciones().getRetencion().size();
          retencion++) {
        Comprobante.Conceptos.Concepto.Impuestos.Retenciones.Retencion retencionComprobante =
            comprobanteImpuesto.getRetenciones().getRetencion().get(retencion);

        RetencionConcepto retencionCfdi = retencionCfdiList.get(retencion);
        log.debug("Retencion {}", retencionCfdi);
        assertEquals(retencionComprobante.getBase(), retencionCfdi.getBase());
        assertEquals(retencionComprobante.getImporte(), retencionCfdi.getImporte());
        assertEquals(retencionComprobante.getTasaOCuota(), retencionCfdi.getTasaOCuota());
        assertEquals(retencionComprobante.getImpuesto(), retencionCfdi.getImpuesto());
        assertEquals(retencionComprobante.getTipoFactor().value(), retencionCfdi.getTipoFactor());
      }

      for (int traslado = 0;
          traslado < comprobanteImpuesto.getTraslados().getTraslado().size();
          traslado++) {

        Comprobante.Conceptos.Concepto.Impuestos.Traslados.Traslado trasladoComprobante =
            comprobanteImpuesto.getTraslados().getTraslado().get(traslado);

        TrasladoConcepto trasladoCfdi = trasladoCfdiList.get(traslado);
        log.debug("Traslado {}", trasladoCfdi);
        assertEquals(trasladoComprobante.getBase(), trasladoCfdi.getBase());
        assertEquals(trasladoComprobante.getImporte(), trasladoCfdi.getImporte());
        assertEquals(trasladoComprobante.getTasaOCuota(), trasladoCfdi.getTasaOCuota());
        assertEquals(trasladoComprobante.getImpuesto(), trasladoCfdi.getImpuesto());
        assertEquals(trasladoComprobante.getTipoFactor().value(), trasladoCfdi.getTipoFactor());
      }
    }
  }

  @Test
  public void ComprobanteMapperTest() throws IOException {
    Comprobante comprobante =
        objectMapper.readValue(
            new File("src/test/resources/json/comprobante.json"), Comprobante.class);

    Cfdi cfdi = cfdiMapper.comprobanteToCfdi(comprobante);
    log.debug("CFDI {}", cfdi);

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
    assertEquals(comprobante.getConceptos().getConcepto().size(), cfdi.getConceptos().size());
  }

  @Test
  public void impuestosTest() throws IOException {
    Comprobante comprobante =
        objectMapper.readValue(
            new File("src/test/resources/json/comprobante.json"), Comprobante.class);

    Cfdi cfdi = cfdiMapper.comprobanteToCfdi(comprobante);

    assertEquals(
        comprobante.getImpuestos().getTotalImpuestosTrasladados(),
        cfdi.getImpuestos().get(0).getTotalImpuestosTrasladados());
    assertEquals(
        comprobante.getImpuestos().getTotalImpuestosRetenidos(),
        cfdi.getImpuestos().get(0).getTotalImpuestosRetenidos());

    assertEquals(
        comprobante.getImpuestos().getTraslados().getTraslado().size(),
        cfdi.getImpuestos().get(0).getTraslados().size());
    assertEquals(
        comprobante.getImpuestos().getRetenciones().getRetencion().size(),
        cfdi.getImpuestos().get(0).getRetenciones().size());

    for (int retenidos = 0;
        retenidos < comprobante.getImpuestos().getRetenciones().getRetencion().size();
        retenidos++) {
      assertEquals(
          comprobante.getImpuestos().getRetenciones().getRetencion().get(retenidos).getImporte(),
          cfdi.getImpuestos().get(0).getRetenciones().get(retenidos).getImporte());
      assertEquals(
          comprobante.getImpuestos().getRetenciones().getRetencion().get(retenidos).getImpuesto(),
          cfdi.getImpuestos().get(0).getRetenciones().get(retenidos).getImpuesto());
    }

    for (int traslados = 0;
        traslados < comprobante.getImpuestos().getTraslados().getTraslado().size();
        traslados++) {
      assertEquals(
          comprobante.getImpuestos().getTraslados().getTraslado().get(traslados).getImporte(),
          cfdi.getImpuestos().get(0).getTraslados().get(traslados).getImporte());
      assertEquals(
          comprobante.getImpuestos().getTraslados().getTraslado().get(traslados).getImpuesto(),
          cfdi.getImpuestos().get(0).getTraslados().get(traslados).getImpuesto());
      assertEquals(
          comprobante.getImpuestos().getTraslados().getTraslado().get(traslados).getBase(),
          cfdi.getImpuestos().get(0).getTraslados().get(traslados).getBase());
      assertEquals(
          comprobante
              .getImpuestos()
              .getTraslados()
              .getTraslado()
              .get(traslados)
              .getTipoFactor()
              .value(),
          cfdi.getImpuestos().get(0).getTraslados().get(traslados).getTipoFactor());
      assertEquals(
          comprobante.getImpuestos().getTraslados().getTraslado().get(traslados).getTasaOCuota(),
          cfdi.getImpuestos().get(0).getTraslados().get(traslados).getTasaOCuota());
    }
  }
}
