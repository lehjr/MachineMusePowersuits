package com.lehjr.numina.common.utils;

import com.lehjr.numina.common.constants.NuminaConstants;
import com.lehjr.numina.common.math.Color;
import com.lehjr.numina.common.registration.NuminaCodecs;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

public class TagUtils {
    static CompoundTag getTag(ItemStack stack, DataComponentType<CompoundTag> dataComponent, String tagKey) {
        if (stack.isEmpty()) {
            return new CompoundTag();
        }

        if (stack.has(dataComponent)) {
            @Nullable var data = stack.getOrDefault(dataComponent, new CompoundTag());
            CompoundTag tag = data.copy();
            if (tag.contains(tagKey, Tag.TAG_COMPOUND)) {
                return tag.getCompound(tagKey);
            }
        }
        return new CompoundTag();
    }

    static ItemStack setTag(@Nonnull ItemStack stack, DataComponentType<CompoundTag> dataComponent, String tagKey, @Nonnull CompoundTag nbt) {
        CompoundTag tagCompound = getTag(stack, dataComponent, tagKey);
        tagCompound.put(tagKey, nbt);
        stack.set(dataComponent, tagCompound);
        return stack;
    }

    // Module ---------------------------------------------------------------------------------------------------------
    public static CompoundTag getModuleTag(@Nonnull ItemStack module) {
        return getTag(module, NuminaCodecs.POWERMODULE_ITEM_CODEC, NuminaConstants.MODULES_TAG);
    }

    public static ItemStack setModuleTag(@Nonnull ItemStack module, @Nonnull CompoundTag nbt) {
        return setTag(module, NuminaCodecs.POWERMODULE_ITEM_CODEC, NuminaConstants.MODULES_TAG, nbt);
    }

    public static String getModuleString(@Nonnull ItemStack module, String tagName) {
        return getString(getModuleTag(module), tagName);
    }

    public static ItemStack setModuleString(@Nonnull ItemStack module, String tagName, String value) {
        return setModuleTag(module, setString(getModuleTag(module), tagName, value));
    }

    public static float getModuleFloat(@Nonnull ItemStack module, String tagName) {
        return getFloatOrZero(getModuleTag(module), tagName);
    }

    public static ItemStack setModuleFloat(@Nonnull ItemStack module, String tagName, float value) {
        return setModuleTag(module, setFloat(getModuleTag(module), tagName, value));
    }

    public static ItemStack setModuleDouble(@Nonnull ItemStack module, String tagName, double value) {
        CompoundTag tag = getModuleTag(module);
        return setModuleTag(module, setDouble(tag, tagName, value));
    }

    public static double getModuleDouble(@Nonnull ItemStack module, String tagName) {
        return getDoubleOrZero(getModuleTag(module), tagName);
    }

    public static ItemStack setModuleInt(@Nonnull ItemStack module, String tagName, int value) {
        CompoundTag tag = getModuleTag(module);
        return setModuleTag(module, setInt(tag, tagName, value));
    }

    public static int getModuleInt(@Nonnull ItemStack module, String tagName) {
        return getIntOrZero(getModuleTag(module), tagName);
    }

    public static ItemStack setModuleBoolean(@Nonnull ItemStack module, String tagName, boolean value) {
        CompoundTag tag = getModuleTag(module);
        return setModuleTag(module, setBool(tag, tagName, value));
    }

    public static boolean getModuleIsOnline(@Nonnull ItemStack module) {
        var tmp =  module.getOrDefault(NuminaCodecs.ONLINE, false);
        if (tmp instanceof Boolean ret) {
            return ret;
        }
        return false;
    }

    public static ItemStack setModuleIsOnline(@Nonnull ItemStack module, boolean isOnline) {
        module.set(NuminaCodecs.ONLINE, isOnline);
        return module;
    }

    public static boolean getModuleBoolean(@Nonnull ItemStack module, String tagName) {
        return getBool(getModuleTag(module), tagName);
    }

