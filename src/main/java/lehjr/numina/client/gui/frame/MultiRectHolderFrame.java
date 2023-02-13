package lehjr.numina.client.gui.frame;

import com.mojang.blaze3d.matrix.MatrixStack;
import lehjr.numina.client.gui.clickable.IClickable;
import lehjr.numina.client.gui.gemoetry.IDrawable;
import lehjr.numina.client.gui.gemoetry.IDrawableRect;
import lehjr.numina.client.gui.gemoetry.IRect;
import net.minecraft.util.text.ITextComponent;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * A bunch of rectangles laid out either vertically or horizontally
 */

public class MultiRectHolderFrame<T extends List<IRect>> extends GUISpacer {
    protected double margin;
    protected double maxHeight = -1;
    protected double maxWidth = -1;

    float zLevel;
    boolean isEnabled = true;
    boolean isVisible = true;
    @Nullable
    IDrawableRect background = null;
    List<IRect> rects = new ArrayList<>();
    boolean horizontalLayout;
    boolean startTopLeft;
    /**
     *  Note, all rectangles should have the same width for vertical layout, or same height for horizontal layout.
     *
     * @param horizontalLayout if true, layout will be like books on a shelf, or vertical (layered from top to bottom)
     * @param startTopLeft determines where the first link in the chain is, since that is the one that will determine which points to set
     *                     when the outer rectangle is moved. For instance, false will start on the bottom for vertical layout, or right for
     *                     horizontal layout. The rest in the chain should be using setMe-LOCATION-Of.
     * @param minWidth
     * @param minHeight
     */
    public MultiRectHolderFrame(boolean horizontalLayout, boolean startTopLeft, double minWidth, double minHeight) {
        super(minWidth, minHeight);
        this.horizontalLayout = horizontalLayout;
        this.startTopLeft = startTopLeft;
    }

