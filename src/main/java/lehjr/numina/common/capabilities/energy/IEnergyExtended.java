package lehjr.numina.common.capabilities.energy;

import net.minecraft.nbt.Tag;
import net.neoforged.neoforge.common.util.INBTSerializable;
import net.neoforged.neoforge.energy.IEnergyStorage;

public interface IEnergyExtended extends IEnergyStorage, INBTSerializable<Tag> {
    int getMaxTransfer();
}