    public static ItemStack setModuleBlockState(@Nonnull ItemStack stack, BlockState mimicBlock) {
        CompoundTag tagCompound = getModuleTag(stack);
        CompoundTag nbt = NbtUtils.writeBlockState(mimicBlock);
        tagCompound.put("blockState", nbt);
        return setModuleTag(stack, tagCompound);
    }

    public static BlockState getModuleBlockState(@Nonnull ItemStack stack) {
        CompoundTag tagCompound = getModuleTag(stack);
        if(tagCompound.contains("blockState")) {
            return NbtUtils.readBlockState(BuiltInRegistries.BLOCK.asLookup(), tagCompound.getCompound("blockState"));
        }
        return Blocks.AIR.defaultBlockState();
    }

    public static void setModuleResourceLocation(@Nonnull ItemStack module, String string, ResourceLocation value) {
//        CompoundTag tagCompound = new CompoundTag();
//        CompoundTag nbt = NbtUtils.writeBlockState(mimicBlock);
//        tagCompound.put("mimic", nbt);
//        item.set(DataComponents.CUSTOM_DATA, CustomData.of(tagCompound.copy()));
//        if (value == null) {
//            value = ResourceLocation.fromNamespaceAndPath("air");
//        }
//        getTag(module).putString(string, value.toString());
    }

    public static Optional<ResourceLocation> getModuleResourceLocation(@Nonnull ItemStack module, String string) {
//        CompoundTag moduleTag = getTag(module);
//        if (moduleTag.contains(string, Tag.TAG_STRING)) {
//            return Optional.of(ResourceLocation.fromNamespaceAndPath(moduleTag.getString(string)));
//        }
        return Optional.empty();
    }

    // Modular Item ---------------------------------------------------------------------------------------------------
    public static CompoundTag getModularItemTag(@Nonnull ItemStack stack) {
        return getTag(stack, NuminaCodecs.MODULAR_ITEM_CODEC, NuminaConstants.MODULAR_ITEM_TAG);
    }

    public static ItemStack setModularItemTag(@Nonnull ItemStack stack, @Nonnull CompoundTag nbt) {
        return setTag(stack, NuminaCodecs.MODULAR_ITEM_CODEC, NuminaConstants.MODULAR_ITEM_TAG, nbt);
    }

    public static float getModularItemFloat(@Nonnull ItemStack stack, String tagName) {
        return getFloatOrZero(getModularItemTag(stack), tagName);
    }

    public static ItemStack setModularItemFloat(@Nonnull ItemStack stack, String tagName, float value) {
        CompoundTag tag = getModularItemTag(stack);
        return setModularItemTag(stack, setFloat(tag, tagName, value));
    }

    public static double getModularItemDouble(@Nonnull ItemStack stack, String tagName) {
        return getDoubleOrZero(getModularItemTag(stack), tagName);
    }

    public static ItemStack setModularItemDouble(@Nonnull ItemStack stack, String tagName, double value) {
        CompoundTag tag = getModularItemTag(stack);
        return setModularItemTag(stack, setDouble(tag, tagName, value));
    }

    public static int getModularItemInt(@Nonnull ItemStack stack, String tagName) {
        return getIntOrZero(getModularItemTag(stack), tagName);
    }

    public static ItemStack setModularItemInt(@Nonnull ItemStack stack, String tagName, int value) {
        CompoundTag tag = getModularItemTag(stack);
        return setModularItemTag(stack, setInt(tag, tagName, value));
    }

    public static int getModularItemIntOrDefault(@Nonnull ItemStack stack, String tagName, int defVal) {
        return getIntOrDefault(getModularItemTag(stack), tagName, defVal);
    }

    public static ItemStack setModularItemBoolean(@Nonnull ItemStack stack, String tagName, boolean value) {
        CompoundTag tag = getModularItemTag(stack);
        tag = setBool(tag, tagName, value);
        return setModularItemTag(stack, tag);
    }

    public static boolean getModularItemBoolean(@Nonnull ItemStack stack, String tagName) {
        return getBool(getModularItemTag(stack), tagName);
    }

    // Render ---------------------------------------------------------------------------------------------------------
    public static CompoundTag getRenderTag(@Nonnull ItemStack stack) {
        return getTag(stack, NuminaCodecs.MODEL_SPEC_ITEM_CODEC, NuminaConstants.RENDER_TAG);
    }

