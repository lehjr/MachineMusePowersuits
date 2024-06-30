package numina.common;

import lehjr.numina.common.constants.NuminaConstants;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import numina.client.lang.*;
import numina.common.loot.NuminaBlockTagProvider;
import numina.common.loot.NuminaLootTableProvider;


@Mod.EventBusSubscriber(modid = NuminaConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class NuminaDataGenerator {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();

        //Client side data generators
        gen.addProvider(event.includeClient(), new NuminaLanguageProvider_AF_ZA(gen));
        gen.addProvider(event.includeClient(), new NuminaLanguageProvider_DE_DE(gen));
        gen.addProvider(event.includeClient(), new NuminaLanguageProvider_FR_FR(gen));
        gen.addProvider(event.includeClient(), new NuminaLanguageProvider_HE_IL(gen));
        gen.addProvider(event.includeClient(), new NuminaLanguageProvider_PT_BR(gen));
        gen.addProvider(event.includeClient(), new NuminaLanguageProvider_PT_PT(gen));
        gen.addProvider(event.includeClient(), new NuminaLanguageProvider_RU_RU(gen));
        gen.addProvider(event.includeClient(), new NuminaLanguageProvider_US_EN(gen));
        gen.addProvider(event.includeClient(), new NuminaLanguageProvider_ZH_CN(gen));
        gen.addProvider(event.includeClient(), new NuminaLanguageProvider_ZH_TW(gen));







        //Server side data generators
        gen.addProvider(event.includeServer(), new NuminaLootTableProvider(gen));
        gen.addProvider(event.includeServer(), new NuminaBlockTagProvider(gen, event.getExistingFileHelper()));
    }
}

