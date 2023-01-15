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
public class Nomina implements Serializable {
  private Emisor emisor;
  @NonNull private Receptor receptor;
  private Percepciones percepciones;
  private Deducciones deducciones;
  @NonNull @Builder.Default private List<OtroPago> otrosPagos = new ArrayList<>();
  @NonNull @Builder.Default private List<Incapacidad> incapacidades = new ArrayList<>();
  @NonNull private String version;
  @NonNull private String tipoNomina;
  @NonNull private String fechaPago;
  @NonNull private String fechaInicialPago;
  @NonNull private String fechaFinalPago;
  @NonNull private BigDecimal numDiasPagados;
  private BigDecimal totalPercepciones;
  private BigDecimal totalDeducciones;
  private BigDecimal totalOtrosPagos;
}
