package com.lehjr.numina.common.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

import javax.annotation.Nonnull;

public class PlayerUtils {
    public static void resetFloatKickTicks(Player player) {
        if (player instanceof ServerPlayer) {
            ((ServerPlayer) player).connection.aboveGroundTickCount = 0;
        }
    }

    public static void teleportEntity(Player Player, HitResult rayTraceResult) {
        if (Player instanceof ServerPlayer player) {
            if (player.connection.getConnection().isConnected()) {
                switch (rayTraceResult.getType()) {
                    case ENTITY:
                        player.teleportTo(rayTraceResult.getLocation().x, rayTraceResult.getLocation().y, rayTraceResult.getLocation().z);
                        break;
                    case BLOCK:
                        double hitx = rayTraceResult.getLocation().x;
                        double hity = rayTraceResult.getLocation().y;
                        double hitz = rayTraceResult.getLocation().z;
                        switch (((BlockHitResult)rayTraceResult).getDirection()) {
                            case DOWN: // Bottom
                                hity -= 2;
                                break;
                            case UP: // Top
                                // hity += 1;
                                break;
                            case NORTH: // North
                                hitx -= 0.5;
                                break;
                            case SOUTH: // South
                                hitx += 0.5;
                                break;
                            case WEST: // West
                                hitz += 0.5;
                                break;
                            case EAST: // East
                                hitz -= 0.5;
                                break;
                        }

                        player.teleportTo(hitx, hity, hitz);
                        break;
                    default:
                        break;

                }
            }
        }
    }

    public static float getPlayerCoolingBasedOnMaterial(@Nonnull Player player) {
        Level level = player.level();
        if (player.isInLava()) {
            return 0;
        }

        float cool = ((2.0F - getBiome(player, level).getTemperature(new BlockPos((int) player.getX(), (int) player.getY(), (int) player.getZ())) / 2)); // Algorithm that returns a getValue from 0.0 -> 1.0. Biome temperature is from 0.0 -> 2.0

        if (player.isInWater())
            cool += 0.5F;

        // If high in the air, increase cooling
        if ((int) player.getY() > 128)
            cool += 0.5F;

        // If nighttime and in the desert, increase cooling
        if (!level.isDay() && getBiome(player, level).coldEnoughToSnow(player.blockPosition()) /*.getBiomeCategory().equals(Biomes.DESERT) FIXME*/) {
            cool += 0.8F;
        }

        // check for rain and if player is in the rain
        // check if rain can happen in the biome the player is in
        if (getBiome(player, level).hasPrecipitation()
                // check if raining in the world
                && level.isRaining()
                // check if the player can see the sky
                && level.canSeeSky(player.blockPosition().offset(0, 1, 0))) {
            cool += 0.2;
        }
        return cool;
    }

    public static Biome getBiome(Player player, Level level) {
        return level.getBiome(player.blockPosition()).value();
    }
}
