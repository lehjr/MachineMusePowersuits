//package com.lehjr.numina.common.integration.scannable;
//
//import com.mojang.blaze3d.systems.RenderSystem;
//import com.mojang.blaze3d.vertex.PoseStack;
//import li.cil.scannable.api.API;
//import li.cil.scannable.common.config.Constants;
//import li.cil.scannable.util.Migration;
//import net.minecraft.client.gui.screen.inventory.AbstractContainerMenuScreen;
//import net.minecraft.inventory.container.ClickType;
//import net.minecraft.inventory.container.Slot;
//import net.minecraft.network.chat.Component;
//import net.minecraft.network.chat.Component;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.world.entity.player.Inventory;
//import net.minecraft.world.item.ItemStack;
//
//import javax.annotation.Nullable;
//
///**
// * Basically, just a copy of Scannable's GuiScanner simply because of the container
// */
//public class MPSGuiScanner extends AbstractContainerScreen<MPSAbstractContainerMenuScanner> {
//    private static final ResourceLocation BACKGROUND = ResourceLocation.fromNamespaceAndPath(API.MOD_ID, "textures/gui/container/scanner.png");
//    private static final Component.translatable SCANNER_MODULES_TEXT = Component.translatable(Constants.GUI_SCANNER_MODULES);
//    private static final Component.translatable SCANNER_MODULES_TOOLTIP = Component.translatable(Constants.GUI_SCANNER_MODULES_TOOLTIP);
//    private static final Component.translatable SCANNER_MODULES_INACTIVE_TEXT = Component.translatable(Constants.GUI_SCANNER_MODULES_INACTIVE);
//    private static final Component.translatable SCANNER_MODULES_INACTIVE_TOOLTIP = Component.translatable(Constants.GUI_SCANNER_MODULES_INACTIVE_TOOLTIP);
//
//    // --------------------------------------------------------------------- //
//
//    private final MPSAbstractContainerMenuScanner container;
//
//    // --------------------------------------------------------------------- //
//
//    public MPSGuiScanner(final MPSAbstractContainerMenuScanner container, final Inventory inventory, final Component title) {
//        super(container, inventory, title);
//        this.container = container;
//        imageHeight = 159;
//        field_230711_n_ = false;
//        inventoryLabelX = 8;
//        inventoryLabelY = 65;
//    }
//
//    // --------------------------------------------------------------------- //
//
//    @Override
//    public void render(final PoseStack matrixStack, final int mouseX, final int mouseY, final float partialTicks) {
//        renderBackground(matrixStack);
//        super.render(matrixStack, mouseX, mouseY, partialTicks);
//
//        if (isHovering(8, 23, Migration.FontRenderer.getStringWidth(font, SCANNER_MODULES_TEXT), font.field_78288_b, mouseX, mouseY)) {
//            func_238652_a_(matrixStack, SCANNER_MODULES_TOOLTIP, mouseX, mouseY);
//        }
//        if (isHovering(8, 49, Migration.FontRenderer.getStringWidth(font, SCANNER_MODULES_INACTIVE_TEXT), font.field_78288_b, mouseX, mouseY)) {
//            func_238652_a_(matrixStack, SCANNER_MODULES_INACTIVE_TOOLTIP, mouseX, mouseY);
//        }
//
//        renderTooltip(matrixStack, mouseX, mouseY);
//    }
//
//    @Override
//    protected void renderLabels(final PoseStack matrixStack, final int mouseX, final int mouseY) {
//        super.renderLabels(matrixStack, mouseX, mouseY);
//
//        Migration.FontRenderer.drawString(font, matrixStack, SCANNER_MODULES_TEXT, 8, 23, 0x404040);
//        Migration.FontRenderer.drawString(font, matrixStack, SCANNER_MODULES_INACTIVE_TEXT, 8, 49, 0x404040);
//    }
//
//    @Override
//    protected void renderBg(final PoseStack matrixStack, final float partialTicks, final int mouseX, final int mouseY) {
//        RenderSystem.setShaderColor(1, 1, 1, 1);
//        minecraft.getTextureManager().bindForSetup(BACKGROUND);
//        final int x = (width - imageWidth) / 2;
//        final int y = (height - imageHeight) / 2;
//        blit(matrixStack, x, y, 0, 0, imageWidth, imageHeight);
//    }
//
//    @Override
//    protected void func_184098_a(@Nullable final Slot slot, final int slotId, final int mouseButton, final ClickType type) {
//        if (slot != null) {
//            final ItemStack tool = field_213127_e.player.getItemInHand(container.getHand());
//            if (slot.getItem() == tool) {
//                return;
//            }
//            if (type == ClickType.SWAP && field_213127_e.getItem(mouseButton) == tool) {
//                return;
//            }
//        }
//
//        super.func_184098_a(slot, slotId, mouseButton, type);
//    }
//
//    @Override
//    public void func_231164_f_() {
////        NuminaLogger.logDebug(ItemUtils.getActiveModuleOrEmpty(Minecraft.getInstance().player.getMainHandItem()).serializeNBT());
//
//        super.func_231164_f_();
//    }
//
//    @Override
//    public void func_231175_as__() {
////        NuminaLogger.logDebug(ItemUtils.getActiveModuleOrEmpty(Minecraft.getInstance().player.getMainHandItem()).serializeNBT());
//
//        super.func_231175_as__();
//    }
//}
//
