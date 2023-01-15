package com.unknown.cfdi.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unknown.cfdi.mappers.pagos.DocumentoRelacionadoMapper;
import com.unknown.cfdi.mappers.pagos.PagosMapper;
import com.unknown.cfdi.modelos.complementos.pagos.DocumentoRelacionado;
import com.unknown.cfdi.modelos.complementos.pagos.Pagos;
import com.unknown.models.generated.Pagos.Pago.DoctoRelacionado;
import java.io.File;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

@Slf4j
public class ComplementosGeneradosMapperTest {

  private ObjectMapper objectMapper = new ObjectMapper();
  public static final DocumentoRelacionadoMapper documentoRelacionadoMapper =
      Mappers.getMapper(DocumentoRelacionadoMapper.class);
  public static final PagosMapper pagosMapper = Mappers.getMapper(PagosMapper.class);

  @Test
  public void documentoRelacionadoMapperTest() throws IOException {

    Pagos pagos =
        objectMapper.readValue(new File("src/test/resources/json/pagos.json"), Pagos.class);

    DocumentoRelacionado documentoRelacionado = pagos.getPagos().get(0).getRelacionados().get(0);

    DoctoRelacionado doctoRelacionado =
        documentoRelacionadoMapper.documentoRelacionadoToDoctoRelacionado(documentoRelacionado);

    assertEquals(doctoRelacionado.getSerie(), documentoRelacionado.getSerie());
    assertEquals(doctoRelacionado.getFolio(), documentoRelacionado.getFolio());
    assertEquals(doctoRelacionado.getIdDocumento(), documentoRelacionado.getIdDocumento());
    assertEquals(doctoRelacionado.getObjetoImpDR(), documentoRelacionado.getObjetoImpDR());
    assertEquals(doctoRelacionado.getMonedaDR().value(), documentoRelacionado.getMonedaDR());
    assertEquals(
        doctoRelacionado.getNumParcialidad().intValue(), documentoRelacionado.getNumParcialidad());
    assertEquals(doctoRelacionado.getImpPagado(), documentoRelacionado.getImpPagado());
    assertEquals(doctoRelacionado.getImpSaldoAnt(), documentoRelacionado.getImpSaldoAnt());
    assertEquals(
        doctoRelacionado.getImpSaldoInsoluto(), documentoRelacionado.getImpSaldoInsoluto());
    assertEquals(doctoRelacionado.getEquivalenciaDR(), documentoRelacionado.getEquivalenciaDR());
    assertEquals(
        doctoRelacionado
            .getImpuestosDR()
            .getRetencionesDR()
            .getRetencionDR()
            .get(0)
            .getImpuestoDR(),
        documentoRelacionado
            .getImpuestosDR()
            .getRetencionesDR()
            .getRetencionDR()
            .get(0)
            .getImpuestoDR());
    assertEquals(
        doctoRelacionado.getImpuestosDR().getRetencionesDR().getRetencionDR().get(0).getBaseDR(),
        documentoRelacionado
            .getImpuestosDR()
            .getRetencionesDR()
            .getRetencionDR()
            .get(0)
            .getBaseDR());
    assertEquals(
        doctoRelacionado.getImpuestosDR().getRetencionesDR().getRetencionDR().get(0).getImporteDR(),
        documentoRelacionado
            .getImpuestosDR()
            .getRetencionesDR()
            .getRetencionDR()
            .get(0)
            .getImporteDR());
    assertEquals(
        doctoRelacionado
            .getImpuestosDR()
            .getRetencionesDR()
            .getRetencionDR()
            .get(0)
            .getTasaOCuotaDR(),
        documentoRelacionado
            .getImpuestosDR()
            .getRetencionesDR()
            .getRetencionDR()
            .get(0)
            .getTasaOCuotaDR());
    assertEquals(
        doctoRelacionado
            .getImpuestosDR()
            .getRetencionesDR()
            .getRetencionDR()
            .get(0)
            .getTipoFactorDR(),
        documentoRelacionado
            .getImpuestosDR()
            .getRetencionesDR()
            .getRetencionDR()
            .get(0)
            .getTipoFactorDR());
    assertEquals(
        doctoRelacionado.getImpuestosDR().getTrasladosDR().getTrasladoDR().get(0).getTipoFactorDR(),
        documentoRelacionado
            .getImpuestosDR()
            .getTrasladosDR()
            .getTrasladoDR()
            .get(0)
            .getTipoFactorDR());
    assertEquals(
        doctoRelacionado.getImpuestosDR().getTrasladosDR().getTrasladoDR().get(0).getTipoFactorDR(),
        documentoRelacionado
            .getImpuestosDR()
            .getTrasladosDR()
            .getTrasladoDR()
            .get(0)
            .getTipoFactorDR());
    assertEquals(
        doctoRelacionado.getImpuestosDR().getTrasladosDR().getTrasladoDR().get(0).getTipoFactorDR(),
        documentoRelacionado
            .getImpuestosDR()
            .getTrasladosDR()
            .getTrasladoDR()
            .get(0)
            .getTipoFactorDR());
    assertEquals(
        doctoRelacionado.getImpuestosDR().getTrasladosDR().getTrasladoDR().get(0).getTipoFactorDR(),
        documentoRelacionado
            .getImpuestosDR()
            .getTrasladosDR()
            .getTrasladoDR()
            .get(0)
            .getTipoFactorDR());
    assertEquals(
        doctoRelacionado.getImpuestosDR().getTrasladosDR().getTrasladoDR().get(0).getTipoFactorDR(),
        documentoRelacionado
            .getImpuestosDR()
            .getTrasladosDR()
            .getTrasladoDR()
            .get(0)
            .getTipoFactorDR());
  }

