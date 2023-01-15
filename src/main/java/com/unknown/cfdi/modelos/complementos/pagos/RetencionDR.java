package com.unknown.cfdi.modelos.complementos.pagos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.sun.istack.NotNull;
import com.unknown.models.generated.CTipoFactor;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
public class RetencionDR implements Serializable {

  @NotNull private BigDecimal baseDR;
  @NotNull private String impuestoDR;
  @NotNull private CTipoFactor tipoFactorDR;
  @NotNull private BigDecimal tasaOCuotaDR;
  @NotNull private BigDecimal importeDR;
}
