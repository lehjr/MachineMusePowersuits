package lehjr.powersuits.client.gui.clickable;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.mojang.blaze3d.matrix.MatrixStack;
import lehjr.numina.client.gui.clickable.ClickableButton2;
import lehjr.numina.client.gui.clickable.ClickableModule;
import lehjr.numina.client.gui.gemoetry.MusePoint2D;
import lehjr.numina.client.render.NuminaRenderer;
import lehjr.numina.common.capabilities.inventory.modularitem.IModularItem;
import lehjr.numina.common.math.Colour;
import lehjr.numina.common.network.NuminaPackets;
import lehjr.numina.common.network.packets.ToggleRequestPacket;
import lehjr.numina.common.string.StringUtils;
import lehjr.powersuits.client.control.KeybindManager;
import lehjr.powersuits.client.control.MPSKeyBinding;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.util.InputMappings;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.CapabilityItemHandler;

import java.util.Set;
import java.util.stream.Collectors;

public class HudKbDisplayBox extends ClickableButton2 {
    BiMap<MPSKeyBinding,ClickableModule> boundModules = HashBiMap.create();
    public boolean toggleval = false;
    public boolean displayOnHUD = false;
    boolean toggled = false;

    InputMappings.Input key;

    public HudKbDisplayBox(InputMappings.Input keyIn) {
        super(keyIn.getDisplayName(), MusePoint2D.ZERO, true);
        this.key = keyIn;
    }

    public void doToggleTick() {
        doToggleIf(boundModules.keySet().stream().filter(mpsKeyBinding -> mpsKeyBinding.consumeClick()).collect(Collectors.toList()).size() > 0 );
    }

    public void doToggleIf(boolean value) {
        if (value && !toggled) {
            toggleModules();
            KeybindManager.INSTANCE.writeOutKeybindSetings();
        }
        toggled = value;
    }

    public void toggleModules() {
        ClientPlayerEntity player = Minecraft.getInstance().player;
        if (player == null) {
            return;
        }

        for (ClickableModule module : boundModules.values()) {
            ResourceLocation registryName = module.getRegName();

            for (int i = 0; i < player.inventory.getContainerSize(); i++) {
                player.inventory.getItem(i).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
                        .filter(IModularItem.class::isInstance)
                        .map(IModularItem.class::cast)
                        .ifPresent(handler -> handler.toggleModule(registryName, toggleval));
            }
            NuminaPackets.CHANNEL_INSTANCE.sendToServer(new ToggleRequestPacket(registryName, toggleval));
        }
        toggleval = !toggleval;
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        for (ClickableModule module : boundModules.values()) {
            NuminaRenderer.drawLineBetween(this, module, Colour.LIGHT_BLUE, 0); // FIXME
            matrixStack.pushPose();
            matrixStack.scale(0.5F, 0.5F, 0.5F);
            matrixStack.translate(0, 0, 100);
            if (displayOnHUD) {
                StringUtils.drawShadowedString(matrixStack, StringUtils.wrapFormatTags("HUD", StringUtils.FormatCodes.BrightGreen), this.centerX() * 2 + 6, this.centerY() * 2 + 6);
            } else {
                StringUtils.drawShadowedString(matrixStack, StringUtils.wrapFormatTags("x", StringUtils.FormatCodes.Red), this.centerX() * 2 + 6, this.centerY() * 2 + 6);
            }
            matrixStack.popPose();
        }
    }

    public InputMappings.Input getKeyBinding() {
        return key;
    }

    public Set<ClickableModule> getBoundModules() {
        return boundModules.values();
    }

    public void bindModule(ClickableModule module, MPSKeyBinding keyBinding) {
        if (!boundModules.containsValue(module) && !boundModules.containsKey(keyBinding)) {
            boundModules.put(keyBinding, module);
        }
    }

    public void unbindModule(ClickableModule module) {
        boundModules.remove(module);
    }


    public void toggleHUDState() {
        displayOnHUD = !displayOnHUD;
    }

    public boolean equals(HudKbDisplayBox other) {
        return other.key.getValue() == this.key.getValue();
    }
}