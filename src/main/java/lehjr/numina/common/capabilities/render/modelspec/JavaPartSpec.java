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

package lehjr.numina.common.capabilities.render.modelspec;

import com.google.common.base.Objects;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

/**
 * This just provides a way to tie the armor skin for vanilla armor
 */
public class JavaPartSpec extends PartSpecBase {
    final ResourceLocation textureLocation;

    public JavaPartSpec(final SpecBase spec,
                        final SpecBinding binding,
                        final Integer enumColourIndex,
                        final String partName,
                        final ResourceLocation textureLocation) {
        this(spec, binding, enumColourIndex, partName, textureLocation, false);
    }

    public JavaPartSpec(final SpecBase spec,
                        final SpecBinding binding,
                        final Integer enumColourIndex,
                        final String partName,
                        final ResourceLocation textureLocation,
                        boolean glow) {
        super(spec, binding, partName, enumColourIndex, glow);
        this.textureLocation = textureLocation;
    }

    @Override
    String getNamePrefix() {
        return "javaModel.";
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