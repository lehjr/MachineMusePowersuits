package com.github.lehjr.powersuits.network.packets.reworked_crafting_packets;

import net.minecraft.item.crafting.IRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * Send the Ghost recipe to the tinker table for display in the Install/Salvage frame
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
        System.out.println("fixme");

//        Minecraft client = Minecraft.getInstance();
//        Container container = client.player.openContainer;
//        if (container.windowId == message.windowId && container.getCanCraft(client.player)) {
//            client.world.getRecipeManager().getRecipe(message.recipeId).ifPresent((iRecipe) -> {
//                if (client.currentScreen instanceof MPAWorkbenchGui) {
//                    ((MPAWorkbenchGui) client.currentScreen).setupGhostRecipe(iRecipe, container.inventorySlots);
//                }
//            });
//        }
    }

    public static void handle(SPlaceGhostRecipePacket message, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            System.out.println("fixme");
//            if (ctx.get().getDirection().getReceptionSide().isClient()) {
//                handleClient(message);
//            }
        });
        ctx.get().setPacketHandled(true);
    };
}