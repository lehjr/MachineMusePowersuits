package numina.client.util.lang;

import com.google.gson.*;
import lehjr.numina.common.base.NuminaLogger;
import numina.client.util.JSonLoader;
import numina.client.util.lang.translators.Language;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LangMapWrapper {
    Map<String, String> data = new HashMap<>();
    String filePath;

    public LangMapWrapper(File jsonFile) {
        loadAndParse(jsonFile);
        this.filePath = jsonFile.getAbsolutePath();
    }

    // use this to create a file in the source dir to save progress
    public LangMapWrapper(File dir, Language language) {
        filePath = new File(dir, language.mc_label() + ".json").getAbsolutePath();
    }

    public String getTranslation(String key) {
        return data.getOrDefault(key, "");
    }

    public Map<String, String> getData() {
        return data;
    }

    public void addEntryToMap(Map.Entry<String, String> entry) {
        data.put(entry.getKey(), entry.getValue());
    }

    public void addTranslation(String key, String value) {
        data.put(key, value);
        save();
    }

    public void loadAndParse(File jsonFile) {
        NuminaLogger.logDebug("parsing file: " + jsonFile.getName());

        Map<String, String> map = JSonLoader.parseJsonFile(jsonFile);
        NuminaLogger.logDebug("map size: " + map.size());

        map.entrySet().stream().filter(entry -> !entry.getKey().equals("_comment"))
                .forEach(entry -> addEntryToMap(entry));
        NuminaLogger.logDebug("data size: " + data.size());

    }

//    public void savetoOutputFolder(CachedOutput cache, Path outputFolder) {
//        try {
//            if (!thisIsDefault()) {
//                if (!extraData.isEmpty()) {
//                    File extraFile = new File(outputFolder.toFile(), locale + "_extra.json");
//                    save(cache, maptoJsonObject(extraData), extraFile.toPath(), true);
//                }
//
//                Map<String, String> missingMap = new TreeMap<>();
//                getDefaultMapEntries().stream().filter(thing -> !thing.getKey().equals("_comment")).forEach(entry ->{
//                    if (!data.containsKey(entry.getKey())) {
//                        missingMap.put(entry.getKey(), entry.getValue());
//                    }
//                });
//
//                if (!missingMap.isEmpty()) {
//                    missingMap.forEach((k, v) -> data.putIfAbsent(k, v));
//                    File missingFile = new File(outputFolder.toFile(), locale + "_missing.json");
//                    save(cache, maptoJsonObject(missingMap), missingFile.toPath(), true);
//                }
//            }
//
//            if (!data.isEmpty()) {
//                File outFile = new File(outputFolder.toFile(), locale + ".json");
//                save(cache, maptoJsonObject(data), outFile.toPath(), true);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
    JsonObject maptoJsonObject(Map<String, String> map) {
        JsonObject jsonObject = new JsonObject();
        map.entrySet().forEach(entry -> jsonObject.addProperty(entry.getKey(), entry.getValue()));
        return jsonObject;
    }

//    public void save(CachedOutput pOutput, JsonObject pJson, Path pPath, boolean overwrite) throws IOException {
//        System.out.println("json: " + pJson.toString());
//
//        ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
//        HashingOutputStream hashingoutputstream = new HashingOutputStream(Hashing.sha1(), bytearrayoutputstream);
//        Writer writer = new OutputStreamWriter(hashingoutputstream, StandardCharsets.UTF_8);
//        JsonWriter jsonwriter = new JsonWriter(writer);
//        jsonwriter.setSerializeNulls(false);
//        jsonwriter.setIndent("  ");
//        GsonHelper.writeValue(jsonwriter, pJson, KEY_COMPARATOR);
//        jsonwriter.close();
//        pOutput.writeIfNeeded(pPath, bytearrayoutputstream.toByteArray(), hashingoutputstream.hash());
//    }

    public void save() {
        Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
        JsonObject json = maptoJsonObject(data);
        JsonElement je = JsonParser.parseString(json.toString());
        String prettyJsonString = gson.toJson(je);
        FileWriter writer;
        try {
            writer = new FileWriter(new File(filePath));
            writer.write(prettyJsonString);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

