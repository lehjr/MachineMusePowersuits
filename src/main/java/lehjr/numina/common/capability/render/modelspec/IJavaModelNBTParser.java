package lehjr.numina.common.capability.render.modelspec;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import lehjr.numina.common.math.Color;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.nbt.CompoundTag;

import javax.annotation.Nullable;
import java.util.List;

public interface IJavaModelNBTParser {
    final int FULL_BRIGHTNESS = 0XF000F0; // 15728880 also used in vanilla rendering item in gui
    ModelPart getRoot();

    /**
     * Biggest issue with this is that the parent sets up a transform before the child part does.. so rendering child first breaks things
     * @param tag
     * @param colors
     * @param poseStack
     * @param bufferIn
     * @param packedLightIn
     * @param packedOverlayIn
     */
    default void renderPart(CompoundTag tag, int[] colors, PoseStack poseStack, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn) {
        if (colors.length == 0) {
            colors = new int[]{Color.WHITE.getARGBInt()};
        }

        int partColor;
        PartSpecBase partSpec = NuminaModelSpecRegistry.getInstance().getPart(tag);

        if (partSpec == null) {
            return;
        }

        int ix = partSpec.getColorIndex(tag);
        // checks the range of the index to avoid errors OpenGL or crashing
        if (ix < colors.length && ix >= 0) {
            partColor = colors[ix];
        } else {
            partColor = -1;
        }
        boolean glow = partSpec.getGlow(tag);

        poseStack.pushPose();
        ModelPart part = getPart(partSpec.getPartName(), poseStack);

        Color color = new Color(partColor);

        if (part != null) {
            part.compile(poseStack.last(), bufferIn,
                    glow? FULL_BRIGHTNESS : packedLightIn,
                    glow? OverlayTexture.NO_OVERLAY : packedOverlayIn,
                    color.r, color.g, color.b, color.a);
        }
        poseStack.popPose();
    }

    List<String> ignore();

    @Nullable
    default ModelPart getPart(String path, PoseStack poseStack) {
        ModelPart part = this.getRoot();
        translateAndRotate(part, poseStack);

        if (part != null) {
            translateAndRotate(part, poseStack);
            if (path.contains(".") && part != null) {
                String[] splitPath = path.split("\\.");
                for (int i = 0; i < splitPath.length; i++) {
                    if (ignore().contains(splitPath[i])) continue;
                    part = part.getChild(splitPath[i]);
                    translateAndRotate(part, poseStack);
                }
            } else {
                part = part.getChild(path);
                translateAndRotate(part, poseStack);
            }
        }
        return part;
    }

    default void translateAndRotate(ModelPart part, PoseStack poseStack) {
        if (part!= null) {
            part.translateAndRotate(poseStack);
        }
    }
}