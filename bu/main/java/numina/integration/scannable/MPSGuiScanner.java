package lehjr.numina.integration.scannable;

import com.mojang.blaze3d.matrix.PoseStack;
import com.mojang.blaze3d.systems.RenderSystem;
import lehjr.numina.util.capabilities.inventory.modechanging.IModeChangingItem;
import li.cil.scannable.api.API;
import li.cil.scannable.common.config.Constants;
import li.cil.scannable.util.Migration;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.Component;
import net.minecraft.util.text.TranslatableComponent;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nullable;

/**
 * Basically, just a copy of Scannable's GuiScanner simply because of the container
 */
public class MPSGuiScanner extends ContainerScreen<MPSContainerScanner> {
    private static final ResourceLocation BACKGROUND = new ResourceLocation(API.MOD_ID, "textures/gui/container/scanner.png");
    private static final TranslatableComponent SCANNER_MODULES_TEXT = new TranslatableComponent(Constants.GUI_SCANNER_MODULES);
    private static final TranslatableComponent SCANNER_MODULES_TOOLTIP = new TranslatableComponent(Constants.GUI_SCANNER_MODULES_TOOLTIP);
    private static final TranslatableComponent SCANNER_MODULES_INACTIVE_TEXT = new TranslatableComponent(Constants.GUI_SCANNER_MODULES_INACTIVE);
    private static final TranslatableComponent SCANNER_MODULES_INACTIVE_TOOLTIP = new TranslatableComponent(Constants.GUI_SCANNER_MODULES_INACTIVE_TOOLTIP);

    // --------------------------------------------------------------------- //

    private final MPSContainerScanner container;

    // --------------------------------------------------------------------- //

    public MPSGuiScanner(final MPSContainerScanner container, final PlayerInventory inventory, final Component title) {
        super(container, inventory, title);
        this.container = container;
        imageHeight = 159;
        passEvents = false;
        inventoryLabelX = 8;
        inventoryLabelY = 65;
    }

    // --------------------------------------------------------------------- //

    @Override
    public void render(final PoseStack matrixStack, final int mouseX, final int mouseY, final float partialTicks) {
        renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);

        if (isHovering(8, 23, Migration.FontRenderer.getStringWidth(font, SCANNER_MODULES_TEXT), font.lineHeight, mouseX, mouseY)) {
            renderTooltip(matrixStack, SCANNER_MODULES_TOOLTIP, mouseX, mouseY);
        }
        if (isHovering(8, 49, Migration.FontRenderer.getStringWidth(font, SCANNER_MODULES_INACTIVE_TEXT), font.lineHeight, mouseX, mouseY)) {
            renderTooltip(matrixStack, SCANNER_MODULES_INACTIVE_TOOLTIP, mouseX, mouseY);
        }

        renderTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void renderLabels(final PoseStack matrixStack, final int mouseX, final int mouseY) {
        super.renderLabels(matrixStack, mouseX, mouseY);

        Migration.FontRenderer.drawString(font, matrixStack, SCANNER_MODULES_TEXT, 8, 23, 0x404040);
        Migration.FontRenderer.drawString(font, matrixStack, SCANNER_MODULES_INACTIVE_TEXT, 8, 49, 0x404040);
    }

    @Override
    protected void renderBg(final PoseStack matrixStack, final float partialTicks, final int mouseX, final int mouseY) {
        RenderSystem.color4f(1, 1, 1, 1);
        minecraft.getTextureManager().bind(BACKGROUND);
        final int x = (width - imageWidth) / 2;
        final int y = (height - imageHeight) / 2;
        blit(matrixStack, x, y, 0, 0, imageWidth, imageHeight);
    }

    @Override
    protected void slotClicked(@Nullable final Slot slot, final int slotId, final int mouseButton, final ClickType type) {
        if (slot != null) {
            final ItemStack tool = inventory.player.getItemInHand(container.getHand());
            if (slot.getItem() == tool) {
                return;
            }
            if (type == ClickType.SWAP && inventory.getItem(mouseButton) == tool) {
                return;
            }
        }

        super.slotClicked(slot, slotId, mouseButton, type);
    }
}

