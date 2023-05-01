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


import lehjr.numina.client.gui.overlay.ModeChangingIconOverlay;
import lehjr.numina.client.render.entity.NuminaArmorStandRenderer;
import lehjr.numina.client.render.item.NuminaArmorLayer;
import lehjr.numina.common.base.NuminaObjects;
import lehjr.numina.common.constants.NuminaConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Mod.EventBusSubscriber(modid=NuminaConstants.MOD_ID, bus=Mod.EventBusSubscriber.Bus.MOD, value=Dist.CLIENT)
public class ClientEventBusSubscriber {
    @SubscribeEvent
    public static void onRegisterRenderers(final EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(NuminaObjects.ARMOR_STAND__ENTITY_TYPE.get(), NuminaArmorStandRenderer::new);
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
            renderer.addLayer(new NuminaArmorLayer<>(renderer, bipedArmorLayer.innerModel, bipedArmorLayer.outerModel));
        }
    }

    @SubscribeEvent
    public void onAddAdditional(ModelEvent.RegisterAdditional e) {
        System.out.println("add additonal event");
        modelList.stream().forEach(resourceLocation -> e.register(resourceLocation));
    }

    static List<ResourceLocation> modelList = new ArrayList<>();

    public static void addModelLocation(ResourceLocation modelLocation) {
        modelList.add(modelLocation);
    }
}