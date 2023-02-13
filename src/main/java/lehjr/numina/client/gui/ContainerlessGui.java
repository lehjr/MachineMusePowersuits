package lehjr.numina.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import lehjr.numina.client.gui.frame.IGuiFrame;
import lehjr.numina.client.gui.gemoetry.MusePoint2D;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.ITextComponent;

import java.util.ArrayList;
import java.util.List;

public class ContainerlessGui extends Screen {
    /** The X size of the inventory window in pixels. */
    public int imageWidth = 176;
    /** The Y size of the inventory window in pixels. */
    public int imageHeight = 166;
    /** Starting X position for the Gui. Inconsistent use for Gui backgrounds. */
    public int leftPos;
    /** Starting Y position for the Gui. Inconsistent use for Gui backgrounds. */
    public int topPos;

    protected List<IGuiFrame> frames;

    protected ContainerlessGui(ITextComponent titleIn) {
        super(titleIn);
        frames = new ArrayList();
    }

    public ContainerlessGui(ITextComponent titleIn, int guiWidth, int guiHeight) {
        this(titleIn);
        this.imageWidth = guiWidth;
        this.imageHeight = guiHeight;
    }

    @Override
    public void init() {
        super.init();
        minecraft.keyboardHandler.setSendRepeatsToGui(true);
        this.leftPos = (this.width - this.imageWidth) / 2;
        this.topPos = (this.height - this.imageHeight) / 2;
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
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float frameTime) {
        this.renderBackground(matrixStack);
        update(mouseX, mouseY);
        matrixStack.pushPose();
        matrixStack.translate(0, 0, 10);
        renderFrames(matrixStack, mouseX, mouseY, frameTime);
        matrixStack.translate(0, 0, 10);
        super.render(matrixStack, mouseX, mouseY, frameTime);
        matrixStack.translate(0, 0, 100);
        renderLabels(matrixStack, mouseX, mouseY);
        matrixStack.popPose();
        this.renderTooltip(matrixStack, mouseX, mouseY);
    }

    public void update(double x, double y) {
        for (IGuiFrame frame : frames) {
            frame.update(x, y);
        }
    }

    public void renderFrames(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        for (IGuiFrame frame : frames) {
            frame.render(matrixStack, mouseX, mouseY, partialTicks);
        }
    }

    public void renderLabels(MatrixStack matrixStack, int mouseX, int mouseY) {
        renderFrameLabels(matrixStack, mouseX, mouseY);
    }

    public void renderFrameLabels(MatrixStack matrixStack, int mouseX, int mouseY) {
        frames.forEach(frame -> frame.renderLabels(matrixStack, mouseX, mouseY));
    }

    /**
     * Does gui pause the game in single player.
     */
    @Override
    public boolean isPauseScreen() {
        return false;
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
            if(frame.mouseReleased(mouseX, mouseY, which)) {
                return true;
            }
        }
        return false;
    }

    public void renderTooltip(MatrixStack matrixStack, int mouseX, int mouseY) {
        List<ITextComponent> tooltip = getToolTip(mouseX, mouseY);
        if (tooltip != null) {
            renderComponentTooltip(matrixStack,tooltip, mouseX,mouseY);
        }
    }

    public List<ITextComponent> getToolTip(int x, int y) {
        List<ITextComponent> hitTip;
        for (IGuiFrame frame : frames) {
            hitTip = frame.getToolTip(x, y);
            if (hitTip != null) {
                return hitTip;
            }
        }
        return null;
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
        return new MusePoint2D(getLeftPos(), getTopPos()).plus(getXSize() * 0.5, getYSize() * 0.5);
    }

    public int getLeftPos() {
        return leftPos;
    }

    public int getTopPos() {
        return topPos;
    }

    public int getXSize() {
        return imageWidth;
    }

    public void setXSize(int xSize) {
        this.imageWidth = xSize;
        this.leftPos = (this.width - getXSize()) / 2;
    }

    public int getYSize() {
        return imageHeight;
    }

    public void setYSize(int ySize) {
        this.imageHeight = ySize;
        this.topPos = (this.height - getYSize()) / 2;
    }

    @Override
    public Minecraft getMinecraft() {
        this.minecraft = Minecraft.getInstance();
        return this.minecraft;
    }
}
