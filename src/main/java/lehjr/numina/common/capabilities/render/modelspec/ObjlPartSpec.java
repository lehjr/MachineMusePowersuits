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

import com.mojang.math.Transformation;
import lehjr.numina.client.model.obj.OBJBakedPart;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * Ported to Java by lehjr on 11/8/16.
 */
@OnlyIn(Dist.CLIENT)
public class ObjlPartSpec extends PartSpecBase {
    private Transformation partTransform = Transformation.identity();

    public ObjlPartSpec(final ObjModelSpec objModelSpec,
                        final SpecBinding binding,
                        final String partName,
                        final Integer defaultcolorindex,
                        final Boolean defaultglow) {
        super(objModelSpec, binding, partName, defaultcolorindex, defaultglow);
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

    public OBJBakedPart getPart() {
        return ((ObjModelSpec) (this.spec)).getModel().getPart(this.partName);
    }

    //    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        if (!super.equals(o)) return false;
//        ModelPartSpec that = (ModelPartSpec) o;
//        return defaultglow == that.defaultglow;
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(super.hashCode(), defaultglow);
//    }
}