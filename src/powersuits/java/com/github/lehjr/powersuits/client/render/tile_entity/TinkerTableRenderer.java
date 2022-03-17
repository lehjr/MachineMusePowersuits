///*
// * Copyright (c) 2021. MachineMuse, Lehjr
// *  All rights reserved.
// *
// * Redistribution and use in source and binary forms, with or without
// * modification, are permitted provided that the following conditions are met:
// *
// *      Redistributions of source code must retain the above copyright notice, this
// *      list of conditions and the following disclaimer.
// *
// *     Redistributions in binary form must reproduce the above copyright notice,
// *     this list of conditions and the following disclaimer in the documentation
// *     and/or other materials provided with the distribution.
// *
// *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
// *  ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
// *  WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
// *  DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
// *  ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
// *  (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
// *  LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
// *  ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
// *  (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
// *  SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
// */
//
//package com.github.lehjr.powersuits.client.render.tile_entity;
//
//import com.github.lehjr.powersuits.client.model.block.TinkerTableModel2;
//import com.github.lehjr.powersuits.constants.MPSConstants;
//import com.github.lehjr.powersuits.tile_entity.TinkerTableTileEntity;
//import com.mojang.blaze3d.matrix.MatrixStack;
//import net.minecraft.client.renderer.IRenderTypeBuffer;
//import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
//import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
//import net.minecraft.util.ResourceLocation;
//
//public class TinkerTableRenderer<T extends TinkerTableTileEntity> extends TileEntityRenderer<T> {
//    ResourceLocation TEXTURE = new ResourceLocation(MPSConstants.MOD_ID, "textures/models/tinkertable_tx.png");
//    TinkerTableModel2 test = new TinkerTableModel2();
//
//
//    public TinkerTableRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
//        super(rendererDispatcherIn);
//    }
//
//    @Override
//    public void render(T tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
//        test.render(matrixStackIn,
//                bufferIn.getBuffer(test.getRenderType(TEXTURE)),
//                combinedLightIn, combinedOverlayIn, 1F, 1F, 1F, 1F);
//    }
//}