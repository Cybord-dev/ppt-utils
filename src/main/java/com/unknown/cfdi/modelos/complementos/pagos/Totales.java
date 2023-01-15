package com.unknown.cfdi.modelos.complementos.pagos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
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
public class Totales implements Serializable {
  protected BigDecimal totalRetencionesIVA;
  protected BigDecimal totalRetencionesISR;
  protected BigDecimal totalRetencionesIEPS;
  protected BigDecimal totalTrasladosBaseIVA16;
  protected BigDecimal totalTrasladosImpuestoIVA16;
  protected BigDecimal totalTrasladosBaseIVA8;
  protected BigDecimal totalTrasladosImpuestoIVA8;
  protected BigDecimal totalTrasladosBaseIVA0;
  protected BigDecimal totalTrasladosImpuestoIVA0;
  protected BigDecimal totalTrasladosBaseIVAExento;
  protected BigDecimal montoTotalPagos;
}
