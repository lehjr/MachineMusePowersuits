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

package com.github.lehjr.numina.client.control;

import com.github.lehjr.numina.util.capabilities.player.CapabilityPlayerKeyStates;
import com.github.lehjr.numina.util.capabilities.player.IPlayerKeyStates;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.entity.player.RemoteClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.common.util.LazyOptional;

public class PlayerMovementInputWrapper {
    public static class PlayerMovementInput {
        public float moveStrafe;
        public float moveForward;
        public boolean jumpKey;
        public boolean downKey;
        public boolean sneakKey;

        public PlayerMovementInput(
                float moveStrafe,
                float moveForward,
                boolean jumpKey,
                boolean downKey,
                boolean sneakKey) {
            this.moveStrafe = Math.signum(moveStrafe);
            this.moveForward = Math.signum(moveForward);
            this.jumpKey = jumpKey;
            this.downKey = downKey;
            this.sneakKey = sneakKey;
        }
    }

    public static PlayerMovementInput get(PlayerEntity player) {
        if (player.world.isRemote) {
            if (player instanceof RemoteClientPlayerEntity) // multiplayer not dedicated server
                return fromServer(player);
            return fromClient(player);
        }
        return fromServer(player);
    }

    static LazyOptional<IPlayerKeyStates> getCapability(PlayerEntity player) {
        return player.getCapability(CapabilityPlayerKeyStates.PLAYER_KEYSTATES, null);
    }

    static PlayerMovementInput fromServer(PlayerEntity player) {
        boolean jumpKey = false;
        boolean downKey = false;

        LazyOptional<IPlayerKeyStates> playerCap = getCapability(player);
        if (playerCap.isPresent()) {
            jumpKey = playerCap.map(m -> m.getJumpKeyState()).orElse(false);
            downKey = playerCap.map(m -> m.getDownKeyState()).orElse(false);
        }

        return new PlayerMovementInput(
                player.moveStrafing,
                player.moveForward,
                jumpKey,
                downKey,
                player.isCrouching());
    }

    static PlayerMovementInput fromClient(PlayerEntity player) {
        boolean jumpKey = false;
        boolean downKey = false;

        ClientPlayerEntity clientPlayer = (ClientPlayerEntity) player;

        LazyOptional<IPlayerKeyStates> playerCap = getCapability(player);
        if (playerCap.isPresent()) {
            jumpKey = playerCap.map(m -> m.getJumpKeyState()).orElse(false);
            downKey = playerCap.map(m -> m.getDownKeyState()).orElse(false);
        }

        return new PlayerMovementInput(
                clientPlayer.movementInput.moveStrafe,
                clientPlayer.movementInput.moveForward,
                jumpKey,
                downKey,
        clientPlayer.isCrouching());
    }
}