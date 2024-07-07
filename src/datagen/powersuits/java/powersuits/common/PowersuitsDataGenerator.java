package powersuits.common;

import lehjr.powersuits.common.constants.MPSConstants;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import powersuits.client.lang.*;
import powersuits.common.loot.MPSBlockTagProvider;
import powersuits.common.loot.MPSLootTableProvider;

@Mod.EventBusSubscriber(modid = MPSConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class PowersuitsDataGenerator {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        //Client side data generators
        gen.addProvider(event.includeClient(), new MPSLanguageProvider_AF_ZA(gen));
        gen.addProvider(event.includeClient(), new MPSLanguageProvider_DE_DE(gen));
        gen.addProvider(event.includeClient(), new MPSLanguageProvider_EN_US(gen));
//        gen.addProvider(event.includeClient(), new MPSLanguageProvider_FR_FR(gen));
//        gen.addProvider(event.includeClient(), new MPSLanguageProvider_HE_IL(gen));
//        gen.addProvider(event.includeClient(), new MPSLanguageProvider_PT_BR(gen));
//        gen.addProvider(event.includeClient(), new MPSLanguageProvider_PT_PT(gen));
//        gen.addProvider(event.includeClient(), new MPSLanguageProvider_RU_RU(gen));
//        gen.addProvider(event.includeClient(), new MPSLanguageProvider_ZH_CN(gen));
//        gen.addProvider(event.includeClient(), new MPSLanguageProvider_ZH_TW(gen));


        //Server side data generators
        gen.addProvider(event.includeServer(), new MPSLootTableProvider(gen));
        gen.addProvider(event.includeServer(), new MPSBlockTagProvider(gen, existingFileHelper));
    }
}