package com.lehjr.powersuits.client.render.entity;

import com.lehjr.powersuits.common.constants.MPSConstants;
import com.lehjr.powersuits.common.entity.SpinningBladeEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.util.TransformationHelper;
import org.joml.Vector3f;

import javax.annotation.Nonnull;
import java.util.Random;

public class SpinningBladeEntityRenderer extends EntityRenderer<SpinningBladeEntity> {
//    static final Quaternionf ANGLE_90 = TransformationHelper.quatFromXYZ(new Vector3f(-90, 0, 0), true);

    public static final ResourceLocation textureLocation = ResourceLocation.parse(MPSConstants.TEXTURE_PREFIX + "item/module/weapon/spinningblade.png");

    public SpinningBladeEntityRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager);
        this.shadowRadius = 0.15F;
        this.shadowStrength = 0.75F;
    }

    @Nonnull
    @Override
    public ResourceLocation getTextureLocation(@Nonnull SpinningBladeEntity entity) {
        return textureLocation;
    }

    private final Random random = new Random();

    public void render(SpinningBladeEntity  entityIn, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        ItemStack itemstack = new ItemStack(BuiltInRegistries.ITEM.get(MPSConstants.BLADE_LAUNCHER_MODULE));
        int i = itemstack.isEmpty() ? 187 : Item.getId(itemstack.getItem()) + itemstack.getDamageValue();
        this.random.setSeed(i);
        BakedModel ibakedmodel = Minecraft.getInstance().getItemRenderer().getModel(itemstack, entityIn.level(), null, entityIn.getId());
        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(partialTicks, entityIn.yRotO, entityIn.getYRot()) - 90.0F));
        poseStack.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(partialTicks, entityIn.xRotO, entityIn.getXRot())));

        int time = (int) System.currentTimeMillis() % 360;
        poseStack.mulPose(TransformationHelper.quatFromXYZ(new Vector3f(0, time * -0.5F, 0), true));
        poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));

        boolean flag = ibakedmodel.isGui3d();
        Minecraft.getInstance().getItemRenderer().render(itemstack, ItemDisplayContext.NONE, false, poseStack, buffer, packedLight, OverlayTexture.NO_OVERLAY, ibakedmodel);
        poseStack.popPose();

        if (flag) {
            poseStack.translate(0.0, 0.0, 0.09375F);
        }
        super.render(entityIn, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }
}
