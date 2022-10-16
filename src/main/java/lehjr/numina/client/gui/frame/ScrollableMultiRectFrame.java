package lehjr.numina.client.gui.frame;

import com.mojang.blaze3d.matrix.MatrixStack;
import lehjr.numina.client.gui.clickable.IClickable;
import lehjr.numina.client.gui.gemoetry.IDrawable;
import lehjr.numina.client.gui.gemoetry.IDrawableRect;
import lehjr.numina.client.gui.gemoetry.IRect;
import lehjr.numina.client.gui.gemoetry.MusePoint2D;
import net.minecraft.util.text.ITextComponent;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


/**
 * A scrollable bunch of rectangles laid out vertically
 */
public class ScrollableMultiRectFrame<T extends Map<Integer, IRect>> extends GUISpacer implements IScrollable {
    protected int buttonSize = 5;
    protected int totalSize;
    protected int currentScrollPixels;
    protected double scrollAmount = 8;
    protected double margin;
    protected double maxHeight = -1;
    protected double maxWidth = -1;

    float zLevel;
    boolean isEnabled = true;
    boolean isVisible = true;
    @Nullable
    IDrawableRect background = null;
    Map<Integer, IRect> rects = new HashMap<>();
    boolean startTop;
    /**
     *  Note, all rectangles should have the same width for vertical layout, or same height for horizontal layout.
     *
     * @param startTop determines where the first link in the chain is, since that is the one that will determine which points to set
     *                     when the outer rectangle is moved. For instance, false will start on the bottom for vertical layout, or right for
     *                     horizontal layout. The rest in the chain should be using setMe-LOCATION-Of.
     * @param minWidth
     * @param minHeight
     */
    public ScrollableMultiRectFrame(boolean startTop, double minWidth, double minHeight) {
        super(minWidth, minHeight);
        this.startTop = startTop;
    }

    public ScrollableMultiRectFrame(boolean startTop, double minWidth, double minHeight, double maxWidth, double maxHeight) {
        super(minWidth, minHeight);
        this.startTop = startTop;
        this.maxHeight = maxHeight;
        this.maxWidth = maxWidth;
    }

    public void setMargin(double margin) {
        this.margin = margin;
    }

    /**
     * Automatically sets orientation of next IRect based on Layout and direction settings
     * @param rect
     * @return
     */
    public ScrollableMultiRectFrame addRect(IRect rect) {
        if (rects.size() >0) {
            if (startTop) {
                rect.setMeBelow(rects.get(rects.size() -1));
            } else {
                rect.setMeAbove(rects.get(rects.size() -1));
            }
        }
        rects.put(rects.size(), rect);
        return this;
    }

    public ScrollableMultiRectFrame setMaxHeight(double maxHeight) {
        this.maxHeight = maxHeight;
        return this;
    }

    public ScrollableMultiRectFrame setMaxWidth(double maxWidth) {
        this.maxWidth = maxWidth;
        return this;
    }

    public ScrollableMultiRectFrame setMinHeight(double minHeight) {
        super.setHeight(minHeight);
        return this;
    }

    public ScrollableMultiRectFrame setMinWidth(double minWidth) {
        super.setWidth(minWidth);
        return this;
    }

    public Optional<IRect> getLast() {
        return Optional.ofNullable(rects.size() > 0 ? (rects.get(rects.size() -1)) : null);
    }

    public void setRects(Map<Integer, IRect> rects){
        this.rects = rects;
    }

    public Map<Integer, IRect> getRects() {
        return rects;
    }

    /**
     * Automatically set width and height
     */
    public void doneAdding() {
        double finalVal = 0;
        double highestVal = 0;
        for (IRect rect : rects.values()) {
            // single max width, multiple heights
            finalVal += rect.finalHeight();
            if (rect.finalWidth() > highestVal) {
                highestVal = rect.finalWidth();
            }
        }
        highestVal += margin * 2;
        {
            if (maxWidth > 0) {
                super.setWidth(Math.min(Math.max(highestVal, super.finalWidth()), maxWidth));
            } else {
                super.setWidth(Math.max(highestVal, super.finalWidth()));
            }
            if (maxHeight > 0) {
                super.setHeight(Math.min(Math.max(finalVal, super.finalHeight()), maxHeight));
                this.totalSize = (int) (finalVal + 10);
            } else {
                super.setHeight(Math.max(finalVal, super.finalHeight()));
            }
        }
    }

    public ScrollableMultiRectFrame setBackground(IDrawableRect background) {
        this.background = background;
        return this;
    }

    Optional<IDrawableRect> getBackground() {
        return Optional.ofNullable(this.background);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (IScrollable.super.mouseClicked(mouseX, mouseY, button)) {
            double scrollY = mouseY + currentScrollPixels;
            return rects.values().stream().filter(IGuiFrame.class::isInstance).map(IGuiFrame.class::cast).anyMatch(rect->rect.mouseClicked(mouseX, scrollY, button)) ||
                    rects.values().stream().filter(IClickable.class::isInstance).map(IClickable.class::cast).anyMatch(rect->rect.mouseClicked(mouseX, scrollY, button));
        }
        return false;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        double scrollY = mouseY + currentScrollPixels;
        return rects.values().stream().filter(IGuiFrame.class::isInstance).map(IGuiFrame.class::cast).anyMatch(rect->rect.mouseReleased(mouseX, scrollY, button)) ||
                rects.values().stream().filter(IClickable.class::isInstance).map(IClickable.class::cast).anyMatch(rect->rect.mouseReleased(mouseX, scrollY, button));
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double dWheel) {
        if (IScrollable.super.mouseScrolled(mouseX, mouseY, dWheel)) {
            return rects.values().stream().filter(IGuiFrame.class::isInstance).map(IGuiFrame.class::cast).anyMatch(rect->rect.mouseScrolled(mouseX, mouseY, dWheel));
        }
        return false;
    }

