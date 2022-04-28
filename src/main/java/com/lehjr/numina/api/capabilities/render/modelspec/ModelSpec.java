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

package com.lehjr.numina.api.capabilities.render.modelspec;

import com.mojang.math.Transformation;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.client.model.obj.OBJModel;

import java.util.Objects;

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 7:44 AM, 4/28/13
 * <p>
 * Ported to Java by lehjr on 11/8/16.
 */
public class ModelSpec extends SpecBase {
    private final OBJModel model;
    private final ModelState modelTransforms;

    public ModelSpec(final OBJModel model, final ModelState transforms, final String name, final boolean isDefault, final SpecType specType) {
        super(name, isDefault, specType);
        this.modelTransforms = transforms;
        this.model = model;
    }

    public Transformation getTransform(ItemTransforms.TransformType transformType) {
        Transformation transformation = modelTransforms.getPartTransformation(transformType);
        return transformation;
    }

    @Override
    public Component getDisaplayName() {
        return new TranslatableComponent(new StringBuilder("model.")
                .append(this.getOwnName())
                .append(".modelName")
                .toString());
    }

    @Override
    public String getOwnName() {
        String name = ModelRegistry.getInstance().getName(this);
        return (name != null) ? name : "";
    }

    public OBJModel getModel() {
        return model;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ModelSpec modelSpec = (ModelSpec) o;
        return Objects.equals(model, modelSpec.model) &&
                Objects.equals(modelTransforms, modelSpec.modelTransforms);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), model, modelTransforms);
    }
}