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

package com.github.lehjr.numina.util.client.event;

import com.github.lehjr.numina.config.NuminaSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.glfw.GLFW;

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 10:07 PM, 10/17/13
 * <p>
 * Ported to Java by lehjr on 10/10/16.
 */

@OnlyIn(Dist.CLIENT)
public class FOVUpdateEventHandler {
    public FOVUpdateEventHandler() {
        ClientRegistry.registerKeyBinding(fovToggleKey);
    }
    public static KeyBinding fovToggleKey = new KeyBinding("keybind.fovfixtoggle", GLFW.GLFW_KEY_UNKNOWN, "Numina");

    public boolean fovIsActive = NuminaSettings.fovFixDefaultState();

    @SubscribeEvent
    public void onFOVUpdate(FOVUpdateEvent e) {
        if (NuminaSettings.useFovFix()) {
            ClientPlayerEntity player = Minecraft.getInstance().player;
            if (fovToggleKey.consumeClick()) {
                fovIsActive = !fovIsActive;
                if (fovIsActive) {
                    player.sendMessage(new StringTextComponent(I18n.get("fovfixtoggle.enabled")), player.getUUID());
                } else {
                    player.sendMessage(new StringTextComponent(I18n.get("fovfixtoggle.disabled")), player.getUUID());
                }
            }

            if (fovIsActive) {
                ModifiableAttributeInstance attributeinstance = e.getEntity().getAttribute(Attributes.MOVEMENT_SPEED);
                e.setNewfov((float) (e.getNewfov() / ((attributeinstance.getValue() / e.getEntity().abilities.getWalkingSpeed() + 1.0) / 2.0)));
            }
        }
    }
}