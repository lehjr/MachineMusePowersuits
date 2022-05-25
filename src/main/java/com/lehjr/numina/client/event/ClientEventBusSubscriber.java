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

package com.lehjr.numina.client.event;


import com.lehjr.numina.common.constants.NuminaConstants;
import com.lehjr.numina.client.render.entity.NuminaArmorStandRenderer;
import com.lehjr.numina.common.base.Numina;
import com.lehjr.numina.common.base.NuminaObjects;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = NuminaConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventBusSubscriber {
    @SubscribeEvent
    public static void onRegisterRenderers(final EntityRenderersEvent.RegisterRenderers event) {
        Numina.LOGGER.info("trying to setup armor stand renderer");

        event.registerEntityRenderer(NuminaObjects.ARMOR_STAND__ENTITY_TYPE.get(), NuminaArmorStandRenderer::new);
    }


    @SubscribeEvent
    public static void onTextureStitch(TextureStitchEvent.Pre event) {
        if (!event.getAtlas().location().equals(TextureAtlas.LOCATION_BLOCKS)) {

            System.out.println(event.getAtlas().location());

//            return;
        }
//        event.addSprite(REDHALO);
    }

//    @SubscribeEvent
//    public static void onRenderTickEvent(TickEvent.RenderTickEvent event) {
//        ClientTickHandler.INSTANCE.onRenderTickEvent(event);
//    }


//    @SubscribeEvent
//    public static void onClientSetup(FMLClientSetupEvent event) {
//        RenderingRegistry.registerEntityRenderingHandler(NuminaObjects.ARMOR_STAND__ENTITY_TYPE.get(), NuminaArmorStandRenderer::new);
//    }
//
//    @SubscribeEvent
//    public static void onTextureStitchPre(TextureStitchEvent.Pre event) {
//        System.out.println("fixme");
//
////        AtlasTexture map = event.getMap();
////        // only adds if it doesn't already exist
////        if (map.location() == AtlasTexture.LOCATION_BLOCKS) {
////            event.addSprite(NuminaConstants.TEXTURE_WHITE_SHORT);
////        }
//    }
//
    @SubscribeEvent
    public static void onModelBake(ModelBakeEvent event) {

    }
}