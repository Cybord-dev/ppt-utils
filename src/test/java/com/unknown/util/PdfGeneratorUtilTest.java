package com.unknown.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.google.common.io.Resources;
import com.unknown.error.PptUtilException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import org.junit.jupiter.api.Test;

public class PdfGeneratorUtilTest {

  @Test
  public void generatePdf_whenIsSuccess_facturaTimbrada() throws IOException, PptUtilException {
    String data =
        Resources.toString(
            getClass().getClassLoader().getResource("pdf-config/data-factura-timbrada.xml"),
            StandardCharsets.UTF_8);

    String template =
        Resources.toString(
            getClass().getClassLoader().getResource("pdf-config/template-factura-timbrada.xml"),
            StandardCharsets.UTF_8);
    String response =
        PdfGeneratorUtil.generatePdf(
            Base64.getEncoder().withoutPadding().encodeToString(template.getBytes()),
            "xsd",
            Base64.getEncoder().withoutPadding().encodeToString(data.getBytes()));
    assertNotNull(response);
  }

  @Test
  public void generatePdf_whenIsSuccess_facturaSinTimbrar() throws IOException, PptUtilException {
    String data =
        Resources.toString(
            getClass().getClassLoader().getResource("pdf-config/data-factura-sin-timbrar.xml"),
            StandardCharsets.UTF_8);

    String template =
        Resources.toString(
            getClass().getClassLoader().getResource("pdf-config/template-factura-sin-timbrar.xml"),
            StandardCharsets.UTF_8);
    String response =
        PdfGeneratorUtil.generatePdf(
            Base64.getEncoder().withoutPadding().encodeToString(template.getBytes()),
            "xsd",
            Base64.getEncoder().withoutPadding().encodeToString(data.getBytes()));
    assertNotNull(response);
  }

  @Test
  public void generatePdf_whenIsSuccess_comlementoTimbrado() throws IOException, PptUtilException {
    String data =
        Resources.toString(
            getClass().getClassLoader().getResource("pdf-config/data-complemento-timbrada.xml"),
            StandardCharsets.UTF_8);

    String template =
        Resources.toString(
            getClass().getClassLoader().getResource("pdf-config/template-complemento-timbrado.xml"),
            StandardCharsets.UTF_8);
    String response =
        PdfGeneratorUtil.generatePdf(
            Base64.getEncoder().withoutPadding().encodeToString(template.getBytes()),
            "xsd",
            Base64.getEncoder().withoutPadding().encodeToString(data.getBytes()));
    assertNotNull(response);
  }

  @Test
  public void generatePdf_whenFails_invalidTemplate() throws IOException, PptUtilException {
    String data =
        Resources.toString(
            getClass().getClassLoader().getResource("pdf-config/data-complemento-timbrada.xml"),
            StandardCharsets.UTF_8);

    String template = "";

    PptUtilException thrown =
        assertThrows(
            PptUtilException.class,
            () -> {
              PdfGeneratorUtil.generatePdf(
                  Base64.getEncoder().withoutPadding().encodeToString(template.getBytes()),
                  "xsd",
                  Base64.getEncoder().withoutPadding().encodeToString(data.getBytes()));
            });
    assertEquals("Template is mandatory", thrown.getMessage());
  }

  @Test
  public void generatePdf_whenFails_invalidData() throws IOException, PptUtilException {
    String data = "";

    String template =
        Resources.toString(
            getClass().getClassLoader().getResource("pdf-config/template-complemento-timbrado.xml"),
            StandardCharsets.UTF_8);
    PptUtilException thrown =
        assertThrows(
            PptUtilException.class,
            () -> {
              PdfGeneratorUtil.generatePdf(
                  Base64.getEncoder().withoutPadding().encodeToString(template.getBytes()),
                  "xsd",
                  Base64.getEncoder().withoutPadding().encodeToString(data.getBytes()));
            });
    assertEquals("Data is mandatory", thrown.getMessage());
  }

  @Test
  public void generatePdf_whenFails_invalidXsd() throws IOException, PptUtilException {
    String data =
        Resources.toString(
            getClass().getClassLoader().getResource("pdf-config/data-complemento-timbrada.xml"),
            StandardCharsets.UTF_8);

    String template =
        Resources.toString(
            getClass().getClassLoader().getResource("pdf-config/template-complemento-timbrado.xml"),
            StandardCharsets.UTF_8);
    PptUtilException thrown =
        assertThrows(
            PptUtilException.class,
            () -> {
              PdfGeneratorUtil.generatePdf(
                  Base64.getEncoder().withoutPadding().encodeToString(template.getBytes()),
                  "",
                  Base64.getEncoder().withoutPadding().encodeToString(data.getBytes()));
            });
    assertEquals("Xsd is mandatory", thrown.getMessage());
  }
}
