package com.github.lehjr.numina.util.capabilities.render.chameleon;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class ChameleonCapability {
    @CapabilityInject(IChameleon.class)
    public static Capability<IChameleon> CHAMELEON = null;

    public static void register() {
        CapabilityManager.INSTANCE.register(IChameleon.class, new Capability.IStorage<IChameleon>() {
                    @Override
                    public INBT writeNBT(Capability<IChameleon> capability, IChameleon instance, Direction side) {
                        return instance instanceof Chameleon ? ((Chameleon) instance).serializeNBT() : null;
                    }

                    @Override
                    public void readNBT(Capability<IChameleon> capability, IChameleon instance, Direction side, INBT nbt) {
                        if (!(instance instanceof Chameleon)) {
                            throw new IllegalArgumentException("Can not deserialize to an instance that isn't the default implementation");
                        } else {
                            ((Chameleon) instance).deserializeNBT((StringNBT) nbt);
                        }
                    }
                },
                () -> new Chameleon(ItemStack.EMPTY));
    }
}