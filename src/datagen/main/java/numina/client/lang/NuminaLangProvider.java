package numina.client.lang;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lehjr.numina.constants.NuminaConstants;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import numina.ResourceList;
import org.apache.commons.lang3.text.translate.JavaUnicodeEscaper;

import javax.annotation.Nonnull;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Stupid code to do stupid things
 * Seriously, all this is for is aiding in porting old language files from MPS to the modern format with
 * the updated keys.
 */
public class NuminaLangProvider implements IDataProvider {
    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();
    private final DataGenerator gen;
    private final String modid;
    private final String defaultLocale = "en_us";
    ExistingFileHelper fileHelper;
    private final String root;
    ArrayList<NuminaLangMapWrapper> langMapWrappers = new ArrayList<>();

//    ExistingFileHelper.IResourceType resourceType = VanillaResourceType.LANGUAGES;// new net.minecraftforge.common.data.ExistingFileHelper.ResourceType(VanillaResourceType.LANGUAGES, ".json", "lang");

    public NuminaLangProvider(DataGenerator gen, ExistingFileHelper existingFileHelper, String modid, String root) {
        this.gen = gen;
        this.modid = modid;
        this.fileHelper = existingFileHelper;
        this.root = root;
    }

    @Override
    public void run(DirectoryCache cache) throws IOException {
        Path src = gen.getOutputFolder().getParent().getParent().getParent();
        File langFolder = new File(src.toFile(), root + "/resources/assets/" + modid + "/lang");
        System.out.println("source folder: " +langFolder);

        if (langFolder.exists() && langFolder.isDirectory()) {
            ArrayList<File> files = ResourceList.getResourcesFromDirectory(langFolder, Pattern.compile(".json", Pattern.CASE_INSENSITIVE));

            // setup default language (en_us)
            files.stream().filter(file -> file.getName().contains(defaultLocale)).forEach(file -> {
                langMapWrappers.add(new NuminaLangMapWrapper(file));
            });

            // setup other languages
            files.stream().filter(file -> !file.getName().contains(defaultLocale)).forEach(file -> {
                langMapWrappers.add(new NuminaLangMapWrapper(file, langMapWrappers.get(0)));
            });

            System.out.println("modID: " + modid + ", output folder: " + gen.getOutputFolder());

            langMapWrappers.forEach(wrapper -> wrapper.savetoOutputFolder(cache, gen.getOutputFolder().resolve("assets/" + modid + "/lang/")));
        } else {
            System.out.println("lang folder not found !!!:");
        }
    }

    @Override
    public String getName() {
        return "LangThing:" + NuminaConstants.MOD_ID;
    }
}
