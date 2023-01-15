package com.unknown.util;

import com.unknown.error.PptUtilException;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;

@Slf4j
public class PdfGeneratorUtil {

  /**
   * Generate a Pdf in base 64 for the application , template and input provided
   *
   * @param xsd xsd to validate input in base 64
   * @param template Xml template in base 64
   * @param input input value should be String of the xml parameters
   * @return
   * @throws {@link PptUtilException}
   */
  public static String generatePdf(String template, String xsd, String input)
      throws PptUtilException {

    // TODO: ADD VALIDATIONS AND XSD VALIDATION FOR INPUT
    if (template == null || template.isBlank()) throw new PptUtilException("Template is mandatory");
    if (xsd == null || xsd.isBlank()) throw new PptUtilException("Xsd is mandatory");
    if (input == null || input.isBlank()) throw new PptUtilException("Data is mandatory");
    try {
      InputStream confStream =
          Thread.currentThread()
              .getContextClassLoader()
              .getResourceAsStream("pdf-config/fop.xconf");
      String templateParameter =
          new String(Base64.getDecoder().decode(template.getBytes(StandardCharsets.UTF_8)));
      String inputParameter =
          new String(Base64.getDecoder().decode(input.getBytes(StandardCharsets.UTF_8)));
      FopFactory fopFactory = FopFactory.newInstance(new File(".").toURI(), confStream);
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, outputStream);
      TransformerFactory transformerFactory = TransformerFactory.newInstance();
      Transformer transformer =
          transformerFactory.newTransformer(
              new StreamSource(new InputStreamReader(IOUtils.toInputStream(templateParameter))));
      transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
      transformer.transform(
          new StreamSource(new InputStreamReader(IOUtils.toInputStream(inputParameter))),
          new SAXResult(fop.getDefaultHandler()));
      return Base64.getEncoder().encodeToString(outputStream.toByteArray());
    } catch (Exception e) {
      log.error("General error in Fop tool", e);
      throw new PptUtilException(String.format("General error in Fop tool %s", e.getMessage()));
    }
  }
}
