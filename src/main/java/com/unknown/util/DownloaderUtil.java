package com.unknown.util;

import com.unknown.error.PptUtilException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.dhatim.fastexcel.Workbook;
import org.dhatim.fastexcel.Worksheet;

@Slf4j
public class DownloaderUtil {

  /**
   * Generates a Base64 Excel Report using the headers and the data provided as parameter
   *
   * @param reportName name of the report to be downloaded
   * @param data Map of information to be charged
   * @param headers Headers of the report file
   * @return
   * @throws {@link PptUtilException}
   */
  public static String generateBase64Report(
      String reportName, List<Map<String, Object>> data, List<String> headers)
      throws PptUtilException {
    if (data == null) throw new PptUtilException("data is mandatory");
    if (headers == null) throw new PptUtilException("headers is mandatory");
    try {
      if (!data.isEmpty()) {
        log.info("Generating Excel report for {} data", data.size());
        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
          Workbook wb = new Workbook(os, "InvManagerReport", "1.0");
          Worksheet ws = wb.newWorksheet(reportName);
          int column = 0;
          int row = 0;
          for (String header : headers) {
            ws.value(0, column, header);
            column++;
          }
          row++;
          for (Map<String, Object> map : data) {
            column = 0;
            for (String header : headers) {
              String value = (map.get(header) != null ? map.get(header).toString() : " ");
              ws.value(row, column, value);
              column++;
            }
            row++;
          }
          wb.finish();
          return Base64.getEncoder().encodeToString(os.toByteArray());
        }
      } else {
        throw new PptUtilException("Cant generate report , no data provided");
      }
    } catch (IOException e) {
      throw new PptUtilException(e.getMessage());
    }
  }
}
