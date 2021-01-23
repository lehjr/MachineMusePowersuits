package com.github.lehjr.powersuits.network.packets.reworked_crafting_packets;

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
            player.markPlayerActive();

            if (!player.isSpectator() &&
                    player.openContainer.windowId == message.windowId &&
                    player.openContainer.getCanCraft(player) &&
                    player.openContainer instanceof RecipeBookContainer) {
                player.world.getRecipeManager().getRecipe(message.recipeId).ifPresent((iRecipe) ->
                        ((RecipeBookContainer)player.openContainer).func_217056_a(message.placeAll, iRecipe, player));
            }
        });
        ctx.get().setPacketHandled(true);
    }
}