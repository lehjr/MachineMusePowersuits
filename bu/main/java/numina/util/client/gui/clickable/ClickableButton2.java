package lehjr.numina.util.client.gui.clickable;


import com.mojang.blaze3d.matrix.PoseStack;
import lehjr.numina.util.client.gui.gemoetry.DrawableTile;
import lehjr.numina.util.client.gui.gemoetry.IDrawable;
import lehjr.numina.util.client.gui.gemoetry.MusePoint2D;
import lehjr.numina.util.client.render.MuseRenderer;
import lehjr.numina.util.math.Color;
import net.minecraft.util.text.Component;

/**
 * @author MachineMuse
 */
public class ClickableButton2 extends DrawableTile implements IClickable {
    boolean isVisible = true;
    boolean isEnabled = true;
    protected Component label;
    protected MusePoint2D radius;
    private Color enabledTopBorder  = Color.WHITE.withAlpha(0.8F);
    private Color enabledBottomBorder  = Color.getGreyscale(0.216F, 1.0F);
    private Color enabledBackground = Color.GREY_GUI_BACKGROUND;
    private Color disabledTopBorder = Color.WHITE.withAlpha(0.8F);

    private Color disabledBottomBorder = Color.getGreyscale(0.216F, 1.0F);
    private Color disabledBackground = Color.DARK_GREY.interpolate(Color.WHITE, 0.2F);
    private IPressable onPressed;
    private IReleasable onReleased;

    public ClickableButton2(Component label, MusePoint2D position, boolean enabled) {
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

    public ClickableButton2 setEnabledTopBorder(Color enabledTopBorder) {
        this.enabledTopBorder = enabledTopBorder;
        return this;
    }

    public ClickableButton2 setEnabledBottomBorder(Color enabledBottomBorder) {
        this.enabledBottomBorder = enabledBottomBorder;
        return this;
    }

    public ClickableButton2 setEnabledBackground(Color enabledBackground) {
        this.enabledBackground = enabledBackground;
        return this;
    }

    public ClickableButton2 setDisabledTopBorder(Color disabledTopBorder) {
        this.disabledTopBorder = disabledTopBorder;
        return this;
    }

    public ClickableButton2 setDisabledBottomBorder(Color disabledBottomBorder) {
        this.disabledBottomBorder = disabledBottomBorder;
        return this;
    }

    public ClickableButton2 setDisabledBackground(Color disabledBackground) {
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
    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
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
    public void renderButton(PoseStack matrixStack, int mouseX, int mouseY, float frameTIme) {
        if (isVisible) {
            this.setBackgroundColor(isEnabled() ? enabledBackground : disabledBackground);
            this.setTopBorderColor(isEnabled() ? enabledTopBorder : disabledTopBorder);
            this.setBottomBorderColor(isEnabled() ? enabledBottomBorder : disabledBottomBorder);
            super.render(matrixStack, mouseX, mouseY, frameTIme);
        }
    }

    /**
     * Container based GUI's should use the separate button and text renderer
     * Call this from the container GUI's drawGuiContainerForegroundLayer
     * @param mouseX
     * @param mouseY
     */
    public void renderText(PoseStack matrixStack, int mouseX, int mouseY) {
        if (isVisible()) {
            if (label.getString().contains("\n")) {
                String[] s = label.getString().split("\n");
                for (int i = 0; i < s.length; i++) {
                    MuseRenderer.drawShadowedStringCentered(matrixStack, s[i], getPosition().getX(), getPosition().getY() + (i * MuseRenderer.getStringHeight() + 1));
                }
            } else {
                MuseRenderer.drawCenteredText(matrixStack, this.label, getPosition().getX(), getPosition().getY() + 1, new Color(16777215));
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

    public ClickableButton2 setLable(Component label) {
        this.label = label;
        return this;
    }

    public Component getLabel() {
        return label;
    }
}