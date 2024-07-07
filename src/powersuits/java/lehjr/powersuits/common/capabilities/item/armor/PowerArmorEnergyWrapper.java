package lehjr.powersuits.common.capabilities.item.armor;

import com.mojang.datafixers.util.Pair;
import lehjr.numina.common.capability.energy.AbstractModularItemEnergyWrapper;
import lehjr.numina.common.capability.module.powermodule.ModuleCategory;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;

import java.util.ArrayList;
import java.util.List;

public class PowerArmorEnergyWrapper extends AbstractModularItemEnergyWrapper {
    public PowerArmorEnergyWrapper(ItemStack itemStack) {
        super(itemStack);
    }

    @Override
    public List<IEnergyStorage> getInternalEnergyStorage() {
        return getModularItemCap().map(iModularItem -> {
            List<IEnergyStorage> ret = new ArrayList<>();
            Pair<Integer, Integer> range = iModularItem.getRangeForCategory(ModuleCategory.ENERGY_STORAGE);
            if(range != null) {
                for (int j = range.getFirst(); j < range.getSecond(); j++) {
                    ItemStack module = iModularItem.getStackInSlot(j);
                    IEnergyStorage energyStorage = module.getCapability(Capabilities.EnergyStorage.ITEM);
                    if (energyStorage != null) {
                        ret.add(energyStorage);
                    }
                }
            }
            return ret;
        }).orElse(List.of());
    }
}
