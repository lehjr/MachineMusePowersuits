//import com.mojang.blaze3d.systems.RenderSystem;
//import net.neoforged.api.distmarker.Dist;
//import net.neoforged.api.distmarker.OnlyIn;
//import org.lwjgl.opengl.GL11;
//
/////*
//// * Copyright (c) 2021. MachineMuse, Lehjr
//// *  All rights reserved.
//// *
//// * Redistribution and use in source and binary forms, with or without
//// * modification, are permitted provided that the following conditions are met:
//// *
//// *      Redistributions of source code must retain the above copyright notice, this
//// *      list of conditions and the following disclaimer.
//// *
//// *     Redistributions in binary form must reproduce the above copyright notice,
//// *     this list of conditions and the following disclaimer in the documentation
//// *     and/or other materials provided with the distribution.
//// *
//// *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
//// *  ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
//// *  WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
//// *  DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
//// *  ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
//// *  (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
//// *  LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
//// *  ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
//// *  (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
//// *  SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
//// */
////
////package lehjr.numina.client.render;
////
////import com.mojang.blaze3d.platform.GlStateManager;
////import com.mojang.blaze3d.systems.RenderSystem;
////import lehjr.numina.common.constants.NuminaConstants;
////import net.minecraft.client.Minecraft;
////import net.minecraft.client.renderer.RenderType;
////import com.mojang.blaze3d.vertex.DefaultVertexFormat;
////import net.neoforged.api.distmarker.Dist;
////import net.neoforged.api.distmarker.OnlyIn;
////import org.lwjgl.opengl.GL11;
////import org.lwjgl.opengl.GL13C;
////
/////**
//// * Author: MachineMuse (Claire Semple)
//// * Created: 2:41 PM, 9/6/13
//// * <p>
//// * Ported to Java by lehjr on 10/23/16.
//// */
//@OnlyIn(Dist.CLIENT)
//public final class NuminaRenderState {
////    public static final RenderState.TransparencyState TRANSLUCENT_TRANSPARENCY = new RenderState.TransparencyState("translucent_transparency_numina", () -> {
////        RenderSystem.enableBlend();
////        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
////    }, () -> {
////        RenderSystem.disableBlend();
////        RenderSystem.defaultBlendFunc();
////    });
////
////    public static final RenderState.DiffuseLightingState DIFFUSE_LIGHTING_ENABLED = new RenderState.DiffuseLightingState(true);
////    public static final RenderState.AlphaState DEFAULT_ALPHA = new RenderState.AlphaState(0.003921569F);
////    public static final RenderState.LightmapState LIGHTMAP_ENABLED = new RenderState.LightmapState(true);
////    public static final RenderState.OverlayState OVERLAY_ENABLED = new RenderState.OverlayState(true);
////
////    public static RenderType LIGHTNING_TEST() {
////        RenderType.State state = RenderType.State.func_228694_a_()
////                .func_228724_a_(new RenderState.TextureState(NuminaConstants.LOCATION_NUMINA_GUI_TEXTURE_ATLAS, false, false))
////                .func_228726_a_(TRANSLUCENT_TRANSPARENCY)
////                .func_228716_a_(DIFFUSE_LIGHTING_ENABLED)
////                .func_228713_a_(DEFAULT_ALPHA)
////                .func_228719_a_(LIGHTMAP_ENABLED)
////                .func_228722_a_(OVERLAY_ENABLED)
////                .func_228728_a_(true);
////        return RenderType.func_228633_a_(
////                "lighting_test",
////                DefaultVertexFormat.field_227852_q_,
////                GL11.GL_QUADS, 256, true, true, state);
////    }
////
////    /**
////     * 2D rendering mode on/off
////     */
////    public static void on2D() {
////        // GL11.glPushAttrib(GL11.GL_ENABLE_BIT);
////        // RenderSystem.pushLightingAttributes(); // might work?
////        glPushAttrib(GL11.GL_ENABLE_BIT);
////
////        // GL11.glDisable(GL11.GL_DEPTH_TEST);
////        RenderSystem.disableDepthTest();
////
////        // GL11.glDisable(GL11.GL_CULL_FACE);
////        RenderSystem.disableCull();
////
////        // GL11.glDisable(GL11.GL_LIGHTING);
////        disableLighting();
////    }
////
////    public static void disableLighting() {
////        assertThread(RenderSystem::isOnGameThread);
////        GlStateManager._disableLighting();
////
////        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
////        LIGHTING.disable();
////
////    }
////
////    public static void off2D() {
////        RenderSystem.popAttributes();
////    }
////
////    /**
////     * Arrays on/off
////     */
////    public static void arraysOnColor() {
////        // GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
////        _disableClientState(GL11.GL_VERTEX_ARRAY);
////
////        // GL11.glEnableClientState(GL11.GL_COLOR_ARRAY);
////        _disableClientState(GL11.GL_COLOR_ARRAY);
////    }
////
////    public static void arraysOnTexture() {
////        // GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
////        _disableClientState(GL11.GL_VERTEX_ARRAY);
////
////        // GL11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
////        _disableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
////    }
////
////    public static void arraysOff() {
////        // GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY);
////        _disableClientState(GL11.GL_VERTEX_ARRAY);
////
////        // GL11.glDisableClientState(GL11.GL_COLOR_ARRAY);
////        _disableClientState(GL11.GL_COLOR_ARRAY);
////
////        // GL11.glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
////        _disableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
////    }
////
////
////
////
////    @Deprecated
////    public static void _disableClientState(int p_227772_0_) {
////        RenderSystem.isOnRenderThread();
////        GL11.glDisableClientState(p_227772_0_);
////    }
////
////
////
////    /**
////     * Call before doing any pure geometry (ie. with colors rather than textures).
////     */
////    public static void texturelessOn() {
//////        GL11.glDisable(GL11.GL_TEXTURE_2D);
////        RenderSystem.disableTexture();
////    }
////
////    /**
////     * Call after doing pure geometry (ie. with colors) to go back to the texture mode (default).
////     */
////    public static void texturelessOff() {
//////        GL11.glEnable(GL11.GL_TEXTURE_2D);
////        RenderSystem.enableTexture();
////    }
////
////    /**
////     * Call before doing anything with alpha blending
////     */
////    public static void blendingOn() {
//////        GL11.glPushAttrib(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_LIGHTING_BIT);
//////        if (Minecraft.isFancyGraphicsEnabled()) {
//////            GL11.glShadeModel(GL11.GL_SMOOTH);
//////            GL11.glDisable(GL11.GL_ALPHA_TEST);
//////            GL11.glEnable(GL11.GL_BLEND);
//////            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
//////        }
////        RenderSystem.enableBlend();
////    }
////
////    /**
////     * Call after doing anything with alpha blending
////     */
////    public static void blendingOff() {
//////        RenderSystem.popAttributes();
////        RenderSystem.disableBlend();
////    }
////
////    public static void glPushAttrib(int mask) {
////        RenderSystem.assertOnGameThread();
////        GL11.glPushAttrib(mask);
////    }
////
//    public static void glEnable(int target) {
//        RenderSystem.assertOnGameThread();
//        GL11.glEnable(target);
//    }
////
////
////    /**
////     * Used primarily for model rendering to make a surface "glow"
////     *
////     * Use light map value of something like 0x00F000F0 instead
////     */
//////    private static float lightmapLastX = .0f;
//////    private static float lightmapLastY = .0f;
//////    @Deprecated
//////    public static void glowOn() {
//////        glPushAttrib(GL11.GL_LIGHTING_BIT);
//////        lightmapLastX = GlStateManager.lastBrightnessX;
//////        lightmapLastY = GlStateManager.lastBrightnessY;
//////        RenderHelper.func_74518_a();
//////        GlStateManager._glMultiTexCoord2f(GL13C.GL_TEXTURE1, 240.0F, 240.0F);
//////    }
////
//////    @Deprecated
//////    public static void glowOff() {
//////        GlStateManager.func_227640_a_(GL13C.GL_TEXTURE1, lightmapLastX, lightmapLastY);
//////        RenderHelper.func_227780_a_();
//////        RenderSystem.popAttributes();
//////    }
//}