    public MultiRectHolderFrame(boolean horizontalLayout, boolean startTopLeft, double minWidth, double minHeight, double maxWidth, double maxHeight) {
        super(minWidth, minHeight);
        this.horizontalLayout = horizontalLayout;
        this.startTopLeft = startTopLeft;
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
    public MultiRectHolderFrame addRect(IRect rect) {
        if (rects.size() >0) {
            if (horizontalLayout) {
                if (startTopLeft) {
                    rect.setRightOf(rects.get(rects.size() -1));
                } else {
                    rect.setLeftOf(rects.get(rects.size() -1));
                }
            } else {
                if (startTopLeft) {
                    rect.setBelow(rects.get(rects.size() -1));
                } else {
                    rect.setAbove(rects.get(rects.size() -1));
                }
            }
        }
        rects.add(rect);
        return this;
    }

    public void removeRect(IRect rectIn) {
        List<IRect> tempList = rects.stream().filter(rect-> !rect.equals(rectIn)).collect(Collectors.toList());
        rects = new ArrayList<>();
        tempList.stream().forEach(rect ->addRect(rectIn));
        doneAdding();
    }

    public MultiRectHolderFrame setMaxHeight(double maxHeight) {
        this.maxHeight = maxHeight;
        return this;
    }

    public MultiRectHolderFrame setMaxWidth(double maxWidth) {
        this.maxWidth = maxWidth;
        return this;
    }

    public MultiRectHolderFrame setMinHeight(double minHeight) {
        super.setHeight(minHeight);
        return this;
    }

    public MultiRectHolderFrame setMinWidth(double minWidth) {
        super.setWidth(minWidth);
        return this;
    }

    public Optional<IRect> getLast() {
        return Optional.ofNullable(rects.size() > 0 ? (rects.get(rects.size() -1)) : null);
    }

    public void setRects(List<IRect> rects){
        this.rects = rects;
    }

    public List<IRect> getRects() {
        return rects;
    }

    /**
     * Automatically set width and height
     */
    public void doneAdding() {
        double finalVal = 0;
        double highestVal = 0;
        for (IRect rect : rects) {
            // single max height, multiple widths
            if (horizontalLayout) {
                finalVal += rect.width();
                if (rect.height() > highestVal) {
                    highestVal = rect.height();
                }
                // single max width, multiple heights
            } else {
                finalVal += rect.height();
                if (rect.width() > highestVal) {
                    highestVal = rect.width();
                }
            }
        }
        highestVal += margin * 2;
        if (horizontalLayout) {
            if (maxWidth > 0) {
                super.setWidth(Math.min(Math.max(finalVal, super.width()), maxWidth));
            } else {
                super.setWidth(Math.max(finalVal, super.width()));
            }
            if (maxHeight > 0) {
                super.setHeight(Math.min(Math.max(highestVal, super.height()), maxHeight));
            } else {
                super.setHeight(Math.max(highestVal, super.height()));
            }
        } else {
            if (maxWidth > 0) {
                super.setWidth(Math.min(Math.max(highestVal, super.width()), maxWidth));
            } else {
                super.setWidth(Math.max(highestVal, super.width()));
            }
            if (maxHeight > 0) {
                super.setHeight(Math.min(Math.max(finalVal, super.height()), maxHeight));
            } else {
                super.setHeight(Math.max(finalVal, super.height()));
            }
        }
    }

    public MultiRectHolderFrame setBackground(IDrawableRect background) {
        this.background = background;
        return this;
    }

    Optional<IDrawableRect> getBackground() {
        return Optional.ofNullable(this.background);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        for (IRect rect : rects) {
            if (rect instanceof IGuiFrame && ((IGuiFrame) rect).mouseClicked(mouseX, mouseY, button)) {
                return true;
            } else if (rect instanceof IClickable && ((IClickable) rect).mouseClicked(mouseX, mouseY, button)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        for (IRect rect : rects) {
            if (rect instanceof IGuiFrame && ((IGuiFrame) rect).mouseReleased(mouseX, mouseY, button)) {
                return true;
            } else if (rect instanceof IClickable && ((IClickable) rect).mouseReleased(mouseX, mouseY, button)) {
                return true;
            }
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double dWheel) {
        for (IRect rect : rects) {
            if (rect instanceof IGuiFrame && ((IGuiFrame) rect).mouseScrolled(mouseX, mouseY, dWheel)) {
                return true;
            }
        }
        return false;
    }

    public void renderRects(MatrixStack matrixStack, int mouseX, int mouseY, float frameTime) {
        if(isVisible()) {
            for (IRect rect : rects) {
                if (rect instanceof IDrawable) {
                    ((IDrawable) rect).render(matrixStack, mouseX, mouseY, frameTime);
                }
            }
        }
    }

    public void renderBackground(MatrixStack matrixStack, int mouseX, int mouseY, float frameTime) {
        getBackground().ifPresent(background -> background.render(matrixStack, mouseX, mouseY, frameTime));
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
        for (IRect rect : rects) {
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
        for (IRect rect : rects) {
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
        for (IRect rect : rects) {
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
        for (IRect rect : rects) {
            if (rect instanceof IDrawable) {
                ((IDrawable) rect).setZLevel(zLevel);
            }
        }

        return this;
    }

//    @Override
//    public void initGrowth() {
//        super.initGrowth();
//        for(IRect rect : rects) {
//            rect.initGrowth();
//        }
//        refreshRects();
//    }

    // FIXME: this is just a workaround... maybe figure out why this is needed in the first place and fix
    public void refreshRects() {
        if (rects.size() > 0) {
            if (horizontalLayout) {
                // find leftmost box and set the left value
                if (startTopLeft) {
                    rects.get(0).setLeft(left());
                } else {
                    rects.get(rects.size() - 1).setRight(right());
                }

                for (int i = 0; i< rects.size(); i++) {
                    /** sets the rects centered vertically */
                    rects.get(i).setTop(this.centerY() - rects.get(i).height() * 0.5);
                    rects.get(i).setLeft(rects.get(i).left());
                }

            } else {
                // all boxes linked from top to bottom
                if (startTopLeft) {
                    rects.get(0).setTop(top());
                    // all boxes lined from bottom to top
                } else {
                    rects.get(rects.size() - 1).setBottom(this.bottom());
                }

                for (int i = 0; i< rects.size(); i++) {
                    // centered height
                    rects.get(i).setTop(rects.get(i).top());
                    /** sets the rects centered horizontally */
                    rects.get(i).setLeft(this.centerX() - rects.get(i).width() * 0.5);
                }
            }
        }

        getBackground().ifPresent(rect-> {
            rect.setPosition(this.center());
            rect.setWidth(this.width());
            rect.setHeight(this.height());
        });
    }

    @Override
    public void update(double mouseX, double mouseY) {
        for (IRect rect : rects) {
            if (rect instanceof IGuiFrame) {
                ((IGuiFrame) rect).update(mouseX, mouseY);
            }
        }
    }

//    @Override
//    public IRect setWH(MusePoint2D wh) {
//        return this;
//    }
//
//    @Override
//    public void doThisOnChange() {
//        refreshRects();
//        super.doThisOnChange();
//    }
}
