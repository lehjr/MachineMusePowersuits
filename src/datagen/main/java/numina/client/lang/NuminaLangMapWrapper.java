package numina.client.lang;

import com.google.common.hash.Hashing;
import com.google.common.hash.HashingOutputStream;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import lehjr.numina.common.base.NuminaLogger;
import net.minecraft.data.CachedOutput;
import net.minecraft.util.GsonHelper;
import org.apache.commons.io.FilenameUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import static net.minecraft.data.DataProvider.KEY_COMPARATOR;

public class NuminaLangMapWrapper {
    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();
    String locale;
    Map<String, String> data = new HashMap<>();
    Map<String, String> extraData = new HashMap<>();
    NuminaLangMapWrapper defaultLangWrapper;

    public NuminaLangMapWrapper(File jsonFile) {
        defaultLangWrapper =this;
        loadAndParse(jsonFile);
    }

    public NuminaLangMapWrapper(File jsonFile, NuminaLangMapWrapper defaultLangWrapper) {
        this.defaultLangWrapper = defaultLangWrapper;
        loadAndParse(jsonFile);
    }

    /**
     * Called by other instances to evaluate where to put the translation key and translation
     * @param key
     * @return
     */
    public boolean defaultMapHasKey(String key) {
        if (defaultLangWrapper == this) {
            return data.containsKey(key);
        }
        return defaultLangWrapper.defaultMapHasKey(key);
    }

    public Set<Map.Entry<String, String>> getDefaultMapEntries() {
        if (thisIsDefault()) {
            return data.entrySet();
        }
        return defaultLangWrapper.getDefaultMapEntries();
    }

    public void addEntryToMap(Map.Entry<String, String> entry) {
        if (defaultLangWrapper == this) {
            data.put(entry.getKey(), entry.getValue());
        }

        if (defaultMapHasKey(entry.getKey())) {
            data.put(entry.getKey(), entry.getValue());
        } else {
            extraData.put(entry.getKey(), entry.getValue());
        }
    }

    boolean thisIsDefault () {
        return defaultLangWrapper == this;
    }

    public void loadAndParse(File jsonFile) {
        this.locale = FilenameUtils.getBaseName(jsonFile.getName());
        NuminaLogger.logDebug("parsing file: " + jsonFile.getName());
        NuminaLogger.logDebug("this is default: " + thisIsDefault());

        Map<String, String> map = JSonLoader.parseJsonFile(jsonFile);
        NuminaLogger.logDebug("map size: " + map.size());


        map.entrySet().stream().filter(entry -> !entry.getKey().equals("_comment"))
                .forEach(entry -> addEntryToMap(entry));
        NuminaLogger.logDebug("data size: " + data.size());
        NuminaLogger.logDebug("extra data size: " + extraData.size());

    }

    public void savetoOutputFolder(CachedOutput cache, Path outputFolder) {
        try {
            if (!thisIsDefault()) {
                if (!extraData.isEmpty()) {
                    File extraFile = new File(outputFolder.toFile(), locale + "_extra.json");
                    save(cache, maptoJsonObject(extraData), extraFile.toPath(), true);
                }

                Map<String, String> missingMap = new TreeMap<>();
                getDefaultMapEntries().stream().filter(thing -> !thing.getKey().equals("_comment")).forEach(entry ->{
                    if (!data.containsKey(entry.getKey())) {
                        missingMap.put(entry.getKey(), entry.getValue());
                    }
                });

                if (!missingMap.isEmpty()) {
                    missingMap.forEach((k, v) -> data.putIfAbsent(k, v));
                    File missingFile = new File(outputFolder.toFile(), locale + "_missing.json");
                    save(cache, maptoJsonObject(missingMap), missingFile.toPath(), true);
                }
            }

            if (!data.isEmpty()) {
                File outFile = new File(outputFolder.toFile(), locale + ".json");
                save(cache, maptoJsonObject(data), outFile.toPath(), true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    JsonObject maptoJsonObject(Map<String, String> map) {
        JsonObject jsonObject = new JsonObject();
        map.entrySet().forEach(entry -> jsonObject.addProperty(entry.getKey(), entry.getValue()));
        return jsonObject;
    }

    public void save(CachedOutput pOutput, JsonObject pJson, Path pPath, boolean overwrite) throws IOException {
        System.out.println("json: " + pJson.toString());

        ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
        HashingOutputStream hashingoutputstream = new HashingOutputStream(Hashing.sha1(), bytearrayoutputstream);
        Writer writer = new OutputStreamWriter(hashingoutputstream, StandardCharsets.UTF_8);
        JsonWriter jsonwriter = new JsonWriter(writer);
        jsonwriter.setSerializeNulls(false);
        jsonwriter.setIndent("  ");
        GsonHelper.writeValue(jsonwriter, pJson, KEY_COMPARATOR);
        jsonwriter.close();
        pOutput.writeIfNeeded(pPath, bytearrayoutputstream.toByteArray(), hashingoutputstream.hash());
    }
}

