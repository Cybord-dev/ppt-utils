package com.unknown.cfdi.mappers.auxiliars;

import com.unknown.models.generated.CTipoFactor;

public class ImpuestosAuxiliarMapper {

  public com.unknown.models.generated.CTipoFactor map(String value) {
    return CTipoFactor.fromValue(value);
  }

  public String map(com.unknown.models.generated.CTipoFactor value) {
    return value.value();
  }
}
