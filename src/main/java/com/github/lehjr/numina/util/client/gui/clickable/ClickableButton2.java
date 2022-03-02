package com.github.lehjr.numina.util.client.gui.clickable;


import com.github.lehjr.numina.util.client.gui.gemoetry.DrawableTile;
import com.github.lehjr.numina.util.client.gui.gemoetry.IDrawable;
import com.github.lehjr.numina.util.client.gui.gemoetry.MusePoint2D;
import com.github.lehjr.numina.util.client.render.MuseRenderer;
import com.github.lehjr.numina.util.math.Colour;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.widget.list.KeyBindingList;
import net.minecraft.util.text.ITextComponent;

/**
 * @author MachineMuse
 */
public class ClickableButton2 extends DrawableTile implements IClickable {
    boolean isVisible = true;
    boolean isEnabled = true;
    protected ITextComponent label;
    protected MusePoint2D radius;
    private Colour enabledTopBorder  = Colour.WHITE.withAlpha(0.8F);
    private Colour enabledBottomBorder  = Colour.getGreyscale(0.216F, 1.0F);
    private Colour enabledBackground = Colour.GREY_GUI_BACKGROUND;
    private Colour disabledTopBorder = Colour.WHITE.withAlpha(0.8F);

    private Colour disabledBottomBorder = Colour.getGreyscale(0.216F, 1.0F);
    private Colour disabledBackground = Colour.DARK_GREY.interpolate(Colour.WHITE, 0.2F);
    private IPressable onPressed;
    private IReleasable onReleased;

    public ClickableButton2(ITextComponent label, MusePoint2D position, boolean enabled) {
        super(1,1,1, 1);
        this.label = label;
        this.setPosition(position);

        if (label.getString().contains("\n")) {
            String[] x = label.getString().split("\n");

            int longestIndex = 0;
            for (int i = 0; i < x.length; i++) {
                if (x[i].length() > x[longestIndex].length())
                    longestIndex = i;
            }
            this.radius = new MusePoint2D((float) (MuseRenderer.getStringWidth(x[longestIndex]) / 2F + 2F), 6 * x.length);
        } else {
            this.radius = new MusePoint2D((float) (MuseRenderer.getStringWidth(label.getString()) / 2F + 2F), 6);
        }

        setLeft(position.getX() - radius.getX());
        setTop(position.getY() - radius.getY());
        setWidth(radius.getX() * 2);
        setHeight(radius.getY() * 2);
        this.setEnabled(enabled);
    }

    public ClickableButton2 setEnabledTopBorder(Colour enabledTopBorder) {
        this.enabledTopBorder = enabledTopBorder;
        return this;
    }

    public ClickableButton2 setEnabledBottomBorder(Colour enabledBottomBorder) {
        this.enabledBottomBorder = enabledBottomBorder;
        return this;
    }

    public ClickableButton2 setEnabledBackground(Colour enabledBackground) {
        this.enabledBackground = enabledBackground;
        return this;
    }

    public ClickableButton2 setDisabledTopBorder(Colour disabledTopBorder) {
        this.disabledTopBorder = disabledTopBorder;
        return this;
    }

    public ClickableButton2 setDisabledBottomBorder(Colour disabledBottomBorder) {
        this.disabledBottomBorder = disabledBottomBorder;
        return this;
    }

    public ClickableButton2 setDisabledBackground(Colour disabledBackground) {
        this.disabledBackground = disabledBackground;
        return this;
    }

    /**
     * Container based GUI's should use the separate button and text renderer
     *
     * @param mouseX
     * @param mouseY
     * @param partialTicks
     */
    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        renderButton(matrixStack, mouseX, mouseY, partialTicks);
        renderText(matrixStack, mouseX, mouseY);
    }

    @Override
    public float getZLevel() {
        return 0;
    }

    @Override
    public IDrawable setZLevel(float zLevel) {
        return null;
    }

    /**
     * Container based GUI's should use the separate button and text renderer
     * Call this from the container GUI's main render loop
     *
     * @param mouseX
     * @param mouseY
     * @param frameTIme
     */
    public void renderButton(MatrixStack matrixStack, int mouseX, int mouseY, float frameTIme) {
        if (isVisible) {
            this.setBackgroundColour(isEnabled() ? enabledBackground : disabledBackground);
            this.setTopBorderColour(isEnabled() ? enabledTopBorder : disabledTopBorder);
            this.setBottomBorderColour(isEnabled() ? enabledBottomBorder : disabledBottomBorder);
            super.render(matrixStack, mouseX, mouseY, frameTIme);
        }
    }

    /**
     * Container based GUI's should use the separate button and text renderer
     * Call this from the container GUI's drawGuiContainerForegroundLayer
     * @param mouseX
     * @param mouseY
     */
    public void renderText(MatrixStack matrixStack, int mouseX, int mouseY) {
        if (isVisible()) {
            if (label.getString().contains("\n")) {
                String[] s = label.getString().split("\n");
                for (int i = 0; i < s.length; i++) {
                    MuseRenderer.drawShadowedStringCentered(matrixStack, s[i], getPosition().getX(), getPosition().getY() + (i * MuseRenderer.getStringHeight() + 1));
                }
            } else {
                MuseRenderer.drawCenteredText(matrixStack, this.label, getPosition().getX(), getPosition().getY() + 1, new Colour(16777215));
            }
        }
    }

    public MusePoint2D getRadius () {
        return radius.copy();
    }

    @Override
    public boolean hitBox(double x, double y) {
        return containsPoint(x, y);
    }

    @Override
    public void setEnabled(boolean enabled) {
        this.isEnabled = enabled;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    @Override
    public void setVisible(boolean visible) {
        this.isVisible = visible;
    }

    @Override
    public boolean isVisible() {
        return isVisible;
    }

    @Override
    public void setOnPressed(IPressable onPressed) {
        this.onPressed = onPressed;
    }

    @Override
    public void setOnReleased(IReleasable onReleased) {
        this.onReleased = onReleased;
    }

    @Override
    public void onPressed() {
        if (this.isVisible() && this.isEnabled() && this.onPressed != null) {
            this.onPressed.onPressed(this);
        }
    }

    @Override
    public void onReleased() {
        if (this.isVisible() && this.isEnabled() && this.onReleased != null) {
            this.onReleased.onReleased(this);
        }
    }

    public ClickableButton2 setLable(ITextComponent label) {
        this.label = label;
        return this;
    }

    public ITextComponent getLabel() {
        return label;
    }
}