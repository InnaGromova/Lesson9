package example.org;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static com.codeborne.pdftest.assertj.Assertions.assertThat;
import static java.nio.charset.StandardCharsets.UTF_8;

public class FileParseTest {
    ClassLoader classLoader = FileParseTest.class.getClassLoader();

    @Test
    void testZip() throws Exception {
        InputStream is = classLoader.getResourceAsStream("Testzip.zip");
        ZipInputStream zis = new ZipInputStream(is);
        ZipEntry entry;
        while ((entry = zis.getNextEntry()) != null) {
            if (entry.getName().contains("xlsx")) {
                XLS xls = new XLS(zis);
                assertThat(xls.excel
                        .getSheetAt(0)
                        .getRow(2)
                        .getCell(1)
                        .getNumericCellValue()).isEqualTo(3.0);
            }
            if (entry.getName().contains("pdf")) {
                PDF pdfFile = new PDF(zis);
                assertThat(pdfFile.text).contains("Подменный контент");
            }
            if (entry.getName().contains("csv")) {
                CSVReader csvReader = new CSVReader(new InputStreamReader(zis, UTF_8));
                List<String[]> csvList = csvReader.readAll();
                assertThat(csvList).contains(
                        new String[]{"Test ;1"});
            }
        }
    }
}