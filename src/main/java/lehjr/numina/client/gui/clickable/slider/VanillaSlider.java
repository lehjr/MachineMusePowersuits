package lehjr.numina.client.gui.clickable.slider;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import lehjr.numina.client.gui.geometry.MusePoint2D;
import lehjr.numina.client.render.IconUtils;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

/**
 * uses vanilla texture for vanilla look and feel
 */
public class VanillaSlider extends AbstractSlider {
    public ITextComponent displayString = new StringTextComponent("");
    public ITextComponent message = new StringTextComponent("");
    public String suffix = "";
    boolean showDecimal = false;
    int precision = 2;
    boolean drawString = true;


    public VanillaSlider(double left, double top, double width, String id) {
        super(left, top, left + width, top + 20, id, true);
    }

    public VanillaSlider(MusePoint2D ul, double width, String id) {
        super(ul, ul.plus(width, 20), id, true);
    }

    public int getFGColor() {
        return this.active ? 16777215 : 10526880; // White : Light Grey
    }
    boolean active = false;

    public void setActive(boolean active) {
        this.active = active;
    }


    @Override
    public void renderBg(MatrixStack matrixStack, int mouseX, int mouseY, float frameTime) {
        FontRenderer fontrenderer = getMinecraft().font;
        getMinecraft().getTextureManager().bind(Widget.WIDGETS_LOCATION);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        int i = 0;
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
        IconUtils.INSTANCE.blit(matrixStack,
                this.left(), // left
                this.top(), // top
                0,                  // texture startx
                46 + i * 20,        // texture starty
                (this.width() * 0.5),
                this.height());

        IconUtils.INSTANCE.blit(matrixStack,
                centerX(),
                this.top(),
                200 - this.width() * 0.5,
                46 + i * 20,
                this.width() * 0.5,
                this.height());
//            this.renderBg(matrixStack, mouseX, mouseY);
        int j = getFGColor();
        getMinecraft().screen.drawCenteredString(matrixStack, fontrenderer, this.getMessage(), (int) (this.left() + this.width() / 2), (int) (this.top() + (this.height() - 8) / 2), j | MathHelper.ceil(255.0F) << 24);
    }

    ITextComponent getMessage() {
        return message;
    }

    @Override
    public void renderKnob(MatrixStack matrixStack, int mouseX, int mouseY, float frameTime) {
        getMinecraft().getTextureManager().bind(Widget.WIDGETS_LOCATION);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        int i = (this.containsPoint(mouseX, mouseY) ? 2 : 1) * 20;
        IconUtils.INSTANCE.blit(matrixStack,
                (this.left() + (this.sliderValue * (this.width() - 8))),
                this.top(),
                0,
                46 + i,
                4,
                20);
        IconUtils.INSTANCE.blit(matrixStack,
                (this.left() + (this.sliderValue * (this.width() - 8)) + 4),
                this.top(),
                196,
                46 + i,
                4,
                20);

        updateSlider();
    }

    public void setDisplayString(ITextComponent dispString) {
        this.displayString = dispString;
    }

    public void setMessage(ITextComponent pMessage) {
        this.message = pMessage;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public void setShowDecimal(boolean showDecimal) {
        this.showDecimal = showDecimal;
    }

    public void setPrecision(int precision) {
        this.precision = precision;
    }

    public void setDrawString(boolean drawString) {
        this.drawString = drawString;
    }

    public void updateSlider() {
        String valString;

        if (showDecimal) {
            valString = Double.toString( (sliderValue * (maxValue - minValue) + minValue));

            if (valString.substring(valString.indexOf(".") + 1).length() > precision) {
                valString = valString.substring(0, valString.indexOf(".") + precision + 1);

                if (valString.endsWith(".")) {
                    valString = valString.substring(0, valString.indexOf(".") + precision);
                }
            } else {
                while (valString.substring(valString.indexOf(".") + 1).length() < precision) {
                    valString = valString + "0";
                }
            }
        } else {
            valString = Integer.toString((int) Math.round(sliderValue * (maxValue - minValue) + minValue));
        }

        if (drawString) {
            setMessage(new StringTextComponent("").append(displayString).append(valString).append(suffix));
        }
    }
}
