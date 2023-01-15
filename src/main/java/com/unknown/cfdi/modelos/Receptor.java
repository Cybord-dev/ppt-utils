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
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Receptor implements Serializable {

  private static final long serialVersionUID = 171758513601059506L;

  @NonNull private String rfc;
  @NonNull private String nombre;
  @NonNull private String domicilioFiscalReceptor;
  private String residenciaFiscal;
  private String numRegIdTrib;
  @NonNull private String regimenFiscalReceptor;
  @NonNull private String usoCfdi;
  private String direccion;
}
