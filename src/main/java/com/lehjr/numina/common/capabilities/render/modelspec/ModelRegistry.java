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

package com.lehjr.numina.common.capabilities.render.modelspec;

import com.lehjr.numina.common.map.NuminaRegistry;
import com.lehjr.numina.common.string.StringUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.obj.OBJModel;
import com.lehjr.numina.common.constants.TagConstants;

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 7:44 AM, 4/28/13
 * <p>
 * Ported to Java by lehjr on 11/8/16.
 * <p>
 * Note: make sure to have null checks in place.
 */
public class ModelRegistry extends NuminaRegistry<SpecBase> {
    private static volatile ModelRegistry INSTANCE;

    private ModelRegistry() {
    }

    public static ModelRegistry getInstance() {
        if (INSTANCE == null) {
            synchronized (ModelRegistry.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ModelRegistry();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * TextureSpec does not have an IModelState so this is relatively safe
     * @return
     */
    public OBJModel loadBakedModel(ResourceLocation resource) {
        String name = StringUtils.extractName(resource);
        SpecBase spec = get(name);
        return ((ModelSpec) (spec)).getModel();
    }

    public Iterable<SpecBase> getSpecs() {
        return this.elems();
    }

    public Iterable<String> getNames() {
        return this.names();
    }

    /**
     * FIXME: texture spec needs a model tag for this to work. Model tag does not have to be a real model, just a unique string for the spec k-v pair
     */
    public SpecBase getModel(CompoundTag nbt) {
        return get(nbt.getString(TagConstants.MODEL));
    }

    public PartSpecBase getPart(CompoundTag nbt, SpecBase model) {
        return model.get(nbt.getString(TagConstants.PART));
    }

    public PartSpecBase getPart(CompoundTag nbt) {
        return getPart(nbt, getModel(nbt));
    }

    public CompoundTag getSpecTag(CompoundTag museRenderTag, PartSpecBase spec) {
        String name = makeName(spec);
        return (museRenderTag.contains(name)) ? (museRenderTag.getCompound(name)) : null;
    }

    public String makeName(PartSpecBase spec) {
        return spec.spec.getOwnName() + "." + spec.partName;
    }
}