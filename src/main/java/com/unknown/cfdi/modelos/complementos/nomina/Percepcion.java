package com.unknown.cfdi.modelos.complementos.nomina;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.math.BigDecimal;
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
public class Percepcion implements Serializable {
  private AccionesOTitulos accionesOTitulos;
  private List<HorasExtra> horasExtra;
  @NonNull private String tipoPercepcion;
  @NonNull private String clave;
  @NonNull private String concepto;
  @NonNull private BigDecimal importeGravado;
  @NonNull private BigDecimal importeExento;
}
