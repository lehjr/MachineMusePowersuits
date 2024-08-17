package com.lehjr.numina.common.capabilities.render.modelspec;

import com.google.common.base.Objects;
import com.lehjr.numina.common.math.Color;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;

/**
 * This just provides a way to tie the armor skin for vanilla armor
 */
public class JavaPartSpec extends PartSpecBase {
    final ResourceLocation textureLocation;

    public JavaPartSpec(final SpecBase spec,
                        final SpecBinding binding,
                        final Color color,
                        final String partName,
                        final ResourceLocation textureLocation) {
        this(spec, binding, color, partName, textureLocation, false);
    }

    public JavaPartSpec(final SpecBase spec,
                        final SpecBinding binding,
                        final Color color,
                        final String partName,
                        final ResourceLocation textureLocation,
                        boolean glow) {
        super(spec, binding, partName, color, glow);
        this.textureLocation = textureLocation;
    }

    @Override
    String getNamePrefix() {
        return "javaModel.";
    }

    public Component getDisaplayName() {
        if (binding.getSlot().getType() == EquipmentSlot.Type.HUMANOID_ARMOR) {
            return Component.translatable(new StringBuilder(getNamePrefix())
                    .append(this.partName)
                    .append(".partName")
                    .toString());
        }
        return super.getDisaplayName();
    }

    public ResourceLocation getTextureLocation() {
        return textureLocation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        JavaPartSpec that = (JavaPartSpec) o;
        if(binding != that.binding) return false;
        return Objects.equal(getTextureLocation(), that.getTextureLocation());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), getTextureLocation());
    }

//    public void render(ModelPart modelPart, PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
//        modelPart.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
//
//
////        this.mainarm.visible = true;
////        this.root.children.values().forEach(part-> {
////            part.visible = true;
////            part.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
////        });
//    }


}