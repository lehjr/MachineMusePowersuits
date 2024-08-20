package com.example.mod3;

import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

@Mod("testmod3")
public class Mod3Entrypoint {
    public Mod3Entrypoint(ModContainer container) {
        System.out.println(container.getModId() + " initialised!");
    }
}
