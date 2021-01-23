package com.github.lehjr.powersuits.client.control;

import com.github.lehjr.numina.network.NuminaPackets;
import com.github.lehjr.numina.network.packets.PlayerUpdatePacket;
import com.github.lehjr.numina.util.capabilities.inventory.modechanging.IModeChangingItem;
import com.github.lehjr.numina.util.capabilities.player.CapabilityPlayerKeyStates;
import com.github.lehjr.powersuits.client.gui.modechanging.GuiModeSelector;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.items.CapabilityItemHandler;
import org.lwjgl.glfw.GLFW;

@OnlyIn(Dist.CLIENT)
public class KeybindKeyHandler {
    Minecraft minecraft;

    public static final String mpa = "Modular Powersuits";
    public static final KeyBinding openKeybindGUI = new KeyBinding("Open MPS Keybind GUI", GLFW.GLFW_KEY_UNKNOWN, mpa);
    public static final KeyBinding goDownKey = new KeyBinding("Go Down (MPS Flight Control)", GLFW.GLFW_KEY_Z, mpa);
    public static final KeyBinding cycleToolBackward = new KeyBinding("Cycle Tool Backward (MPS)", GLFW.GLFW_KEY_UNKNOWN, mpa);
    public static final KeyBinding cycleToolForward = new KeyBinding("Cycle Tool Forward (MPS)", GLFW.GLFW_KEY_UNKNOWN, mpa);
    public static final KeyBinding openCosmeticGUI = new KeyBinding("Cosmetic (MPS)", GLFW.GLFW_KEY_UNKNOWN, mpa);
    public static final KeyBinding[] keybindArray = new KeyBinding[]{openKeybindGUI, goDownKey, cycleToolBackward, cycleToolForward, openCosmeticGUI};

    public KeybindKeyHandler() {
        minecraft = Minecraft.getInstance();
        for (KeyBinding key : keybindArray) {
            ClientRegistry.registerKeyBinding(key);
        }
    }

    void updatePlayerValues(ClientPlayerEntity clientPlayer) {
        if (clientPlayer == null) {
            return;
        }
        clientPlayer.getCapability(CapabilityPlayerKeyStates.PLAYER_KEYSTATES).ifPresent(playerCap -> {
            boolean markForSync = false;
            boolean downKeyState = goDownKey.isKeyDown();
            boolean jumpKeyState = minecraft.gameSettings.keyBindJump.isKeyDown();

            if (playerCap.getDownKeyState() != downKeyState) {
                playerCap.setDownKeyState(downKeyState);
                markForSync = true;
            }

            if (playerCap.getJumpKeyState() != jumpKeyState) {
                playerCap.setJumpKeyState(jumpKeyState);
                markForSync = true;
            }

            if (markForSync) {
                NuminaPackets.CHANNEL_INSTANCE.sendToServer(new PlayerUpdatePacket(downKeyState, jumpKeyState));
            }
        });
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent e) {
        ClientPlayerEntity player = minecraft.player;
        if (player == null) {
            return;
        }

        KeyBinding[] hotbarKeys = minecraft.gameSettings.keyBindsHotbar;
        updatePlayerValues(player);

        // Mode changinging GUI
        if (hotbarKeys[player.inventory.currentItem].isKeyDown() && minecraft.isGameFocused()) {
            player.inventory.getCurrentItem().getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(iModeChanging->{
                if(player.world.isRemote) {
                    if (!(Minecraft.getInstance().currentScreen instanceof GuiModeSelector)) {
                        Minecraft.getInstance().enqueue(() -> Minecraft.getInstance().displayGuiScreen(new GuiModeSelector(player, new StringTextComponent("modeChanging"))));
                    }
                }
            });
        }

        /* cycleToolBackward/cycleToolForward */
        if (cycleToolBackward.isKeyDown()) {
            minecraft.playerController.tick();
            player.inventory.getStackInSlot(player.inventory.currentItem).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
                    .ifPresent(handler-> {
                        if (handler instanceof IModeChangingItem)
                            ((IModeChangingItem) handler).cycleMode(player, 1);
                    });
        }

        if (cycleToolForward.isKeyDown()) {
            minecraft.playerController.tick();
            player.inventory.getStackInSlot(player.inventory.currentItem).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
                    .ifPresent(handler-> {
                        if (handler instanceof IModeChangingItem)
                            ((IModeChangingItem) handler).cycleMode(player, -1);
                    });
        }
    }
}