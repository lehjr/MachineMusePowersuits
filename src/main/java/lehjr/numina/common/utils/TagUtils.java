package lehjr.numina.common.utils;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;
import java.util.Optional;

public class TagUtils {

    public static double getDoubleOrZero(CompoundTag nbt, String tagName) {
        if(nbt != null && nbt.contains(tagName, Tag.TAG_DOUBLE)) {
            return nbt.getDouble(tagName);
        }
        return 0;
    }

    public static CompoundTag setDoubleOrRemove(CompoundTag nbt, String tagName, double value) {
        if(nbt != null && nbt.contains(tagName, Tag.TAG_DOUBLE)) {
            if (value == 0) {
                nbt.remove(tagName);
            } else {
                nbt.putDouble(tagName, value);
            }
        }
        return nbt;
    }

    public static CompoundTag getTag(ItemStack stack) {
        return stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
    }

    public static void setTag(@Nonnull ItemStack itemStack, @Nonnull CompoundTag nbt) {
        itemStack.set(DataComponents.CUSTOM_DATA, CustomData.of(nbt.copy()));
    }

    // TODO: replace with serialized blockstate instead
    // Misc -----------------------------------------------------------------------------------------------------------
    public static void setModuleResourceLocation(@Nonnull ItemStack module, String string, ResourceLocation value) {
        if (value == null) {
            value = new ResourceLocation("air");
        }
        getTag(module).putString(string, value.toString());
    }

    public static Optional<ResourceLocation> getModuleResourceLocation(@Nonnull ItemStack module, String string) {
        CompoundTag moduleTag = getTag(module);
        if (moduleTag.contains(string, Tag.TAG_STRING)) {
            return Optional.of(new ResourceLocation(moduleTag.getString(string)));
        }
        return Optional.empty();
    }

    public static HolderLookup.Provider getProvider(@Nonnull Level level) {
        return level.registryAccess();
    }
}