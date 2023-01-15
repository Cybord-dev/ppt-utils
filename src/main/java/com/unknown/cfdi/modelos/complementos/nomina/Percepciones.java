package com.unknown.cfdi.modelos.complementos.nomina;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
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
public class Percepciones implements Serializable {
  @NonNull @Builder.Default private List<Percepcion> incapacidad = new ArrayList<>();
  private JubilacionPensionRetiro jubilacionPensionRetiro;
  private SeparacionIndemnizacion separacionIndemnizacion;
  private BigDecimal totalSueldos;
  private BigDecimal totalSeparacionIndemnizacion;
  private BigDecimal totalJubilacionPensionRetiro;
  @NonNull private BigDecimal totalGravado;
  @NonNull private BigDecimal totalExento;
}
