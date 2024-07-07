/*
 * Copyright (c) 2021. MachineMuse, Lehjr
 *  All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *      Redistributions of source code must retain the above copyright notice, this
 *      list of conditions and the following disclaimer.
 *
 *     Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 *  ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 *  WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 *  DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 *  ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 *  (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 *  LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 *  ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *  (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *  SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package lehjr.numina.common.base;

import lehjr.numina.client.event.ClientEventBusSubscriber;
import lehjr.numina.client.event.ModelBakeEventHandler;
import lehjr.numina.client.sound.SoundDictionary;
import lehjr.numina.common.capability.NuminaCapabilities;
import lehjr.numina.common.capability.heat.IHeatStorage;
import lehjr.numina.common.capability.inventory.modechanging.IModeChangingItem;
import lehjr.numina.common.capability.inventory.modularitem.IModularItem;
import lehjr.numina.common.capability.module.powermodule.IPowerModule;
import lehjr.numina.common.capability.player.keystates.IPlayerKeyStates;
import lehjr.numina.common.capability.player.keystates.PlayerKeyStateWrapper;
import lehjr.numina.common.capability.render.IModelSpec;
import lehjr.numina.common.capability.render.chameleon.IChameleon;
import lehjr.numina.common.capability.render.color.IColorTag;
import lehjr.numina.common.capability.render.highlight.IHighlight;
import lehjr.numina.common.config.ConfigHelper;
import lehjr.numina.common.config.NuminaSettings;
import lehjr.numina.common.constants.NuminaConstants;
import lehjr.numina.common.entity.NuminaArmorStand;
import lehjr.numina.common.event.LogoutEventHandler;
import lehjr.numina.common.event.PlayerUpdateHandler;
import lehjr.numina.common.network.NuminaPackets;
import lehjr.numina.common.recipe.RecipeSerializersRegistry;
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

@Mod(NuminaConstants.MOD_ID)
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
//        event.addCapability(new ResourceLocation(NuminaConstants.MOD_ID, "player_keystates1"), new PlayerKeyStateWrapper((Player) event.getObject()));
////        event.addCapability(new ResourceLocation(NuminaConstants.MOD_ID, "player_hand_storage"), new PlayerHandStorageWrapper((Player) event.getObject()));
//    }
}
