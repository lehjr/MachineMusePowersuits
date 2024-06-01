package lehjr.numina.common.utils;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;

public class TagUtils {

    public static double getDoubleOrZero(CompoundTag nbt, String tagName) {
        return (nbt.contains(tagName, Tag.TAG_DOUBLE) ? nbt.getDouble(tagName) : 0);
    }

    /**
     * Sets the getValue of the given nbt tag, or removes it if the getValue would be
     * zero.
     */
    public static void setDoubleOrRemove(CompoundTag tag, String string, double value) {
        if (tag != null) {
            if (value == 0) {
                tag.remove(string);
            } else {
                tag.putDouble(string, value);
            }
        }
    }
}
