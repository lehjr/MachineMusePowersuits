//package com.lehjr.numina.common.integration.scannable;
//
//import com.mojang.blaze3d.systems.RenderSystem;
//import com.mojang.blaze3d.vertex.BufferBuilder;
//import com.mojang.blaze3d.vertex.Tesselator;
//import li.cil.scannable.common.config.Constants;
//import li.cil.scannable.util.Migration;
//import net.minecraft.client.Minecraft;
//import com.mojang.blaze3d.vertex.DefaultVertexFormat;
//import net.minecraft.network.chat.Component;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.util.math.MathHelper;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.item.ItemStack;
//import net.minecraftforge.client.event.RenderGameOverlayEvent;
//import net.minecraftforge.eventbus.api.SubscribeEvent;
//import org.lwjgl.opengl.GL11;
//
///**
// * Copy of Scannable's overlay renderer recreated here because the item check below
// */
//public enum MPSOverlayRenderer {
//    INSTANCE;
//
//    private static final ResourceLocation PROGRESS = ResourceLocation.fromNamespaceAndPath("scannable", "textures/gui/overlay/scanner_progress.png");
//
//    @SubscribeEvent
//    public void onOverlayRender(final RenderGameOverlayEvent.Post event) {
//        if (event.getType() != RenderGameOverlayEvent.ElementType.ALL) {
//            return;
//        }
//
//        final Minecraft mc = Minecraft.getInstance();
//        final Player player = mc.player;
//        if (player == null) {
//            return;
//        }
//
//        final ItemStack stack = player.func_184607_cu();
//        if (stack.isEmpty()) {
//            return;
//        }
//
//        if (!ScannableHandler.isScanner(stack)) {
//            return;
//        }
//
//        final int total = stack.getUseDuration();
//        final int remaining = player.getUseItemRemainingTicks();
//
//        final float progress = MathHelper.func_76131_a(1 - (remaining - event.getPartialTicks()) / (float) total, 0, 1);
//
//        final int screenWidth = mc.getWindow().getGuiScaledWidth();
//        final int screenHeight = mc.getWindow().getGuiScaledHeight();
//
//        RenderSystem.enableBlend();
//        RenderSystem.setShaderColor(0.66f, 0.8f, 0.93f, 0.66f);
//        mc.getTextureManager().bindForSetup(PROGRESS);
//
//        final Tesselator tessellator = Tesselator.getInstance();
//        final BufferBuilder buffer = tessellator.getBuilder();
//
//        buffer.begin(GL11.GL_TRIANGLES, DefaultVertexFormat.POSITION_TEX);
//
//        final int width = 64;
//        final int height = 64;
//        final int midX = screenWidth / 2;
//        final int midY = screenHeight / 2;
//        final int left = midX - width / 2;
//        final int right = midX + width / 2;
//        final int top = midY - height / 2;
//        final int bottom = midY + height / 2;
//
//        final float angle = (float) (progress * Math.PI * 2);
//        final float tx = MathHelper.func_76126_a(angle);
//        final float ty = MathHelper.func_76134_b(angle);
//
//        buffer.addVertex(midX, top, 0).uv(0.5f, 1);
//        if (progress < 0.125) { // Top right.
//            buffer.addVertex(midX, midY, 0).uv(0.5f, 0.5f);
//
//            final float x = tx / ty * 0.5f;
//            buffer.addVertex(midX + x * width, top, 0).uv(0.5f + x, 1);
//        } else {
//            buffer.addVertex(midX, midY, 0).uv(0.5f, 0.5f);
//            buffer.addVertex(right, top, 0).uv(1, 1);
//
//            buffer.addVertex(right, top, 0).uv(1, 1);
//            if (progress < 0.375) { // Right.
//                buffer.addVertex(midX, midY, 0).uv(0.5f, 0.5f);
//
//                final float y = Math.abs(ty / tx - 1) * 0.5f;
//                buffer.addVertex(right, top + y * height, 0).uv(1, 1 - y);
//            } else {
//                buffer.addVertex(midX, midY, 0).uv(0.5f, 0.5f);
//                buffer.addVertex(right, bottom, 0).uv(1, 0);
//
//                buffer.addVertex(right, bottom, 0).uv(1, 0);
//                if (progress < 0.625) { // Bottom.
//                    buffer.addVertex(midX, midY, 0).uv(0.5f, 0.5f);
//
//                    final float x = Math.abs(tx / ty - 1) * 0.5f;
//                    buffer.addVertex(left + x * width, bottom, 0).uv(x, 0);
//                } else {
//                    buffer.addVertex(midX, midY, 0).uv(0.5f, 0.5f);
//                    buffer.addVertex(left, bottom, 0).uv(0, 0);
//
//                    buffer.addVertex(left, bottom, 0).uv(0, 0);
//                    if (progress < 0.875) { // Left.
//                        buffer.addVertex(midX, midY, 0).uv(0.5f, 0.5f);
//
//                        final float y = (ty / tx + 1) * 0.5f;
//                        buffer.addVertex(left, top + y * height, 0).uv(0, 1 - y);
//                    } else {
//                        buffer.addVertex(midX, midY, 0).uv(0.5f, 0.5f);
//                        buffer.addVertex(left, top, 0).uv(0, 1);
//
//                        buffer.addVertex(left, top, 0).uv(0, 1);
//                        if (progress < 1) { // Top left.
//                            buffer.addVertex(midX, midY, 0).uv(0.5f, 0.5f);
//
//                            final float x = Math.abs(tx / ty) * 0.5f;
//                            buffer.addVertex(midX - x * width, top, 0).uv(0.5f - x, 1);
//                        } else {
//                            buffer.addVertex(midX, midY, 0).uv(0.5f, 0.5f);
//                            buffer.addVertex(midX, top, 0).uv(0.5f, 1);
//                        }
//                    }
//                }
//            }
//        }
//
//        tessellator.end();
//
//        Migration.FontRenderer.drawStringWithShadow(mc.font, event.getPoseStack(),
//                Component.translatable(Constants.GUI_SCANNER_PROGRESS, MathHelper.func_76141_d(progress * 100)),
//                right + 12, midY - mc.font.field_78288_b / 2, 0xCCAACCEE);
//
//        RenderSystem.bindTexture(0);
//    }
//}
