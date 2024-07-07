package lehjr.powersuits.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import lehjr.powersuits.common.constants.MPSConstants;
import lehjr.powersuits.common.entity.SpinningBladeEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.util.TransformationHelper;
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
        ItemStack itemstack = new ItemStack(BuiltInRegistries.ITEM.get(MPSConstants.BLADE_LAUNCHER_MODULE));
        int i = itemstack.isEmpty() ? 187 : Item.getId(itemstack.getItem()) + itemstack.getDamageValue();
        this.random.setSeed(i);
        BakedModel ibakedmodel = Minecraft.getInstance().getItemRenderer().getModel(itemstack, entityIn.level(), null, entityIn.getId());
        //  FIXME... figure out how to rotate blades to correspond with path

        matrixStackIn.mulPose(TransformationHelper.quatFromXYZ(new Vector3f(90, 0, 0), true));
        int time = (int) System.currentTimeMillis() % 360;
        matrixStackIn.mulPose(TransformationHelper.quatFromXYZ(new Vector3f(0, 0, time / 2), true));


        boolean flag = ibakedmodel.isGui3d();
        matrixStackIn.pushPose();
        Minecraft.getInstance().getItemRenderer().render(itemstack, ItemDisplayContext.NONE, false, matrixStackIn, bufferIn, packedLightIn, OverlayTexture.NO_OVERLAY, ibakedmodel);
        matrixStackIn.popPose();
        if (flag) {
            matrixStackIn.translate(0.0, 0.0, 0.09375F);
        }
        matrixStackIn.popPose();
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }
}