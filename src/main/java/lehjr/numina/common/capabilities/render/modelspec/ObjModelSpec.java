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
import lehjr.numina.client.event.ModelBakeEventHandler;
import lehjr.numina.client.model.obj.OBJBakedCompositeModel;
import lehjr.numina.common.base.NuminaLogger;
import net.minecraft.client.renderer.block.model.ItemTransform;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.Optional;

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 7:44 AM, 4/28/13
 * <p>
 * Ported to Java by lehjr on 11/8/16.
 */
public class ObjModelSpec extends SpecBase {
    private Transformation modelTransform = Transformation.identity();
    private ItemTransforms itemTransforms = ItemTransforms.NO_TRANSFORMS;
    private final ResourceLocation location;

    public ObjModelSpec(final ResourceLocation location, final String name, final boolean isDefault, final SpecType specType) {
        this(location, ItemTransforms.NO_TRANSFORMS, name, isDefault, specType);
    }

    public ObjModelSpec(final ResourceLocation location, @Nonnull final ItemTransforms itemTransforms, final String name, final boolean isDefault, final SpecType specType) {
        super(name, isDefault, specType);
        this.itemTransforms = itemTransforms;
        this.location = location;
    }

    public ItemTransform getTransform(ItemDisplayContext transformType) {
        return itemTransforms.getTransform(transformType);
    }

    public Transformation getModelTransform() {
        return modelTransform;
    }

    public void setModelTransform(Transformation modelTransform) {
        this.modelTransform = modelTransform;
    }

    @Override
    public Component getDisaplayName() {
        return Component.translatable(new StringBuilder("model.")
                .append(this.getOwnName())
                .append(".modelName")
                .toString());
    }

    @Override
    public String getOwnName() {
        String name = NuminaModelSpecRegistry.getInstance().getName(this);
        return (name != null) ? name : getName();
    }

    @Nonnull
    public Optional<OBJBakedCompositeModel> getModel() {
        BakedModel model = ModelBakeEventHandler.INSTANCE.getBakedItemModel(location);
        if (model instanceof OBJBakedCompositeModel) {
            return Optional.of((OBJBakedCompositeModel) model);
        }
        NuminaLogger.logDebug("model at location < " + location + " > missing");
        return Optional.empty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ObjModelSpec objModelSpec = (ObjModelSpec) o;
        return Objects.equals(location, objModelSpec.location) &&
                Objects.equals(this.getName(), objModelSpec.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), location, itemTransforms);
    }
}
