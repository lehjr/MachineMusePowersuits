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

package lehjr.numina.client.event;


import forge.NuminaObjLoader;
import lehjr.numina.client.gui.NuminaIcons;
import lehjr.numina.client.gui.overlay.ModeChangingIconOverlay;
import lehjr.numina.client.render.IconUtils;
import lehjr.numina.client.render.NuminaSpriteUploader;
import lehjr.numina.client.render.entity.NuminaArmorStandRenderer;
import lehjr.numina.client.render.item.NuminaArmorLayer;
import lehjr.numina.client.screen.ArmorStandScreen;
import lehjr.numina.client.screen.ChargingBaseScreen;
import lehjr.numina.common.base.NuminaLogger;
import lehjr.numina.common.base.NuminaObjects;
import lehjr.numina.common.constants.NuminaConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import javax.annotation.Nullable;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Mod.EventBusSubscriber(modid=NuminaConstants.MOD_ID, bus=Mod.EventBusSubscriber.Bus.MOD, value=Dist.CLIENT)
public class ClientEventBusSubscriber {
    @SubscribeEvent
    public static void onRegisterRenderers(final EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(NuminaObjects.ARMOR_STAND__ENTITY_TYPE.get(), NuminaArmorStandRenderer::new);
    }

    @SubscribeEvent
    public static void onTextureStitchPre(TextureStitchEvent event) {
        TextureAtlas map = event.getAtlas();
        // only adds if it doesn't already exist
        if (map.location() == TextureAtlas.LOCATION_BLOCKS) {
            System.out.println("fixme, sprite upload not fixed yet!!!!");
//            event.addSprite(NuminaConstants.TEXTURE_WHITE_SHORT);


        }
    }

    @SubscribeEvent
    public static void registerKeyBinding(RegisterKeyMappingsEvent event) {
        event.register(FOVUpdateEventHandler.fovToggleKey.get());
    }

    @SubscribeEvent
    public static void registerOverlay(RegisterGuiOverlaysEvent event) {
        event.registerAboveAll("numina_mode_changing_icon", ModeChangingIconOverlay.MODE_CHANGING_ICON_OVERLAY);
    }

    @SubscribeEvent
    public static void addLayers(EntityRenderersEvent.AddLayers event) {
        //Add our own custom armor layer to the various player renderers
        for (String skinName : event.getSkins()) {
            addCustomLayers(EntityType.PLAYER, (PlayerRenderer) event.getSkin(skinName));
        }
        //Add our own custom armor layer to everything that has an armor layer
        //Note: This includes any modded mobs that have vanilla's BipedArmorLayer added to them
        for (Map.Entry<EntityType<?>, EntityRenderer<?>> entry : Minecraft.getInstance().getEntityRenderDispatcher().renderers.entrySet()) {
            EntityRenderer<?> renderer = entry.getValue();
            if (renderer instanceof LivingEntityRenderer) {
                EntityType<?> entityType = entry.getKey();
                //noinspection unchecked,rawtypes
                addCustomLayers(entityType, event.getRenderer((EntityType) entityType));
            }
        }
    }

    private static <T extends LivingEntity, M extends HumanoidModel<T>> void addCustomLayers(EntityType<?> type, @Nullable LivingEntityRenderer<T, M> renderer) {
        if (renderer == null) {
            return;
        }
        HumanoidArmorLayer<T, M, ?> bipedArmorLayer = null;
        boolean hasElytra = false;
        for (RenderLayer<T, M> layerRenderer : renderer.layers) {
            //Validate against the layer render being null, as it seems like some mods do stupid things and add in null layers
            if (layerRenderer != null) {
                //Only allow an exact class match, so we don't add to modded entities that only have a modded extended armor or elytra layer
                Class<?> layerClass = layerRenderer.getClass();
                if (layerClass == HumanoidArmorLayer.class) {
                    bipedArmorLayer = (HumanoidArmorLayer<T, M, ?>) layerRenderer;
                    if (hasElytra) {
                        break;
                    }
                } else if (layerClass == ElytraLayer.class) {
                    hasElytra = true;
                    if (bipedArmorLayer != null) {
                        break;
                    }
                }
            }
        }
        if (bipedArmorLayer != null) {
            renderer.addLayer(new NuminaArmorLayer<>(renderer, bipedArmorLayer.innerModel, bipedArmorLayer.outerModel, Minecraft.getInstance().getModelManager()));
        }
    }

    @SubscribeEvent
    public void onAddAdditional(ModelEvent.RegisterAdditional e) {
        NuminaLogger.logDebug("adding additional models");
        modelList.stream().forEach(resourceLocation -> e.register(resourceLocation));
    }

    static List<ResourceLocation> modelList = new ArrayList<>();

    public static void addModelLocation(ResourceLocation modelLocation) {
        modelList.add(modelLocation);
    }

    public static void onRegisterReloadListenerEvent(RegisterClientReloadListenersEvent event) {
        NuminaIcons icon = IconUtils.INSTANCE.getIcon();
        System.out.println("reload listener " + Objects.isNull(icon.getSpriteUploader()));
        event.registerReloadListener(icon.getSpriteUploader());
    }

    public static void doClientStuff(final FMLClientSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(new FOVUpdateEventHandler());
        MinecraftForge.EVENT_BUS.register(new ToolTipEvent());
        event.enqueueWork(() -> {
            MenuScreens.register(NuminaObjects.CHARGING_BASE_CONTAINER_TYPE.get(), ChargingBaseScreen::new);
            MenuScreens.register(NuminaObjects.ARMOR_STAND_CONTAINER_TYPE.get(), ArmorStandScreen::new);
            //        ScreenManager.func_216911_a(NuminaObjects.SCANNER_CONTAINER.get(), MPSGuiScanner::new);
        });
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.register(ClientEventBusSubscriber.class);
        modEventBus.addListener(ModelBakeEventHandler.INSTANCE::onAddAdditional);

//        MinecraftForge.EVENT_BUS.addListener((InputEvent.Key e) -> {
////            ModelTransformCalibration.CALIBRATION.transformCalibration(e);
//        });
    }

    public static void modelRegistry(ModelEvent.RegisterGeometryLoaders event) {
        event.register( "obj", NuminaObjLoader.INSTANCE);
    }
}