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

package com.github.lehjr.numina.util.client.control;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraftforge.client.settings.KeyBindingMap;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

/**
 * Created by leon on 7/4/16.
 */
public class KeyBindingHelper {
    static KeyBindingMap hash;

    static {
        new KeyBindingHelper();
    }

    static KeyBindingMap getKeyBindingMap() {
        try {
            if (hash == null) {
                hash = ObfuscationReflectionHelper.getPrivateValue(KeyBinding.class, null,  "field_74514_b");
            }
        } catch (Exception e) {
            return null;
        }
        return hash;
    }

    public static InputMappings.Input getInputByCode(int keyCode) {
        return InputMappings.Type.KEYSYM.getOrMakeInput(keyCode);
    }

    public boolean keyBindingHasKey(int keyCode) {
        return keyBindingHasKey(getInputByCode(keyCode));
    }

    public boolean keyBindingHasKey(InputMappings.Input keyCode) {
        try {
            return (getKeyBindingMap() != null) ? (getKeyBindingMap().lookupActive(keyCode) != null) : false;
        } catch (Exception ignored) {

        }
        return false;
    }

    public void removeKey(int keyCode) {
        removeKey(getInputByCode(keyCode));
    }

    public void removeKey(InputMappings.Input keyCode) {
        try {
            if (getKeyBindingMap() != null)
                hash.removeKey(hash.lookupActive(keyCode));
        } catch (Exception ignored) {
            ignored.printStackTrace();
        }
    }

    public void removeKey(KeyBinding key) {
        try {
            if (getKeyBindingMap() != null)
                hash.removeKey(key);
        } catch (Exception ignored) {
            ignored.printStackTrace();
        }
    }
}