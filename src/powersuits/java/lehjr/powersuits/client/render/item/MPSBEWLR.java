package lehjr.powersuits.client.render.item;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Transformation;
import lehjr.numina.client.model.helper.ModelTransformCalibration;
import lehjr.numina.common.base.NuminaLogger;
import lehjr.powersuits.client.model.block.TinkerTableModel;
import lehjr.powersuits.common.base.MPSObjects;
import lehjr.powersuits.common.blockentity.TinkerTableBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.Lazy;

public class MPSBEWLR extends BlockEntityWithoutLevelRenderer {
    TinkerTableModel tinkerTableModel;
    ModelTransformCalibration CALIBRATION;
    Lazy<TinkerTableBlockEntity> tinkerTableBlockEntity = Lazy.of(()->new TinkerTableBlockEntity(BlockPos.ZERO, MPSObjects.TINKER_TABLE_BLOCK.get().defaultBlockState()));

    public MPSBEWLR() {
        super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), new EntityModelSet());
        tinkerTableModel = new TinkerTableModel();

        this.CALIBRATION = new ModelTransformCalibration();

    }



    @Override
    public void renderByItem(ItemStack itemStack, ItemTransforms.TransformType transformType, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
//        super.renderByItem(itemStack, transformType, poseStack, buffer, packedLight, packedOverlay);
        Item item = itemStack.getItem();

        if (item.equals(MPSObjects.TINKER_TABLE_ITEM.get())) {
            switch(transformType) {
                case FIRST_PERSON_LEFT_HAND -> tinkerTableModel.renderToBuffer(poseStack, buffer.getBuffer(RenderType.entityTranslucentCull(TinkerTableModel.TEXTURE)), packedLight, packedOverlay, 1F, 1F, 1F, 1F);
                case FIRST_PERSON_RIGHT_HAND -> tinkerTableModel.renderToBuffer(poseStack, buffer.getBuffer(RenderType.entityTranslucentCull(TinkerTableModel.TEXTURE)), packedLight, packedOverlay, 1F, 1F, 1F, 1F);
                case GUI -> tinkerTableModel.renderToBuffer(poseStack, buffer.getBuffer(RenderType.entityTranslucentCull(TinkerTableModel.TEXTURE)), packedLight, packedOverlay, 1F, 1F, 1F, 1F);
                case HEAD -> tinkerTableModel.renderToBuffer(poseStack, buffer.getBuffer(RenderType.entityTranslucentCull(TinkerTableModel.TEXTURE)), packedLight, packedOverlay, 1F, 1F, 1F, 1F);
                case FIXED -> tinkerTableModel.renderToBuffer(poseStack, buffer.getBuffer(RenderType.itemEntityTranslucentCull(TinkerTableModel.TEXTURE)), packedLight, packedOverlay, 1F, 1F, 1F, 1F);
                case GROUND -> tinkerTableModel.renderToBuffer(poseStack, buffer.getBuffer(RenderType.itemEntityTranslucentCull(TinkerTableModel.TEXTURE)), packedLight, packedOverlay, 1F, 1F, 1F, 1F);
                case THIRD_PERSON_LEFT_HAND -> tinkerTableModel.renderToBuffer(poseStack, buffer.getBuffer(RenderType.itemEntityTranslucentCull(TinkerTableModel.TEXTURE)), packedLight, packedOverlay, 1F, 1F, 1F, 1F);
                case THIRD_PERSON_RIGHT_HAND -> tinkerTableModel.renderToBuffer(poseStack, buffer.getBuffer(RenderType.itemEntityTranslucentCull(TinkerTableModel.TEXTURE)), packedLight, packedOverlay, 1F, 1F, 1F, 1F);
            }




            /** Important: the render type used here is required for gui and first person rendering for some reason */
//            tinkerTableModel.renderToBuffer(poseStack, buffer.getBuffer(RenderType.entityTranslucentCull(TinkerTableModel.TEXTURE)), packedLight, packedOverlay, 1F, 1F, 1F, 1F);

            /**
             * tried render types
             * entityTranslucentCull: (works good for first person and gui )
             * entityTranslucent: screens flicker like mad
             *
             *
             */







            if (transformType == ItemTransforms.TransformType.GUI) {
//                NuminaLogger.logger.info("rendering in gui");
            }


//           Minecraft.getInstance().getBlockEntityRenderDispatcher().renderItem(tinkerTableBlockEntity.get(), poseStack, buffer, packedLight, packedOverlay);
        }

        if (item.equals(MPSObjects.POWER_FIST)) {

        }






    }

    Transformation transform(ItemTransforms.TransformType transformType) {
        switch(transformType) {
            case NONE:
            case THIRD_PERSON_LEFT_HAND:
                case THIRD_PERSON_RIGHT_HAND:
            // scale 0.625
            // rot y: 45

                case FIRST_PERSON_LEFT_HAND:
            case FIRST_PERSON_RIGHT_HAND:
            case HEAD:
            case GUI:
            case GROUND:
                // scale: 0.25
                // translate: 5, -24, 5, 0, 0, 0
            case FIXED:
        }
        return Transformation.identity();
    }

/*
"display": {
    "thirdperson_righthand": {
        "rotation": [ 75, 45, 0 ],
        "translation": [ 0.00, 2.50, 0.00 ],
        "scale": [ 0.38, 0.38, 0.38 ]
    },
    "thirdperson_lefthand": {
        "rotation": [ 75, 45, 0 ],
        "translation": [ 0.00, 2.50, 0.00 ],
        "scale": [ 0.38, 0.38, 0.38 ]
    },
    "firstperson_righthand": {
        "rotation": [ 0, 45, 0 ],
        "translation": [ 0.00, 0.00, 0.00 ],
        "scale": [ 0.40, 0.40, 0.40 ]
    },
    "firstperson_lefthand": {
        "rotation": [ 0, 225, 0 ],
        "translation": [ 0.00, 0.00, 0.00 ],
        "scale": [ 0.40, 0.40, 0.40 ]
    },
    "gui": {
        "rotation": [ 30, 225, 0 ],
        "translation": [ 0.00, 0.00, 0.00 ],
        "scale": [ 0.63, 0.63, 0.63 ]
    },
    "head": {
        "rotation": [ 0, 0, 0 ],
        "translation": [ 0.00, 0.00, 0.00 ],
        "scale": [ 1.00, 1.00, 1.00 ]
    },
    "fixed": {
        "rotation": [ 0, 0, 0 ],
        "translation": [ 0.00, 0.00, 0.00 ],
        "scale": [ 0.50, 0.50, 0.50 ]
    },
    "ground": {
        "rotation": [ 0, 0, 0 ],
        "translation": [ 0.00, 3.00, 0.00 ],
        "scale": [ 0.25, 0.25, 0.25 ]
    }
}
 */




}
