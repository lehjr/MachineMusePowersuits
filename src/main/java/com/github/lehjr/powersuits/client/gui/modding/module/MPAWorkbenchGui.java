package com.github.lehjr.powersuits.client.gui.modding.module;

import com.github.lehjr.numina.util.client.gui.ExtendedContainerScreen;
import com.github.lehjr.numina.util.client.gui.gemoetry.DrawableMuseRect;
import com.github.lehjr.numina.util.client.gui.gemoetry.MusePoint2D;
import com.github.lehjr.numina.util.client.render.MuseRenderer;
import com.github.lehjr.numina.util.math.Colour;
import com.github.lehjr.powersuits.client.gui.common.ItemSelectionFrame;
import com.github.lehjr.powersuits.client.gui.common.TabSelectFrame;
import com.github.lehjr.powersuits.container.MPAWorkbenchContainer;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

/**
 * Requires all module and inventory slots be accounted for before constructing
 *
 *
 */
public class MPAWorkbenchGui extends ExtendedContainerScreen<MPAWorkbenchContainer> {
    final int spacer = 7;

    MPAWorkbenchContainer container;
    protected DrawableMuseRect backgroundRect;
    protected ItemSelectionFrame itemSelectFrame;
    protected ModuleSelectionFrame moduleSelectFrame;
    protected DetailedSummaryFrame summaryFrame;
    protected InstallSalvageFrame installFrame;
    protected ModuleTweakFrame tweakFrame;
    protected TabSelectFrame tabSelectFrame;

    public MPAWorkbenchGui(MPAWorkbenchContainer containerIn, PlayerInventory playerInventory, ITextComponent titleIn) {
        super(containerIn, playerInventory, titleIn);
        this.minecraft = Minecraft.getInstance();
        PlayerEntity player = getMinecraft().player;
        this.container = containerIn;
        rescale();
        backgroundRect = new DrawableMuseRect(absX(-1), absY(-1), absX(1), absY(1), true,
                new Colour(0.0F, 0.2F, 0.0F, 0.8F),
                new Colour(0.1F, 0.9F, 0.1F, 0.8F));

        itemSelectFrame = new ItemSelectionFrame(
                container,
                new MusePoint2D(absX(-0.95), absY(-0.95)),
                new MusePoint2D(absX(-0.78), absY(0.95)),
                this.getBlitOffset(),
                Colour.DARKBLUE.withAlpha(0.8F),
                Colour.LIGHT_BLUE.withAlpha(0.8F), player);
        addFrame(itemSelectFrame);

        moduleSelectFrame = new ModuleSelectionFrame(itemSelectFrame,
                new MusePoint2D(absX(-0.75F), absY(-0.95f)),
                new MusePoint2D(absX(-0.05F), absY(0.75f)),
                this.getBlitOffset(),
                Colour.DARKBLUE.withAlpha(0.8F),
                Colour.LIGHT_BLUE.withAlpha(0.8F));
        addFrame(moduleSelectFrame);

        itemSelectFrame.setDoOnNewSelect(doThis -> moduleSelectFrame.loadModules(false));

        /** GLErrors */
        summaryFrame = new DetailedSummaryFrame(player,
                new MusePoint2D(absX(0), absY(-0.9)),
                new MusePoint2D(absX(0.95), absY(-0.3)),
                this.getBlitOffset(),
                Colour.DARKBLUE.withAlpha(0.8F),
                Colour.LIGHT_BLUE.withAlpha(0.8F),
                itemSelectFrame);
        addFrame(summaryFrame);

        installFrame = new InstallSalvageFrame(
                containerIn,
                player,
                new MusePoint2D(absX(-0.75),
                        absY(0.6)),
                new MusePoint2D(absX(-0.05),
                        absY(0.95f)),
                this.getBlitOffset(),
                Colour.DARKBLUE.withAlpha(0.8F),
                Colour.LIGHT_BLUE.withAlpha(0.8F),
                itemSelectFrame,
                moduleSelectFrame);
        addFrame(installFrame);

        tweakFrame = new ModuleTweakFrame(
                new MusePoint2D(absX(0), absY(-0.25)),
                new MusePoint2D(absX(0.95), absY(0.95)),
                getBlitOffset(),
                Colour.DARKBLUE.withAlpha(0.8F),
                Colour.LIGHT_BLUE.withAlpha(0.8F),
                itemSelectFrame,
                moduleSelectFrame);
        addFrame(tweakFrame);

        tabSelectFrame = new TabSelectFrame(player, 0, this.getBlitOffset());
        addFrame(tabSelectFrame);
    }

