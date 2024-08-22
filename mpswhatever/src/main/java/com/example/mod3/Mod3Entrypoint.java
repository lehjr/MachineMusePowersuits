package com.example.mod3;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

@Mod("mpswhatever")
public class Mod3Entrypoint {
    public Mod3Entrypoint(IEventBus modEventBus, ModContainer container) {
        System.out.println(container.getModId() + " initialised!");
    }
}
