package example.org;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.Test;
import java.io.InputStream;
import java.io.InputStreamReader;
import static com.codeborne.pdftest.assertj.Assertions.assertThat;
public class JsonTest {
    ClassLoader classLoader = JsonTest.class.getClassLoader();
    @Test
    void testJson() throws Exception {
        InputStream inputStream = classLoader.getResourceAsStream("Test1.json");
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(new InputStreamReader(inputStream), JsonObject.class);
        assertThat(jsonObject.get("name").getAsString()).isEqualTo("Lana Smit");
        assertThat(jsonObject.get("age").getAsInt()).isEqualTo(20);
        assertThat(jsonObject.get("married").getAsBoolean()).isEqualTo(true);
        assertThat(jsonObject.get("parents").getAsJsonObject().get("mother").getAsString()).isEqualTo("Lada Smit");
        assertThat(jsonObject.get("parents").getAsJsonObject().get("father").getAsString()).isEqualTo("Alan Smit");
    }
}