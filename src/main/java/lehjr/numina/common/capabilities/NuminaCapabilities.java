package lehjr.numina.common.capabilities;

import lehjr.numina.common.capabilities.heat.IHeatStorage;
import lehjr.numina.common.capabilities.module.powermodule.IPowerModule;
import lehjr.numina.common.capabilities.player.keystates.IPlayerKeyStates;
import lehjr.numina.common.capabilities.render.IModelSpec;
import lehjr.numina.common.capabilities.render.chameleon.IChameleon;
import lehjr.numina.common.capabilities.render.color.IColorTag;
import lehjr.numina.common.capabilities.render.highlight.IHighlight;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

public class NuminaCapabilities {
    public static final Capability<IHeatStorage> HEAT = CapabilityManager.get(new CapabilityToken<>(){});

    public static final Capability<IColorTag> COLOR = CapabilityManager.get(new CapabilityToken<>(){});

    public static final Capability<IPowerModule> POWER_MODULE = CapabilityManager.get(new CapabilityToken<>(){});

    public static final Capability<IModelSpec> RENDER = CapabilityManager.get(new CapabilityToken<>(){});

    public static final Capability<IHighlight> HIGHLIGHT = CapabilityManager.get(new CapabilityToken<>(){});

    public static final Capability<IChameleon> CHAMELEON = CapabilityManager.get(new CapabilityToken<>(){});

    public static final Capability<IPlayerKeyStates> PLAYER_KEYSTATES = CapabilityManager.get(new CapabilityToken<>(){});

//    public static final Capability<IPlayerHandStorage> PLAYER_HAND_STORAGE = CapabilityManager.get(new CapabilityToken<>(){});
}
