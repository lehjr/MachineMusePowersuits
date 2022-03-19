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

package lehjr.numina.util.capabilities.render.modelspec;

import com.google.common.base.Objects;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

/**
 * This just provides a way to tie the armor skin for vanilla armor
 */
public class TexturePartSpec extends PartSpecBase {
    final String textureLocation;

    public TexturePartSpec(final SpecBase spec,
                           final SpecBinding binding,
                           final Integer enumColourIndex,
                           final String partName,
                           final String textureLocation) {
        super(spec, binding, partName, enumColourIndex);
        this.textureLocation = textureLocation;
    }

    @Override
    public ITextComponent getDisaplayName() {
        return new TranslationTextComponent(new StringBuilder("textureSpec.")
                .append(this.binding.getSlot().getName())
                .append(".partName")
                .toString());
    }

    public String getTextureLocation() {
        return textureLocation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        TexturePartSpec that = (TexturePartSpec) o;
        return Objects.equal(getTextureLocation(), that.getTextureLocation());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), getTextureLocation());
    }
}