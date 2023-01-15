package com.unknown.helper;

import static javax.xml.parsers.DocumentBuilderFactory.newInstance;

import com.unknown.error.StampException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.PrivateKey;
import java.security.Signature;
import javax.xml.bind.DatatypeConverter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.apache.commons.ssl.PKCS8Key;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class StampHelper {

  private static final String SIGN_ALGORITHM = "SHA256withRSA";
  private static final String CADENA_ORIGINAL_FILE_PATH = "xslt/cadenaoriginal.xslt";
  private static final String SELLO_ATTRIBUTE = "Sello";

  private static final Logger log = LoggerFactory.getLogger(StampHelper.class);

  public String stampCfdi(String xml, String keyWord, String signKey) throws StampException {
    try {
      String cadenaOriginal = getStampChain(xml);
      String sello = getSign(cadenaOriginal, keyWord, signKey);
      log.info("Cadena original : {}", cadenaOriginal);
      log.debug("Sello : {}", sello);
      DocumentBuilderFactory factory = newInstance();
      DocumentBuilder builder;
      TransformerFactory tf = TransformerFactory.newInstance();
      Transformer transformer;
      builder = factory.newDocumentBuilder();
      Document doc = builder.parse(new InputSource(new StringReader(xml)));
      doc.getDocumentElement().setAttribute(SELLO_ATTRIBUTE, sello);
      transformer = tf.newTransformer();
      StringWriter writer = new StringWriter();
      transformer.transform(new DOMSource(doc), new StreamResult(writer));
      return writer.getBuffer().toString();
    } catch (ParserConfigurationException | IOException | TransformerException | SAXException e) {
      throw new StampException(e.getMessage());
    }
  }

  private String getSign(String originalChain, String keyWord, String keyFile)
      throws StampException {
    try {
      InputStream myInputStream =
          new ByteArrayInputStream(DatatypeConverter.parseBase64Binary(keyFile));
      PKCS8Key pkcs8 = new PKCS8Key(myInputStream, keyWord.toCharArray());
      PrivateKey pk = pkcs8.getPrivateKey();
      Signature signature = Signature.getInstance(SIGN_ALGORITHM);
      signature.initSign(pk);
      signature.update(originalChain.getBytes(StandardCharsets.UTF_8));
      return DatatypeConverter.printBase64Binary(signature.sign());
    } catch (GeneralSecurityException | IOException e) {
      log.error("Error signing XML using key file", e);
      throw new StampException(e.getMessage());
    }
  }

  private String getStampChain(String xml) throws StampException {
    try {
      TransformerFactory transformerFactory = TransformerFactory.newInstance();

      InputStreamReader xsltReader =
          new InputStreamReader(
              Thread.currentThread()
                  .getContextClassLoader()
                  .getResourceAsStream(CADENA_ORIGINAL_FILE_PATH));
      Source xslt = new StreamSource(xsltReader);
      Transformer transformer = transformerFactory.newTransformer(xslt);
      Source xmlSource = new StreamSource(new StringReader(xml));
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      Result out = new StreamResult(baos);
      transformer.transform(xmlSource, out);
      return baos.toString("UTF-8");
    } catch (TransformerException | UnsupportedEncodingException e) {
      log.error("Error generating XML original chain", e);
      throw new StampException(e.getMessage());
    }
  }
}
