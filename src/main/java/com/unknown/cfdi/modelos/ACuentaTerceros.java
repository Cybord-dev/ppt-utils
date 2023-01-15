package com.unknown.cfdi.modelos;

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
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ACuentaTerceros implements Serializable {

  private static final long serialVersionUID = 113298243726456784L;

  @NonNull private String rfcAcuentaTerceros;
  @NonNull private String nombreAcuentaTerceros;
  @NonNull private String regimenFiscalAcuentaTerceros;
  @NonNull private String domicilioFiscalAcuentaTerceros;
}
