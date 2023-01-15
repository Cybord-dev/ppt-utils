package com.unknown.cfdi.modelos.complementos.nomina;

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
public class SeparacionIndemnizacion implements Serializable {
  @NonNull BigDecimal totalPagado;
  @NonNull private int numAñosServicio;
  @NonNull private BigDecimal ultimoSueldoMensOrd;
  @NonNull private BigDecimal ingresoAcumulable;
  @NonNull private BigDecimal ingresoNoAcumulable;
}
