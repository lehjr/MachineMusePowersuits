package com.lehjr.numina.common.capabilities.render.color;

import com.lehjr.numina.common.math.Color;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.Tag;
import net.neoforged.neoforge.common.util.INBTSerializable;

public interface IColorTag extends INBTSerializable<Tag> {

    Color getColor();

    void setColor(Color color);

    @Override
    default Tag serializeNBT(HolderLookup.Provider provider) {
        return IntTag.valueOf(getColor().getARGBInt());
    }

    @Override
    default void deserializeNBT(HolderLookup.Provider provider, Tag nbt) {
        if (!(nbt instanceof IntTag intNbt)) {
            throw new IllegalArgumentException("Can not deserialize to an instance that isn't the default implementation");
        }
        setColor(new Color(intNbt.getAsInt()));
    }
}
