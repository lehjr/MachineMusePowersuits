package lehjr.numina.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import lehjr.numina.client.gui.frame.IGuiFrame;
import lehjr.numina.client.gui.geometry.MusePoint2D;
import lehjr.numina.client.gui.slot.IHideableSlot;
import lehjr.numina.client.gui.slot.IIConProvider;
import lehjr.numina.common.math.Colour;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.util.text.ITextComponent;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO: inventory label
 * @param <T>
 */
public class ExtendedContainerScreen2 <T extends Container> extends ContainerScreen<T> {
    protected long creationTime;
    private List<IGuiFrame> frames;

    public ExtendedContainerScreen2(T screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
        frames = new ArrayList();
        this.minecraft = Minecraft.getInstance();
    }

    public ExtendedContainerScreen2(T screenContainer, PlayerInventory inv, ITextComponent titleIn, int guiWidth, int guiHeight) {
        this(screenContainer, inv, titleIn);
        this.imageWidth = guiWidth;
        this.imageHeight = guiHeight;
    }

    MusePoint2D getUlOffset () {
        return new MusePoint2D(getGuiLeft(), getGuiTop());
    }

    public IContainerULOffSet.ulGetter ulGetter() {
        return () -> getUlOffset();
    }

    @Override
    protected void renderSlot(MatrixStack matrixStack, Slot slot) {
        if (slot!= null && slot instanceof IHideableSlot) {
            if (slot.isActive()) {
                super.renderSlot(matrixStack, slot);
            }
//            else {
//                System.out.println("index: "+ menu.slots.indexOf(slot));
//            }
        } else {
            super.renderSlot(matrixStack, slot);


//            System.out.println("index: "+ menu.slots.indexOf(slot) +", class: " + slot.getClass());
        }

        if (slot instanceof IIConProvider && slot.getItem().isEmpty() && slot.isActive() ) {
//            System.out.println("rendering");

            this.setBlitOffset(100);
            this.itemRenderer.blitOffset = 100.0F;
            ((IIConProvider) slot).drawIconAt(matrixStack, slot.x, slot.y, Colour.WHITE);
            this.itemRenderer.blitOffset = 0.0F;
            this.setBlitOffset(0);
        }
    }

    @Override
    public void init() {
        super.init();
        frames.clear();
        minecraft.keyboardHandler.setSendRepeatsToGui(true);
        creationTime = System.currentTimeMillis();
    }

    /**
     * Adds a frame to this gui's draw list.
     *
     * @param frame
     */
    public void addFrame(IGuiFrame frame) {
        frames.add(frame);
    }

    /**
     * inherited from ContainerScreen..
     * @param matrixStack
     * @param frameTime
     * @param mouseX
     * @param mouseY
     */
    @Override
    public void renderBg(MatrixStack matrixStack, float frameTime, int mouseX, int mouseY) {
        update(mouseX, mouseY);
        renderFrames(matrixStack, mouseX, mouseY, frameTime);
    }

    public void update(double x, double y) {
        frames.forEach(frame->frame.update(x, y));
    }

    public void renderFrames(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        frames.forEach(frame->frame.render(matrixStack, mouseX, mouseY, partialTicks));
    }

    @Override
    public void renderLabels(MatrixStack matrixStack, int mouseX, int mouseY) {
        super.renderLabels(matrixStack, mouseX, mouseY);
        renderFrameLabels(matrixStack, mouseX, mouseY);
    }

    public void renderFrameLabels(MatrixStack matrixStack, int mouseX, int mouseY) {
        frames.forEach(frame -> frame.renderLabels(matrixStack, mouseX, mouseY));
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double dWheel) {
        if (frames.stream().anyMatch(frame->frame.mouseScrolled(mouseX, mouseY, dWheel))) {
            return true;
        }
        return super.mouseScrolled(mouseX, mouseY, dWheel);
    }

    /**
     * Called when the mouse is clicked.
     */
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (frames.stream().anyMatch(frame->frame.mouseClicked(mouseX, mouseY, button))) {
            return true;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean isHovering(Slot slot, double mouseX, double mouseY) {
        return slot.isActive() && this.isHovering(slot.x, slot.y, 16, 16, mouseX, mouseY);
    }

    /**
     * Called when the mouse is moved or a mouse button is released. Signature:
     * (mouseX, mouseY, which) which==-1 is mouseMove, which==0 or which==1 is
     * mouseUp
     */
    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int which) {
        if (frames.stream().anyMatch(frame->frame.mouseReleased(mouseX, mouseY, which))) {
            return true;
        }
        return super.mouseReleased(mouseX, mouseY, which);
    }

    @Override
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

    public void setXSize(int xSize) {
        this.imageWidth = xSize;
        this.leftPos = (this.width - getXSize()) / 2;
    }

    public void setYSize(int ySize) {
        this.imageHeight = ySize;
        this.topPos = (this.height - getYSize()) / 2;
    }

    public MusePoint2D center() {
        return new MusePoint2D(getGuiLeft(), getGuiTop()).plus(getXSize() * 0.5, getYSize() * 0.5);
    }

    /**
     * Returns absolute screen coordinates (int 0 to width) from a relative
     * coordinate (float -1.0F to +1.0F)
     *
     * @param relx Relative X coordinate
     * @return Absolute X coordinate
     */
    public int absX(double relx) {
        int absx = (int) ((relx + 1) * getXSize() / 2);
        int xpadding = (width - getXSize()) / 2;
        return absx + xpadding;
    }

    /**
     * Returns relative coordinate (float -1.0F to +1.0F) from absolute
     * coordinates (int 0 to width)
     */
    public int relX(double absx) {
        int padding = (width - getXSize()) / 2;
        return (int) ((absx - padding) * 2 / getXSize() - 1);
    }

    /**
     * Returns absolute screen coordinates (int 0 to width) from a relative
     * coordinate (float -1.0F to +1.0F)
     *
     * @param rely Relative Y coordinate
     * @return Absolute Y coordinate
     */
    public int absY(double rely) {
        int absy = (int) ((rely + 1) * getYSize() / 2);
        int ypadding = (height - getYSize()) / 2;
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

    @Override
    public Minecraft getMinecraft() {
        this.minecraft = Minecraft.getInstance();
        return this.minecraft;
    }
}
