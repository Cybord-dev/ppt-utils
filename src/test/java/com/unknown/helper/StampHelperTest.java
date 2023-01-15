package com.unknown.helper;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.unknown.error.StampException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StampHelperTest {

  private static final Logger log = LoggerFactory.getLogger(StampHelperTest.class);

  private static final String DATE_REPLACEMENT = "%fecha-timbrado%";
  private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
  private static final StampHelper helper = new StampHelper();

  @Test
  public void testStamp() throws IOException, StampException {
    String xml =
        new String(
            Files.readAllBytes(Paths.get("./src/test/resources/cfdi-samples/cfdi-sample.xml")));

    xml = xml.replace(DATE_REPLACEMENT, formatter.format(new Date()));

    final String PW_SAT = "12345678a";

    String privateKey =
        new String(Files.readAllBytes(Paths.get("./src/test/resources/sample-certs/key.txt")));

    String xmlWithStamp = helper.stampCfdi(xml, PW_SAT, privateKey);

    log.info(xmlWithStamp);
    assertNotNull(xmlWithStamp);
  }
}
