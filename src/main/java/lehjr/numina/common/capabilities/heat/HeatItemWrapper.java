package lehjr.numina.common.capabilities.heat;

import lehjr.numina.common.base.NuminaObjects;
import lehjr.numina.common.capabilities.module.powermodule.IPowerModule;
import lehjr.numina.common.constants.NuminaConstants;
import net.minecraft.nbt.DoubleTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.Optional;

public class HeatItemWrapper extends HeatStorage {
    ItemStack stack;
    public HeatItemWrapper(@Nonnull ItemStack stack, double baseMax, Optional<IPowerModule> moduleCap) {
        this(stack, baseMax + moduleCap.map(cap->cap.applyPropertyModifiers(NuminaConstants.MAXIMUM_HEAT)).orElse(0D));
    }

    public HeatItemWrapper(@Nonnull ItemStack stack, double capacity) {
        this(stack, capacity, capacity, capacity);
    }

    public HeatItemWrapper(@Nonnull ItemStack stack, double capacity, double maxTransfer) {
        this(stack, capacity, maxTransfer, maxTransfer);
    }

    public HeatItemWrapper(@Nonnull ItemStack stack, double capacity, double maxReceive, double maxExtract) {
        super(capacity, maxReceive, maxExtract, 0);
        this.stack = stack;
    }

    public void setHeat(int heat) {
        Tag nbt = super.serializeNBT(null);
        if(nbt instanceof DoubleTag nbtDouble) {
            stack.set(NuminaObjects.HEAT, nbtDouble.getAsDouble());
        }
    }
}