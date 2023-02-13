package lehjr.numina.client.gui.clickable.button;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import lehjr.numina.client.gui.clickable.Clickable;
import lehjr.numina.client.gui.gemoetry.MusePoint2D;
import lehjr.numina.client.gui.gemoetry.Rect;
import lehjr.numina.client.render.IconUtils;
import lehjr.numina.common.string.StringUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;


/**
 * Vanilla styled button based on vanilla button
 */
public class VanillaButton extends Clickable {
    ITextComponent label;


    /**
     * For a button with a set width
     * @param left
     * @param top
     * @param width
     * @param label
     * @param enabled
     */
    public VanillaButton(double left, double top, double width, ITextComponent label, boolean enabled) {
        super(new Rect(left, top, left + width, top + 20));
        this.label = label;
        this.setEnabled(enabled);
    }

    public VanillaButton(double left, double top, ITextComponent label, boolean enabled) {
        this(new MusePoint2D(left, top), label, enabled);
    }

    public VanillaButton(MusePoint2D ul, ITextComponent label, boolean enabled) {
        super(ul, MusePoint2D.ZERO, false);
        this.label = label;
        this.setWH(new MusePoint2D(StringUtils.getStringWidth(label.getString()) + 20, 20));
        this.setEnabled(enabled);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float frameTime) {
        renderButton(matrixStack, mouseX, mouseY, frameTime);
        renderBg(matrixStack, mouseX, mouseY, frameTime);
    }

    public static final int UNSET_FG_COLOR = -1;
    protected int packedFGColor = UNSET_FG_COLOR;

    public int getFGColor() {
        if (packedFGColor != UNSET_FG_COLOR) return packedFGColor;
        return this.isEnabled() ? 16777215 : 10526880; // White : Light Grey
    }

    float alpha = 1;

    protected int getYImage(boolean pIsHovered) {
        int i = 1;
        if (!this.isEnabled()) {
            i = 0;
        } else if (pIsHovered) {
            i = 2;
        }

        return i;
    }

    public void renderButton(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        Minecraft minecraft = Minecraft.getInstance();
        FontRenderer fontrenderer = minecraft.font;
        getMinecraft().getTextureManager().bind(Widget.WIDGETS_LOCATION);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, this.alpha);
        int i = this.getYImage(containsPoint(mouseX, mouseY));
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
        IconUtils.INSTANCE.blit(matrixStack, this.left(), this.top(), 0, 46 + i * 20, this.width() / 2, this.height());
        IconUtils.INSTANCE.blit(matrixStack, this.left() + this.width() / 2, this.top(), 200 - this.width() / 2, 46 + i * 20, this.width() / 2, this.height());
        this.renderBg(matrixStack, mouseX, mouseY, partialTicks);
        int j = getFGColor();
        getMinecraft().screen.drawCenteredString(matrixStack, fontrenderer, this.getLabel(), (int) (this.left() + this.width() / 2), (int) (this.top() + (this.height() - 8) / 2), j | MathHelper.ceil(this.alpha * 255.0F) << 24);
    }

    protected void renderBg(MatrixStack pMatrixStack, int pMouseX, int pMouseY, float frameTime) {

    }

    public void setLabel(ITextComponent label) {
        this.label = label;
    }

    public ITextComponent getLabel() {
        return label;
    }
}