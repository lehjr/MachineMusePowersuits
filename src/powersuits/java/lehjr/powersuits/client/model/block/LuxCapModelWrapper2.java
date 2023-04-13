//package lehjr.powersuits.client.model.block;
//
//import com.mojang.blaze3d.vertex.PoseStack;
//import lehjr.numina.common.capabilities.NuminaCapabilities;
//import lehjr.numina.common.constants.TagConstants;
//import lehjr.numina.common.math.Color;
//import lehjr.powersuits.common.block.LuxCapacitorBlock;
//import lehjr.powersuits.common.constants.MPSConstants;
//import net.minecraft.client.multiplayer.ClientLevel;
//import net.minecraft.client.renderer.RenderType;
//import net.minecraft.client.renderer.block.model.BakedQuad;
//import net.minecraft.client.renderer.block.model.ItemOverrides;
//import net.minecraft.client.renderer.block.model.ItemTransforms;
//import net.minecraft.client.resources.model.BakedModel;
//import net.minecraft.core.Direction;
//import net.minecraft.nbt.Tag;
//import net.minecraft.util.RandomSource;
//import net.minecraft.world.entity.LivingEntity;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.world.level.block.state.BlockState;
//import net.minecraftforge.client.ChunkRenderTypeSet;
//import net.minecraftforge.client.model.BakedModelWrapper;
//import net.minecraftforge.client.model.data.ModelData;
//import org.jetbrains.annotations.NotNull;
//import org.jetbrains.annotations.Nullable;
//
//import java.util.List;
//
///**
// * FIXME: the quads are not fetched
// */
//public class LuxCapModelWrapper2 extends BakedModelWrapper {
//    Color color;
//    private LuxCapacitorItemOverrides overrides;
//
//    public LuxCapModelWrapper2(BakedModel originalModel) {
//        super(originalModel);
//        this.overrides = new LuxCapacitorItemOverrides(this);
//    }
//
//    @Override
//    public @NotNull List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @NotNull RandomSource rand, @NotNull ModelData extraData, @Nullable RenderType renderType) {
//        System.out.println("getting quads with model data");
//        System.out.println("modelData: " + extraData);
//        System.out.println("renderType: " + renderType);
//        // renderType: RenderType[solid:CompositeState[[texture[Optional[minecraft:textures/atlas/blocks.png](blur=false, mipmap=true)], shader[Optional[net.minecraft.client.renderer.RenderStateShard$$Lambda$4956/0x000000080174ab78@1d82e396]], no_transparency, depth_test[<=], cull[true], lightmap[true], overlay[false], no_layering, main_target, default_texturing, write_mask_state[writeColor=true, writeDepth=true], line_width[1.0]], outlineProperty=affects_outline]]
//
//        return super.getQuads(state, side, rand, extraData, renderType);
//    }
//
//    @Override
//    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, RandomSource rand) {
//        System.out.println("getting quads without model data");
//
//        return super.getQuads(state, side, rand);
//    }
//
//    @Override
//    public ItemTransforms getTransforms() {
//        return super.getTransforms();
//    }
//
//    @Override
//    public BakedModel applyTransform(ItemTransforms.TransformType cameraTransformType, PoseStack poseStack, boolean applyLeftHandTransform) {
//        return super.applyTransform(cameraTransformType, poseStack, applyLeftHandTransform);
//    }
//
//    @Override
//    public ChunkRenderTypeSet getRenderTypes(@NotNull BlockState state, @NotNull RandomSource rand, @NotNull ModelData data) {
//        return super.getRenderTypes(state, rand, data);
//    }
//
//    @Override
//    public List<RenderType> getRenderTypes(ItemStack itemStack, boolean fabulous) {
//        return super.getRenderTypes(itemStack, fabulous);
//    }
//
//    @Override
//    public List<BakedModel> getRenderPasses(ItemStack itemStack, boolean fabulous) {
//        return super.getRenderPasses(itemStack, fabulous);
//    }
//
//    /**
//     * required to set Lens color
//     */
//    @Override
//    public ItemOverrides getOverrides() {
//        return overrides;
//    }
//
//    private class LuxCapacitorItemOverrides extends ItemOverrides {
//        LuxCapModelWrapper2 itemModel;
//        public LuxCapacitorItemOverrides(LuxCapModelWrapper2 model) {
//            this.itemModel = model;
//        }
//
//        @javax.annotation.Nullable
//        @Override
//        public BakedModel resolve(BakedModel model, ItemStack stack, @javax.annotation.Nullable ClientLevel worldIn, @javax.annotation.Nullable LivingEntity entityIn, int pSeed) {
////            Color colour;
//            // this one is just for the launched item
//            if (stack.hasTag() && stack.getTag().contains(TagConstants.COLOR, Tag.TAG_INT)) {
//                color = new Color( stack.getTag().getInt(TagConstants.COLOR));
//                // this is for the active icon
//            } else {
//                color = stack.getCapability(NuminaCapabilities.POWER_MODULE).map(pm -> {
//                    float red = (float) pm.applyPropertyModifiers(MPSConstants.RED_HUE);
//                    float green = (float) pm.applyPropertyModifiers(MPSConstants.GREEN_HUE);
//                    float blue = (float) pm.applyPropertyModifiers(MPSConstants.BLUE_HUE);
//                    float alpha = (float) pm.applyPropertyModifiers(MPSConstants.OPACITY);
//                    return new Color(red, green, blue, alpha);
//                }).orElse(LuxCapacitorBlock.defaultColor);
//            }
//
//            if (model instanceof LuxCapModelWrapper2) {
//                System.out.println("model IS wrapped");
//                ((LuxCapModelWrapper2) model).color = color;
//                return model;
//            }
//            System.out.println("model is not wrapped");
//
//            itemModel.color = color;
//            return itemModel;
//        }
//    }
//}
