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

package lehjr.powersuits.client.render.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import lehjr.numina.client.render.entity.NuminaEntityRenderer;
import lehjr.numina.util.math.Colour;
import lehjr.powersuits.basemod.MPSObjects;
import lehjr.powersuits.entity.LuxCapacitorEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.common.model.TransformationHelper;

import javax.annotation.Nullable;
import java.util.Random;

public class LuxCapacitorEntityRenderer extends NuminaEntityRenderer<LuxCapacitorEntity> {


    public LuxCapacitorEntityRenderer(EntityRendererManager renderManager) {
        super(renderManager);
    }

    @Nullable
    @Override
    public ResourceLocation getTextureLocation(LuxCapacitorEntity luxCapacitorEntity) {
        return null;
    }

    private final Random random = new Random();

    ItemStack getStack(Colour color) {
        ItemStack stack = new ItemStack(MPSObjects.LUX_CAPACITOR_MODULE.get());
        CompoundTag nbt = stack.getOrCreateTag();
        nbt.putInt("colour", color.getInt());
        return stack;
    }

    @Override
    public void render(LuxCapacitorEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        matrixStackIn.pushPose();
        ItemStack itemstack = getStack(entityIn.color);
        int i = itemstack.isEmpty() ? 187 : Item.getId(itemstack.getItem()) + itemstack.getDamageValue();
        this.random.setSeed((long) i);
        IBakedModel ibakedmodel = Minecraft.getInstance().getItemRenderer().getModel(itemstack, entityIn.level, (LivingEntity) null);
        int time = (int) System.currentTimeMillis() % 360;
        matrixStackIn.mulPose(TransformationHelper.quatFromXYZ(new Vector3f(0, time / 2, 0), true));
        matrixStackIn.scale(1.8F, 1.8F, 1.8F);

        boolean flag = ibakedmodel.isGui3d();
        matrixStackIn.pushPose();
        Minecraft.getInstance().getItemRenderer().render(itemstack, ItemCameraTransforms.TransformType.GROUND, false, matrixStackIn, bufferIn, packedLightIn, OverlayTexture.NO_OVERLAY, ibakedmodel);
        matrixStackIn.popPose();
        if (!flag) {
            matrixStackIn.translate(0.0, 0.0, 0.09375F);
        }
        matrixStackIn.popPose();
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }
}