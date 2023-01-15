package com.unknown.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.unknown.error.PptUtilException;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

public class NumberTranslatorUtilTest {

  @Test
  public void getStringNumber_whenIsSuccess_pesosCurrency() throws PptUtilException {
    String response = NumberTranslatorUtil.getStringNumber(BigDecimal.valueOf(10.10), "MXN");
    assertNotNull(response);
    assertEquals("Diez Pesos Un  Centavos", response);
  }

  @Test
  public void getStringNumber_whenIsSuccess_pesosCurrency2() throws PptUtilException {
    String response = NumberTranslatorUtil.getStringNumber(BigDecimal.valueOf(9999.99), "MXN");
    assertNotNull(response);
    assertEquals("Nueve Mil Novecientos Noventa y Nueve Pesos Noventa y Nueve  Centavos", response);
  }

  @Test
  public void getStringNumber_whenIsSuccess_dolaresCurrency() throws PptUtilException {
    String response = NumberTranslatorUtil.getStringNumber(BigDecimal.valueOf(10.10), "USD");
    assertNotNull(response);
    assertEquals("Diez Dolares Un  Centavos", response);
  }

  @Test
  public void getStringNumber_whenIsSuccess_DolaresCurrency2() throws PptUtilException {
    String response = NumberTranslatorUtil.getStringNumber(BigDecimal.valueOf(9999.99), "USD");
    assertNotNull(response);
    assertEquals(
        "Nueve Mil Novecientos Noventa y Nueve Dolares Noventa y Nueve  Centavos", response);
  }

  @Test
  public void getStringNumber_whenFails_numberNull() throws PptUtilException {
    PptUtilException thrown =
        assertThrows(
            PptUtilException.class,
            () -> {
              NumberTranslatorUtil.getStringNumber(null, "USD");
            });
    assertEquals("Number is mandatory", thrown.getMessage());
  }

  @Test
  public void getStringNumber_whenFails_currencyNull() throws PptUtilException {
    PptUtilException thrown =
        assertThrows(
            PptUtilException.class,
            () -> {
              NumberTranslatorUtil.getStringNumber(BigDecimal.TEN, "");
            });
    assertEquals("currency is mandatory", thrown.getMessage());
  }
}
