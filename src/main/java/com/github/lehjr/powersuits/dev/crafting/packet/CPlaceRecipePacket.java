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

package com.github.lehjr.powersuits.dev.crafting.packet;

import com.github.lehjr.numina.basemod.MuseLogger;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.RecipeBookContainer;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * Used for setting craftable recipe in the Install/Salvage frame
 *
 * based on the vanilla packet by the same name.
 */
public class CPlaceRecipePacket {
    private int windowId;
    private ResourceLocation recipeId;
    private boolean placeAll;

    public CPlaceRecipePacket() {
    }

    public CPlaceRecipePacket(int windowId, IRecipe<?> recipeIn, boolean placeAll) {
        this(windowId, recipeIn.getId(), placeAll);
    }

    public CPlaceRecipePacket(int windowId, ResourceLocation recipeIdIn, boolean placeAll) {
        this.windowId = windowId;
        this.recipeId = recipeIdIn;
        this.placeAll = placeAll;
    }

    public static void encode(CPlaceRecipePacket msg, PacketBuffer outBuffer) {
        outBuffer.writeByte(msg.windowId);
        outBuffer.writeResourceLocation(msg.recipeId);
        outBuffer.writeBoolean(msg.placeAll);
    }

    public static CPlaceRecipePacket decode(PacketBuffer inBuffer) {
        return new CPlaceRecipePacket(
                inBuffer.readByte(),
                inBuffer.readResourceLocation(),
                inBuffer.readBoolean()
        );
    }

    public static void handle(CPlaceRecipePacket message, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            final ServerPlayerEntity player = ctx.get().getSender();
            player.resetLastActionTime();

            if (!player.isSpectator() &&
                    player.containerMenu.containerId == message.windowId &&
                    player.containerMenu.isSynched(player) &&
                    player.containerMenu instanceof RecipeBookContainer) {
                MuseLogger.logDebug("handling recipe placing packet");
                player.level.getRecipeManager().byKey(message.recipeId).ifPresent((iRecipe) ->
                        ((RecipeBookContainer)player.containerMenu).handlePlacement(message.placeAll, iRecipe, player));
            } else{
                MuseLogger.logDebug("failed to handle recipe placing packet");
                MuseLogger.logDebug("player.containerMenu.windowId == message.windowId?: " + (player.containerMenu.containerId == message.windowId));
                MuseLogger.logDebug("player.containerMenu.windowId: " + player.containerMenu.containerId);
                MuseLogger.logDebug("message.windowId?: " + message.windowId);
                MuseLogger.logDebug("player.containerMenu.getClass(): " + player.containerMenu.getClass());



                MuseLogger.logDebug("player.containerMenu.getCanCraft(player)?: " + (player.containerMenu.isSynched(player)));
                MuseLogger.logDebug("player.containerMenu instanceof RecipeBookContainer?: " + (player.containerMenu instanceof RecipeBookContainer));
            }
        });
        ctx.get().setPacketHandled(true);
    }
}