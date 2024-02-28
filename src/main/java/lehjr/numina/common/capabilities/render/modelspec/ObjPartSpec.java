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

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Transformation;
import lehjr.numina.client.model.obj.OBJBakedPart;
import lehjr.numina.common.math.Color;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Quaternionf;

import java.util.Optional;

/**
 * Ported to Java by lehjr on 11/8/16.
 */
@OnlyIn(Dist.CLIENT)
public class ObjPartSpec extends PartSpecBase {
    private Transformation partTransform = Transformation.identity();

    public ObjPartSpec(final ObjModelSpec objModelSpec,
                       final SpecBinding binding,
                       final String partName,
                       final Color color,
                       final Boolean defaultglow) {
        super(objModelSpec, binding, partName, color, defaultglow);
    }

    public Transformation getPartTransform() {
        return partTransform;
    }

    public void setPartTransform(Transformation partTransform) {
        this.partTransform = partTransform;
    }

    @Override
    String getNamePrefix() {
        return "model.";
    }

    public Optional<OBJBakedPart> getPart() {
        return getParent().getModel().map(objBakedCompositeModel -> objBakedCompositeModel.getPart(this.partName));
    }

//    public void applyTransform(PoseStack poseStack) {
//        if (partTransform != Transformation.identity()) {
//            PoseStack stack = new PoseStack();
//            stack.pushTransformation(partTransform);
//            // Apply the transformation to the real matrix stack
//            Matrix4f tMat = stack.last().pose();
//            Matrix3f nMat = stack.last().normal();
//            poseStack.last().pose().mul(tMat);
//            poseStack.last().normal().mul(nMat);
//        }
//    }

    ObjModelSpec getParent() {
        return (ObjModelSpec) this.spec;
    }

    public void apply(boolean pLeftHand, PoseStack pPoseStack) {
        if (this.partTransform != Transformation.identity()) {
            float f = this.partTransform.getLeftRotation().x; // x
            float f1 = this.partTransform.getLeftRotation().y; // y
            float f2 = this.partTransform.getLeftRotation().z; // z
            if (pLeftHand) {
                f1 = -f1;
                f2 = -f2;
            }

            int i = pLeftHand ? -1 : 1;
            pPoseStack.translate((float)i * this.partTransform.getTranslation().x(), this.partTransform.getTranslation().y(), this.partTransform.getTranslation().z());
            pPoseStack.mulPose(new Quaternionf(f, f1, f2, 1));
            pPoseStack.scale(this.partTransform.getScale().x(), this.partTransform.getScale().y(), this.partTransform.getScale().z());
//            pPoseStack.mulPose(net.minecraftforge.common.util.TransformationHelper.quatFromXYZ(this.partTransform.getRightRotation().i(), this.partTransform.getRightRotation().j() * (pLeftHand ? -1 : 1), this.partTransform.getRightRotation().k() * (pLeftHand ? -1 : 1), true));
            pPoseStack.mulPose(new Quaternionf(this.partTransform.getRightRotation().x, this.partTransform.getRightRotation().y * (pLeftHand ? -1 : 1), this.partTransform.getRightRotation().z * (pLeftHand ? -1 : 1), 1));

        }
    }
}