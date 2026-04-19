package lehjr.powersuits.common.base;

import lehjr.powersuits.client.sound.MPSSoundDictionary;
import lehjr.powersuits.common.constants.MPSConstants;
import lehjr.powersuits.common.event.MovementManager;
import lehjr.powersuits.common.event.PlayerEventHandler;
import lehjr.powersuits.common.network.MPSPackets;
import lehjr.powersuits.common.registration.MPSArmorMaterial;
import lehjr.powersuits.common.registration.MPSBlocks;
import lehjr.powersuits.common.registration.MPSCapabilities;
import lehjr.powersuits.common.registration.MPSConfigurations;
import lehjr.powersuits.common.registration.MPSEntities;
import lehjr.powersuits.common.registration.MPSItems;
import lehjr.powersuits.common.registration.MPSMenuTypes;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
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

        NeoForge.EVENT_BUS.register(MovementManager.INSTANCE);

        MPSSoundDictionary.MPS_SOUND_EVENTS.register(modEventBus);

        NeoForge.EVENT_BUS.register(PlayerEventHandler.class);
//        NeoForge.EVENT_BUS.register(ItemEvent::onAttributeEvent);

        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (ExampleMod) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);

        modEventBus.addListener(MPSPackets::register);


        modEventBus.addListener(MPSCapabilities::registerCapabilities);
        // not valid for this bus
//        modEventBus.addListener(ClientEventBusSubscriber::renderBlockHighlight); // not valid for this bus, biut
//        NeoForge.EVENT_BUS.addListener(ClientEventBusSubscriber::renderBlockHighlight);



        // Register the item to a creative tab
//        modEventBus.addListener(this::addCreative);

        // Register all the configuration files
        MPSConfigurations.setup(modContainer);


    }

//    @SubscribeEvent
//    public void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
//        NuminaLogger.logDebug("firing player login event");
//        PlayerEventHandler.onPlayerLogin(event);
//    }



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
//    @EventBusSubscriber(modid = MPSConstants.MOD_ID, value = Dist.CLIENT)
//    public static class ClientModEvents {
//        @SubscribeEvent
//        public static void onClientSetup(FMLClientSetupEvent event) {
////            // Some client setup code
////            LOGGER.info("HELLO FROM CLIENT SETUP");
////            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
//
//
//
//        }
//    }
}
