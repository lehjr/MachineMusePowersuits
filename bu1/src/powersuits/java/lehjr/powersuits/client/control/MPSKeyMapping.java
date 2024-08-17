package lehjr.powersuits.client.control;

import com.mojang.blaze3d.platform.InputConstants;
import com.lehjr.numina.common.capabilities.NuminaCapabilities;
import com.lehjr.numina.common.capabilities.inventory.modularitem.IModularItem;
import com.lehjr.numina.common.network.NuminaPackets;
import com.lehjr.numina.common.network.packets.serverbound.ToggleRequestPacketServerBound;
import lehjr.powersuits.client.overlay.MPSOverlay;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public class MPSKeyMapping extends KeyMapping {
    public final ResourceLocation registryName;
    public boolean showOnHud = true;
    public boolean toggleVal = false; // fixme: get value on load?

    public MPSKeyMapping(ResourceLocation registryName, String name, int key, String category, boolean showOnHud) {
        super(name, key, category);
        this.registryName = registryName;
        this.showOnHud = showOnHud;
        initToggleVal();
    }

    /**
     * Do not use this
     */
    @Override
    public void setKey(InputConstants.Key key) {
        super.setKey(key);
        KeyMappingReaderWriter.INSTANCE.writeOutKeybindSetings();
        MPSOverlay.makeKBDisplayList();
    }

    /**
     * Use this one to set the key from inside MPS
     * @param key
     */
    public void setKeyInternal(InputConstants.Key key) {
        super.setKey(key);
    }

    void initToggleVal() {
        if (Minecraft.getInstance().player != null) {
            Player player = Minecraft.getInstance().player;
            for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
                if (NuminaCapabilities.getCapability(player.getInventory().getItem(i),
                                NuminaCapabilities.Inventory.MODULAR_ITEM)
                        .map(handler -> handler.isModuleOnline(registryName)).orElse(false)) {
                        toggleVal = true;
                        break;
                }
            }
        }
    }

    public void toggleModules() {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) {
            return;
        }

        NuminaPackets.sendToServer(new ToggleRequestPacketServerBound(registryName, toggleVal));
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            IModularItem modularItem = NuminaCapabilities.getModularItemOrModeChangingCapability(player.getInventory().getItem(i));
            if (modularItem != null) {
                modularItem.toggleModule(registryName, toggleVal);
            }
        }
        toggleVal = !toggleVal;
    }
}