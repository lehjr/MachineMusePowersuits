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

import com.mojang.blaze3d.vertex.PoseStack;
import lehjr.powersuits.common.base.MPSItems;
import lehjr.powersuits.common.constants.MPSConstants;
import lehjr.powersuits.common.constants.MPSRegistryNames;
import lehjr.powersuits.common.entity.SpinningBladeEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.TransformationHelper;
import net.minecraftforge.registries.ForgeRegistries;
import org.joml.Vector3f;

import javax.annotation.Nullable;
import java.util.Random;

public class SpinningBladeEntityRenderer extends net.minecraft.client.renderer.entity.EntityRenderer<SpinningBladeEntity> {
    public SpinningBladeEntityRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager);
        this.shadowRadius = 0.15F;
        this.shadowStrength = 0.75F;
    }

    public static final ResourceLocation textureLocation = new ResourceLocation(MPSConstants.TEXTURE_PREFIX + "item/module/weapon/spinningblade.png");

    @Nullable
    @Override
    public ResourceLocation getTextureLocation(SpinningBladeEntity entity) {
        return textureLocation;
    }

    private final Random random = new Random();

    @Override
    public void render(SpinningBladeEntity entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
        matrixStackIn.pushPose();
        ItemStack itemstack = new ItemStack(ForgeRegistries.ITEMS.getValue(MPSRegistryNames.BLADE_LAUNCHER_MODULE));
        int i = itemstack.isEmpty() ? 187 : Item.getId(itemstack.getItem()) + itemstack.getDamageValue();
        this.random.setSeed(i);
        BakedModel ibakedmodel = Minecraft.getInstance().getItemRenderer().getModel(itemstack, entityIn.level, null, entityIn.getId());
        //  FIXME... figure out how to rotate blades to correspond with path

        matrixStackIn.mulPose(TransformationHelper.quatFromXYZ(new Vector3f(90, 0, 0), true));
        int time = (int) System.currentTimeMillis() % 360;
        matrixStackIn.mulPose(TransformationHelper.quatFromXYZ(new Vector3f(0, 0, time / 2), true));


        boolean flag = ibakedmodel.isGui3d();
        matrixStackIn.pushPose();
        Minecraft.getInstance().getItemRenderer().render(itemstack, ItemTransforms.TransformType.NONE, false, matrixStackIn, bufferIn, packedLightIn, OverlayTexture.NO_OVERLAY, ibakedmodel);
        matrixStackIn.popPose();
        if (flag) {
            matrixStackIn.translate(0.0, 0.0, 0.09375F);
        }
        matrixStackIn.popPose();
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }
}