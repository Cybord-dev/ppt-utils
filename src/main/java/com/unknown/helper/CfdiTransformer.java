package com.unknown.helper;

import static javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT;

import com.unknown.error.XMLParserException;
import com.unknown.models.generated.Comprobante;
import com.unknown.models.generated.Nomina;
import com.unknown.models.generated.Pagos;
import com.unknown.models.generated.Retenciones;
import com.unknown.models.generated.TimbreFiscalDigital;
import com.unknown.util.CfdiNamespaceMapper;
import com.unknown.util.RetencionesNamespaceMapper;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class CfdiTransformer {

  public static String cfdiMoldelToString(Comprobante cfdi) throws XMLParserException {
    try {
      JAXBContext jaxbContext = JAXBContext.newInstance(Comprobante.class);
      Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
      jaxbMarshaller.setProperty(
          "com.sun.xml.bind.namespacePrefixMapper", new CfdiNamespaceMapper());
      jaxbMarshaller.setProperty(JAXB_FORMATTED_OUTPUT, true);
      StringWriter sw = new StringWriter();
      jaxbMarshaller.marshal(cfdi, sw);
      return sw.toString();
    } catch (JAXBException ex) {
      throw new XMLParserException(ex.getMessage());
    }
  }

  public static String retentionMoldelToString(Retenciones cfdi) throws XMLParserException {
    try {
      JAXBContext jaxbContext = JAXBContext.newInstance(Retenciones.class);
      Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
      jaxbMarshaller.setProperty(
          "com.sun.xml.bind.namespacePrefixMapper", new RetencionesNamespaceMapper());
      jaxbMarshaller.setProperty(JAXB_FORMATTED_OUTPUT, true);
      StringWriter sw = new StringWriter();
      jaxbMarshaller.marshal(cfdi, sw);
      return sw.toString();
    } catch (JAXBException ex) {
      throw new XMLParserException(ex.getMessage());
    }
  }

  public static Comprobante xmlToCfdiModel(InputStream xmlStream) throws XMLParserException {
    try {
      JAXBContext jc = JAXBContext.newInstance(Comprobante.class);
      Unmarshaller um = jc.createUnmarshaller();
      return (Comprobante) um.unmarshal(xmlStream);
    } catch (JAXBException ex) {
      throw new XMLParserException(ex.getMessage());
    }
  }

  public static Comprobante xmlToCfdiModel(String xml) throws XMLParserException {
    return xmlToCfdiModel(new ByteArrayInputStream(xml.getBytes()));
  }

  public static TimbreFiscalDigital xmlToTFD(InputStream xmlStream) throws XMLParserException {
    try {
      JAXBContext jc = JAXBContext.newInstance(TimbreFiscalDigital.class);
      Unmarshaller um = jc.createUnmarshaller();
      return (TimbreFiscalDigital) um.unmarshal(xmlStream);
    } catch (JAXBException ex) {
      throw new XMLParserException(ex.getMessage());
    }
  }

  public static TimbreFiscalDigital xmlToTFD(String xml) throws XMLParserException {
    return xmlToTFD(new ByteArrayInputStream(xml.getBytes()));
  }

  public static Retenciones xmlToRetencionModel(InputStream xmlStream) throws XMLParserException {
    try {
      JAXBContext jc = JAXBContext.newInstance(Retenciones.class);
      Unmarshaller um = jc.createUnmarshaller();
      return (Retenciones) um.unmarshal(xmlStream);
    } catch (JAXBException ex) {
      throw new XMLParserException(ex.getMessage());
    }
  }

  public static Retenciones xmlToRetencionModel(String xml) throws XMLParserException {
    return xmlToRetencionModel(new ByteArrayInputStream(xml.getBytes()));
  }

  public static Pagos xmlToPagosModel(InputStream xmlStream) throws XMLParserException {
    try {
      JAXBContext jc = JAXBContext.newInstance(Pagos.class);
      Unmarshaller um = jc.createUnmarshaller();
      return (Pagos) um.unmarshal(xmlStream);
    } catch (JAXBException ex) {
      throw new XMLParserException(ex.getMessage());
    }
  }

  public static Pagos xmlToPagosModel(String xml) throws XMLParserException {
    return xmlToPagosModel(new ByteArrayInputStream(xml.getBytes()));
  }

  public static Nomina xmlToNominaModel(InputStream xmlStream) throws XMLParserException {
    try {
      JAXBContext jc = JAXBContext.newInstance(Nomina.class);
      Unmarshaller um = jc.createUnmarshaller();
      return (Nomina) um.unmarshal(xmlStream);
    } catch (JAXBException ex) {
      throw new XMLParserException(ex.getMessage());
    }
  }

  public static Nomina xmlToNominaModel(String xml) throws XMLParserException {
    return xmlToNominaModel(new ByteArrayInputStream(xml.getBytes()));
  }
}
