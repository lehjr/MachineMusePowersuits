package com.lehjr.numina.common.capabilities.render.highlight;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;

public class HighLightCapability {
    public static final Capability<IHighlight> HIGHLIGHT = CapabilityManager.get(new CapabilityToken<>(){});;

    public static void register(RegisterCapabilitiesEvent event)
    {
        event.register(IHighlight.class);
    }
}