    MusePoint2D getUlOffset() {
        return new MusePoint2D(guiLeft + 8, guiTop + 8);
    }

    public void rescale() {
        this.setXSize((Math.min(minecraft.getMainWindow().getScaledWidth() - 50, 500)));
        this.setYSize((Math.min(minecraft.getMainWindow().getScaledHeight() - 50, 300)));
    }

    @Override
    public void init() {
        rescale();
        backgroundRect.setTargetDimensions(getGuiLeft(), getGuiTop(), getGuiLeft() + getXSize(), getGuiTop() + getYSize());

        itemSelectFrame.init(
                backgroundRect.finalLeft() + spacer,
                backgroundRect.finalTop() + spacer,
                backgroundRect.finalLeft() + spacer + 36,
                backgroundRect.finalBottom() - spacer);

        moduleSelectFrame.init(
                backgroundRect.finalLeft() + spacer + 36 + spacer, //  border plus itemselection frame plus spacer,
                backgroundRect.finalTop() + spacer, // border top plus spacer
                backgroundRect.finalLeft() + spacer + 200, // adjust as needed
                backgroundRect.finalBottom() - spacer - 18 - spacer - 3 * 18 - spacer);
        moduleSelectFrame.loadModules(true);

        summaryFrame.init(
                backgroundRect.finalLeft() + spacer + 200 + spacer,
                backgroundRect.finalTop() + spacer,
                backgroundRect.finalRight() - spacer,
                backgroundRect.finalTop() + spacer + 80);

        tweakFrame.init(
                backgroundRect.finalLeft() + spacer + 200 + spacer,
                backgroundRect.finalTop() + spacer + 80 + spacer,
                backgroundRect.finalRight() - spacer,
                backgroundRect.finalBottom() - spacer);

        installFrame.setUlShift(getUlOffset());
        installFrame.init(
                backgroundRect.finalLeft() + spacer + 36 + spacer, // border plus spacer + 9 slots wide
                backgroundRect.finalBottom() - spacer - 18 - spacer - 3 * 18,
                backgroundRect.finalLeft() + spacer + 200, // adjust as needed
                backgroundRect.finalBottom() - spacer);

        tabSelectFrame.init(getGuiLeft(), getGuiTop(), getGuiLeft() + getXSize(), getGuiTop() + getYSize());
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        if (backgroundRect.width() == backgroundRect.finalWidth() && backgroundRect.height() == backgroundRect.finalHeight()) {
            if (container.getModularItemToSlotMap().isEmpty()) {
                float centerx = absX(0);
                float centery = absY(0);
                MuseRenderer.drawCenteredText(matrixStack, new TranslationTextComponent("gui.powersuits.noModulesFound.line1"), centerx, centery - 5, Colour.WHITE);
                MuseRenderer.drawCenteredText(matrixStack, new TranslationTextComponent("gui.powersuits.noModulesFound.line2"), centerx, centery + 5, Colour.WHITE);
                tabSelectFrame.render(matrixStack, mouseX, mouseY, partialTicks);
            } else {
                super.render(matrixStack, mouseX, mouseY, partialTicks);
                drawToolTip(matrixStack, mouseX, mouseY);
            }
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int x, int y) {

    }

    @Override
    public void renderBackground(MatrixStack matrixStack) {
        super.renderBackground(matrixStack);
        this.backgroundRect.draw(matrixStack, getBlitOffset());
    }
}