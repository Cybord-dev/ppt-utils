package com.unknown.util;

import com.sun.xml.bind.marshaller.NamespacePrefixMapper;

public class RetencionesNamespaceMapper extends NamespacePrefixMapper {

  @Override
  public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix) {

    switch (namespaceUri) {
      case NamespaceConstants.RETENCIONES_NS:
        suggestion = NamespaceConstants.RETENCIONES_PREFIX;
        break;
      case NamespaceConstants.W3_NS:
        suggestion = NamespaceConstants.XSI_PREFIX;
        break;
      case NamespaceConstants.TFD_NS:
        suggestion = NamespaceConstants.TFD_PREFIX;
        break;
      case NamespaceConstants.DIVIDENDOS_NS:
        suggestion = NamespaceConstants.DIVIDENDOS_PREFIX;
        break;
      case NamespaceConstants.PLAN_RETIRO_NS:
        suggestion = NamespaceConstants.PLAN_RETIRO_PREFIX;
        break;
      case NamespaceConstants.PLATAFORMAS_TEC_NS:
        suggestion = NamespaceConstants.PLATAFORMAS_TEC_PREFIX;
        break;
      case NamespaceConstants.HIPOTECARIOS_NS:
        suggestion = NamespaceConstants.HIPOTECARIOS_PREFIX;
        break;
      case NamespaceConstants.PREMIOS_NS:
        suggestion = NamespaceConstants.PREMIOS_PREFIX;
        break;
      case NamespaceConstants.INTERESES_NS:
        suggestion = NamespaceConstants.INTERESES_PREFIX;
        break;
    }

    return suggestion;
  }

  @Override
  public String[] getPreDeclaredNamespaceUris() {
    return new String[] {NamespaceConstants.W3_NS};
  }
}
