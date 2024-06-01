package lehjr.numina.common.base;

import lehjr.numina.client.config.ClientConfig;
import lehjr.numina.client.event.NuminaClientEventBusSubscriber;
import lehjr.numina.client.sound.SoundDictionary;
import lehjr.numina.common.config.CommonConfig;
import lehjr.numina.common.constants.NuminaConstants;
import lehjr.numina.common.entity.NuminaArmorStand;
import lehjr.numina.common.utils.ConfigHelper;
import net.minecraft.client.Minecraft;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(NuminaConstants.MOD_ID)
public class Numina {









    // Directly reference a slf4j logger
    private static final Logger LOGGER = NuminaLogger.getLogger();


    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public Numina(IEventBus modEventBus, ModContainer modContainer) {
        NuminaObjects.NUMINA_BLOCKS.register(modEventBus);
        NuminaObjects.NUMINA_ITEMS.register(modEventBus);
        NuminaObjects.BLOCKENTITY_TYPES.register(modEventBus);
        NuminaObjects.ENTITY_TYPES.register(modEventBus);
        NuminaObjects.MENU_TYPES.register(modEventBus);
        NuminaObjects.NUMINA_CREATIVE_MODE_TAB.register(modEventBus);
        NuminaObjects.DATA_COMPONENT_TYPES.register(modEventBus);


        modEventBus.addListener(this::addEntityAttributes);

        SoundDictionary.NUMINA_SOUND_EVENTS.register(modEventBus);

        modEventBus.addListener(NuminaClientEventBusSubscriber::onRegisterReloadListenerEvent);


        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);


        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (ExampleMod) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
//        modEventBus.addListener(this::addCreative);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.CLIENT, ClientConfig.CLIENT_SPEC, ConfigHelper.setupConfigFile("numina-client-only.toml", NuminaConstants.MOD_ID).getAbsolutePath());
        modContainer.registerConfig(ModConfig.Type.COMMON, CommonConfig.COMMON_SPEC, ConfigHelper.setupConfigFile("numina-common.toml", NuminaConstants.MOD_ID).getAbsolutePath());

        modEventBus.addListener(NuminaObjects::registerCapabilities);

    }

    public void addEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(NuminaObjects.ARMOR_STAND__ENTITY_TYPE.get(), NuminaArmorStand.createAttributes().build());
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");

//        if (Config.logDirtBlock)
//            LOGGER.info("DIRT BLOCK >> {}", BuiltInRegistries.BLOCK.getKey(Blocks.DIRT));
//
//        LOGGER.info(Config.magicNumberIntroduction + Config.magicNumber);
//
//        Config.items.forEach((item) -> LOGGER.info("ITEM >> {}", item.toString()));
    }


    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent

    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            // Some client setup code
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
        }
    }
}
