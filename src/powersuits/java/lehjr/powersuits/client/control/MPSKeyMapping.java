package lehjr.powersuits.client.control;

import com.mojang.blaze3d.platform.InputConstants;
import lehjr.numina.common.capabilities.inventory.modularitem.IModularItem;
import lehjr.numina.common.network.NuminaPackets;
import lehjr.numina.common.network.packets.ToggleRequestPacket;
import lehjr.powersuits.client.event.RenderEventHandler;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.capabilities.ForgeCapabilities;

public class MPSKeyMapping extends KeyMapping {
    public final ResourceLocation registryName;
    public boolean showOnHud = true;
    public boolean toggleval = false;

    public MPSKeyMapping(ResourceLocation registryName, String name, int key, String category) {
        super(name, key, category);
        this.registryName = registryName;
    }

    public MPSKeyMapping(ResourceLocation registryName, String name, int key, String category, boolean showOnHud) {
        super(name, key, category);
        this.registryName = registryName;
        this.showOnHud = showOnHud;
    }


    /**
     * Do not use this
     */
    @Override
    public void setKey(InputConstants.Key key) {
        super.setKey(key);
        KeybindManager.INSTANCE.writeOutKeybindSetings();
        RenderEventHandler.INSTANCE.makeKBDisplayList();
    }

    /**
     * Use this one to set the key from inside MPS
     * @param key
     */
    public void setKeyInternal(InputConstants.Key key) {
        super.setKey(key);
    }


    public void toggleModules() {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) {
            return;
        }

        NuminaPackets.CHANNEL_INSTANCE.sendToServer(new ToggleRequestPacket(registryName, toggleval));
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            player.getInventory().getItem(i).getCapability(ForgeCapabilities.ITEM_HANDLER)
                    .filter(IModularItem.class::isInstance)
                    .map(IModularItem.class::cast)
                    .ifPresent(handler -> handler.toggleModule(registryName, toggleval));
        }
        toggleval = !toggleval;
    }
}