package lehjr.numina.client.gui.clickable.slider;

import com.mojang.blaze3d.systems.RenderSystem;
import lehjr.numina.client.gui.geometry.MusePoint2D;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;


/**
 * uses vanilla texture for vanilla look and feel
 */
public class VanillaSlider extends AbstractSlider {
    public Component displayString = Component.empty();
    public Component message = Component.empty();
    public String suffix = "";
    boolean showDecimal = false;
    int precision = 2;
    boolean drawString = true;
    private static final ResourceLocation SLIDER_SPRITE = new ResourceLocation("widget/slider");
    private static final ResourceLocation HIGHLIGHTED_SPRITE = new ResourceLocation("widget/slider_highlighted");
    private static final ResourceLocation SLIDER_HANDLE_SPRITE = new ResourceLocation("widget/slider_handle");
    private static final ResourceLocation SLIDER_HANDLE_HIGHLIGHTED_SPRITE = new ResourceLocation("widget/slider_handle_highlighted");

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

    float alpha = 1F;

    @Override
    public void renderBg(GuiGraphics gfx, int mouseX, int mouseY, float frameTime) {
            gfx.setColor(1.0F, 1.0F, 1.0F, this.alpha);
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.enableDepthTest();
            gfx.blitSprite(this.containsPoint(mouseX, mouseY) ? HIGHLIGHTED_SPRITE : SLIDER_SPRITE, (int)this.left(), (int)this.top(), (int)this.width(), (int)this.height());
    }

    Component getMessage() {
        return message;
    }

    @Override
    public void renderKnob(GuiGraphics gfx, int mouseX, int mouseY, float frameTime) {
        gfx.blitSprite(this.containsPoint(mouseX, mouseY) ? SLIDER_HANDLE_HIGHLIGHTED_SPRITE : SLIDER_HANDLE_SPRITE, (int)this.left() + (int)(this.sliderValue * (this.width()- 8)), (int)this.top(), 8, (int)this.height());
        gfx.setColor(1.0F, 1.0F, 1.0F, 1.0F);
        Font fontrenderer = getMinecraft().font;
        int j = getFGColor();
        gfx.drawCenteredString(fontrenderer, this.getMessage(), (int) (this.left() + this.width() / 2), (int) (this.top() + (this.height() - 8) / 2), j | Mth.ceil(255.0F) << 24);
        updateSlider();
    }

    public void setDisplayString(Component dispString) {
        this.displayString = dispString;
    }

    public void setMessage(Component pMessage) {
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
            setMessage(Component.empty().append(displayString).append(valString).append(suffix));
        }
    }
}
