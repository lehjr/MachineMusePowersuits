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

package com.github.lehjr.numina.basemod;

import com.github.lehjr.numina.client.event.ArmorLayerSetup;
import com.github.lehjr.numina.client.event.FOVUpdateEventHandler;
import com.github.lehjr.numina.client.event.LogoutEventHandler;
import com.github.lehjr.numina.client.event.ToolTipEvent;
import com.github.lehjr.numina.client.gui.ArmorStandGui;
import com.github.lehjr.numina.client.gui.ChargingBaseGui;
import com.github.lehjr.numina.config.ConfigHelper;
import com.github.lehjr.numina.config.NuminaSettings;
import com.github.lehjr.numina.constants.NuminaConstants;
import com.github.lehjr.numina.entity.MPAArmorStandEntity;
import com.github.lehjr.numina.event.EventBusHelper;
import com.github.lehjr.numina.event.PlayerUpdateHandler;
import com.github.lehjr.numina.network.NuminaPackets;
import com.github.lehjr.numina.recipe.NuminaRecipeConditionFactory;
import com.github.lehjr.numina.util.capabilities.heat.HeatCapability;
import com.github.lehjr.numina.util.capabilities.module.powermodule.PowerModuleCapability;
import com.github.lehjr.numina.util.capabilities.player.CapabilityPlayerKeyStates;
import com.github.lehjr.numina.util.capabilities.render.ModelSpecNBTCapability;
import com.github.lehjr.numina.util.client.NuminaSpriteUploader;
import com.github.lehjr.numina.util.client.gui.GuiIcon;
import com.github.lehjr.numina.util.client.render.MuseIconUtils;
import com.github.lehjr.numina.util.client.render.RenderGameOverlayEventHandler;
import forge.NuminaOBJLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.resources.IReloadableResourceManager;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(NuminaConstants.MOD_ID)
public class Numina {
    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();

    public Numina() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, NuminaSettings.CLIENT_SPEC, ConfigHelper.setupConfigFile("numina-client-only.toml", NuminaConstants.MOD_ID).getAbsolutePath());
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, NuminaSettings.SERVER_SPEC); // note config file location for dedicated server is stored in the world config

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();


        // Register the setup method for modloading
        modEventBus.addListener(this::setup);

        // Register the doClientStuff method for modloading
        modEventBus.addListener(this::doClientStuff);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        NuminaObjects.ITEMS.register(modEventBus);
        NuminaObjects.BLOCKS.register(modEventBus);
        NuminaObjects.TILE_TYPES.register(modEventBus);
        NuminaObjects.ENTITY_TYPES.register(modEventBus);
        NuminaObjects.CONTAINER_TYPES.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(new LogoutEventHandler());

        DistExecutor.runWhenOn(Dist.CLIENT, ()->()-> clientStart(modEventBus));

        // handles loading and reloading event
        modEventBus.addListener((ModConfig.ModConfigEvent event) -> {
            new RuntimeException("Got config " + event.getConfig() + " name " + event.getConfig().getModId() + ":" + event.getConfig().getFileName());

            final ModConfig config = event.getConfig();
            if (config.getSpec() == NuminaSettings.SERVER_SPEC) {
                NuminaSettings.getModuleConfig().setServerConfig(config);
            }
        });
    }

    // Ripped from JEI
    private static void clientStart(IEventBus modEventBus) {
        if (Minecraft.getInstance() != null) {
            ModelLoaderRegistry.registerLoader(new ResourceLocation(NuminaConstants.MOD_ID, "obj"), NuminaOBJLoader.INSTANCE); // crashes if called in mod constructor
        }

        EventBusHelper.addListener(modEventBus, ColorHandlerEvent.Block.class, setupEvent -> {
            NuminaSpriteUploader iconUploader = new NuminaSpriteUploader(Minecraft.getInstance().textureManager, "gui");
            GuiIcon icons = new GuiIcon(iconUploader);
            IResourceManager resourceManager = Minecraft.getInstance().getResourceManager();
            if (resourceManager instanceof IReloadableResourceManager) {
                IReloadableResourceManager reloadableResourceManager = (IReloadableResourceManager) resourceManager;
                reloadableResourceManager.addReloadListener(iconUploader);
            }
            EventBusHelper.addLifecycleListener(modEventBus, FMLLoadCompleteEvent.class, loadCompleteEvent ->
                    MuseIconUtils.setIconInstance(icons));
        });
    }

    @Mod.EventBusSubscriber(modid = NuminaConstants.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class MyStaticClientOnlyEventHandler {
        @SubscribeEvent
        public static void loadComplete(FMLLoadCompleteEvent evt) {
            ArmorLayerSetup.loadComplete(evt);
        }
    }

    private void setup(final FMLCommonSetupEvent event) {
        NuminaPackets.registerNuminaPackets();

        HeatCapability.register();

        // Modules
        PowerModuleCapability.register();
        ModelSpecNBTCapability.register();

        // Player
        CapabilityPlayerKeyStates.register();

        MinecraftForge.EVENT_BUS.register(new PlayerUpdateHandler());

        // Recipe condition factory
        CraftingHelper.register(NuminaRecipeConditionFactory.Serializer.INSTANCE);

        DeferredWorkQueue.runLater(() -> {
            GlobalEntityTypeAttributes.put(NuminaObjects.ARMOR_WORKSTATION__ENTITY_TYPE.get(), MPAArmorStandEntity.setCustomAttributes().create());
        });
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(new FOVUpdateEventHandler());
        MinecraftForge.EVENT_BUS.register(new RenderGameOverlayEventHandler());

//        MinecraftForge.EVENT_BUS.register(new LogoutEventHandler());

        MinecraftForge.EVENT_BUS.register(new ToolTipEvent());

        ScreenManager.registerFactory(NuminaObjects.CHARGING_BASE_CONTAINER_TYPE.get(), ChargingBaseGui::new);
        ScreenManager.registerFactory(NuminaObjects.ARMOR_STAND_CONTAINER_TYPE.get(), ArmorStandGui::new);
    }

    @SubscribeEvent
    public void attachCapability(AttachCapabilitiesEvent<Entity> event) {
        if (!(event.getObject() instanceof PlayerEntity)) {
            return;
        }
        event.addCapability(new ResourceLocation(NuminaConstants.MOD_ID, "player_keystates"), new CapabilityPlayerKeyStates());
    }
}
