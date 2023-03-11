package lehjr.powersuits.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import lehjr.powersuits.common.blockentity.LuxCapacitorBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.world.phys.Vec3;

// TODO: do I really want to go back to this just to avoid color issues?

public class LuxCapBER implements BlockEntityRenderer<LuxCapacitorBlockEntity> {
    @Override
    public void render(LuxCapacitorBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {

    }

    @Override
    public boolean shouldRenderOffScreen(LuxCapacitorBlockEntity pBlockEntity) {
        return BlockEntityRenderer.super.shouldRenderOffScreen(pBlockEntity);
    }

    @Override
    public int getViewDistance() {
        return BlockEntityRenderer.super.getViewDistance();
    }

    @Override
    public boolean shouldRender(LuxCapacitorBlockEntity pBlockEntity, Vec3 pCameraPos) {
        return BlockEntityRenderer.super.shouldRender(pBlockEntity, pCameraPos);
    }
}
