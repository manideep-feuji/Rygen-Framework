package utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.testng.annotations.DataProvider;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class TestDataProvider {

    @DataProvider(name = "orderData")
    public Object[][] getOrderData() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        File jsonFile = new File("src/test/resources/testdata/OrderTestData.json");

        // Parse JSON array into a List of Maps
        List<Map<String, Object>> dataList = mapper.readValue(
                jsonFile, 
                new TypeReference<List<Map<String, Object>>>() {}
        );

        // Convert List of Maps to Object[][] for TestNG
        Object[][] data = new Object[dataList.size()][1];
        for (int i = 0; i < dataList.size(); i++) {
            data[i][0] = dataList.get(i);
        }

        return data;
    }
}
