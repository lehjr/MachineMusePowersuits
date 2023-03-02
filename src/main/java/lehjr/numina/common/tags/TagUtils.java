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

package lehjr.numina.common.tags;

import lehjr.numina.common.constants.TagConstants;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.Optional;

public class TagUtils {
    /**
     * Gets or creates stack.getTag.getTag(TAG_ITEM_PREFIX)
     *
     * @param stack
     * @return an CompoundTag, may be newly created. If stack is empty, returns null.
     */
    public static CompoundTag getMuseItemTag(@Nonnull ItemStack stack) {
        if (stack.isEmpty()) {
            return new CompoundTag();
        }

        CompoundTag stackTag = stack.getOrCreateTag();
        CompoundTag properties = (stackTag.contains(TagConstants.TAG_ITEM_PREFIX)) ? stackTag.getCompound(TagConstants.TAG_ITEM_PREFIX) : new CompoundTag();
        stackTag.put(TagConstants.TAG_ITEM_PREFIX, properties);
        stack.setTag(stackTag);
        return properties;
    }

    /**
     * Gets or creates stack.getTag.getTag(TAG_MODULE_PREFIX)
     *
     * @param module
     * @return an CompoundTag, may be newly created. If stack is empty, returns null.
     */
    public static CompoundTag getModuleTag(@Nonnull ItemStack module) {
        if (module.isEmpty()) {
            return new CompoundTag();
        }

        CompoundTag stackTag = module.getOrCreateTag();
        CompoundTag properties = (stackTag.contains(TagConstants.TAG_MODULE_PREFIX)) ? stackTag.getCompound(TagConstants.TAG_MODULE_PREFIX) : new CompoundTag();
        stackTag.put(TagConstants.TAG_MODULE_PREFIX, properties);
        module.setTag(stackTag);
        return properties;
    }

    // Floats ---------------------------------------------------------------------------------------------------------
    /**
     * Bouncer for succinctness. Checks the itemStack's modular properties and
     * returns the getValue if it exists, otherwise 0.
     */
    public static float getModuleFloatOrZero(@Nonnull ItemStack stack, String string) {
        return getFloatOrZero(getModuleTag(stack), string);
    }

    public static float getModularItemFloatOrZero(@Nonnull ItemStack stack, String string) {
        return getFloatOrZero(getMuseItemTag(stack), string);
    }

    public static float getFloatOrZero(CompoundTag nbt, String tagName) {
        return (nbt.contains(tagName, Tag.TAG_FLOAT) ? nbt.getFloat(tagName) : 0);
    }

    /**
     * Sets the given itemstack's modular property, or removes it if the getValue
     * would be zero.
     */
    public static void setModularItemFloatOrRemove(@Nonnull ItemStack stack, String string, float value) {
        setFloatOrRemove(TagUtils.getMuseItemTag(stack), string, value);
    }

    public static void setModuleFloatOrRemove(@Nonnull ItemStack stack, String string, float value) {
        setFloatOrRemove(TagUtils.getModuleTag(stack), string, value);
    }

    /**
     * Sets the getValue of the given nbt tag, or removes it if the getValue would be
     * zero.
     */
    public static void setFloatOrRemove(CompoundTag itemProperties, String string, float value) {
        if (itemProperties != null) {
            /**
             * Float#compare(f1, f2);
             * F1 < F2 = -1
             * F1 > F2 = 1
             * F1 == F2 = 0;
             */
            if (Float.compare(value, 0F) == 0) {
                itemProperties.remove(string);
            } else {
                itemProperties.putFloat(string, value);
            }
        }
    }

    // Doubles --------------------------------------------------------------------------------------------------------
    /**
     * Bouncer for succinctness. Checks the itemStack's modular properties and
     * returns the getValue if it exists, otherwise 0.
     */
    public static double getModuleDoubleOrZero(@Nonnull ItemStack stack, String string) {
        return getDoubleOrZero(getModuleTag(stack), string);
    }

    public static double getModularItemDoubleOrZero(@Nonnull ItemStack stack, String string) {
        return getDoubleOrZero(getMuseItemTag(stack), string);
    }

    public static double getDoubleOrZero(CompoundTag nbt, String tagName) {
        return (nbt.contains(tagName,Tag.TAG_DOUBLE) ? nbt.getDouble(tagName) : 0);
    }

