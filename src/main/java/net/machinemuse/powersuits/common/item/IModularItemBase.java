package net.machinemuse.powersuits.common.item;

import net.machinemuse.numina.common.base.MuseLogger;
import net.machinemuse.numina.common.capabilities.energy.adapter.IMuseElectricItem;
import net.machinemuse.numina.client.render.modelspec.ModelRegistry;
import net.machinemuse.numina.client.render.modelspec.TexturePartSpec;
import net.machinemuse.numina.common.constants.ModelSpecTags;
import net.machinemuse.numina.common.item.IModularItem;
import net.machinemuse.numina.common.math.Colour;
import net.machinemuse.numina.common.module.IModuleManager;
import net.machinemuse.numina.common.string.MuseStringUtils;
import net.machinemuse.powersuits.common.base.ModuleManager;
import net.machinemuse.powersuits.common.config.MPSConfig;
import net.machinemuse.powersuits.common.utils.nbt.MPSNBTUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import javax.annotation.Nonnull;

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 7:49 PM, 4/23/13
 * <p>
 * Ported to Java by lehjr on 11/4/16.
 */
public interface IModularItemBase extends IModularItem, IMuseElectricItem {
    @Override
    default double getMaxBaseHeat(@Nonnull ItemStack itemStack) {
        return MPSConfig.INSTANCE.getBaseMaxHeat(itemStack);
    }

    default Colour getColorFromItemStack(@Nonnull ItemStack stack) {
        try {
            NBTTagCompound renderTag = MPSNBTUtils.getMuseRenderTag(stack);
            if (renderTag.hasKey(ModelSpecTags.NBT_TEXTURESPEC_TAG)) {
                TexturePartSpec partSpec = (TexturePartSpec) ModelRegistry.getInstance().getPart(renderTag.getCompoundTag(ModelSpecTags.NBT_TEXTURESPEC_TAG));
                NBTTagCompound specTag = renderTag.getCompoundTag(ModelSpecTags.NBT_TEXTURESPEC_TAG);
                int index = partSpec.getColourIndex(specTag);
                int[] colours = renderTag.getIntArray(ModelSpecTags.TAG_COLOURS);
                if (colours.length > index)
                    return new Colour(colours[index]);
            }
        } catch (Exception e) {
            MuseLogger.logException("something failed here: ", e);
        }
        return Colour.WHITE;
    }

    default String formatInfo(String string, double value) {
        return string + '\t' + MuseStringUtils.formatNumberShort(value);
    }

    @Override
    default IModuleManager getModuleManager() {
        return ModuleManager.INSTANCE;
    }

    default double getArmorDouble(EntityPlayer player, ItemStack stack) {
        return 0;
    }
}