/*
 * Copyright (c) 2021. MachineMuse, Lehjr
 *  All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *      Redistributions of source code must retain the above copyright notice, this
 *      list of conditions and the following disclaimer.
 *
 *     Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 *  ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 *  WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 *  DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 *  ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 *  (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 *  LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 *  ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *  (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *  SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package lehjr.powersuits.client.control;

import lehjr.numina.common.capabilities.inventory.modechanging.IModeChangingItem;
import lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import lehjr.numina.common.capabilities.module.powermodule.ModuleTarget;
import lehjr.numina.common.capabilities.module.powermodule.PowerModuleCapability;
import lehjr.numina.common.capabilities.module.rightclick.IRightClickModule;
import lehjr.numina.common.capabilities.module.toggleable.IToggleableModule;
import lehjr.numina.common.capabilities.player.CapabilityPlayerKeyStates;
import lehjr.numina.common.math.MathUtils;
import lehjr.numina.common.network.NuminaPackets;
import lehjr.numina.common.network.packets.PlayerUpdatePacket;
import lehjr.powersuits.client.gui.modechanging.GuiModeSelector;
import lehjr.powersuits.common.constants.MPSConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.commons.lang3.ArrayUtils;
import org.lwjgl.glfw.GLFW;
import sun.security.util.ArrayUtil;

import java.util.Arrays;
import java.util.Optional;

@OnlyIn(Dist.CLIENT)
public class KeybindKeyHandler {
    Minecraft minecraft;
    // FIXME: Translations
    public static final String mps =  "itemGroup.powersuits";

    public static final KeyBinding goDownKey = new KeyBinding(new TranslationTextComponent("keybinding.powersuits.goDownKey").getKey(), GLFW.GLFW_KEY_Z, mps);
    public static final KeyBinding cycleToolBackward = new KeyBinding(new TranslationTextComponent("keybinding.powersuits.cycleToolBackward").getKey(), GLFW.GLFW_KEY_UNKNOWN, mps);
    public static final KeyBinding cycleToolForward = new KeyBinding(new TranslationTextComponent("keybinding.powersuits.cycleToolForward").getKey(), GLFW.GLFW_KEY_UNKNOWN, mps);
    public static final KeyBinding openKeybindGUI = new KeyBinding(new TranslationTextComponent("keybinding.powersuits.openKeybindGui").getKey()/*"Open MPS Keybind GUI"*/, GLFW.GLFW_KEY_UNKNOWN, mps);
    public static final KeyBinding openCosmeticGUI = new KeyBinding(new TranslationTextComponent("keybinding.powersuits.openCosmeticGUI").getKey() /*Cosmetic GUI (MPS)"*/, GLFW.GLFW_KEY_UNKNOWN, mps);
    public static final KeyBinding openModuleTweakGUI = new KeyBinding(new TranslationTextComponent("keybinding.powersuits.openModuleTweakGUI").getKey() /*Open MPS Keybind GUI"*/, GLFW.GLFW_KEY_UNKNOWN, mps);
    public static final KeyBinding openInstallSalvageGUI = new KeyBinding(new TranslationTextComponent("keybinding.powersuits.openInstallSalvageGUI").getKey() /*Cosmetic GUI (MPS)"*/, GLFW.GLFW_KEY_UNKNOWN, mps);
    public static final KeyBinding[] keybindArray = new KeyBinding[]{goDownKey, cycleToolBackward, cycleToolForward, openKeybindGUI, openCosmeticGUI, openModuleTweakGUI, openInstallSalvageGUI};

    public static boolean isKeyPressed(int key) {
        return GLFW.glfwGetKey(Minecraft.getInstance().getWindow().getWindow(), key) == GLFW.GLFW_PRESS;
    }

    public KeybindKeyHandler() {
        minecraft = Minecraft.getInstance();
        for (KeyBinding key : keybindArray) {
            ClientRegistry.registerKeyBinding(key);
        }

        KeybindManager.INSTANCE.readInKeybinds(false);
    }

    void updatePlayerValues(ClientPlayerEntity clientPlayer) {
        if (clientPlayer == null) {
            return;
        }

        clientPlayer.getCapability(CapabilityPlayerKeyStates.PLAYER_KEYSTATES).ifPresent(playerCap -> {
            boolean markForSync = false;

            // minecraft.player.input
            boolean forwardKeyState = minecraft.options.keyUp.isDown();
            boolean reverseKeyState = minecraft.options.keyDown.isDown();
            boolean strafeLeftKeyState = minecraft.options.keyLeft.isDown();
            boolean strafeRightKeyState = minecraft.options.keyRight.isDown();
            boolean downKeyState = goDownKey.isDown();
            boolean jumpKeyState = minecraft.options.keyJump.isDown();


            if (playerCap.getForwardKeyState() != forwardKeyState) {
                playerCap.setForwardKeyState(forwardKeyState);
                markForSync = true;
            }

            if (playerCap.getReverseKeyState() != reverseKeyState) {
                playerCap.setReverseKeyState(reverseKeyState);
                markForSync = true;
            }

            if (playerCap.getLeftStrafeKeyState() != strafeLeftKeyState) {
                playerCap.setLeftStrafeKeyState(strafeLeftKeyState);
                markForSync = true;
            }

            if (playerCap.getRightStrafeKeyState() != strafeRightKeyState) {
                playerCap.setRightStrafeKeyState(strafeRightKeyState);
                markForSync = true;
            }

            if (playerCap.getDownKeyState() != downKeyState) {
                playerCap.setDownKeyState(downKeyState);
                markForSync = true;
            }

            if (playerCap.getJumpKeyState() != jumpKeyState) {
                playerCap.setJumpKeyState(jumpKeyState);
                markForSync = true;
            }

            boolean[] boolArray = new boolean[]{
                    forwardKeyState,
                    reverseKeyState,
                    strafeLeftKeyState,
                    strafeRightKeyState,
                    downKeyState,
                    jumpKeyState,
                    false,
                    false
            };

            byte byteOut = MathUtils.boolArrayToByte(boolArray);

            System.out.println("byteOut: " + byteOut);

            if (markForSync) {
                NuminaPackets.CHANNEL_INSTANCE.sendToServer(new PlayerUpdatePacket(byteOut));
            }


            // looks weird, but if both keys are pressed it just balances
//            byte strafeKeyState = (byte) ((minecraft.options.keyRight.isDown() ? -1 : 0) + (minecraft.options.keyLeft.isDown() ? 1 : 0));

        });
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent e) {
        ClientPlayerEntity player = minecraft.player;
        if (player == null) {
            return;
        }

        KeybindManager.INSTANCE.getMPSKeyBinds().stream().filter(kb->kb.isDown()).forEach(kb->{
            kb.toggleModules();
        });

        KeyBinding[] hotbarKeys = minecraft.options.keyHotbarSlots;
        updatePlayerValues(player);

        // Mode changinging GUI
        if (hotbarKeys[player.inventory.selected].isDown() && minecraft.isWindowActive()) {
            player.inventory.getSelected().getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
                    .filter(IModeChangingItem.class::isInstance)
                    .map(IModeChangingItem.class::cast)
                    .ifPresent(iModeChanging->{
                        if(player.level.isClientSide) {
                            if (!(Minecraft.getInstance().screen instanceof GuiModeSelector)) {
                                Minecraft.getInstance().tell(() -> Minecraft.getInstance().setScreen(new GuiModeSelector(player, new StringTextComponent("modeChanging"))));
                            }
                        }
                    });
        }

        /* cycleToolBackward/cycleToolForward */
        if (cycleToolBackward.isDown()) {
            minecraft.gameMode.tick();
            player.inventory.getItem(player.inventory.selected).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
                    .filter(IModeChangingItem.class::isInstance)
                    .map(IModeChangingItem.class::cast)
                    .ifPresent(handler-> handler.cycleMode(player, 1));
        }

        if (cycleToolForward.isDown()) {
            minecraft.gameMode.tick();
            player.inventory.getItem(player.inventory.selected).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
                    .filter(IModeChangingItem.class::isInstance)
                    .map(IModeChangingItem.class::cast)
                    .ifPresent(handler-> handler.cycleMode(player, -1));
        }
    }

    public static Optional<KeyBinding> getKeyIfExits(String keybindingName) {
        return Arrays.stream(Minecraft.getInstance().options.keyMappings).filter(keyBinding1 -> keyBinding1.getName().equals(keybindingName)).findFirst();
    }


    public static void RegisterKeybinding(ResourceLocation registryName) {
        String keybindingName = new StringBuilder("keybinding.").append(registryName.getNamespace()).append(".").append(registryName.getPath()).toString();
        Optional<KeyBinding> keyBinding = getKeyIfExits(keybindingName);

        if (keyBinding.isPresent()) {
            keyBinding.ifPresent(kb-> {
                // This is to replace keybindings in the
                if(!(kb instanceof MPSKeyBinding) || ((MPSKeyBinding) kb).registryName == Items.AIR.getRegistryName()) {
                    int index = ArrayUtils.indexOf(Minecraft.getInstance().options.keyMappings, kb);
                    int key = kb.getKey().getValue();
                    MPSKeyBinding mpskb = new MPSKeyBinding(registryName, keybindingName, key, mps);
                    if (kb instanceof MPSKeyBinding) {
                        mpskb.showOnHud = ((MPSKeyBinding) kb).showOnHud;
                    }
                    Minecraft.getInstance().options.keyMappings[index] = mpskb;
                }
            });
        } else {
            // This is mostly just to populate the list in the event the list doesn't exist or to add new modules
            ClientRegistry.registerKeyBinding(new MPSKeyBinding(registryName, keybindingName, GLFW.GLFW_KEY_UNKNOWN, mps));
        }
    }
}