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
import com.lehjr.numina.common.registration.NuminaBlockEntities;
import com.lehjr.numina.common.registration.NuminaBlocks;
import com.lehjr.numina.common.registration.NuminaCapabilities;
import com.lehjr.numina.common.registration.NuminaCodecs;
import com.lehjr.numina.common.registration.NuminaEntities;
import com.lehjr.numina.common.registration.NuminaIngredientTypes;
import com.lehjr.numina.common.registration.NuminaItems;
import com.lehjr.numina.common.registration.NuminaMenus;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(NuminaConstants.MOD_ID)
public class Numina {

    public Numina(IEventBus modEventBus, ModContainer modContainer) {
        NuminaBlocks.NUMINA_BLOCKS.register(modEventBus);
        NuminaBlockEntities.BLOCKENTITY_TYPES.register(modEventBus);
        NuminaItems.NUMINA_ITEMS.register(modEventBus);
        NuminaEntities.ENTITY_TYPES.register(modEventBus);
        NuminaMenus.MENU_TYPES.register(modEventBus);
        NuminaItems.NUMINA_CREATIVE_MODE_TAB.register(modEventBus);
        NuminaCodecs.DATA_COMPONENT_TYPES.register(modEventBus);
        NuminaCodecs.ATTACHMENT_TYPES.register(modEventBus);
        NuminaIngredientTypes.INGREDIENT_TYPES.register(modEventBus);

        NeoForge.EVENT_BUS.register(HarvestEventHandler.class);
//        NeoForge.EVENT_BUS.addListener(HarvestEventHandler::handleHarvestCheck);
//        NeoForge.EVENT_BUS.addListener(HarvestEventHandler::handleBreakSpeed);
//        NeoForge.EVENT_BUS.addListener(HarvestEventHandler::onBlockBreak);

        modEventBus.addListener(this::addEntityAttributes);
        modEventBus.addListener(NuminaPackets::register);

        NeoForge.EVENT_BUS.addListener(PlayerUpdateHandler::onPlayerUpdate);
        RecipeSerializersRegistry.RECIPE_SERIALIZERS.register(modEventBus);

        SoundDictionary.NUMINA_SOUND_EVENTS.register(modEventBus);

        modEventBus.addListener(NuminaClientEventBusSubscriber::onRegisterReloadListenerEvent);
        modEventBus.addListener(NuminaClientEventBusSubscriber::modelRegistry);

        modEventBus.addListener(NuminaCapabilities::registerCapabilities);

//        // Register ourselves for server and other game events we are interested in.
//        // Note that this is necessary if and only if we want *this* class (ExampleMod) to respond directly to events.
//        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
//        NeoForge.EVENT_BUS.register(this);

        modContainer.registerConfig(ModConfig.Type.CLIENT, NuminaClientConfig.CLIENT_SPEC, ConfigHelper.setupConfigFile("numina-client-only.toml", NuminaConstants.MOD_ID).getAbsolutePath());
        modContainer.registerConfig(ModConfig.Type.COMMON, NuminaCommonConfig.COMMON_SPEC, ConfigHelper.setupConfigFile("numina-common.toml", NuminaConstants.MOD_ID).getAbsolutePath());
    }

    public void addEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(NuminaEntities.ARMOR_STAND__ENTITY_TYPE.get(), NuminaArmorStand.createAttributes().build());
    }
}
