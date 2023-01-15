package com.unknown.cfdi.modelos.complementos.pagos;

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
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DocumentoRelacionado implements Serializable {
  private static final long serialVersionUID = 9005239859167130237L;

  private ImpuestosDR impuestosDR;
  @NonNull private String idDocumento;
  private String serie;
  private String folio;
  @NonNull private String monedaDR;
  private BigDecimal equivalenciaDR;
  @NonNull private Integer numParcialidad;
  @NonNull private BigDecimal impSaldoAnt;
  @NonNull private BigDecimal impPagado;
  @NonNull private BigDecimal impSaldoInsoluto;
  @NonNull private String objetoImpDR;
}
