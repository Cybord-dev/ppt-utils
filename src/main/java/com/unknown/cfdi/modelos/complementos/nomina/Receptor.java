package com.unknown.cfdi.modelos.complementos.nomina;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
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
public class Receptor implements Serializable {

  private List<SubContratacion> subContratacion;
  @NonNull private String curp;
  private String numSeguridadSocial;
  private String fechaInicioRelLaboral;
  private String antigüedad;
  @NonNull private String tipoContrato;
  private String sindicalizado;
  private String tipoJornada;
  @NonNull private String tipoRegimen;
  @NonNull private String numEmpleado;
  private String departamento;
  private String puesto;
  private String riesgoPuesto;
  @NonNull private String periodicidadPago;
  private String banco;
  private BigInteger cuentaBancaria;
  private BigDecimal salarioBaseCotApor;
  private BigDecimal salarioDiarioIntegrado;
  @NonNull private String claveEntFed;
}
