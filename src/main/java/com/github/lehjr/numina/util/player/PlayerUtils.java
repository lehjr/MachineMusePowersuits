/*
 * Copyright (c) 2019 MachineMuse, Lehjr
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.github.lehjr.numina.util.player;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.biome.Biome;

import javax.annotation.Nonnull;

/**
 * Created by Claire Semple on 9/9/2014.
 * <p>
 * Ported to Java by lehjr on 10/24/16.
 */
public final class PlayerUtils {
    public static void resetFloatKickTicks(PlayerEntity player) {
        if (player instanceof ServerPlayerEntity) {
            ((ServerPlayerEntity) player).connection.floatingTickCount = 0;
        }
    }

    public static void teleportEntity(PlayerEntity PlayerEntity, RayTraceResult rayTraceResult) {
        if (rayTraceResult != null && PlayerEntity instanceof ServerPlayerEntity) {
            ServerPlayerEntity player = (ServerPlayerEntity) PlayerEntity;
            if (player.connection.netManager.isChannelOpen()) {
                switch (rayTraceResult.getType()) {
                    case ENTITY:
                        player.setPositionAndUpdate(rayTraceResult.getHitVec().x, rayTraceResult.getHitVec().y, rayTraceResult.getHitVec().z);
                        break;
                    case BLOCK:
                        double hitx = rayTraceResult.getHitVec().x;
                        double hity = rayTraceResult.getHitVec().y;
                        double hitz = rayTraceResult.getHitVec().z;
                        switch (((BlockRayTraceResult)rayTraceResult).getFace()) {
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

                        player.setPositionAndUpdate(hitx, hity, hitz);
                        break;
                    default:
                        break;

                }
            }
        }
    }

    public static float getPlayerCoolingBasedOnMaterial(@Nonnull PlayerEntity player) {
        if (player.isInLava()) {
            return 0;
        }

        float cool = ((2.0F - getBiome(player).getTemperature(new BlockPos((int) player.getPosX(), (int) player.getPosY(), (int) player.getPosZ())) / 2)); // Algorithm that returns a getValue from 0.0 -> 1.0. Biome temperature is from 0.0 -> 2.0

        if (player.isInWater())
            cool += 0.5;

        // If high in the air, increase cooling
        if ((int) player.getPosY() > 128)
            cool += 0.5;

        // If nighttime and in the desert, increase cooling
        if (!player.world.isDaytime() && getBiome(player).getCategory() == Biome.Category.DESERT) {
            cool += 0.8;
        }

        // check for rain and if player is in the rain
        // check if rain can happen in the biome the player is in
        if (player.world.getBiome(player.getPosition()).getPrecipitation() != Biome.RainType.NONE
                // check if raining in the world
                && player.world.isRaining()
                // check if the player can see the sky
                && player.world.canBlockSeeSky(player.getPosition().add(0, 1, 0))) {
            cool += 0.2;
        }

        return cool;
    }

    public static Biome getBiome(PlayerEntity player) {
        return player.world.getBiome(player.getPosition());
    }
}