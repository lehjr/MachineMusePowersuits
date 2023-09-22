package powersuits.common;

import powersuits.client.lang.MPSLanguageProvider;
import powersuits.common.loot.MPSBlockTagProvider;
import powersuits.common.loot.MPSLootTableProvider;
import lehjr.powersuits.common.constants.MPSConstants;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import numina.client.lang.NuminaLangParser;

@Mod.EventBusSubscriber(modid = MPSConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class PowersuitsDataGenerator {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();




        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        if (event.includeClient()) {
            System.out.println("hello from MPS datagen client");
            //Client side data generators
//            gen.addProvider(true, new NuminaLangParser(gen, existingFileHelper, MPSConstants.MOD_ID, MPSConstants.MOD_ID));
            gen.addProvider(true, new MPSLanguageProvider(gen, MPSConstants.MOD_ID, "us_en"));
        }
        if (event.includeServer()) {
            System.out.println("hello from MPS datagen server");
            //Server side data generators
            gen.addProvider(true, new MPSLootTableProvider(gen));
            gen.addProvider(true, new MPSBlockTagProvider(gen, existingFileHelper));
        }

        gen.getProviders().forEach(dataProvider -> System.out.println("dataProviderName: " + dataProvider.getName()));

    }
}