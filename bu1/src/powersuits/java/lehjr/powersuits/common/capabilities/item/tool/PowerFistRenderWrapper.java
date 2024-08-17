package lehjr.powersuits.common.capabilities.item.tool;

import com.lehjr.numina.common.capabilities.render.modelspec.*;
import com.lehjr.numina.common.constants.NuminaConstants;
import lehjr.powersuits.common.item.electric.tool.PowerFist;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntArrayTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class PowerFistRenderWrapper extends ModelSpecStorage implements IHandHeldEntitySpecNBT {
    public PowerFistRenderWrapper(@Nonnull ItemStack itemStackIn) {
        super(itemStackIn);
    }

    @Override
    public CompoundTag getDefaultRenderTag() {
        if (getItemStack().isEmpty())
            return new CompoundTag();

        List<CompoundTag> prefArray = new ArrayList<>();

        // ModelPartSpecs
        ListTag specList = new ListTag();

        // TextureSpecBase (only one texture visible at a time)
        CompoundTag texSpecTag = new CompoundTag();

        // List of EnumColor indexes
        List<Integer> colors = new ArrayList<>();

        // temp data holder
        CompoundTag tempNBT;

        EquipmentSlot slot = getItemStack().getEquipmentSlot();

        for (SpecBase spec : NuminaModelSpecRegistry.getInstance().getSpecs()) {
            // Only generate NBT data from Specs marked as "default"
            if (spec.isDefault()) {
                if (getItemStack().getItem() instanceof PowerFist && (spec.getSpecType().equals(SpecType.HANDHELD_OBJ_MODEL) || spec.getSpecType().equals(SpecType.HANDHELD_JAVA_MODEL))) {
                    colors = addNewColorstoList(colors, spec.getColors()); // merge new color int arrays in

                    for (PartSpecBase partSpec : spec.getPartSpecs()) {
//                        if (partSpec instanceof ModelPartSpec) {
                        prefArray.add(partSpec.multiSet(new CompoundTag(),
                                getNewColorIndex(colors, spec.getColors(), partSpec.getDefaultColorIndex()),
                                partSpec.getGlow()));
//                        }
                    }
                }
            }
        }

        CompoundTag nbt = new CompoundTag();
        for (CompoundTag elem : prefArray) {
            nbt.put(elem.getString(NuminaConstants.MODEL) + "." + elem.getString(NuminaConstants.PART), elem);
        }

        if (!specList.isEmpty())
            nbt.put(NuminaConstants.SPECLIST, specList);

//        if (!texSpecTag.isEmpty())
//            nbt.put(NuminaConstants.TEXTURESPEC, texSpecTag);

        nbt.put(NuminaConstants.COLORS, new IntArrayTag(colors));
        return nbt;
    }
}
