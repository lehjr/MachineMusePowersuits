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
import lehjr.numina.client.sound.SoundDictionary;
import lehjr.numina.common.capabilities.heat.IHeatStorage;
import lehjr.numina.common.capabilities.module.powermodule.IPowerModule;
import lehjr.numina.common.capabilities.player.keystates.IPlayerKeyStates;
import lehjr.numina.common.capabilities.player.keystates.PlayerKeyStateWrapper;
import lehjr.numina.common.capabilities.render.IModelSpec;
import lehjr.numina.common.capabilities.render.chameleon.IChameleon;
import lehjr.numina.common.capabilities.render.color.IColorTag;
import lehjr.numina.common.capabilities.render.highlight.IHighlight;
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
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(NuminaConstants.MOD_ID)
public class Numina {
    public Numina() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, NuminaSettings.CLIENT_SPEC, ConfigHelper.setupConfigFile("numina-client-only.toml", NuminaConstants.MOD_ID).getAbsolutePath());
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, NuminaSettings.SERVER_SPEC); // note config file location for dedicated server is stored in the world config

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the setup method for modloading
        modEventBus.addListener(this::setup);

        modEventBus.addListener(this::addEntityAttributes);

        modEventBus.addListener(this::registerCapabilities);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        SoundDictionary.NUMINA_SOUND_EVENTS.register(modEventBus);
        NuminaObjects.NUMINA_ITEMS.register(modEventBus);

        // item group
        NuminaObjects.register(modEventBus);

        NuminaObjects.NUMINA_BLOCKS.register(modEventBus);
        NuminaObjects.TILE_TYPES.register(modEventBus);
        NuminaObjects.ENTITY_TYPES.register(modEventBus);
        NuminaObjects.MENU_TYPES.register(modEventBus);
        RecipeSerializersRegistry.RECIPE_SERIALIZERS.register(modEventBus);
        MinecraftForge.EVENT_BUS.register(new LogoutEventHandler());

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
    public void addEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(NuminaObjects.ARMOR_STAND__ENTITY_TYPE.get(), NuminaArmorStand.createAttributes().build());
    }

//    @SubscribeEvent
    public void registerCapabilities(final RegisterCapabilitiesEvent event) {
        event.register(IHeatStorage.class);
        event.register(IColorTag.class);

        // Modules
        event.register(IPowerModule.class);
        event.register(IModelSpec.class);
        event.register(IHighlight.class);
        event.register(IChameleon.class);

        // Player
        event.register(IPlayerKeyStates.class);
//        event.register(IPlayerHandStorage.class);
    }

    private void setup(final FMLCommonSetupEvent event) {
        NuminaPackets.registerNuminaPackets();
        MinecraftForge.EVENT_BUS.register(new PlayerUpdateHandler());
    }

    @SubscribeEvent
    public void attachCapability(AttachCapabilitiesEvent<Entity> event) {
        if (!(event.getObject() instanceof Player) || event.getObject() == null) {
            return;
        }
        event.addCapability(new ResourceLocation(NuminaConstants.MOD_ID, "player_keystates1"), new PlayerKeyStateWrapper((Player) event.getObject()));
//        event.addCapability(new ResourceLocation(NuminaConstants.MOD_ID, "player_hand_storage"), new PlayerHandStorageWrapper((Player) event.getObject()));
    }
}
