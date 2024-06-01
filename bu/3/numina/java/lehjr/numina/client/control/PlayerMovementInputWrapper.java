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

package lehjr.numina.client.control;

import lehjr.numina.common.capabilities.NuminaCapabilities;
import lehjr.numina.common.capabilities.player.keystates.IPlayerKeyStates;
import net.minecraft.client.player.RemotePlayer;
import net.minecraft.world.entity.player.Player;

import java.util.Optional;

public class PlayerMovementInputWrapper {
    public static class PlayerMovementInput {
        public boolean forwardKey;
        public boolean reverseKey;
        public boolean strafeLeftKey;
        public boolean strafeRightKey;
        public boolean downKey;
        public boolean jumpKey;

        public PlayerMovementInput(
                boolean forwardKey,
                boolean reverseKey,
                boolean strafeLeftKey,
                boolean strafeRightKey,
                boolean downKey,
                boolean jumpKey) {
            this.forwardKey = forwardKey;
            this.reverseKey = reverseKey;
            this.strafeLeftKey = strafeLeftKey;
            this.strafeRightKey = strafeRightKey;
            this.downKey = downKey;
            this.jumpKey = jumpKey;
        }
    }

    public static PlayerMovementInput get(Player player) {
        if (player.level().isClientSide) {
            if (player instanceof RemotePlayer) { // multiplayer not dedicated server
                return fromServer(player);
            }
            return fromClient(player);
        }
        return fromServer(player);
    }

    static Optional<IPlayerKeyStates> getCapability(Player player) {
        return Optional.ofNullable(player.getCapability(NuminaCapabilities.PLAYER_KEYSTATES, null));
    }

    static PlayerMovementInput fromServer(Player player) {
        boolean forwardKey = false;
        boolean reverseKey = false;
        boolean strafeLeftKey = false;
        boolean strafeRightKey = false;
        boolean downKey = false;
        boolean jumpKey = false;

        Optional<IPlayerKeyStates> playerCap = getCapability(player);
        if (playerCap.isPresent()) {
            forwardKey =  playerCap.map(IPlayerKeyStates::getForwardKeyState).orElse(false);
            reverseKey =  playerCap.map(IPlayerKeyStates::getReverseKeyState).orElse(false);
            strafeLeftKey =  playerCap.map(IPlayerKeyStates::getLeftStrafeKeyState).orElse(false);
            strafeRightKey = playerCap.map(IPlayerKeyStates::getRightStrafeKeyState).orElse(false);
            downKey = playerCap.map(IPlayerKeyStates::getDownKeyState).orElse(false);
            jumpKey = playerCap.map(IPlayerKeyStates::getJumpKeyState).orElse(false);
        }

        return new PlayerMovementInput(
        forwardKey,
        reverseKey,
        strafeLeftKey,
        strafeRightKey,
        downKey,
        jumpKey);
    }

    static PlayerMovementInput fromClient(Player player) {
        boolean forwardKey = false;
        boolean reverseKey = false;
        boolean strafeLeftKey = false;
        boolean strafeRightKey = false;
        boolean downKey = false;
        boolean jumpKey = false;
        Optional<IPlayerKeyStates> playerCap = getCapability(player);
        if (playerCap.isPresent()) {
            forwardKey =  playerCap.map(IPlayerKeyStates::getForwardKeyState).orElse(false);
            reverseKey =  playerCap.map(IPlayerKeyStates::getReverseKeyState).orElse(false);
            strafeLeftKey =  playerCap.map(IPlayerKeyStates::getLeftStrafeKeyState).orElse(false);
            strafeRightKey = playerCap.map(IPlayerKeyStates::getRightStrafeKeyState).orElse(false);
            downKey = playerCap.map(IPlayerKeyStates::getDownKeyState).orElse(false);
            jumpKey = playerCap.map(IPlayerKeyStates::getJumpKeyState).orElse(false);
        }

        return new PlayerMovementInput(
                forwardKey,
                reverseKey,
                strafeLeftKey,
                strafeRightKey,
                downKey,
                jumpKey);
    }
}