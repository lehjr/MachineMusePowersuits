package lehjr.powersuits.client.control;

import lehjr.numina.common.capabilities.inventory.modularitem.IModularItem;
import lehjr.numina.common.network.NuminaPackets;
import lehjr.numina.common.network.packets.ToggleRequestPacket;
import lehjr.powersuits.client.event.RenderEventHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.CapabilityItemHandler;

public class MPSKeyBinding extends KeyBinding {
    public final ResourceLocation registryName;
    public boolean showOnHud = true;
    public boolean toggleval = false;

    public MPSKeyBinding(ResourceLocation registryName, String name, int key, String category) {
        super(name, key, category);
        this.registryName = registryName;
    }

    /**
     * Do not use this
     */
    @Override
    public void setKey(InputMappings.Input key) {
        super.setKey(key);
        KeybindManager.INSTANCE.writeOutKeybindSetings();
        RenderEventHandler.INSTANCE.makeKBDisplayList();
    }

    /**
     * Use this one to set the key from inside MPS
     * @param key
     */
    public void setKeyInternal(InputMappings.Input key) {
        super.setKey(key);
    }


    public void toggleModules() {
        System.out.println("toggling here?");

        ClientPlayerEntity player = Minecraft.getInstance().player;
        if (player == null) {
            return;
        }
    // FIXME: needed client side?
        NuminaPackets.CHANNEL_INSTANCE.sendToServer(new ToggleRequestPacket(registryName, toggleval));
        for (int i = 0; i < player.inventory.getContainerSize(); i++) {
            player.inventory.getItem(i).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
                    .filter(IModularItem.class::isInstance)
                    .map(IModularItem.class::cast)
                    .ifPresent(handler -> handler.toggleModule(registryName, toggleval));
        }
        toggleval = !toggleval;
    }
}