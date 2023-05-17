package lehjr.mpsrecipecreator.basemod;

import lehjr.mpsrecipecreator.basemod.config.Config;
import lehjr.mpsrecipecreator.client.gui.MPSRCGui;
import lehjr.mpsrecipecreator.network.NetHandler;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

/**
 * @author lehjr
 */
@Mod(Constants.MOD_ID)
public final class MPS_RecipeCreator {
    public MPS_RecipeCreator() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.COMMON_SPEC);
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, Config.SERVER_SPEC);

        // Register the setup method for modloading
//        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register the setupClient method for modloading
//        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setupClient);



        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModObjects.BLOCKS.register(modEventBus);
        ModObjects.ITEMS.register(modEventBus);
        ModObjects.MENU_TYPES.register(modEventBus);


        modEventBus.register(this);
        modEventBus.addListener(this::setupClient);
        modEventBus.addListener(this::setup);
        ConditionsJsonLoader.setFile();
    }

    private void setupClient(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            MenuScreens.register(ModObjects.RECIPE_WORKBENCH_CONTAINER_TYPE.get(), MPSRCGui::new);
        });
    }

    private void setup(final FMLCommonSetupEvent event) {
        NetHandler.registerMPALibPackets();
    }
}