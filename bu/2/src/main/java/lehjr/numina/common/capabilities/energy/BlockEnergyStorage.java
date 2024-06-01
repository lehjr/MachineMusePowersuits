package lehjr.numina.common.capabilities.energy;

import lehjr.numina.common.capabilities.CapabilityUpdate;
import net.neoforged.neoforge.energy.EnergyStorage;

/**
 * Energy handler for block entity itself
 */
public class BlockEnergyStorage extends EnergyStorage implements CapabilityUpdate {

    public BlockEnergyStorage(int capacity, int maxTransfer) {
        super(capacity, maxTransfer);
        loadCapValues(); // FIXME
    }

    @Override
    public void loadCapValues() {

    }

    @Override
    public void onValueChanged() {

    }

    public void setEnergy(int energy) {
        this.energy = energy;
        onValueChanged();
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        int recieved =  super.receiveEnergy(maxReceive, simulate);
        if (recieved > 0) {
            onValueChanged();
        }
        return recieved;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        int extracted = super.extractEnergy(maxExtract, simulate);
        if(extracted > 0) {
            onValueChanged();
        }
        return extracted;
    }
}
