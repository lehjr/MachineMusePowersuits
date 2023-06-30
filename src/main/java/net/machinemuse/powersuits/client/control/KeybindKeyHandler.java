package net.machinemuse.powersuits.client.control;

import net.machinemuse.numina.common.capabilities.player.CapabilityPlayerValues;
import net.machinemuse.numina.common.capabilities.player.IPlayerValues;
import net.machinemuse.numina.common.item.IModeChangingItem;
import net.machinemuse.powersuits.common.base.ModularPowersuits;
import net.machinemuse.powersuits.common.network.MPSPackets;
import net.machinemuse.powersuits.common.network.packets.MusePacketPlayerUpdate;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

@SideOnly(Side.CLIENT)
public class KeybindKeyHandler {
    public static final String mps = "Modular Powersuits";

    public static final KeyBinding goDownKey = new KeyBinding("key.powersuits.goDownKey", Keyboard.KEY_Z, mps);
    public static final KeyBinding cycleToolForward = new KeyBinding("key.powersuits.cycleToolForward", Keyboard.KEY_NONE, mps);
    public static final KeyBinding cycleToolBackward = new KeyBinding("key.powersuits.cycleToolBackward", Keyboard.KEY_NONE, mps);
    public static final KeyBinding openKeybindGUI = new KeyBinding("key.powersuits.openKeybindGui", Keyboard.KEY_NONE, mps);
    public static final KeyBinding openCosmeticGUI = new KeyBinding("key.powersuits.openCosmeticGUI", Keyboard.KEY_NONE, mps);
    public static final KeyBinding openTinkerGUI = new KeyBinding("key.powersuits.openTinkerGUI", Keyboard.KEY_NONE, mps);

    public KeybindKeyHandler() {
    }

    public static void registerKeyBinds() {
        ClientRegistry.registerKeyBinding(goDownKey);
        ClientRegistry.registerKeyBinding(cycleToolForward);
        ClientRegistry.registerKeyBinding(cycleToolBackward);
        ClientRegistry.registerKeyBinding(openKeybindGUI);
        ClientRegistry.registerKeyBinding(openCosmeticGUI);
        ClientRegistry.registerKeyBinding(openTinkerGUI);
    }

    // TODO: backport full setup from 1.16.5?
    public void updatePlayerValues(EntityPlayerSP clientPlayer, Boolean downKeyState, Boolean jumpKeyState) {
        boolean markForSync = false;

        IPlayerValues playerCap = clientPlayer.getCapability(CapabilityPlayerValues.PLAYER_VALUES, null);
        if (playerCap != null) {
            if(downKeyState != null)
                if (playerCap.getDownKeyState() != downKeyState) {
                    playerCap.setDownKeyState(downKeyState);
                    markForSync = true;
                }

            if (jumpKeyState != null)
                if (playerCap.getJumpKeyState() != jumpKeyState) {
                    playerCap.setJumpKeyState(jumpKeyState);
                    markForSync = true;
                }

            if (markForSync) {
                MPSPackets.sendToServer(new MusePacketPlayerUpdate(clientPlayer, playerCap.getDownKeyState(), playerCap.getJumpKeyState()));
            }
        }
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.player.world.isRemote) {
            Minecraft mc = Minecraft.getMinecraft();
            EntityPlayerSP player = mc.player;

            // Only activate if there is a player to work with
            if (mc.inGameHasFocus) {
                updatePlayerValues(player, goDownKey.isKeyDown() , mc.gameSettings.keyBindJump.isKeyDown());
            }
        }
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent e) {
        int key = Keyboard.getEventKey();
        boolean pressed = Keyboard.getEventKeyState();
        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayerSP player = mc.player;
        World world = mc.world;
        KeyBinding[] hotbarKeys = mc.gameSettings.keyBindsHotbar;

        // Only activate if there is a player to work with
        if (player == null || !mc.inGameHasFocus) {
            return;
        }

        if (pressed) {
            KeybindManager.INSTANCE.getMPSKeybinds().filter(kb->kb.isPressed()).forEach(kb -> kb.toggleModules());

            if (player.inventory.getCurrentItem().getItem() instanceof IModeChangingItem) {
                IModeChangingItem mci = (IModeChangingItem) player.inventory.getCurrentItem().getItem();

                if (player.inventory.currentItem < hotbarKeys.length && key == hotbarKeys[player.inventory.currentItem].getKeyCode()) {
                    if (mc.inGameHasFocus) {
                        player.openGui(ModularPowersuits.getInstance(), 5, world, 0, 0, 0);
                    }
                    // cycleToolBackward/cycleToolForward aren't related to the mouse wheel unless bound to that
                } else if (cycleToolBackward.isPressed()) {
                    mc.playerController.updateController();
                    mci.cycleMode(player.inventory.getStackInSlot(player.inventory.currentItem), player, 1);
                } else if (cycleToolForward.isPressed()) {
                    mc.playerController.updateController();
                    mci.cycleMode(player.inventory.getStackInSlot(player.inventory.currentItem), player, -1);
                }
            }

            if (openTinkerGUI.isPressed()) {
                if (mc.inGameHasFocus) {
                    player.openGui(ModularPowersuits.getInstance(), 0, world, 0, 0, 0);
                }
            } else if (openKeybindGUI.isPressed()) {
                if (mc.inGameHasFocus) {
                    player.openGui(ModularPowersuits.getInstance(), 1, world, 0, 0, 0);
                }
            } else if (openCosmeticGUI.isPressed()) {
                if (mc.inGameHasFocus) {
                    player.openGui(ModularPowersuits.getInstance(), 3, world, 0, 0, 0);
                }
            }
        }
    }
}