package com.lehjr.powersuits.common.capabilities.item.armor;

import com.lehjr.numina.common.capabilities.render.modelspec.*;
import com.lehjr.numina.common.constants.NuminaConstants;
import com.lehjr.numina.common.utils.ItemUtils;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntArrayTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class PowerArmorRenderWrapper extends ModelSpecStorage implements IArmorModelSpecNBT {
    public PowerArmorRenderWrapper(@Nonnull ItemStack itemStackIn) {
        super(itemStackIn);
    }

    @Override
    public NonNullList<SpecBase> getSpecList() {
        CompoundTag renderTag = getRenderTag();
        if (renderTag == null || renderTag.isEmpty()) {
            renderTag = getDefaultRenderTag();
        }
        NonNullList<SpecBase> specs = NonNullList.create();
        for (String key : renderTag.getAllKeys()) {
            if (key.equals(NuminaConstants.COLORS)) {
                continue;
            }
            if (renderTag.get(key) instanceof CompoundTag) {
                SpecBase testSpec = NuminaModelSpecRegistry.getInstance().getModel(renderTag.getCompound(key));
                if (testSpec instanceof ObjModelSpec || testSpec instanceof JavaModelSpec) {
                    specs.add(testSpec);
                }
            }
        }
        return specs;
    }

    @Override
    public CompoundTag getDefaultRenderTag() {
        if (getItemStack().isEmpty()) {
            return new CompoundTag();
        }

        List<CompoundTag> prefArray = new ArrayList<>();

        // ModelPartSpecs
        ListTag specList = new ListTag();

        // List of EnumColor indexes
        List<Integer> colors = new ArrayList<>();

        // temp data holder
        CompoundTag tempNBT;

        EquipmentSlot slot = ItemUtils.getEquipmentSlotForItem(getItemStack());

        for (SpecBase spec : NuminaModelSpecRegistry.getInstance().getSpecs()) {
            // Only generate NBT data from Specs marked as "default"
            if (spec.isDefault()) {
                if (getItemStack().getItem() instanceof ArmorItem) {
                    colors = addNewColorstoList(colors, spec.getColors()); // merge new color int arrays in

                    // Armor Skin
                    if (spec.getSpecType().equals(SpecType.ARMOR_SKIN) && spec.get(slot.getName()) != null) {
                        for (PartSpecBase partSpec : spec.getPartSpecs()) {
                            if (partSpec.getBinding().getSlot() == slot) {
                                prefArray.add(partSpec.multiSet(new CompoundTag(),
                                        getNewColorIndex(colors, spec.getColors(), partSpec.getDefaultColorIndex()),
                                        partSpec.getGlow()));
                            }
                        }
                    }

                    // Armor models
                    else if (spec.getSpecType().equals(SpecType.ARMOR_OBJ_MODEL) /*&& MPSSettings.allowHighPollyArmor()*/) {
                        for (PartSpecBase partSpec : spec.getPartSpecs()) {
                            if (partSpec.getBinding().getSlot() == slot) {
                                /*
                                // jet pack model not displayed by default
                                if (partSpec.binding.getItemState().equals("all") ||
                                        (partSpec.binding.getItemState().equals("jetpack") &&
                                                ModuleManager.INSTANCE.itemHasModule(stack, MPSModuleConstants.MODULE_JETPACK__DATANAME))) { */
                                prefArray.add(partSpec.multiSet(new CompoundTag(),
                                        getNewColorIndex(colors, spec.getColors(), partSpec.getDefaultColorIndex()),
                                        partSpec.getGlow()));
                                /*} */
                            }
                        }
                    }
                }
            }
        }

        CompoundTag nbt = new CompoundTag();
        for (CompoundTag elem : prefArray) {
            nbt.put(elem.getString(NuminaConstants.MODEL) + "." + elem.getString(NuminaConstants.PART), elem);
        }

        if (!specList.isEmpty()) {
            nbt.put(NuminaConstants.SPECLIST, specList);
        }
        nbt.put(NuminaConstants.COLORS, new IntArrayTag(colors));
        return nbt;
    }

    @Override
    public ResourceLocation getArmorTexture(PartSpecBase part) {
        if (part instanceof JavaPartSpec) {
            return ((JavaPartSpec) part).getTextureLocation();
        }
        // TODO: eventually, maybe put armor textures in their own atlas?
        return InventoryMenu.BLOCK_ATLAS;
    }
}
