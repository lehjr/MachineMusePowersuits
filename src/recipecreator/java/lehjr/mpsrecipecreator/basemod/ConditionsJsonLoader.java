package lehjr.mpsrecipecreator.basemod;

import com.google.gson.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ConditionsJsonLoader {
    public static final  File jsonFile = setupJsonFile("conditions.json");

    public static void setFile() {
        // create file
        if (!jsonFile.exists()) {
            JsonObject conditions = new JsonObject();
            JsonArray conditionsList = new JsonArray();

            // mod
            conditionsList.add(
                    forgeModLoadedJsonObject(
                            "Silent Mechanisms Loaded",
                            "silent_mechanisms"));

            // mod
            conditionsList.add(
                    forgeModLoadedJsonObject(
                            "Centralized Materials Loaded",
                            "centralizedmaterials"));

//            // flag
//            conditionsList.add(
//                    flagJsonObject(
//                            "Vanilla Recipes Enabled",
//                            "modularpowersuits:flag",
//                            "vanilla_recipes_enabled"));

            conditions.add("conditions", conditionsList);

            Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
            JsonParser jp = new JsonParser();
            JsonElement je = jp.parse(conditions.toString());
            String prettyJsonString = gson.toJson(je);

            System.out.println("prettyJson: " + prettyJsonString);

            DataPackWriter.INSTANCE.fileWriter(jsonFile, prettyJsonString, false);
        }
    }

    public static JsonArray getConditionsFromFile() {
        //JSON parser object to parse read file
        JsonParser jsonParser = new JsonParser();
        try (FileReader reader = new FileReader(jsonFile)) {
            Object object = jsonParser.parse(reader);
            if (object instanceof JsonObject) {
                JsonObject jsonObject = (JsonObject) object;
                if (jsonObject.has("conditions") && jsonObject.get("conditions") instanceof JsonArray) {
                    return jsonObject.getAsJsonArray("conditions");
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static File setupJsonFile(String fileName) {
        Path configFile = Paths.get("config/lehjr").resolve(Constants.MOD_ID).resolve(fileName);
        File cfgFile = configFile.toFile();
        return cfgFile;
    }

    static JsonObject forgeModLoadedJsonObject(String displayName, String modId) {
        JsonObject json = new JsonObject();
        json.addProperty("type", "forge:mod_loaded");
        json.addProperty("display_name", displayName);
        json.addProperty("modid", modId);
        return json;
    }

    static JsonObject flagJsonObject(String displayName, String type, String flag) {
        JsonObject json = new JsonObject();
        json.addProperty("type", type);
        json.addProperty("display_name", displayName);
        json.addProperty("flag", flag);
        return json;
    }
}