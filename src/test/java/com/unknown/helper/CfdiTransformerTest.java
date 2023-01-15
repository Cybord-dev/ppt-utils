package com.unknown.helper;

import static org.junit.jupiter.api.Assertions.*;

import com.unknown.error.XMLParserException;
import com.unknown.models.generated.CMetodoPago;
import com.unknown.models.generated.CMoneda;
import com.unknown.models.generated.CTipoDeComprobante;
import com.unknown.models.generated.CTipoFactor;
import com.unknown.models.generated.CUsoCFDI;
import com.unknown.models.generated.Comprobante;
import com.unknown.models.generated.Pagos;
import com.unknown.models.generated.Retenciones;
import com.unknown.models.generated.TimbreFiscalDigital;
import com.unknown.util.NamespaceConstants;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CfdiTransformerTest {

  private static final Logger log = LoggerFactory.getLogger(CfdiTransformerTest.class);

  @Test
  public void xmlToCfdiModel_test() throws FileNotFoundException, XMLParserException {

    InputStream is = new FileInputStream("./src/test/resources/cfdi-samples/pagos-impuestos.xml");

    Comprobante comprobante = CfdiTransformer.xmlToCfdiModel(is);

    assertNotNull(comprobante);

    assertEquals("SPR190613I52", comprobante.getEmisor().getRfc());

    assertEquals("MASO451221PM4", comprobante.getReceptor().getRfc());

    assertEquals(BigDecimal.ZERO, comprobante.getTotal());

    assertEquals("84111506", comprobante.getConceptos().getConcepto().get(0).getClaveProdServ());

    Comprobante.Complemento complemento = comprobante.getComplemento();

    Pagos pagos =
        (Pagos) complemento.getAny().stream().filter(c -> c instanceof Pagos).findAny().get();

    assertNotNull(pagos);

    assertEquals(new BigDecimal("1.16"), pagos.getPago().get(0).getMonto());

    assertEquals(
        "5BD612EA-DB91-441D-BF85-37F442796EC1",
        pagos.getPago().get(0).getDoctoRelacionado().get(0).getIdDocumento());

    assertEquals(
        BigInteger.ONE, pagos.getPago().get(0).getDoctoRelacionado().get(0).getNumParcialidad());
  }

  @Test
  public void cfdiMoldelToString_validateNamespacesGeneration() throws XMLParserException {
    Comprobante comprobante = new Comprobante();

    comprobante.setVersion("4.0");
    comprobante.setExportacion("01");
    comprobante.setFecha("2022-01-25T13:13:14");
    comprobante.setDescuento(BigDecimal.ZERO);
    comprobante.setFolio("20225310101440551");
    comprobante.setFormaPago("99");
    comprobante.setLugarExpedicion("04700");
    comprobante.setMetodoPago(CMetodoPago.PUE);
    comprobante.setMoneda(CMoneda.MXN);
    comprobante.setSubTotal(BigDecimal.ONE);
    comprobante.setTipoCambio(BigDecimal.ONE);
    comprobante.setTipoDeComprobante(CTipoDeComprobante.I);
    comprobante.setTotal(new BigDecimal("1.16"));

    Comprobante.Emisor emisor = new Comprobante.Emisor();
    emisor.setNombre("UNIVERSIDAD ROBOTICA ESPAÑOLA SA DE CV");
    emisor.setRfc("URE180429TM6");
    emisor.setRegimenFiscal("601");
    comprobante.setEmisor(emisor);

    Comprobante.Receptor receptor = new Comprobante.Receptor();
    receptor.setNombre("EDGAR JOSEDUARDO RAMOS SILVEYRA");
    receptor.setDomicilioFiscalReceptor("04600");
    receptor.setRfc("RASE8810158B9");
    receptor.setUsoCFDI(CUsoCFDI.G_03);
    receptor.setRegimenFiscalReceptor("601");
    comprobante.setReceptor(receptor);

    Comprobante.Conceptos conceptos = new Comprobante.Conceptos();
    Comprobante.Conceptos.Concepto concepto = new Comprobante.Conceptos.Concepto();
    concepto.setCantidad(BigDecimal.ONE);
    concepto.setClaveProdServ("84111506");
    concepto.setClaveUnidad("H87");
    concepto.setDescripcion("timbres");
    concepto.setDescuento(BigDecimal.ZERO);
    concepto.setImporte(BigDecimal.ONE);
    concepto.setObjetoImp("02");
    concepto.setUnidad("Pieza");
    concepto.setValorUnitario(BigDecimal.ONE);

    Comprobante.Conceptos.Concepto.Impuestos impuestos =
        new Comprobante.Conceptos.Concepto.Impuestos();
    Comprobante.Conceptos.Concepto.Impuestos.Traslados traslado =
        new Comprobante.Conceptos.Concepto.Impuestos.Traslados();
    Comprobante.Conceptos.Concepto.Impuestos.Traslados.Traslado iva =
        new Comprobante.Conceptos.Concepto.Impuestos.Traslados.Traslado();
    iva.setImporte(new BigDecimal("0.16"));
    iva.setBase(BigDecimal.ONE);
    iva.setImpuesto("002");
    iva.setTasaOCuota(new BigDecimal("0.16"));
    iva.setTipoFactor(CTipoFactor.TASA);
    traslado.getTraslado().add(iva);
    impuestos.setTraslados(traslado);
    concepto.setImpuestos(impuestos);
    conceptos.getConcepto().add(concepto);
    comprobante.setConceptos(conceptos);

    Comprobante.Complemento complemento = new Comprobante.Complemento();
    Pagos pagos = new Pagos();
    Pagos.Pago pago = new Pagos.Pago();
    pago.setFechaPago("2022-01-25T13:13:14");
    pago.setFormaDePagoP("03");
    pago.setMonedaP(CMoneda.MXN);
    pago.setMonto(BigDecimal.ONE);
    Pagos.Pago.DoctoRelacionado relacionado = new Pagos.Pago.DoctoRelacionado();
    relacionado.setFolio("20219391201412705");
    relacionado.setIdDocumento("4974BE6B-AE4A-4ED4-A44D-64F0980EE946");
    relacionado.setImpPagado(new BigDecimal("0.5"));
    relacionado.setImpSaldoAnt(BigDecimal.ONE);
    relacionado.setNumParcialidad(BigInteger.ONE);
    relacionado.setImpSaldoInsoluto(new BigDecimal("0.5"));
    relacionado.setMonedaDR(CMoneda.MXN);
    relacionado.setSerie("PFP");
    pago.getDoctoRelacionado().add(relacionado);
    pagos.setVersion("2.0");
    pagos.getPago().add(pago);
    complemento.getAny().add(pagos);
    comprobante.setComplemento(complemento);

    String xml = CfdiTransformer.cfdiMoldelToString(comprobante);

    assertTrue(xml.contains(NamespaceConstants.SAT_NS_4_PREFIX));
    assertTrue(xml.contains(NamespaceConstants.PAGO_PREFIX));
  }

  @Test
  public void xmlToRetencionModel_test() throws FileNotFoundException, XMLParserException {

    InputStream is =
        new FileInputStream("./src/test/resources/cfdi-samples/retencion-intereses-timbrada.xml");

    Retenciones retencion = CfdiTransformer.xmlToRetencionModel(is);

    assertNotNull(retencion);

    assertEquals("URE180429TM6", retencion.getEmisor().getRfcE());

    assertEquals("XAXX010101000", retencion.getReceptor().getNacional().getRfcR());

    assertEquals(BigDecimal.ZERO, retencion.getTotales().getMontoTotRet());

    assertEquals(BigDecimal.ONE, retencion.getTotales().getImpRetenidos().get(0).getBaseRet());

    Retenciones.Complemento complemento = retencion.getComplemento();

    TimbreFiscalDigital tfd =
        (TimbreFiscalDigital)
            complemento.getAny().stream()
                .filter(c -> c instanceof TimbreFiscalDigital)
                .findAny()
                .get();

    assertNotNull(tfd);

    assertEquals("90F8EDCC-3384-49EE-B0FB-9945E7497956", tfd.getUUID());

    assertEquals("2022-09-20T23:16:25", tfd.getFechaTimbrado());

    assertEquals("SPR190613I52", tfd.getRfcProvCertif());
  }

  @Test
  public void retencionMoldelToString() throws XMLParserException {
    Retenciones retencion = new Retenciones();
    Retenciones.Emisor emisor = new Retenciones.Emisor();
    emisor.setNomDenRazSocE("UNIVERSIDAD ROBOTICA ESPAÑOLA");
    emisor.setRfcE("URE180429TM6");
    emisor.setRegimenFiscalE("601");

    Retenciones.Receptor receptor = new Retenciones.Receptor();
    Retenciones.Receptor.Nacional nacional = new Retenciones.Receptor.Nacional();
    nacional.setRfcR("EKU9003173C9");
    nacional.setNomDenRazSocR("ESCUELA KEMPER URGATE");
    nacional.setDomicilioFiscalR("65000");
    receptor.setNacional(nacional);
    receptor.setNacionalidadR("Nacional");

    Retenciones.Periodo periodo = new Retenciones.Periodo();
    periodo.setEjercicio("2021");
    periodo.setMesFin("03");
    periodo.setMesIni("02");

    Retenciones.Totales totales = new Retenciones.Totales();
    Retenciones.Totales.ImpRetenidos impuestos = new Retenciones.Totales.ImpRetenidos();
    impuestos.setBaseRet(new BigDecimal("2000"));
    impuestos.setImpuestoRet("001");
    impuestos.setMontoRet(new BigDecimal("580"));
    impuestos.setTipoPagoRet("03");
    totales.getImpRetenidos().add(impuestos);
    totales.setMontoTotOperacion(new BigDecimal("2000"));
    totales.setMontoTotGrav(new BigDecimal("2000"));
    totales.setMontoTotExent(BigDecimal.ZERO);
    totales.setMontoTotRet(new BigDecimal("580"));

    retencion.setEmisor(emisor);
    retencion.setReceptor(receptor);
    retencion.setPeriodo(periodo);
    retencion.setTotales(totales);

    String xml = CfdiTransformer.retentionMoldelToString(retencion);
  }
}
