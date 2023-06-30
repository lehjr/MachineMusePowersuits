package net.machinemuse.powersuits.common.network;

import net.machinemuse.numina.common.network.NuminaPackets;
import net.machinemuse.powersuits.common.network.packets.*;
import net.minecraftforge.fml.relauncher.Side;

public class MPSPackets extends NuminaPackets {
    public static void registerMPSPackets() {
        INSTANCE.registerMessage(MPSPacketConfig.Handler.class, MPSPacketConfig.class, 10, Side.CLIENT);
        INSTANCE.registerMessage(MusePacketInstallModuleRequest.Handler.class, MusePacketInstallModuleRequest.class, 11, Side.SERVER);
        INSTANCE.registerMessage(MusePacketSalvageModuleRequest.Handler.class, MusePacketSalvageModuleRequest.class, 12, Side.SERVER);
        INSTANCE.registerMessage(MusePacketColourInfo.Handler.class, MusePacketColourInfo.class, 13, Side.SERVER);
        INSTANCE.registerMessage(MusePacketCosmeticInfo.Handler.class, MusePacketCosmeticInfo.class, 14, Side.SERVER);
        INSTANCE.registerMessage(MusePacketToggleRequest.Handler.class, MusePacketToggleRequest.class, 15, Side.SERVER);
        INSTANCE.registerMessage(MusePacketTweakRequestDouble.Handler.class, MusePacketTweakRequestDouble.class, 16, Side.SERVER);
        INSTANCE.registerMessage(MusePacketCosmeticPresetUpdate.Handler.class, MusePacketCosmeticPresetUpdate.class, 17, Side.SERVER);
        INSTANCE.registerMessage(MusePacketCosmeticPreset.Handler.class, MusePacketCosmeticPreset.class, 18, Side.SERVER);
//        INSTANCE.registerMessage(MusePacketPlayerUpdate.Handler.class, MusePacketPlayerUpdate.class, 19, Side.SERVER);
        INSTANCE.registerMessage(MusePacketPlayerUpdate.Handler.class, MusePacketPlayerUpdate.class, 20, Side.SERVER);
    }
}