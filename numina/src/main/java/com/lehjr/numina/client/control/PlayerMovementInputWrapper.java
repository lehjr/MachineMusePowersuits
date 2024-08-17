package com.lehjr.numina.client.control;

import com.lehjr.numina.common.capabilities.player.keystates.IPlayerKeyStates;
import com.lehjr.numina.common.registration.NuminaCapabilities;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.player.RemotePlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.loading.FMLEnvironment;

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

    // TODO: actually check ALL of this stuff... is it even needed? Maybe better way through Mixin?
    public static PlayerMovementInput get(Player player) {
        if (FMLEnvironment.dist == Dist.CLIENT) {
            if (player instanceof LocalPlayer) {
                return fromClient(player);
            }

            if (player instanceof RemotePlayer) { // multiplayer not dedicated server
                return fromServer(player);
            }
        }
        return fromServer(player);
    }

    static IPlayerKeyStates getCapability(Player player) {
        return player.getCapability(NuminaCapabilities.PLAYER_KEYSTATES, null);
    }

    static PlayerMovementInput fromServer(Player player) {
        boolean forwardKey = false;
        boolean reverseKey = false;
        boolean strafeLeftKey = false;
        boolean strafeRightKey = false;
        boolean downKey = false;
        boolean jumpKey = false;

        IPlayerKeyStates playerCap = getCapability(player);
        if (playerCap != null) {
            forwardKey =  playerCap.getForwardKeyState();
            reverseKey =  playerCap.getReverseKeyState();
            strafeLeftKey =  playerCap.getLeftStrafeKeyState();
            strafeRightKey = playerCap.getRightStrafeKeyState();
            downKey = playerCap.getDownKeyState();
            jumpKey = playerCap.getJumpKeyState();
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
        IPlayerKeyStates playerCap = getCapability(player);
        if (playerCap != null) {
            forwardKey =  playerCap.getForwardKeyState();
            reverseKey =  playerCap.getReverseKeyState();
            strafeLeftKey =  playerCap.getLeftStrafeKeyState();
            strafeRightKey = playerCap.getRightStrafeKeyState();
            downKey = playerCap.getDownKeyState();
            jumpKey = playerCap.getJumpKeyState();
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