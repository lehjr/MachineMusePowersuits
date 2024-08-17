package com.lehjr.numina.common.capabilities.render.modelspec;

import com.lehjr.numina.client.event.ModelBakeEventHandler;
import com.lehjr.numina.client.model.obj.OBJBakedCompositeModel;
import com.lehjr.numina.common.base.NuminaLogger;
import com.mojang.math.Transformation;
import net.minecraft.client.renderer.block.model.ItemTransform;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
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
        BakedModel model = ModelBakeEventHandler.getBakedItemModel(new ModelResourceLocation(location, ""));
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