    public static ItemStack setRenderTag(@Nonnull ItemStack stack, @Nonnull CompoundTag nbt) {
        return setTag(stack, NuminaCodecs.MODEL_SPEC_ITEM_CODEC, NuminaConstants.RENDER_TAG, nbt);
    }

    // Color ---------------------------------------------------------------------------------------------------------
    public static Color getColorOrDefault(@Nonnull ItemStack itemStack, Color color) {
        if (itemStack.has(NuminaCodecs.COLOR)) {
            var colorInt = itemStack.get(NuminaCodecs.COLOR);
            if (colorInt != null) {
                return new Color(colorInt);
            }
        }
        return color;
    }

    public static Color getColor(@Nonnull ItemStack itemStack) {
        return getColorOrDefault(itemStack, Color.WHITE);
    }

    public static ItemStack setColor(@Nonnull ItemStack itemStack, Color color) {
        int colorInt = color.getARGBInt();
        itemStack.set(NuminaCodecs.COLOR, colorInt);
        return itemStack;
    }

    // Heat -----------------------------------------------------------------------------------------------------------
    public static double getHeat(@Nonnull ItemStack itemStack) {
        var tmp =  itemStack.getOrDefault(NuminaCodecs.HEAT, 0);
        if (tmp instanceof Double ret) {
            return ret;
        }
        return 0;
    }

    public static ItemStack setHeat(@Nonnull ItemStack itemStack, double heat) {
        itemStack.set(NuminaCodecs.HEAT, heat);
        return itemStack;
    }

    // String --------------------------------------------------------------------------------------------------------
    public static String getString(@Nonnull CompoundTag nbt, String tagName) {
        if(nbt.contains(tagName, Tag.TAG_STRING)) {
            return nbt.getString(tagName);
        }
        return "";
    }

    public static CompoundTag setString(@Nonnull CompoundTag nbt, String tagName, String value) {
        nbt.putString(tagName, value);
        return nbt;
    }

    // Float ---------------------------------------------------------------------------------------------------------
    public static float getFloatOrZero(@Nonnull CompoundTag nbt, String tagName) {
        if(nbt.contains(tagName, Tag.TAG_FLOAT)) {
            return nbt.getFloat(tagName);
        }
        return 0;
    }

    public static CompoundTag setFloat(@Nonnull CompoundTag nbt, String tagName, float value) {
        nbt.putFloat(tagName, value);
        return nbt;
    }

    // Double ---------------------------------------------------------------------------------------------------------
    public static double getDoubleOrZero(@Nonnull CompoundTag nbt, String tagName) {
        if(nbt.contains(tagName, Tag.TAG_DOUBLE)) {
            return nbt.getDouble(tagName);
        }
        return 0;
    }

    public static CompoundTag setDouble(@Nonnull CompoundTag nbt, String tagName, double value) {
        nbt.putDouble(tagName, value);
        return nbt;
    }

    // Integer --------------------------------------------------------------------------------------------------------
    public static int getIntOrDefault(@Nonnull CompoundTag nbt, String tagName, int defVal) {
        if(nbt.contains(tagName, Tag.TAG_INT)) {
            return nbt.getInt(tagName);
        }
        return defVal;
    }

    public static int getIntOrZero(@Nonnull CompoundTag nbt, String tagName) {
        return getIntOrDefault(nbt, tagName, 0);
    }

    public static CompoundTag setInt(@Nonnull CompoundTag nbt, String tagName, int value) {
        nbt.putInt(tagName, value);
        return nbt;
    }

    // Boolean --------------------------------------------------------------------------------------------------------
    public static boolean getBool(@Nonnull CompoundTag nbt, String tagName) {
        if(nbt.contains(tagName, Tag.TAG_BYTE)) {
            return nbt.getBoolean(tagName);
        }
        return false;
    }

    public static CompoundTag setBool(@Nonnull CompoundTag nbt, String tagName, boolean value) {
        nbt.putBoolean(tagName, value);
        return nbt;
    }
}
