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

package lehjr.numina.basemod;

import forge.NuminaOBJLoader;
import lehjr.numina.client.gui.ArmorStandGui;
import lehjr.numina.client.gui.ChargingBaseGui;
import lehjr.numina.config.ConfigHelper;
import lehjr.numina.config.NuminaSettings;
import lehjr.numina.constants.NuminaConstants;
import lehjr.numina.entity.NuminaArmorStandEntity;
import lehjr.numina.event.EventBusHelper;
import lehjr.numina.event.LogoutEventHandler;
import lehjr.numina.event.PlayerUpdateHandler;
import lehjr.numina.integration.scannable.MPSGuiScanner;
import lehjr.numina.network.NuminaPackets;
import lehjr.numina.recipe.RecipeSerializersRegistry;
import lehjr.numina.util.capabilities.heat.CapabilityHeat;
import lehjr.numina.util.capabilities.module.powermodule.PowerModuleCapability;
import lehjr.numina.util.capabilities.player.CapabilityPlayerKeyStates;
import lehjr.numina.util.capabilities.render.ModelSpecNBTCapability;
import lehjr.numina.util.capabilities.render.chameleon.ChameleonCapability;
import lehjr.numina.util.capabilities.render.colour.ColourCapability;
import lehjr.numina.util.capabilities.render.highlight.HighLightCapability;
import lehjr.numina.util.client.NuminaSpriteUploader;
import lehjr.numina.util.client.event.ArmorLayerSetup;
import lehjr.numina.util.client.event.FOVUpdateEventHandler;
import lehjr.numina.util.client.event.ToolTipEvent;
import lehjr.numina.util.client.gui.GuiIcon;
import lehjr.numina.util.client.render.MuseIconUtils;
import lehjr.numina.util.client.render.RenderGameOverlayEventHandler;
import li.cil.scannable.client.gui.GuiScanner;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.entity.player.Player;
import net.minecraft.resources.IReloadableResourceManager;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.MinecraftForge;
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

@Mod(NuminaConstants.MOD_ID)
public class Numina {
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
        RecipeSerializersRegistry.RECIPE_SERIALIZERS.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(new LogoutEventHandler());

        DistExecutor.runWhenOn(Dist.CLIENT, ()-> ()-> clientStart(modEventBus));

        // TODO? reload recipes on config change?
        // [14:33:10] [Thread-1/DEBUG] [ne.mi.fm.co.ConfigFileTypeHandler/CONFIG]: Config file numina-server.toml changed, sending notifies
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

        EventBusHelper.addListener(Numina.class, modEventBus, ColorHandlerEvent.Block.class, setupEvent -> {
            NuminaSpriteUploader iconUploader = new NuminaSpriteUploader();
            GuiIcon icons = new GuiIcon(iconUploader);
            IResourceManager resourceManager = Minecraft.getInstance().getResourceManager();
            if (resourceManager instanceof IReloadableResourceManager) {
                IReloadableResourceManager reloadableResourceManager = (IReloadableResourceManager) resourceManager;
                reloadableResourceManager.registerReloadListener(iconUploader);
            }
            EventBusHelper.addLifecycleListener(Numina.class, modEventBus, FMLLoadCompleteEvent.class, loadCompleteEvent ->
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

        CapabilityHeat.register();

        ColourCapability.register();

        // Modules
        PowerModuleCapability.register();
        ModelSpecNBTCapability.register();
        HighLightCapability.register();
        ChameleonCapability.register();

        // Player
        CapabilityPlayerKeyStates.register();

        MinecraftForge.EVENT_BUS.register(new PlayerUpdateHandler());

        DeferredWorkQueue.runLater(() -> {
            GlobalEntityTypeAttributes.put(NuminaObjects.ARMOR_WORKSTATION__ENTITY_TYPE.get(), NuminaArmorStandEntity.setCustomAttributes().build());
        });
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(new FOVUpdateEventHandler());
        MinecraftForge.EVENT_BUS.register(new RenderGameOverlayEventHandler());

//        MinecraftForge.EVENT_BUS.register(new LogoutEventHandler());

        MinecraftForge.EVENT_BUS.register(new ToolTipEvent());

        ScreenManager.register(NuminaObjects.CHARGING_BASE_CONTAINER_TYPE.get(), ChargingBaseGui::new);
        ScreenManager.register(NuminaObjects.ARMOR_STAND_CONTAINER_TYPE.get(), ArmorStandGui::new);

        ScreenManager.register(NuminaObjects.SCANNER_CONTAINER.get(), MPSGuiScanner::new);
    }

    @SubscribeEvent
    public void attachCapability(AttachCapabilitiesEvent<Entity> event) {
        if (!(event.getObject() instanceof Player)) {
            return;
        }
        event.addCapability(new ResourceLocation(NuminaConstants.MOD_ID, "player_keystates"), new CapabilityPlayerKeyStates());
    }
}
