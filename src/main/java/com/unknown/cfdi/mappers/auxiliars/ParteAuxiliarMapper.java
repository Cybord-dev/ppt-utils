package com.unknown.cfdi.mappers.auxiliars;

import com.google.common.collect.ImmutableList;
import com.unknown.cfdi.mappers.InformacionAduaneraMapper;
import com.unknown.cfdi.modelos.InformacionAduanera;
import com.unknown.models.generated.Comprobante;
import java.util.List;
import java.util.Objects;
import org.mapstruct.factory.Mappers;

public class ParteAuxiliarMapper {

  public static final InformacionAduaneraMapper informacionAduaneraMapper =
      Mappers.getMapper(InformacionAduaneraMapper.class);

  public InformacionAduanera mapComprobanteParteInformacionAduanera(
      List<Comprobante.Conceptos.Concepto.Parte.InformacionAduanera> value) {
    if (Objects.nonNull(value) && !value.isEmpty()) {
      return value.stream()
          .map(
              e ->
                  informacionAduaneraMapper.informacionAduaneraConceptoToCfdiInformacionAduanera(e))
          .findAny()
          .orElseThrow(null);
    }
    return null;
  }

  public List<Comprobante.Conceptos.Concepto.Parte.InformacionAduanera> mapInformacionAduanera(
      InformacionAduanera value) {
    Comprobante.Conceptos.Concepto.Parte.InformacionAduanera informacionAduanera =
        new Comprobante.Conceptos.Concepto.Parte.InformacionAduanera();
    informacionAduanera.setNumeroPedimento(value.getNumeroPedimento());
    List<Comprobante.Conceptos.Concepto.Parte.InformacionAduanera> informacionAduaneraList =
        ImmutableList.of(informacionAduanera);
    return informacionAduaneraList;
  }
}
