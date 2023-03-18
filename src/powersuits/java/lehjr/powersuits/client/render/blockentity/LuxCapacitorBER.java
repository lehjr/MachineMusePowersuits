package lehjr.powersuits.client.render.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import lehjr.powersuits.common.block.LuxCapacitorBlock;
import lehjr.powersuits.common.blockentity.LuxCapacitorBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.phys.Vec3;

// TODO: do I really want to go back to this just to avoid color issues?

public class LuxCapacitorBER implements BlockEntityRenderer<LuxCapacitorBlockEntity> {


    public LuxCapacitorBER(BlockEntityRendererProvider.Context context) {
//        context.bakeLayer(new ModelLayerLocation(MPSRegistryNames.LUX_CAPACITOR, "facing:up"));


//powersuits:luxcapacitor#facing=north,waterlogged=true
//powersuits:luxcapacitor#facing=north,waterlogged=false

//powersuits:luxcapacitor#facing=down,waterlogged=true
//powersuits:luxcapacitor#facing=down,waterlogged=false

//powersuits:luxcapacitor#facing=west,waterlogged=false
//powersuits:luxcapacitor#facing=west,waterlogged=true

//powersuits:luxcapacitor#facing=south,waterlogged=true
//powersuits:luxcapacitor#facing=south,waterlogged=false

//powersuits:luxcapacitor#facing=east,waterlogged=false
//powersuits:luxcapacitor#facing=east,waterlogged=true

//powersuits:luxcapacitor#facing=up,waterlogged=false
//powersuits:luxcapacitor#facing=up,waterlogged=true


//powersuits:luxcapacitor#inventory
//powersuits:luxcapacitor_module#inventory

    }


    @Override
    public void render(LuxCapacitorBlockEntity entity, float pPartialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        if (entity != null && entity.getBlockState() != null) {
            if (entity.getBlockState().hasProperty(LuxCapacitorBlock.FACING)) {
                entity.getBlockState().getValue(LuxCapacitorBlock.FACING);
            }


        }
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