  @Test
  public void pagosMapperTest() throws IOException {
    Pagos pagos =
        objectMapper.readValue(new File("src/test/resources/json/pagos.json"), Pagos.class);

    com.unknown.models.generated.Pagos pagosGenerated = pagosMapper.pagosToGeneratedPagos(pagos);

    assertNotNull(pagos);

    assertEquals(pagosGenerated.getVersion(), pagos.getVersion());
    assertEquals(
        pagosGenerated.getTotales().getMontoTotalPagos(), pagos.getTotales().getMontoTotalPagos());
    assertEquals(
        pagosGenerated.getTotales().getTotalRetencionesIEPS(),
        pagos.getTotales().getTotalRetencionesIEPS());
    assertEquals(
        pagosGenerated.getTotales().getTotalRetencionesISR(),
        pagos.getTotales().getTotalRetencionesISR());
    assertEquals(
        pagosGenerated.getTotales().getTotalRetencionesIVA(),
        pagos.getTotales().getTotalRetencionesIVA());
    assertEquals(
        pagosGenerated.getTotales().getTotalTrasladosBaseIVA0(),
        pagos.getTotales().getTotalTrasladosBaseIVA0());
    assertEquals(
        pagosGenerated.getTotales().getTotalTrasladosBaseIVA8(),
        pagos.getTotales().getTotalTrasladosBaseIVA8());
    assertEquals(
        pagosGenerated.getTotales().getTotalTrasladosBaseIVA16(),
        pagos.getTotales().getTotalTrasladosBaseIVA16());
    assertEquals(
        pagosGenerated.getTotales().getTotalTrasladosBaseIVAExento(),
        pagos.getTotales().getTotalTrasladosBaseIVAExento());
    assertEquals(
        pagosGenerated.getTotales().getTotalTrasladosImpuestoIVA0(),
        pagos.getTotales().getTotalTrasladosImpuestoIVA0());
    assertEquals(
        pagosGenerated.getTotales().getTotalTrasladosImpuestoIVA8(),
        pagos.getTotales().getTotalTrasladosImpuestoIVA8());
    assertEquals(
        pagosGenerated.getTotales().getTotalTrasladosImpuestoIVA16(),
        pagos.getTotales().getTotalTrasladosImpuestoIVA16());

    assertEquals(pagosGenerated.getPago().size(), pagos.getPagos().size());

    for (int i = 0; i > pagosGenerated.getPago().size(); i++) {
      assertEquals(
          pagosGenerated.getPago().get(i).getFechaPago(), pagos.getPagos().get(i).getFechaPago());
      assertEquals(
          pagosGenerated.getPago().get(i).getFormaDePagoP(),
          pagos.getPagos().get(i).getFormaDePagoP());
      assertEquals(
          pagosGenerated.getPago().get(i).getMonedaP().value(),
          pagos.getPagos().get(i).getMonedaP());
      assertEquals(
          pagosGenerated.getPago().get(i).getTipoCambioP(),
          pagos.getPagos().get(i).getTipoCambioP());
      assertEquals(pagosGenerated.getPago().get(i).getMonto(), pagos.getPagos().get(i).getMonto());
      assertEquals(
          pagosGenerated.getPago().get(i).getNumOperacion(),
          pagos.getPagos().get(i).getNumOperacion());
      assertEquals(
          pagosGenerated.getPago().get(i).getRfcEmisorCtaOrd(),
          pagos.getPagos().get(i).getRfcEmisorCtaOrd());
      assertEquals(
          pagosGenerated.getPago().get(i).getNomBancoOrdExt(),
          pagos.getPagos().get(i).getNomBancoOrdExt());
      assertEquals(
          pagosGenerated.getPago().get(i).getCtaOrdenante(),
          pagos.getPagos().get(i).getCtaOrdenante());
      assertEquals(
          pagosGenerated.getPago().get(i).getRfcEmisorCtaBen(),
          pagos.getPagos().get(i).getRfcEmisorCtaBen());
      assertEquals(
          pagosGenerated.getPago().get(i).getCtaBeneficiario(),
          pagos.getPagos().get(i).getCtaBeneficiario());
      assertEquals(
          pagosGenerated.getPago().get(i).getTipoCadPago(),
          pagos.getPagos().get(i).getTipoCadPago());
      assertEquals(
          pagosGenerated.getPago().get(i).getCadPago(), pagos.getPagos().get(i).getCadPago());

      assertEquals(
          pagosGenerated
              .getPago()
              .get(i)
              .getImpuestosP()
              .getRetencionesP()
              .getRetencionP()
              .get(0)
              .getImpuestoP(),
          pagos
              .getPagos()
              .get(i)
              .getImpuestosP()
              .getRetencionesP()
              .getRetencionP()
              .get(0)
              .getImpuestoP());
      assertEquals(
          pagosGenerated
              .getPago()
              .get(i)
              .getImpuestosP()
              .getRetencionesP()
              .getRetencionP()
              .get(0)
              .getImporteP(),
          pagos
              .getPagos()
              .get(i)
              .getImpuestosP()
              .getRetencionesP()
              .getRetencionP()
              .get(0)
              .getImporteP());
      assertEquals(
          pagosGenerated
              .getPago()
              .get(i)
              .getImpuestosP()
              .getRetencionesP()
              .getRetencionP()
              .get(0)
              .getImpuestoP(),
          pagos
              .getPagos()
              .get(i)
              .getImpuestosP()
              .getRetencionesP()
              .getRetencionP()
              .get(0)
              .getImpuestoP());

      assertEquals(
          pagosGenerated
              .getPago()
              .get(i)
              .getImpuestosP()
              .getTrasladosP()
              .getTrasladoP()
              .get(0)
              .getImpuestoP(),
          pagos
              .getPagos()
              .get(i)
              .getImpuestosP()
              .getTrasladosP()
              .getTrasladoP()
              .get(0)
              .getImpuestoP());
      assertEquals(
          pagosGenerated
              .getPago()
              .get(i)
              .getImpuestosP()
              .getTrasladosP()
              .getTrasladoP()
              .get(0)
              .getImporteP(),
          pagos
              .getPagos()
              .get(i)
              .getImpuestosP()
              .getTrasladosP()
              .getTrasladoP()
              .get(0)
              .getImporteP());
      assertEquals(
          pagosGenerated
              .getPago()
              .get(i)
              .getImpuestosP()
              .getTrasladosP()
              .getTrasladoP()
              .get(0)
              .getTipoFactorP(),
          pagos
              .getPagos()
              .get(i)
              .getImpuestosP()
              .getTrasladosP()
              .getTrasladoP()
              .get(0)
              .getTipoFactorP());
      assertEquals(
          pagosGenerated
              .getPago()
              .get(i)
              .getImpuestosP()
              .getTrasladosP()
              .getTrasladoP()
              .get(0)
              .getBaseP(),
          pagos.getPagos().get(i).getImpuestosP().getTrasladosP().getTrasladoP().get(0).getBaseP());
      assertEquals(
          pagosGenerated
              .getPago()
              .get(i)
              .getImpuestosP()
              .getTrasladosP()
              .getTrasladoP()
              .get(0)
              .getTasaOCuotaP(),
          pagos
              .getPagos()
              .get(i)
              .getImpuestosP()
              .getTrasladosP()
              .getTrasladoP()
              .get(0)
              .getTasaOCuotaP());
    }
  }
}
