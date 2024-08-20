package com.lehjr.powersuits;

import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

@Mod("powersuits")
public class ModularPowersuits {
    public ModularPowersuits(ModContainer container) {
        System.out.println(container.getModId() + " initialised!");
    }
}
