package com.lehjr.numina.common.base;

import com.lehjr.numina.client.config.NuminaClientConfig;
import com.lehjr.numina.client.event.NuminaClientEventBusSubscriber;
import com.lehjr.numina.client.sound.SoundDictionary;
import com.lehjr.numina.common.config.ConfigHelper;
import com.lehjr.numina.common.config.NuminaCommonConfig;
import com.lehjr.numina.common.constants.NuminaConstants;
import com.lehjr.numina.common.entity.NuminaArmorStand;
import com.lehjr.numina.common.event.HarvestEventHandler;
import com.lehjr.numina.common.event.PlayerUpdateHandler;
import com.lehjr.numina.common.network.NuminaPackets;
import com.lehjr.numina.common.recipe.RecipeSerializersRegistry;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
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
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);
        NuminaObjects.NUMINA_BLOCKS.register(modEventBus);
        NuminaObjects.BLOCKENTITY_TYPES.register(modEventBus);
        NuminaObjects.NUMINA_ITEMS.register(modEventBus);
        NuminaObjects.ENTITY_TYPES.register(modEventBus);
        NuminaObjects.MENU_TYPES.register(modEventBus);
        NuminaObjects.NUMINA_CREATIVE_MODE_TAB.register(modEventBus);
        NuminaObjects.DATA_COMPONENT_TYPES.register(modEventBus);
        NuminaObjects.ATTACHMENT_TYPES.register(modEventBus);
        NuminaObjects.INGREDIENT_TYPES.register(modEventBus);

        NeoForge.EVENT_BUS.addListener(HarvestEventHandler::handleHarvestCheck);
        NeoForge.EVENT_BUS.addListener(HarvestEventHandler::handleBreakSpeed);
        NeoForge.EVENT_BUS.addListener(HarvestEventHandler::onBlockBreak);

        modEventBus.addListener(this::addEntityAttributes);
        modEventBus.addListener(NuminaPackets::register);

        NeoForge.EVENT_BUS.addListener(PlayerUpdateHandler::onPlayerUpdate);
        RecipeSerializersRegistry.RECIPE_SERIALIZERS.register(modEventBus);

        SoundDictionary.NUMINA_SOUND_EVENTS.register(modEventBus);

        modEventBus.addListener(NuminaClientEventBusSubscriber::onRegisterReloadListenerEvent);
        modEventBus.addListener(NuminaClientEventBusSubscriber::modelRegistry);


        modEventBus.addListener(NuminaObjects::registerCapabilities);

        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (ExampleMod) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);



        modContainer.registerConfig(ModConfig.Type.CLIENT, NuminaClientConfig.CLIENT_SPEC, ConfigHelper.setupConfigFile("numina-client-only.toml", NuminaConstants.MOD_ID).getAbsolutePath());
        modContainer.registerConfig(ModConfig.Type.COMMON, NuminaCommonConfig.COMMON_SPEC, ConfigHelper.setupConfigFile("numina-common.toml", NuminaConstants.MOD_ID).getAbsolutePath());

    }

    public void addEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(NuminaObjects.ARMOR_STAND__ENTITY_TYPE.get(), NuminaArmorStand.createAttributes().build());
    }


    private void commonSetup(final FMLCommonSetupEvent event) {

    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @EventBusSubscriber(modid = NuminaConstants.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            // Some client setup code
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
        }
    }
}