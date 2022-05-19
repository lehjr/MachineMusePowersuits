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

package com.lehjr.powersuits.client.control;

import com.lehjr.numina.common.capabilities.inventory.modechanging.IModeChangingItem;
import com.lehjr.numina.common.capabilities.module.powermodule.CapabilityPowerModule;
import com.lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import com.lehjr.numina.common.capabilities.module.powermodule.ModuleTarget;
import com.lehjr.numina.common.capabilities.module.rightclick.IRightClickModule;
import com.lehjr.numina.common.capabilities.module.toggleable.IToggleableModule;
import com.lehjr.numina.common.capabilities.player.CapabilityPlayerKeyStates;
import com.lehjr.numina.common.network.NuminaPackets;
import com.lehjr.numina.common.network.packets.PlayerUpdatePacket;
import com.lehjr.powersuits.client.gui.modechanging.ModeSelectorScreen;
import com.lehjr.powersuits.common.constants.MPSConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.registries.ForgeRegistries;
import org.lwjgl.glfw.GLFW;

@OnlyIn(Dist.CLIENT)
public class KeybindKeyHandler {
    Minecraft minecraft;
    // FIXME: Translations
    public static final String mps =  "itemGroup.powersuits";

    public static final KeyMapping goDownKey = new KeyMapping(new TranslatableComponent("keybinding.powersuits.goDownKey").getKey(), GLFW.GLFW_KEY_Z, mps);
    public static final KeyMapping cycleToolBackward = new KeyMapping(new TranslatableComponent("keybinding.powersuits.cycleToolBackward").getKey(), GLFW.GLFW_KEY_UNKNOWN, mps);
    public static final KeyMapping cycleToolForward = new KeyMapping(new TranslatableComponent("keybinding.powersuits.cycleToolForward").getKey(), GLFW.GLFW_KEY_UNKNOWN, mps);
    public static final KeyMapping openKeybindGUI = new KeyMapping("Open MPS Keybind GUI", GLFW.GLFW_KEY_UNKNOWN, mps);
    public static final KeyMapping openCosmeticGUI = new KeyMapping("Cosmetic (MPS)", GLFW.GLFW_KEY_UNKNOWN, mps);

    public static final KeyMapping[] keybindArray = new KeyMapping[]{openKeybindGUI, goDownKey, cycleToolBackward, cycleToolForward, openCosmeticGUI};

    public static boolean isKeyPressed(int key) {
        return GLFW.glfwGetKey(Minecraft.getInstance().getWindow().getWindow(), key) == GLFW.GLFW_PRESS;
    }

    void RegisterKeybinding(ResourceLocation registryName) {
        ClientRegistry.registerKeyBinding(new MPSKeyBinding(registryName, "keybinding.powersuits." + registryName.getPath(), GLFW.GLFW_KEY_UNKNOWN, mps));
    }

    public KeybindKeyHandler() {
        minecraft = Minecraft.getInstance();
        for (KeyMapping key : keybindArray) {
            ClientRegistry.registerKeyBinding(key);
        }
        int i = 0;
        NonNullList<ItemStack> modules = NonNullList.create();

        for (Item item : ForgeRegistries.ITEMS.getValues()) {
            if (item.getRegistryName().getNamespace().contains(MPSConstants.MOD_ID)) {
                new ItemStack(item).getCapability(CapabilityPowerModule.POWER_MODULE)
                        .filter(IToggleableModule.class::isInstance)
                        .map(IToggleableModule.class::cast)
                        .ifPresent(pm -> {
                            // Tool settings are a bit odd
                            if (pm.getTarget() == ModuleTarget.TOOLONLY) {
                                if (pm.getCategory() == ModuleCategory.MINING_ENHANCEMENT) {
                                    modules.add(pm.getModuleStack());
                                    RegisterKeybinding(item.getRegistryName());
                                } else if (!IRightClickModule.class.isAssignableFrom(pm.getClass())) {
                                    modules.add(pm.getModuleStack());
                                    RegisterKeybinding(item.getRegistryName());
                                }
                            } else {
                                modules.add(pm.getModuleStack());
                                RegisterKeybinding(item.getRegistryName());
                            }
                        });
                i++;
            }
        }
    }

    void updatePlayerValues(LocalPlayer clientPlayer) {
        if (clientPlayer == null) {
            return;
        }

        clientPlayer.getCapability(CapabilityPlayerKeyStates.PLAYER_KEYSTATES).ifPresent(playerCap -> {
            boolean markForSync = false;
            boolean forwardKeyState = minecraft.options.keyUp.isDown();
            // looks weird, but if both keys are pressed it just balances
            byte strafeKeyState = (byte) ((minecraft.options.keyRight.isDown() ? -1 : 0) + (minecraft.options.keyLeft.isDown() ? 1 : 0));

            boolean downKeyState = goDownKey.isDown();
            boolean jumpKeyState = minecraft.options.keyJump.isDown();

            if (playerCap.getForwardKeyState() != forwardKeyState) {
                playerCap.setForwardKeyState(forwardKeyState);
                markForSync = true;
            }

            if (playerCap.getStrafeKeyState() != strafeKeyState) {
                playerCap.setStrafeKeyState(strafeKeyState);
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

            if (markForSync) {
                NuminaPackets.CHANNEL_INSTANCE.sendToServer(new PlayerUpdatePacket(
                        forwardKeyState,
                        strafeKeyState,
                        downKeyState,
                        jumpKeyState));
            }
        });
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent e) {
        LocalPlayer player = minecraft.player;
        if (player == null) {
            return;
        }

        KeybindManager.INSTANCE.getMPSKeyBinds().stream().filter(kb->kb.isDown()).forEach(kb->{
            kb.toggleModules();
        });

        KeyMapping[] hotbarKeys = minecraft.options.keyHotbarSlots;
        updatePlayerValues(player);

        // Mode changinging GUI
        if (hotbarKeys[player.getInventory().selected].isDown() && minecraft.isWindowActive()) {
            player.getInventory().getSelected().getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
                    .filter(IModeChangingItem.class::isInstance)
                    .map(IModeChangingItem.class::cast)
                    .ifPresent(iModeChanging->{
                        if(player.level.isClientSide) {
                            if (!(Minecraft.getInstance().screen instanceof ModeSelectorScreen)) {
                                Minecraft.getInstance().tell(() -> Minecraft.getInstance().setScreen(new ModeSelectorScreen(player, new TextComponent("modeChanging"))));
                            }
                        }
                    });
        }

        /* cycleToolBackward/cycleToolForward */
        if (cycleToolBackward.isDown()) {
            minecraft.gameMode.tick();
            player.getInventory().getItem(player.getInventory().selected).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
                    .filter(IModeChangingItem.class::isInstance)
                    .map(IModeChangingItem.class::cast)
                    .ifPresent(handler-> handler.cycleMode(player, 1));
        }

        if (cycleToolForward.isDown()) {
            minecraft.gameMode.tick();
            player.getInventory().getItem(player.getInventory().selected).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
                    .filter(IModeChangingItem.class::isInstance)
                    .map(IModeChangingItem.class::cast)
                    .ifPresent(handler-> handler.cycleMode(player, -1));
        }
    }
}