package lehjr.numina.client.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import lehjr.numina.client.gui.frame.IGuiFrame;
import lehjr.numina.client.gui.geometry.IRect;
import lehjr.numina.client.gui.geometry.MusePoint2D;
import lehjr.numina.client.gui.slot.IHideableSlot;
import lehjr.numina.client.gui.slot.IIConProvider;
import lehjr.numina.common.math.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO: inventory label
 * @param <T>
 */
public class ExtendedContainerScreen<T extends AbstractContainerMenu> extends AbstractContainerScreen<T> implements IRect {
    protected long creationTime;
    private List<IGuiFrame> frames;

    public ExtendedContainerScreen(T screenAbstractContainerMenu, Inventory inv, Component titleIn) {
        super(screenAbstractContainerMenu, inv, titleIn);
        frames = new ArrayList();
        this.minecraft = Minecraft.getInstance();
    }

    public ExtendedContainerScreen(T screenAbstractContainerMenu, Inventory inv, Component titleIn, int guiWidth, int guiHeight) {
        this(screenAbstractContainerMenu, inv, titleIn);
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
    public void renderSlot(PoseStack matrixStack, Slot slot) {
        if (slot!= null && slot instanceof IHideableSlot) {
            if (slot.isActive()) {
                super.renderSlot(matrixStack, slot);
            }
//            else {
//                NuminaLogger.logDebug("index: "+ menu.slots.indexOf(slot));
//            }
        } else {
            super.renderSlot(matrixStack, slot);


//            NuminaLogger.logDebug("index: "+ menu.slots.indexOf(slot) +", class: " + slot.getClass());
        }

        if (slot instanceof IIConProvider && slot.getItem().isEmpty() && slot.isActive() ) {
//            NuminaLogger.logDebug("rendering");

            this.setBlitOffset(100);
            this.itemRenderer.blitOffset = 100.0F;
            ((IIConProvider) slot).drawIconAt(matrixStack, slot.x, slot.y, Color.WHITE);
            this.itemRenderer.blitOffset = 0.0F;
            this.setBlitOffset(0);
        }
    }

    @Override
    public void init() {
        super.init();
        frames.clear();
//        minecraft.keyboardHandler.m_90926_(true);
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
     * inherited from AbstractContainerMenuScreen..
     * @param matrixStack
     * @param frameTime
     * @param mouseX
     * @param mouseY
     */
    @Override
    public void renderBg(PoseStack matrixStack, float frameTime, int mouseX, int mouseY) {
        update(mouseX, mouseY);
        renderFrames(matrixStack, mouseX, mouseY, frameTime);
    }

    public void update(double x, double y) {
        frames.forEach(frame->frame.update(x, y));
    }

    public void renderFrames(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        frames.forEach(frame->frame.render(matrixStack, mouseX, mouseY, partialTicks));
    }

    @Override
    public void renderLabels(PoseStack matrixStack, int mouseX, int mouseY) {
        super.renderLabels(matrixStack, mouseX, mouseY);
        renderFrameLabels(matrixStack, mouseX, mouseY);
    }

    public void renderFrameLabels(PoseStack matrixStack, int mouseX, int mouseY) {
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

    protected IRect belowMe;
    protected IRect aboveMe;
    protected IRect leftOfMe;
    protected IRect rightOfMe;

    @Override
    public double left() {
        return leftPos;
    }

    @Override
    public IRect setLeft(double value) {
        leftPos = (int) value;
        return this;
    }

    @Override
    public double top() {
        return topPos;
    }

    @Override
    public IRect setTop(double value) {
        this.topPos = (int) value;
        return this;
    }

    @Override
    public double right() {
        return left() + imageWidth;
    }

    @Override
    public IRect setRight(double value) {
        return setLeft(value - imageWidth);
    }

    @Override
    public double bottom() {
        return top() + imageHeight;
    }

    @Override
    public IRect setBottom(double value) {
        return setTop(value - imageHeight);
    }

    @Override
    public double width() {
        return imageWidth;
    }

    @Override
    public IRect setWidth(double value) {
        this.imageWidth = (int) value;
        return this;
    }

    @Override
    public double height() {
        return imageHeight;
    }

    @Override
    public IRect setHeight(double value) {
        this.imageHeight = (int) value;
        return this;
    }

    @Override
    public void setPosition(MusePoint2D positionIn) {
        IRect.super.setPosition(positionIn);
    }

    @Override
    public MusePoint2D getUL() {
        return new MusePoint2D(left(), top());
    }

    @Override
    public IRect setUL(MusePoint2D ul) {
        setLeft(ul.x());
        setTop(ul.y());
        return this;
    }

    @Override
    public MusePoint2D getWH() {
        return new MusePoint2D(imageWidth, imageHeight);
    }

    @Override
    public IRect setWH(MusePoint2D wh) {
        setWidth(wh.x());
        setHeight(wh.y());
        return this;
    }

    @Override
    public IRect setAbove(IRect belowMe) {
        this.belowMe = belowMe;
        return this;
    }

    @Override
    public IRect setLeftOf(IRect rightOfMe) {
        this.rightOfMe = rightOfMe;
        return this;
    }

    @Override
    public IRect setBelow(IRect aboveMe) {
        this.aboveMe = aboveMe;
        return this;
    }

    @Override
    public IRect setRightOf(IRect leftOfMe) {
        this.leftOfMe = leftOfMe;
        return this;
    }
}