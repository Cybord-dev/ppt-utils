package com.unknown.util;

import com.sun.xml.bind.marshaller.NamespacePrefixMapper;

public class CfdiNamespaceMapper extends NamespacePrefixMapper {

  @Override
  public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix) {

    switch (namespaceUri) {
      case NamespaceConstants.SAT_NS_40:
        suggestion = NamespaceConstants.SAT_NS_4_PREFIX;
        break;
      case NamespaceConstants.W3_NS:
        suggestion = NamespaceConstants.XSI_PREFIX;
        break;
      case NamespaceConstants.PAGOS_NS:
        suggestion = NamespaceConstants.PAGO_PREFIX;
        break;
      case NamespaceConstants.TFD_NS:
        suggestion = NamespaceConstants.TFD_PREFIX;
        break;
      case NamespaceConstants.NOMINA_NS:
        suggestion = NamespaceConstants.NOMINA_PREFIX;
        break;
      case NamespaceConstants.CARTA_PORTE_NS:
        suggestion = NamespaceConstants.CARTA_PORTE_PREFIX;
        break;
      case NamespaceConstants.COMERCIO_EXT_NS:
        suggestion = NamespaceConstants.COMERCIO_EXT_PREFIX;
        break;
    }

    return suggestion;
  }

  @Override
  public String[] getPreDeclaredNamespaceUris() {
    return new String[] {NamespaceConstants.W3_NS};
  }
}
