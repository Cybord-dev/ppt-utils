package com.unknown.cfdi.mappers.nomina;

import static com.unknown.util.NamespaceConstants.NOMINA_PREFIX;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.unknown.cfdi.mappers.CfdiMapper;
import com.unknown.cfdi.modelos.Cfdi;
import com.unknown.cfdi.modelos.complementos.nomina.AccionesOTitulos;
import com.unknown.cfdi.modelos.complementos.nomina.CompensacionSaldosAFavor;
import com.unknown.cfdi.modelos.complementos.nomina.Deduccion;
import com.unknown.cfdi.modelos.complementos.nomina.Deducciones;
import com.unknown.cfdi.modelos.complementos.nomina.Emisor;
import com.unknown.cfdi.modelos.complementos.nomina.EntidadSNCF;
import com.unknown.cfdi.modelos.complementos.nomina.HorasExtra;
import com.unknown.cfdi.modelos.complementos.nomina.Incapacidad;
import com.unknown.cfdi.modelos.complementos.nomina.JubilacionPensionRetiro;
import com.unknown.cfdi.modelos.complementos.nomina.Nomina;
import com.unknown.cfdi.modelos.complementos.nomina.OtroPago;
import com.unknown.cfdi.modelos.complementos.nomina.Percepcion;
import com.unknown.cfdi.modelos.complementos.nomina.Percepciones;
import com.unknown.cfdi.modelos.complementos.nomina.Receptor;
import com.unknown.cfdi.modelos.complementos.nomina.SeparacionIndemnizacion;
import com.unknown.cfdi.modelos.complementos.nomina.SubContratacion;
import com.unknown.cfdi.modelos.complementos.nomina.SubsidioAlEmpleo;
import com.unknown.error.XMLParserException;
import com.unknown.helper.CfdiTransformer;
import com.unknown.models.generated.CTipoDeComprobante;
import com.unknown.models.generated.Comprobante;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class NominaMapperTest {

  private com.unknown.models.generated.Nomina nominaBase;

  private static final CfdiMapper cfdiMapper = Mappers.getMapper(CfdiMapper.class);
  private static final ReceptorMapper receptorMapper = Mappers.getMapper(ReceptorMapper.class);
  private static final SubContratacionMapper subContratacionMapper =
      Mappers.getMapper(SubContratacionMapper.class);
  private static final SeparacionIndemnizacionMapper separacionIndemnizacionMapper =
      Mappers.getMapper(SeparacionIndemnizacionMapper.class);
  private static final HorasExtraMapper horasExtraMapper =
      Mappers.getMapper(HorasExtraMapper.class);
  private static final AccionesOTitulosMapper accionesOTitulosMapper =
      Mappers.getMapper(AccionesOTitulosMapper.class);
  private static final PercepcionMapper percepcionMapper =
      Mappers.getMapper(PercepcionMapper.class);
  private static final JubilacionPensionRetiroMapper jubilacionPensionRetiroMapper =
      Mappers.getMapper(JubilacionPensionRetiroMapper.class);
  private static final PercepcionesMapper percepcionesMapper =
      Mappers.getMapper(PercepcionesMapper.class);
  private static final SubsidioAlEmpleoMapper subsidioAlEmpleoMapper =
      Mappers.getMapper(SubsidioAlEmpleoMapper.class);
  private static final CompensacionSaldosAFavorMapper compensacionSaldosAFavorMapper =
      Mappers.getMapper(CompensacionSaldosAFavorMapper.class);
  private static final OtroPagoMapper otroPagoMapper =
      Mappers.getMapper(OtroPagoMapper.class); // Minimizado
  private static final IncapacidadMapper incapacidadMapper =
      Mappers.getMapper(IncapacidadMapper.class); // Minimizado
  private static final EntidadSNCFMapper entidadSNCFMapper =
      Mappers.getMapper(EntidadSNCFMapper.class);
  private static final EmisorMapper emisorMapper = Mappers.getMapper(EmisorMapper.class);
  private static final DeduccionMapper deduccionMapper = Mappers.getMapper(DeduccionMapper.class);
  private static final DeduccionesMapper deduccionesMapper =
      Mappers.getMapper(DeduccionesMapper.class);
  private static final NominaMapper nominaMapper = Mappers.getMapper(NominaMapper.class);

  @BeforeEach
  public void init() {
    JAXBContext jaxbContext;
    try {
      jaxbContext = JAXBContext.newInstance(com.unknown.models.generated.Nomina.class);
      nominaBase =
          (com.unknown.models.generated.Nomina)
              jaxbContext
                  .createUnmarshaller()
                  .unmarshal(new File("src/test/resources/xml/nomina12.xml"));
    } catch (JAXBException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testNominaToGenerated() throws IOException, XMLParserException {

    Cfdi cfdi =
        Cfdi.builder()
            .serie("123456")
            .formaPago("Efectivo")
            .subtotal(BigDecimal.ONE)
            .descuento(BigDecimal.ZERO)
            .total(BigDecimal.ONE)
            .moneda("MXN")
            .tipoDeComprobante(CTipoDeComprobante.N.value())
            .exportacion("01")
            .lugarExpedicion("04100")
            .emisor(
                com.unknown.cfdi.modelos.Emisor.builder()
                    .rfc("NLC091211KC6")
                    .nombre("NT LINK COMUNICACIONES S.A. DE C.V.")
                    .regimenFiscal("601")
                    .build())
            .receptor(
                com.unknown.cfdi.modelos.Receptor.builder()
                    .rfc("XAXX010101000")
                    .regimenFiscalReceptor("G03")
                    .nombre("JUAN PEREZ GARCIA")
                    .domicilioFiscalReceptor("5300")
                    .usoCfdi("G03")
                    .build())
            .conceptos(ImmutableList.of())
            .impuestos(ImmutableList.of())
            .build();

    Comprobante comprobante = cfdiMapper.cfdiToComprobante(cfdi);

    Comprobante.Complemento complemento = new Comprobante.Complemento();
    Nomina nomina =
        new ObjectMapper()
            .readValue(new File("./src/test/resources/json/nomina.json"), Nomina.class);
    com.unknown.models.generated.Nomina xmlgenerated = nominaMapper.nominaToNominaGenerated(nomina);
    complemento.getAny().add(xmlgenerated);
    comprobante.setComplemento(complemento);

    String xml = CfdiTransformer.cfdiMoldelToString(comprobante);

    assertTrue(xml.contains(NOMINA_PREFIX));
    assertTrue(xml.contains("NumSeguridadSocial=\"12345678910\""));
    assertTrue(xml.contains("RegistroPatronal=\"Y12345678910\""));
    assertTrue(xml.contains("Concepto=\"SUBSIDIO\""));
    assertTrue(xml.contains("SubsidioCausado=\"0.00\""));
    assertTrue(xml.contains("Incapacidades"));
  }

  @Test
  void testNominaMapper() {
    com.unknown.models.generated.Nomina base = nominaBase;
    Nomina mapeado = nominaMapper.nominaGeneratedToNomina(base);
    com.unknown.models.generated.Nomina generado = nominaMapper.nominaToNominaGenerated(mapeado);

    assertEquals(mapeado.getVersion(), generado.getVersion());
    assertEquals(mapeado.getTipoNomina(), generado.getTipoNomina().toString());
    assertEquals(mapeado.getFechaPago(), generado.getFechaPago());
    assertEquals(mapeado.getFechaInicialPago(), generado.getFechaInicialPago());
    assertEquals(mapeado.getFechaFinalPago(), generado.getFechaFinalPago());
    assertEquals(mapeado.getNumDiasPagados(), generado.getNumDiasPagados());
    assertEquals(mapeado.getTotalPercepciones(), generado.getTotalPercepciones());
    assertEquals(mapeado.getTotalDeducciones(), generado.getTotalDeducciones());
    assertEquals(mapeado.getTotalOtrosPagos(), generado.getTotalOtrosPagos());
  }

  @Test
  void testDeduccionesMapper() {
    com.unknown.models.generated.Nomina.Deducciones base = nominaBase.getDeducciones();
    Deducciones mapeado = deduccionesMapper.deduccionesGeneratedToDeducciones(base);
    com.unknown.models.generated.Nomina.Deducciones generado =
        deduccionesMapper.deduccionesTODeduccionesGenerated(mapeado);
    assertEquals(mapeado.getTotalOtrasDeducciones(), generado.getTotalOtrasDeducciones());
    assertEquals(mapeado.getTotalImpuestosRetenidos(), generado.getTotalImpuestosRetenidos());
  }

  @Test
  void testDeduccionMapper() {
    com.unknown.models.generated.Nomina.Deducciones.Deduccion base =
        nominaBase.getDeducciones().getDeduccion().get(0);
    Deduccion mapeado = deduccionMapper.deduccionGeneratedToDeduccion(base);
    com.unknown.models.generated.Nomina.Deducciones.Deduccion generado =
        deduccionMapper.deduccionToDeduccionGenerated(mapeado);
    assertEquals(mapeado.getTipoDeduccion(), generado.getTipoDeduccion());
    assertEquals(mapeado.getClave(), generado.getClave());
    assertEquals(mapeado.getConcepto(), generado.getConcepto());
    assertEquals(mapeado.getImporte(), generado.getImporte());
  }

  @Test
  void testEmisorMapper() {
    com.unknown.models.generated.Nomina.Emisor base = nominaBase.getEmisor();
    Emisor mapeado = emisorMapper.emisorGeneratedToEmisor(base);
    com.unknown.models.generated.Nomina.Emisor generado =
        emisorMapper.emisorToEmisorGenerated(mapeado);
    assertEquals(mapeado.getCurp(), generado.getCurp());
    assertEquals(mapeado.getRegistroPatronal(), generado.getRegistroPatronal());
    assertEquals(mapeado.getRfcPatronOrigen(), generado.getRfcPatronOrigen());
  }

  @Test
  void testEntidadSNCFMapper() {
    com.unknown.models.generated.Nomina.Emisor.EntidadSNCF base =
        nominaBase.getEmisor().getEntidadSNCF();
    EntidadSNCF mapeado = entidadSNCFMapper.entidadSNCFGeneratedToEntidadSNCF(base);
    com.unknown.models.generated.Nomina.Emisor.EntidadSNCF generado =
        entidadSNCFMapper.entidadSNCFToEntidadSNCFGenerated(mapeado);
    assertEquals(mapeado.getOrigenRecurso(), generado.getOrigenRecurso().toString());
    assertEquals(mapeado.getMontoRecursoPropio(), generado.getMontoRecursoPropio());
  }

  @Test
  void testIncapacidadMapper() {
    com.unknown.models.generated.Nomina.Incapacidades.Incapacidad base =
        nominaBase.getIncapacidades().getIncapacidad().get(0);
    Incapacidad mapeado = incapacidadMapper.incapacidadGeneratedToIncapacidad(base);
    com.unknown.models.generated.Nomina.Incapacidades.Incapacidad generado =
        incapacidadMapper.incapacidadToIncapacidadGenerated(mapeado);
    assertEquals(mapeado.getDiasIncapacidad(), generado.getDiasIncapacidad());
    assertEquals(mapeado.getTipoIncapacidad(), generado.getTipoIncapacidad());
    assertEquals(mapeado.getImporteMonetario(), generado.getImporteMonetario());
  }

  @Test
  void testOtroPagoMapper() {
    com.unknown.models.generated.Nomina.OtrosPagos.OtroPago base =
        nominaBase.getOtrosPagos().getOtroPago().get(0);
    OtroPago mapeado = otroPagoMapper.otroPagoGeneratedToOtroPago(base);
    com.unknown.models.generated.Nomina.OtrosPagos.OtroPago generado =
        otroPagoMapper.otroPagoToOtroPagoGenerated(mapeado);
    assertEquals(mapeado.getTipoOtroPago(), generado.getTipoOtroPago());
    assertEquals(mapeado.getClave(), generado.getClave());
    assertEquals(mapeado.getConcepto(), generado.getConcepto());
    assertEquals(mapeado.getImporte(), generado.getImporte());
  }

  @Test
  void testCompensacionSaldosAFavorMapper() {
    com.unknown.models.generated.Nomina.OtrosPagos.OtroPago.CompensacionSaldosAFavor base =
        nominaBase.getOtrosPagos().getOtroPago().get(0).getCompensacionSaldosAFavor();
    CompensacionSaldosAFavor mapeado =
        compensacionSaldosAFavorMapper.compensacionSaldosAFavorGeneratedToCompensacionSaldosAFavor(
            base);
    com.unknown.models.generated.Nomina.OtrosPagos.OtroPago.CompensacionSaldosAFavor generado =
        compensacionSaldosAFavorMapper.compensacionSaldosAFavorToCompensacionSaldosAFavorGenerated(
            mapeado);
    assertEquals(mapeado.getSaldoAFavor(), generado.getSaldoAFavor());
    assertEquals(mapeado.getRemanenteSalFav(), generado.getRemanenteSalFav());
  }

  @Test
  void testSubsidioAlEmpleoMapper() {
    com.unknown.models.generated.Nomina.OtrosPagos.OtroPago.SubsidioAlEmpleo base =
        nominaBase.getOtrosPagos().getOtroPago().get(0).getSubsidioAlEmpleo();
    SubsidioAlEmpleo mapeado =
        subsidioAlEmpleoMapper.subsidioAlEmpleoGeneratedToSubsidioAlEmpleo(base);
    com.unknown.models.generated.Nomina.OtrosPagos.OtroPago.SubsidioAlEmpleo generado =
        subsidioAlEmpleoMapper.subsidioAlEmpleoToSubsidioAlEmpleoGenerated(mapeado);
    assertEquals(mapeado.getSubsidioCausado(), generado.getSubsidioCausado());
  }

  @Test
  void testPercepcionesMapper() {
    com.unknown.models.generated.Nomina.Percepciones base = nominaBase.getPercepciones();
    Percepciones mapeado = percepcionesMapper.percepcionesGeneratedToPercepciones(base);
    com.unknown.models.generated.Nomina.Percepciones generado =
        percepcionesMapper.percepcionesToPercepcionesGenerated(mapeado);
    assertEquals(mapeado.getTotalSueldos(), generado.getTotalSueldos());
    assertEquals(
        mapeado.getTotalSeparacionIndemnizacion(), generado.getTotalSeparacionIndemnizacion());
    assertEquals(
        mapeado.getTotalJubilacionPensionRetiro(), generado.getTotalJubilacionPensionRetiro());
    assertEquals(mapeado.getTotalGravado(), generado.getTotalGravado());
    assertEquals(mapeado.getTotalExento(), generado.getTotalExento());
  }

  @Test
  void testJubilacionPensionRetiroMapper() {
    com.unknown.models.generated.Nomina.Percepciones.JubilacionPensionRetiro base =
        nominaBase.getPercepciones().getJubilacionPensionRetiro();
    JubilacionPensionRetiro mapeado =
        jubilacionPensionRetiroMapper.jubilacionPensionRetiroGeneratedToJubilacionPensionRetiro(
            base);
    com.unknown.models.generated.Nomina.Percepciones.JubilacionPensionRetiro generado =
        jubilacionPensionRetiroMapper.jubilacionPensionRetiroToJubilacionPensionRetiroGenerated(
            mapeado);
    assertEquals(mapeado.getTotalUnaExhibicion(), generado.getTotalUnaExhibicion());
    assertEquals(mapeado.getTotalParcialidad(), generado.getTotalParcialidad());
    assertEquals(mapeado.getMontoDiario(), generado.getMontoDiario());
    assertEquals(mapeado.getIngresoAcumulable(), generado.getIngresoAcumulable());
    assertEquals(mapeado.getIngresoNoAcumulable(), generado.getIngresoNoAcumulable());
  }

  @Test
  void testPercepcionMapper() {
    com.unknown.models.generated.Nomina.Percepciones.Percepcion base =
        nominaBase.getPercepciones().getPercepcion().get(0);
    Percepcion mapeado = percepcionMapper.percepcionGeneratedToPercepcion(base);
    com.unknown.models.generated.Nomina.Percepciones.Percepcion generado =
        percepcionMapper.percepcionToPercepcionGenerated(mapeado);
    assertEquals(mapeado.getTipoPercepcion(), generado.getTipoPercepcion());
    assertEquals(mapeado.getClave(), generado.getClave());
    assertEquals(mapeado.getConcepto(), generado.getConcepto());
    assertEquals(mapeado.getImporteGravado(), generado.getImporteGravado());
    assertEquals(mapeado.getImporteExento(), generado.getImporteExento());
  }

  @Test
  void testAccionesOTitulosMapper() {
    com.unknown.models.generated.Nomina.Percepciones.Percepcion.AccionesOTitulos base =
        nominaBase.getPercepciones().getPercepcion().get(0).getAccionesOTitulos();
    AccionesOTitulos mapeado =
        accionesOTitulosMapper.accionesOTitulosGeneratedToAccionesOTitulos(base);
    com.unknown.models.generated.Nomina.Percepciones.Percepcion.AccionesOTitulos generado =
        accionesOTitulosMapper.accionesOTitulosToAccionesOTitulosGenerated(mapeado);
    assertEquals(mapeado.getValorMercado(), generado.getValorMercado());
    assertEquals(mapeado.getPrecioAlOtorgarse(), generado.getPrecioAlOtorgarse());
  }

  @Test
  void testHorasExtraMapper() {
    com.unknown.models.generated.Nomina.Percepciones.Percepcion.HorasExtra base =
        nominaBase.getPercepciones().getPercepcion().get(0).getHorasExtra().get(0);
    HorasExtra mapeado = horasExtraMapper.horasExtraGeneratedToHorasExtra(base);
    com.unknown.models.generated.Nomina.Percepciones.Percepcion.HorasExtra generado =
        horasExtraMapper.horasExtraToHorasExtraGenerated(mapeado);
    assertEquals(mapeado.getDias(), generado.getDias());
    assertEquals(mapeado.getTipoHoras(), generado.getTipoHoras());
    assertEquals(mapeado.getHorasExtra(), generado.getHorasExtra());
    assertEquals(mapeado.getImportePagado(), generado.getImportePagado());
  }

  @Test
  void testSeparacionIndemnizacionMapper() {
    com.unknown.models.generated.Nomina.Percepciones.SeparacionIndemnizacion base =
        nominaBase.getPercepciones().getSeparacionIndemnizacion();
    SeparacionIndemnizacion mapeado =
        separacionIndemnizacionMapper.separacionIndemnizacionGeneratedToSeparacionIndemnizacion(
            base);
    com.unknown.models.generated.Nomina.Percepciones.SeparacionIndemnizacion generado =
        separacionIndemnizacionMapper.separacionIndemnizacionToSeparacionIndemnizacionGenerated(
            mapeado);
    assertEquals(mapeado.getTotalPagado(), generado.getTotalPagado());
    assertEquals(mapeado.getUltimoSueldoMensOrd(), generado.getUltimoSueldoMensOrd());
    assertEquals(mapeado.getIngresoAcumulable(), generado.getIngresoAcumulable());
    assertEquals(mapeado.getIngresoNoAcumulable(), generado.getIngresoNoAcumulable());
    assertEquals(mapeado.getNumAñosServicio(), generado.getNumAñosServicio());
  }

  @Test
  void testReceptorMapper() {
    com.unknown.models.generated.Nomina.Receptor base = nominaBase.getReceptor();
    Receptor mapeado = receptorMapper.receptorGeneratedToReceptor(base);
    com.unknown.models.generated.Nomina.Receptor generado =
        receptorMapper.receptortoReceptorGenerated(mapeado);
    assertEquals(mapeado.getSalarioDiarioIntegrado(), generado.getSalarioDiarioIntegrado());
    assertEquals(mapeado.getSalarioBaseCotApor(), generado.getSalarioBaseCotApor());
    assertEquals(mapeado.getCuentaBancaria(), generado.getCuentaBancaria());
    assertEquals(mapeado.getBanco(), generado.getBanco());
    assertEquals(mapeado.getPeriodicidadPago(), generado.getPeriodicidadPago());
    assertEquals(mapeado.getRiesgoPuesto(), generado.getRiesgoPuesto());
    assertEquals(mapeado.getPuesto(), generado.getPuesto());
    assertEquals(mapeado.getDepartamento(), generado.getDepartamento());
    assertEquals(mapeado.getNumEmpleado(), generado.getNumEmpleado());
    assertEquals(mapeado.getTipoRegimen(), generado.getTipoRegimen());
    assertEquals(mapeado.getTipoContrato(), generado.getTipoContrato());
    assertEquals(mapeado.getTipoJornada(), generado.getTipoJornada());
    assertEquals(mapeado.getSindicalizado(), generado.getSindicalizado());
    assertEquals(mapeado.getTipoContrato(), generado.getTipoContrato());
    assertEquals(mapeado.getAntigüedad(), generado.getAntigüedad());
    assertEquals(mapeado.getFechaInicioRelLaboral(), generado.getFechaInicioRelLaboral());
    assertEquals(mapeado.getNumSeguridadSocial(), generado.getNumSeguridadSocial());
    assertEquals(mapeado.getCurp(), generado.getCurp());
    assertEquals(mapeado.getClaveEntFed(), generado.getClaveEntFed().toString());
  }

  @Test
  void testSubContratacionMapper() {
    com.unknown.models.generated.Nomina.Receptor.SubContratacion subContratacionBase =
        nominaBase.getReceptor().getSubContratacion().get(0);
    SubContratacion mapeado =
        subContratacionMapper.subContratacionGeneratedToSubContratacion(subContratacionBase);
    com.unknown.models.generated.Nomina.Receptor.SubContratacion generado =
        subContratacionMapper.subContratacionToSubContratacionGenerated(mapeado);
    assertEquals(mapeado.getRfcLabora(), generado.getRfcLabora());
    assertEquals(mapeado.getPorcentajeTiempo(), generado.getPorcentajeTiempo());
  }
}
