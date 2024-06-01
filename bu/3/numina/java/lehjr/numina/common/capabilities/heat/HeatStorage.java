package lehjr.numina.common.capabilities.heat;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.DoubleTag;
import net.minecraft.nbt.Tag;
import net.neoforged.neoforge.common.util.INBTSerializable;
import org.jetbrains.annotations.UnknownNullability;

public abstract class HeatStorage implements IHeatStorage, INBTSerializable<Tag> {
    protected double heat;
    protected double capacity; // this is just a safety boundary, not an absolute cap
    protected double maxReceive;
    protected double maxExtract;

    public HeatStorage(double capacity) {
        this(capacity, capacity, capacity, 0);
    }

    public HeatStorage(double capacity, double maxTransfer) {
        this(capacity, maxTransfer, maxTransfer, 0);
    }

    public HeatStorage(double capacity, double maxReceive, double maxExtract) {
        this(capacity, maxReceive, maxExtract, 0);
    }

    public HeatStorage(double capacity, double maxReceive, double maxExtract, double heat) {
        this.capacity = capacity;
        this.maxReceive = maxReceive;
        this.maxExtract = maxExtract;
        this.heat = heat;
    }

    @Override
    public double receiveHeat(double maxReceive, boolean simulate) {
        if (!canReceive()) {
            return 0;
        }

        double heatReceived = Math.min(capacity - heat, Math.min(this.maxReceive, maxReceive));
        if (!simulate && heatReceived !=0) {
            heat += heatReceived;
        }
        return heatReceived;
    }

    @Override
    public double extractHeat(double maxExtract, boolean simulate) {
        if (!canExtract()) {
            return 0;
        }

        double heatExtracted = Math.min(heat, maxExtract);
        if (!simulate && heatExtracted != 0) {
            heat -= heatExtracted;
        }
        return heatExtracted;
    }

    @Override
    public double getHeatStored() {
        return heat;
    }

    @Override
    public double getMaxHeatStored() {
        return capacity;
    }

    @Override
    public boolean canExtract() {
        return this.heat > 0;
    }

    @Override
    public boolean canReceive() {
        return this.maxReceive > 0;
    }

    @Override
    public void setHeatCapacity(double maxHeat) {
        this.capacity = maxHeat;
    }

    @Override
    public @UnknownNullability Tag serializeNBT(HolderLookup.Provider provider) {
        return DoubleTag.valueOf(this.getHeatStored());
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, Tag nbt) {
        if (!(nbt instanceof DoubleTag doubleNbt)) {
            throw new IllegalArgumentException("Can not deserialize to an instance that isn't the default implementation");
        }
        this.heat = doubleNbt.getAsDouble();
    }
}
