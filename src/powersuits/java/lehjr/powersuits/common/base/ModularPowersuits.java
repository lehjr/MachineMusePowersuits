package lehjr.powersuits.common.base;

import lehjr.numina.common.config.ConfigHelper;
import lehjr.numina.common.event.HarvestEventHandler;
import lehjr.powersuits.client.config.MPSClientConfig;
import lehjr.powersuits.common.config.MPSCommonConfig;
import lehjr.powersuits.common.constants.MPSConstants;
import lehjr.powersuits.common.network.MPSPackets;
import lehjr.powersuits.common.registration.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(MPSConstants.MOD_ID)
public class ModularPowersuits {

    public ModularPowersuits(IEventBus modEventBus, ModContainer modContainer) {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);
        MPSItems.MPS_ITEMS.register(modEventBus);
        MPSMenuTypes.MENU_TYPES.register(modEventBus);
        MPSItems.MPS_CREATIVE_MODE_TAB.register(modEventBus);
        MPSBlocks.MPS_BLOCKS.register(modEventBus);
        MPSEntities.MPS_ENTITIES.register(modEventBus);
        MPSBlocks.MPS_BLOCKENTITY_TYPES.register(modEventBus);
        MPSArmorMaterial.ARMOR_MATERIALS.register(modEventBus);

        NeoForge.EVENT_BUS.addListener(HarvestEventHandler::handleHarvestCheck);
        NeoForge.EVENT_BUS.addListener(HarvestEventHandler::handleBreakSpeed);


//        // Register the Deferred Register to the mod event bus so blocks get registered
//        BLOCKS.register(modEventBus);
//        // Register the Deferred Register to the mod event bus so items get registered
//        ITEMS.register(modEventBus);
//        // Register the Deferred Register to the mod event bus so tabs get registered
//        CREATIVE_MODE_TABS.register(modEventBus);

        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (ExampleMod) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);

        modEventBus.addListener(MPSPackets::register);


        modEventBus.addListener(MPSCapabilities::registerCapabilities);




        // Register the item to a creative tab
//        modEventBus.addListener(this::addCreative);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.CLIENT, MPSClientConfig.CLIENT_SPEC, ConfigHelper.setupConfigFile("powersuits-client-only.toml", MPSConstants.MOD_ID).getAbsolutePath());
        modContainer.registerConfig(ModConfig.Type.COMMON, MPSCommonConfig.COMMON_SPEC, ConfigHelper.setupConfigFile("powersuits-common.toml", MPSConstants.MOD_ID).getAbsolutePath());

    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        // Some common setup code
//        LOGGER.info("HELLO FROM COMMON SETUP");
    }

//    // Add the example block item to the building blocks tab
//    private void addCreative(BuildCreativeModeTabContentsEvent event) {
//        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS)
//            event.accept(EXAMPLE_BLOCK_ITEM);
//    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
//        LOGGER.info("HELLO from server starting");
    }

//    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
//    @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
//    public static class ClientModEvents {
//        @SubscribeEvent
//        public static void onClientSetup(FMLClientSetupEvent event) {
//            // Some client setup code
//            LOGGER.info("HELLO FROM CLIENT SETUP");
//            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
//        }
//    }
}
