package com.unknown.cfdi.modelos.complementos.timbrefiscaldigital;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class TimbreFiscalDigital implements Serializable {
  private static final long serialVersionUID = -8106845652593559134L;

  @NonNull @Builder.Default private String version = "1.1";

  @NonNull private String uuid;

  @NonNull private String fechaTimbrado;

  @NonNull private String selloCFD;

  @NonNull private String rfcProvCertif;

  @NonNull private String selloSAT;

  @NonNull private String noCertificadoSAT;
}
