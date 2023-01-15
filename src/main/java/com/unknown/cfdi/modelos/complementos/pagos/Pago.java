package com.unknown.cfdi.modelos.complementos.pagos;

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
public class Pago implements Serializable {
  private static final long serialVersionUID = 2684754755723604222L;
  private List<DocumentoRelacionado> relacionados;
  protected ImpuestosP impuestosP;
  @NonNull private String fechaPago;
  @NonNull private String formaDePagoP;
  @NonNull private String monedaP;
  private String tipoCambioP;
  @NonNull private BigDecimal monto;
  private String numOperacion;
  private String rfcEmisorCtaOrd;
  private String nomBancoOrdExt;
  private String ctaOrdenante;
  private String rfcEmisorCtaBen;
  private String ctaBeneficiario;
  private String tipoCadPago;
  private byte[] certPago;
  private String cadPago;
  private byte[] selloPago;
}