    /**
     * Sets the given itemstack's modular property, or removes it if the getValue
     * would be zero.
     */
    public static void setModularItemDoubleOrRemove(@Nonnull ItemStack stack, String string, double value) {
        setDoubleOrRemove(TagUtils.getMuseItemTag(stack), string, value);
    }

    public static void setModuleDoubleOrRemove(@Nonnull ItemStack stack, String string, double value) {
        setDoubleOrRemove(TagUtils.getModuleTag(stack), string, value);
    }

    /**
     * Sets the getValue of the given nbt tag, or removes it if the getValue would be
     * zero.
     */
    public static void setDoubleOrRemove(CompoundTag itemProperties, String string, double value) {
        if (itemProperties != null) {
            if (value == 0) {
                itemProperties.remove(string);
            } else {
                itemProperties.putDouble(string, value);
            }
        }
    }

    // Integers -------------------------------------------------------------------------------------------------------
    /**
     * Bouncer for succinctness. Checks the itemStack's modular properties and
     * returns the getValue if it exists, otherwise 0.
     */
    public static int getModuleIntOrZero(@Nonnull ItemStack module, String string) {
        return getIntOrZero(getModuleTag(module), string);
    }

    public static int getModularItemIntOrZero(@Nonnull ItemStack module, String string) {
        return getIntOrZero(getMuseItemTag(module), string);
    }

    static int getIntOrZero(CompoundTag nbt, String tagName) {
        return (nbt.contains(tagName, Tag.TAG_INT) ? nbt.getInt(tagName) : 0);
    }

    public static void setModuleIntOrRemove(@Nonnull ItemStack stack, String tagName, int value, boolean remove) {
        setIntOrRemove(getModuleTag(stack), tagName, value, remove);
    }

    public static void setModularItemIntOrRemove(@Nonnull ItemStack stack, String tagName, int value, boolean remove) {
        setIntOrRemove(getMuseItemTag(stack), tagName, value, remove);
    }

    public static void setIntOrRemove(@Nonnull CompoundTag nbt, String tagName, int value, boolean remove) {
        if (value == 0 && remove)
            nbt.remove(tagName);
        else
            nbt.putInt(tagName, value);
    }

    // Boolean --------------------------------------------------------------------------------------------------------
    public static boolean getModuleBooleanOrSetDefault(@Nonnull ItemStack module, String tagName, boolean defBool) {
        CompoundTag moduleTag = getModuleTag(module);
        if (moduleTag.contains(tagName, Tag.TAG_BYTE)) {
            return getBooleanOrFalse(moduleTag, tagName);
        } else {
            moduleTag.putBoolean(tagName, defBool);
            return defBool;
        }
    }

    public static boolean getModuleBooleanOrFalse(@Nonnull ItemStack module, String string) {
        return getBooleanOrFalse(getModuleTag(module), string);
    }

    public static boolean getItemBooleanOrFalse(@Nonnull ItemStack module, String string) {
        return getBooleanOrFalse(getMuseItemTag(module), string);
    }

    static boolean getBooleanOrFalse(CompoundTag nbt, String tagName) {
        return (nbt.contains(tagName, Tag.TAG_BYTE) ? nbt.getBoolean(tagName) : false);
    }

    public static void setModuleBoolean(@Nonnull ItemStack module, String string, boolean value) {
        getModuleTag(module).putBoolean(string, value);
    }

    public static void setModularItemBoolean(@Nonnull ItemStack module, String string, boolean value) {
        getMuseItemTag(module).putBoolean(string, value);
    }

    // Misc -----------------------------------------------------------------------------------------------------------
    public static void setModuleResourceLocation(@Nonnull ItemStack module, String string, ResourceLocation value) {
        getModuleTag(module).putString(string, value.toString());
    }

    public static Optional<ResourceLocation> getModuleResourceLocation(@Nonnull ItemStack module, String string) {
        CompoundTag moduleTag = getModuleTag(module);
        if (moduleTag.contains(string, Tag.TAG_STRING)) {
            return Optional.of(new ResourceLocation(moduleTag.getString(string)));
        }
        return Optional.empty();
    }
}