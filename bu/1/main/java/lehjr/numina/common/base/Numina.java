package com.lehjr.numina.common.base;

import com.lehjr.numina.client.event.ClientEventBusSubscriber;
import com.lehjr.numina.client.event.ModelBakeEventHandler;
import com.lehjr.numina.client.sound.SoundDictionary;
import com.lehjr.numina.common.capabilities.NuminaCapabilities;
import com.lehjr.numina.common.capabilities.heat.IHeatStorage;
import com.lehjr.numina.common.capabilities.inventory.modechanging.IModeChangingItem;
import com.lehjr.numina.common.capabilities.inventory.modularitem.IModularItem;
import com.lehjr.numina.common.capabilities.module.powermodule.IPowerModule;
import com.lehjr.numina.common.capabilities.player.keystates.IPlayerKeyStates;
import com.lehjr.numina.common.capabilities.player.keystates.PlayerKeyStateWrapper;
import com.lehjr.numina.common.capabilities.render.IModelSpec;
import com.lehjr.numina.common.capabilities.render.chameleon.IChameleon;
import com.lehjr.numina.common.capabilities.render.color.IColorTag;
import com.lehjr.numina.common.capabilities.render.highlight.IHighlight;
import com.lehjr.numina.common.config.ConfigHelper;
import com.lehjr.numina.common.config.NuminaSettings;
import com.lehjr.numina.common.constants.NuminaConstants;
import com.lehjr.numina.common.entity.NuminaArmorStand;
import com.lehjr.numina.common.event.LogoutEventHandler;
import com.lehjr.numina.common.event.PlayerUpdateHandler;
import com.lehjr.numina.common.network.NuminaPackets;
import com.lehjr.numina.common.recipe.RecipeSerializersRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.ItemCapability;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;


public class Numina {
    public Numina(IEventBus modEventBus) {

     ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, NuminaSettings.SERVER_SPEC); // note config file location for dedicated server is stored in the world config



        // Register the setup method for modloading
        modEventBus.addListener(this::setup);

        // Register the doClientStuff method for modloading
        modEventBus.addListener(ClientEventBusSubscriber::doClientStuff);
//        modEventBus.addListener(ClientEventBusSubscriber::doMoreClientStuff);
        modEventBus.addListener(ModelBakeEventHandler.INSTANCE::onAddAdditional);




        modEventBus.addListener(ClientEventBusSubscriber::modelRegistry);



        modEventBus.addListener(this::registerCapabilities);

        // Register ourselves for server and other game events we are interested in
        NeoForge.EVENT_BUS.register(this);



        // item group
        NuminaObjects.register(modEventBus);



        RecipeSerializersRegistry.RECIPE_SERIALIZERS.register(modEventBus);
        NeoForge.EVENT_BUS.register(new LogoutEventHandler());

        // TODO? reload recipes on config change?
        // [14:33:10] [Thread-1/DEBUG] [ne.mi.fm.co.ConfigFileTypeHandler/CONFIG]: Config file numina-server.toml changed, sending notifies
        // handles loading and reloading event
        modEventBus.addListener((ModConfigEvent event) -> {
            new RuntimeException("Got config " + event.getConfig() + " name " + event.getConfig().getModId() + ":" + event.getConfig().getFileName());

            final ModConfig config = event.getConfig();
            if (config.getSpec() == NuminaSettings.SERVER_SPEC) {
                NuminaSettings.getModuleConfig().setServerConfig(config);
            }
        });
    }



    //    @SubscribeEvent


//    @SubscribeEvent
    public void registerCapabilities(final RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, NuminaObjects.CHARGING_BASE_BLOCK_ENTITY.get(),
                (o, direction) -> o.getItemHandler());




//        event.registerItem(NuminaCapabilities.MODULAR_ITEM, Registration.
//
//                Registration.Void> MODULAR_ITEM = ItemCapability.createVoid(create("modularItem"), IModularItem.class);
//
//        event.registerBlockEntity(NuminaCapabilities.MODE_CHANGING_MODULAR_ITEM
//
//
//                .IModeChangingItem, Void> MODE_CHANGING_MODULAR_ITEM = ItemCapability.createVoid(create("modechangingmodularItem"), IModeChangingItem.class);
//
//        event.registerBlockEntity(NuminaCapabilities.IHeatStorage, Void> HEAT = ItemCapability.createVoid(create("heat"), IHeatStorage.class);
//
//        event.registerBlockEntity(NuminaCapabilities.IColorTag, Void> COLOR = ItemCapability.createVoid(create("color"), IColorTag.class);
//
//        event.registerBlockEntity(NuminaCapabilities.IPowerModule, Void> POWER_MODULE = ItemCapability.createVoid(create("powermodule"), IPowerModule.class);
//
//        event.registerBlockEntity(NuminaCapabilities.IModelSpec, Void> RENDER = ItemCapability.createVoid(create("render"), IModelSpec.class);
//
//        event.registerBlockEntity(NuminaCapabilities.IHighlight, Void> HIGHLIGHT = ItemCapability.createVoid(create("highlight"), IHighlight.class);
//
//        event.registerBlockEntity(NuminaCapabilities.IChameleon, Void> CHAMELEON = ItemCapability.createVoid(create("chameleon"), IChameleon.class);
//
//        event.registerBlockEntity(NuminaCapabilities.IPlayerKeyStates, Void> PLAYER_KEYSTATES








//        event.register(IHeatStorage.class);
//        event.register(IColorTag.class);
//
//        // Modules
//        event.registerItem(IPowerModule.class);
//        event.registerItem(IModelSpec.class);
//        event.registerItem(IHighlight.class);
//        event.registerItem(IChameleon.class);
//
//        // Player
//        event.registerEntity(IPlayerKeyStates.class);
//        event.register(IPlayerHandStorage.class);
    }

    private void setup(final FMLCommonSetupEvent event) {
//        NuminaPackets.registerNuminaPackets();
        NeoForge.EVENT_BUS.register(new PlayerUpdateHandler());
    }

//    @SubscribeEvent
//    public void attachCapability(AttachCapabilitiesEvent<Entity> event) {
//        if (!(event.getObject() instanceof Player) || event.getObject() == null) {
//            return;
//        }
//        event.addCapability(ResourceLocation.fromNamespaceAndPath(NuminaConstants.MOD_ID, "player_keystates1"), new PlayerKeyStateWrapper((Player) event.getObject()));
////        event.addCapability(ResourceLocation.fromNamespaceAndPath(NuminaConstants.MOD_ID, "player_hand_storage"), new PlayerHandStorageWrapper((Player) event.getObject()));
//    }
}
