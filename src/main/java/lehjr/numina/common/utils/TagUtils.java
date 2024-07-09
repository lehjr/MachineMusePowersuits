package lehjr.numina.common.utils;

import lehjr.numina.common.base.NuminaObjects;
import lehjr.numina.common.constants.NuminaConstants;
import lehjr.numina.common.math.Color;
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
        CompoundTag tagCompound = new CompoundTag();
        tagCompound.put(tagKey, nbt);
        stack.set(dataComponent, tagCompound);
        return stack;
    }

    // Module ---------------------------------------------------------------------------------------------------------
    public static CompoundTag getModuleTag(@Nonnull ItemStack module) {
        return getTag(module, NuminaObjects.POWERMODULE_ITEM_CODEC, NuminaConstants.MODULES_TAG);
    }

    public static ItemStack setModuleTag(@Nonnull ItemStack module, @Nonnull CompoundTag nbt) {
        return setTag(module, NuminaObjects.POWERMODULE_ITEM_CODEC, NuminaConstants.MODULES_TAG, nbt);
    }

    public static ItemStack setModuleDouble(@Nonnull ItemStack module, String tagName, double value) {
        CompoundTag tag = getModuleTag(module);
        tag = setDouble(tag, tagName, value);
        return setModuleTag(module, tag);
    }

    public static double getModuleDouble(@Nonnull ItemStack module, String tagName) {
        return getDoubleOrZero(getModuleTag(module), tagName);
    }

    public static ItemStack setModuleInt(@Nonnull ItemStack module, String tagName, int value) {
        CompoundTag tag = getModuleTag(module);
        tag = setInt(tag, tagName, value);
        return setModuleTag(module, tag);
    }

    public static int getModuleInt(@Nonnull ItemStack module, String tagName) {
        return getIntOrZero(getModuleTag(module), tagName);
    }

    public static ItemStack setModuleBoolean(@Nonnull ItemStack module, String tagName, boolean value) {
        CompoundTag tag = getModuleTag(module);
        tag = setBool(tag, tagName, value);
        return setModuleTag(module, tag);
    }

    public static boolean getModuleBoolean(@Nonnull ItemStack module, String tagName) {
        return getBool(getModuleTag(module), tagName);
    }

    public static ItemStack setModuleBlockState(@Nonnull ItemStack stack, BlockState mimicBlock) {
        CompoundTag tagCompound = getModuleTag(stack);
        CompoundTag nbt = NbtUtils.writeBlockState(mimicBlock);
        tagCompound.put("blockState", nbt);
        return setModuleTag(stack, nbt);
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
//            value = new ResourceLocation("air");
//        }
//        getTag(module).putString(string, value.toString());
    }

    public static Optional<ResourceLocation> getModuleResourceLocation(@Nonnull ItemStack module, String string) {
//        CompoundTag moduleTag = getTag(module);
//        if (moduleTag.contains(string, Tag.TAG_STRING)) {
//            return Optional.of(new ResourceLocation(moduleTag.getString(string)));
//        }
        return Optional.empty();
    }

    // Modular Item ---------------------------------------------------------------------------------------------------
    public static CompoundTag getModularItemTag(@Nonnull ItemStack stack) {
        return getTag(stack, NuminaObjects.MODULAR_ITEM_CODEC, NuminaConstants.MODULAR_ITEM_TAG);
    }

    public static ItemStack setModularItemTag(@Nonnull ItemStack stack, @Nonnull CompoundTag nbt) {
        return setTag(stack, NuminaObjects.MODULAR_ITEM_CODEC, NuminaConstants.MODULAR_ITEM_TAG, nbt);
    }

    public static ItemStack setModularItemDouble(@Nonnull ItemStack stack, String tagName, double value) {
        CompoundTag tag = getModularItemTag(stack);
        tag = setDouble(tag, tagName, value);
        return setModularItemTag(stack, tag);
    }

    public static double getModularItemDouble(@Nonnull ItemStack stack, String tagName) {
        return getDoubleOrZero(getModularItemTag(stack), tagName);
    }

    public static ItemStack setModularItemInt(@Nonnull ItemStack stack, String tagName, int value) {
        CompoundTag tag = getModularItemTag(stack);
        tag = setInt(tag, tagName, value);
        return setModularItemTag(stack, tag);
    }

    public static int getModularItemIntOrDefault(@Nonnull ItemStack stack, String tagName, int defVal) {
        return getIntOrDefault(getModularItemTag(stack), tagName, defVal);
    }

    public static int getModularItemInt(@Nonnull ItemStack stack, String tagName) {
        return getIntOrZero(getModularItemTag(stack), tagName);
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
        return getTag(stack, NuminaObjects.MODEL_SPEC_ITEM_CODEC, NuminaConstants.RENDER_TAG);
    }

    public static ItemStack setRenderTag(@Nonnull ItemStack stack, @Nonnull CompoundTag nbt) {
        return setTag(stack, NuminaObjects.MODEL_SPEC_ITEM_CODEC, NuminaConstants.RENDER_TAG, nbt);
    }

    // Color ---------------------------------------------------------------------------------------------------------
    public static Color getColorOrDefault(@Nonnull ItemStack itemStack, Color color) {
        if (itemStack.has(NuminaObjects.COLOR)) {
            var colorInt = itemStack.get(NuminaObjects.COLOR);
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
        itemStack.set(NuminaObjects.COLOR, colorInt);
        return itemStack;
    }

    // Heat -----------------------------------------------------------------------------------------------------------
    public static double getHeat(@Nonnull ItemStack itemStack) {
        var tmp =  itemStack.getOrDefault(NuminaObjects.HEAT, 0);
        if (tmp instanceof Double ret) {
            return ret;
        }
        return 0;
    }

    public static ItemStack setHeat(@Nonnull ItemStack itemStack, double heat) {
        itemStack.set(NuminaObjects.HEAT, heat);
        return itemStack;
    }

    // Double ---------------------------------------------------------------------------------------------------------
    public static double getDoubleOrZero(CompoundTag nbt, String tagName) {
        if(nbt != null && nbt.contains(tagName, Tag.TAG_DOUBLE)) {
            return nbt.getDouble(tagName);
        }
        return 0;
    }

    public static CompoundTag setDouble(CompoundTag nbt, String tagName, double value) {
        if(nbt != null) {
            nbt.putDouble(tagName, value);
        }
        return nbt;
    }

    // Integer --------------------------------------------------------------------------------------------------------
    public static int getIntOrDefault(CompoundTag nbt, String tagName, int defVal) {
        if(nbt != null && nbt.contains(tagName, Tag.TAG_INT)) {
            return nbt.getInt(tagName);
        }
        return defVal;
    }

    public static int getIntOrZero(CompoundTag nbt, String tagName) {
        return getIntOrDefault(nbt, tagName, 0);
    }

    public static CompoundTag setInt(CompoundTag nbt, String tagName, int value) {
        if(nbt != null) {
            nbt.putInt(tagName, value);
        }
        return nbt;
    }

    // Boolean --------------------------------------------------------------------------------------------------------
    public static boolean getBool(CompoundTag nbt, String tagName) {
        if(nbt != null && nbt.contains(tagName, Tag.TAG_BYTE)) {
            return nbt.getBoolean(tagName);
        }
        return false;
    }

    public static CompoundTag setBool(CompoundTag nbt, String tagName, boolean value) {
        if(nbt != null) {
            nbt.putBoolean(tagName, value);
        }
        return nbt;
    }
}