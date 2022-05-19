package com.lehjr.numina.client.gui.screen;

import com.lehjr.numina.client.gui.frame.IGuiFrame;
import com.lehjr.numina.client.gui.geometry.MusePoint2D;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;

public abstract class NuminaAbstractContainerlessScreen extends Screen {
    protected long creationTime;
    /** The X size of the inventory window in pixels. */
    public int imageWidth = 176;
    /** The Y size of the inventory window in pixels. */
    public int imageHeight = 166;
    /** Starting X position for the Gui. Inconsistent use for Gui backgrounds. */
    public int guiLeft;
    /** Starting Y position for the Gui. Inconsistent use for Gui backgrounds. */
    public int guiTop;

    private List<IGuiFrame> frames = new ArrayList<>();

    protected NuminaAbstractContainerlessScreen(Component pTitle) {
        super(pTitle);
    }

    public NuminaAbstractContainerlessScreen(Component titleIn, int guiWidth, int guiHeight) {
        this(titleIn);
        this.imageWidth = guiWidth;
        this.imageHeight = guiHeight;
    }


    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    @Override
    public void init() {
        super.init();
        minecraft.keyboardHandler.setSendRepeatsToGui(true);
        creationTime = System.currentTimeMillis();
//        backgroundRect.init(absX(-1), absY(-1), absX(1), absY(1));
    }

    /**
     * Adds a frame to this gui's draw list.
     *
     * @param frame
     */
    public void addFrame(IGuiFrame frame) {
        frames.add(frame);
    }

    @Override
    public void renderBackground(PoseStack matrixStack) {
        super.renderBackground(matrixStack);
    }

    public abstract void renderBackgroundRect(PoseStack matrixStack, int mouseX, int mouseY, float frameTime);
//    {
//        backgroundRect.render(matrixStack, mouseX, mouseY, frameTime);
//    }

    /**
     * Called every frame, draws the screen!
     */
    @Override
    public void render(PoseStack matrixStack, int mouseX, int mouseY, float frameTime) {
        this.renderBackground(matrixStack);
        this.renderBackgroundRect(matrixStack, mouseX, mouseY, frameTime);
        update(mouseX, mouseY);
        matrixStack.pushPose();
        matrixStack.translate(0, 0, 10);
//        if (this.backgroundRect.doneGrowing()) {
            renderFrames(matrixStack, mouseX, mouseY, frameTime);
            matrixStack.translate(0, 0, 10);
            super.render(matrixStack, mouseX, mouseY, frameTime);
            matrixStack.translate(0, 0, 100);
            renderLabels(matrixStack, mouseX, mouseY);
            matrixStack.popPose();
            this.renderTooltip(matrixStack, mouseX, mouseY);
//        }
    }

    public void update(double x, double y) {
        for (IGuiFrame frame : frames) {
            frame.update(x, y);
        }
    }

    public void renderFrames(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        for (IGuiFrame frame : frames) {
            frame.render(matrixStack, mouseX, mouseY, partialTicks);
        }
    }

    public void renderLabels(PoseStack matrixStack, int mouseX, int mouseY) {
        renderFrameLabels(matrixStack, mouseX, mouseY);
    }

    public void renderFrameLabels(PoseStack matrixStack, int mouseX, int mouseY) {
        frames.forEach(frame -> frame.renderLabels(matrixStack, mouseX, mouseY));
    }

    /**
     * Whether or not this gui pauses the game in single player.
     */
    @Override
    public boolean isPauseScreen() {
        return false;
    }

    /**
     * Returns absolute screen coordinates (int 0 to width) from a relative
     * coordinate (float -1.0F to +1.0F)
     *
     * @param relx Relative X coordinate
     * @return Absolute X coordinate
     */
    public int absX(double relx) {
        int absx = (int) ((relx + 1) * getImageWidth() / 2);
        int xpadding = (width - getImageWidth()) / 2;
        return absx + xpadding;
    }

    /**
     * Returns relative coordinate (float -1.0F to +1.0F) from absolute
     * coordinates (int 0 to width)
     */
    public int relX(double absx) {
        int padding = (width - getImageWidth()) / 2;
        return (int) ((absx - padding) * 2 / getImageWidth() - 1);
    }

    /**
     * Returns absolute screen coordinates (int 0 to width) from a relative
     * coordinate (float -1.0F to +1.0F)
     *
     * @param rely Relative Y coordinate
     * @return Absolute Y coordinate
     */
    public int absY(double rely) {
        int absy = (int) ((rely + 1) * imageHeight / 2);
        int ypadding = (height - imageHeight) / 2;
        return absy + ypadding;
    }

    /**
     * Returns relative coordinate (float -1.0F to +1.0F) from absolute
     * coordinates (int 0 to width)
     */
    public int relY(float absy) {
        int padding = (height - getYSize()) / 2;
        return (int) ((absy - padding) * 2 / getYSize() - 1);
    }

    /**
     * @return the xSize
     */
    public int getImageWidth() {
        return imageWidth;
    }

    /**
     * @param imageWidth the xSize to set
     */
    public void setImageWidth(int imageWidth) {
        this.imageWidth = imageWidth;
    }

    /**
     * @return the ySize
     */
    public int getImageHeight() {
        return imageHeight;
    }

    /**
     * @param imageHeight the ySize to set
     */
    public void setImageHeight(int imageHeight) {
        this.imageHeight = imageHeight;
    }

    public MusePoint2D center() {
        return new MusePoint2D(getGuiLeft(), getGuiTop()).plus(getXSize() * 0.5, getYSize() * 0.5);
    }

    public int getGuiLeft() {
        return guiLeft;
    }

    public int getGuiTop() {
        return guiTop;
    }

    public int getXSize() {
        return imageWidth;
    }

    public void setXSize(int xSize) {
        this.imageWidth = xSize;
        this.guiLeft = (this.width - getXSize()) / 2;
    }

    public int getYSize() {
        return imageHeight;
    }

    public void setYSize(int ySize) {
        this.imageHeight = ySize;
        this.guiTop = (this.height - getYSize()) / 2;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double dWheel) {
        for (IGuiFrame frame : frames) {
            if (frame.mouseScrolled(mouseX, mouseY, dWheel)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Called when the mouse is clicked.
     */
    @Override
    public boolean mouseClicked(double x, double y, int button) {
        for (IGuiFrame frame : frames) {
            frame.mouseClicked(x, y, button);
        }
        return true;
    }

    /**
     * Called when the mouse is moved or a mouse button is released. Signature:
     * (mouseX, mouseY, which) which==-1 is mouseMove, which==0 or which==1 is
     * mouseUp
     */

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int which) {
        for (IGuiFrame frame : frames) {
            if(frame.mouseReleased(mouseX, mouseY, which))
                return true;
        }
        return false;
    }

    public void renderTooltip(PoseStack matrixStack, int mouseX, int mouseY) {
        List<Component> tooltip = getToolTip(mouseX, mouseY);
        if (tooltip != null) {
            renderComponentTooltip(matrixStack,tooltip, mouseX,mouseY);
        }
    }

    public List<Component> getToolTip(int x, int y) {
        List<Component> hitTip;
        for (IGuiFrame frame : frames) {
            hitTip = frame.getToolTip(x, y);
            if (hitTip != null) {
                return hitTip;
            }
        }
        return null;
    }

    @Override
    public Minecraft getMinecraft() {
        this.minecraft = Minecraft.getInstance();
        return this.minecraft;
    }
}