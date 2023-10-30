package numina.client.util;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.annotation.Nullable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;


public class JSonLoader {
    private JSonLoader (){
    }

    @Nullable
    public static final JsonObject loadJsonFile(File jsonFile) {
        if (!jsonFile.exists()) {
            return null;
        }

        //JSON parser object to parse read file
        JsonParser jsonParser = new JsonParser();
        try (FileReader reader = new FileReader(jsonFile)) {
            Object object = jsonParser.parse(reader);
            if (object instanceof JsonObject) {
                JsonObject jsonObject = (JsonObject) object;
                return jsonObject;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static final Map<String, String> parseJsonFile(File jsonFile) {
        Map<String, String> data = new TreeMap<>();
        JsonObject jsonObject = loadJsonFile(jsonFile);
        if (jsonObject != null) {
            jsonObject.entrySet().forEach(stringJsonElementEntry -> {
                data.put(stringJsonElementEntry.getKey(), stringJsonElementEntry.getValue().getAsString());
            });
        }
        return data;
    }
}