    public void renderRects(MatrixStack matrixStack, int mouseX, int mouseY, float frameTime) {
        if(isVisible()) {
            for (IRect rect : rects.values()) {
                if (rect instanceof IDrawable) {
                    ((IDrawable) rect).render(matrixStack, mouseX, mouseY, frameTime);
                }
            }
        }
    }

    @Override
    public void preRender(MatrixStack matrixStack, int mouseX, int mouseY, float frameTime) {
        IScrollable.super.preRender(matrixStack, mouseX, mouseY, frameTime);
    }

    public void renderBackground(MatrixStack matrixStack, int mouseX, int mouseY, float frameTime) {
        getBackground().ifPresent(background -> background.render(matrixStack, mouseX, mouseY, frameTime));
    }

    @Override
    public void postRender(int mouseX, int mouseY, float frameTime) {
        IScrollable.super.postRender(mouseX, mouseY, frameTime);
    }

    public void superRender(MatrixStack matrixStack, int mouseX, int mouseY, float frameTime) {
        super.render(matrixStack, mouseX, mouseY, frameTime);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float frameTime) {
        super.render(matrixStack, mouseX, mouseY, frameTime);
        if(isVisible()) {
            renderBackground(matrixStack, mouseX, mouseY, frameTime);
            preRender(matrixStack, mouseX, mouseY, frameTime);
            renderRects(matrixStack, mouseX, mouseY, frameTime);
            postRender(mouseX, mouseY, frameTime);
        }
    }

    @Override
    public List<ITextComponent> getToolTip(int x, int y) {
        List<ITextComponent> toolTip = null;
        for (IRect rect : rects.values()) {
            if (rect instanceof IDrawableRect) {
                toolTip = ((IDrawableRect) rect).getToolTip(x, y);
            }
            if (toolTip != null) {
                return toolTip;
            }
        }
        return null;
    }

    @Override
    public void setEnabled(boolean enabled) {
        this.isEnabled = enabled;
        for (IRect rect : rects.values()) {
            if(rect instanceof IClickable) {
                ((IClickable) rect).setEnabled(enabled);
            }
            if (rect instanceof IGuiFrame) {
                ((IGuiFrame) rect).setEnabled(enabled);
            }
        }
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    @Override
    public void setVisible(boolean visible) {
        this.isVisible = visible;
        for (IRect rect : rects.values()) {
            if(rect instanceof IClickable) {
                ((IClickable) rect).setVisible(visible);
            }
            if (rect instanceof IGuiFrame) {
                ((IGuiFrame) rect).setVisible(visible);
            }
        }
    }

    @Override
    public boolean isVisible() {
        return isVisible;
    }

    @Override
    public float getZLevel() {
        return zLevel;
    }

    @Override
    public IDrawable setZLevel(float zLevel) {
        this.zLevel = zLevel;
        for (IRect rect : rects.values()) {
            if (rect instanceof IDrawable) {
                ((IDrawable) rect).setZLevel(zLevel);
            }
        }

        return this;
    }

    @Override
    public void initGrowth() {
        super.initGrowth();
        for(IRect rect : rects.values()) {
            rect.initGrowth();
        }
        refreshRects();
    }

    // FIXME: this is just a workaround... maybe figure out why this is needed in the first place and fix
    public void refreshRects() {
        if (rects.size() > 0) {
            {
                // all boxes linked from top to bottom
                if (startTop) {
                    rects.get(0).setTop(finalTop());
                    // all boxes lined from bottom to top
                } else {
                    rects.get(rects.size() - 1).setBottom(this.finalBottom());
                }

                for (int i = 0; i< rects.size(); i++) {
                    // centered height
                    rects.get(i).setTop(rects.get(i).finalTop());
                    /** sets the rects centered horizontally */
                    rects.get(i).setLeft(this.centerx() - rects.get(i).finalWidth() * 0.5);
                }
            }
        }

        getBackground().ifPresent(rect-> {
            rect.setPosition(this.getPosition());
            rect.setWidth(this.finalWidth());
            rect.setHeight(this.finalHeight());
        });
    }

    @Override
    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    @Override
    public int getButtonSize() {
        return this.buttonSize;
    }

    @Override
    public void setButtonSize(int buttonSize) {
        this.buttonSize = buttonSize;
    }

    @Override
    public int getTotalSize() {
        return this.totalSize;
    }

    @Override
    public int getCurrentScrollPixels() {
        return this.currentScrollPixels;
    }

    @Override
    public void setCurrentScrollPixels(int scrollPixels) {
        this.currentScrollPixels = scrollPixels;
    }

    @Override
    public double getScrollAmount() {
        return scrollAmount;
    }

    @Override
    public void setScrollAmount(double scrollAmount) {
        this.scrollAmount = scrollAmount;
    }

    @Override
    public void update(double mouseX, double mouseY) {
        for (IRect rect : rects.values()) {
            if (rect instanceof IGuiFrame) {
                ((IGuiFrame) rect).update(mouseX, mouseY);
            }
        }
    }

    @Override
    public IRect setWH(MusePoint2D wh) {
        return this;
    }

    @Override
    public void doThisOnChange() {
        refreshRects();
        super.doThisOnChange();
    }
}