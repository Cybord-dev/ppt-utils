package com.unknown.cfdi.modelos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TrasladoConcepto implements Serializable {

  private static final long serialVersionUID = 3241569278979852126L;

  @NonNull private BigDecimal base;
  @NonNull private String impuesto;
  @NonNull private String tipoFactor;
  @NonNull private BigDecimal tasaOCuota;
  @NonNull private BigDecimal importe;
}
