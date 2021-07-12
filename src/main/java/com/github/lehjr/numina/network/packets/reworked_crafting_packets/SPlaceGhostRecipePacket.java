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

package com.github.lehjr.numina.network.packets.reworked_crafting_packets;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.recipebook.IRecipeShownListener;
import net.minecraft.client.gui.recipebook.RecipeBookGui;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * Send the Ghost recipe to the armor stand for display in the Install/Salvage frame
 *
 * based on the vanilla packet by the same name.
 */
public class SPlaceGhostRecipePacket {
    private int windowId;
    private ResourceLocation recipeId;

    public SPlaceGhostRecipePacket() {
    }

    public SPlaceGhostRecipePacket(int windowIdIn, IRecipe<?> recipeIn) {
        this(windowIdIn, recipeIn.getId());
    }

    public SPlaceGhostRecipePacket(int windowIdIn, ResourceLocation recipeIdIn) {
        this.windowId = windowIdIn;
        this.recipeId = recipeIdIn;
    }

    public static SPlaceGhostRecipePacket decode(PacketBuffer inBuffer) {
        return new SPlaceGhostRecipePacket(inBuffer.readByte(), inBuffer.readResourceLocation());
    }

    public static void encode(SPlaceGhostRecipePacket msg, PacketBuffer outBuffer) {
        outBuffer.writeByte(msg.windowId);
        outBuffer.writeResourceLocation(msg.recipeId);
    }

    @OnlyIn(Dist.CLIENT)
    static void handleClient(SPlaceGhostRecipePacket message) {
        Minecraft client = Minecraft.getInstance();
        Container container = client.player.containerMenu;
        if (container.containerId == message.windowId && container.isSynched(client.player)) {
            client.level.getRecipeManager().byKey(message.recipeId).ifPresent((iRecipe) -> {
                if (client.screen instanceof IRecipeShownListener) {
                    RecipeBookGui recipebookgui = ((IRecipeShownListener)client.screen).getRecipeBookComponent();
                    recipebookgui.setupGhostRecipe(iRecipe, container.slots);
                }
            });
        }
    }

    public static void handle(SPlaceGhostRecipePacket message, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            if (ctx.get().getDirection().getReceptionSide().isClient()) {
                handleClient(message);
            }
        });
        ctx.get().setPacketHandled(true);
    };
}