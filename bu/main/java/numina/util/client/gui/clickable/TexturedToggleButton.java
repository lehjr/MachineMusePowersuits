package lehjr.numina.util.client.gui.clickable;

import com.mojang.blaze3d.matrix.PoseStack;
import lehjr.numina.util.client.gui.GuiIcon;
import lehjr.numina.util.client.gui.gemoetry.MusePoint2D;
import lehjr.numina.util.client.gui.gemoetry.RelativeRect;
import lehjr.numina.util.math.Color;
import net.minecraft.util.ResourceLocation;

public class TexturedToggleButton extends AbstractTexturedButton {
    protected boolean isStateOn = false;
    protected double texDiffX = 0;
    protected double texDiffY = 0;

    public TexturedToggleButton(double left, double top, double right, double bottom, boolean growFromMiddle, Color backgroundColorEnabled, Color backgroundColorDisabled, Color borderColorEnabled, Color borderColorDisabled, double textureWidth, double textureHeight, ResourceLocation textureLocation) {
        super(left, top, right, bottom, growFromMiddle, backgroundColorEnabled, backgroundColorDisabled, borderColorEnabled, borderColorDisabled, textureWidth, textureHeight, textureLocation);
    }

    public TexturedToggleButton(double left, double top, double right, double bottom, Color backgroundColorEnabled, Color backgroundColorDisabled, Color borderColorEnabled, Color borderColorDisabled, double textureWidth, double textureHeight, ResourceLocation textureLocation) {
        super(left, top, right, bottom, backgroundColorEnabled, backgroundColorDisabled, borderColorEnabled, borderColorDisabled, textureWidth, textureHeight, textureLocation);
    }

    public TexturedToggleButton(MusePoint2D ul, MusePoint2D br, Color backgroundColorEnabled, Color backgroundColorDisabled, Color borderColorEnabled, Color borderColorDisabled, double textureWidth, double textureHeight, ResourceLocation textureLocation) {
        super(ul, br, backgroundColorEnabled, backgroundColorDisabled, borderColorEnabled, borderColorDisabled, textureWidth, textureHeight, textureLocation);
    }

    public TexturedToggleButton(RelativeRect ref, Color backgroundColorEnabled, Color backgroundColorDisabled, Color borderColorEnabled, Color borderColorDisabled, double textureWidth, double textureHeight, ResourceLocation textureLocation) {
        super(ref, backgroundColorEnabled, backgroundColorDisabled, borderColorEnabled, borderColorDisabled, textureWidth, textureHeight, textureLocation);
    }

    public TexturedToggleButton setState(boolean state) {
        this.isStateOn = state;
        return this;
    }

    public boolean getState() {
        return isStateOn;
    }

    public TexturedToggleButton setTexDiffX(double diff) {
        this.texDiffX = diff;
        return this;
    }

    public TexturedToggleButton setTexDiffY(double diff) {
        this.texDiffY = diff;
        return this;
    }

    @Override
    public void onPressed() {
        this.isStateOn =! this.isStateOn;
        super.onPressed();
    }

    @Override
    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        if(this.isVisible) {
            super.render(matrixStack, mouseX, mouseY, partialTicks);
            Color color;
            if (this.isEnabled) {
                color = this.hitBox(mouseX, mouseY) ? Color.LIGHT_BLUE.withAlpha(0.6F) : Color.WHITE;
            } else {
                color = Color.RED.withAlpha(0.6F);
            }
            matrixStack.pushPose();
            matrixStack.translate(0,0,100);
            GuiIcon.renderTextureWithColor(this.textureLocation, matrixStack,
                    // the actual coordinates of the target rendering area
                    left(),
                    right(),
                    top(),
                    bottom(),
                    getZLevel(), // depth of the rendering area
                    iconWidth, // width of icon to be rendered
                    iconHeight, // width of icon to be rendered
                    texStartX + (isStateOn ? texDiffX : 0),
                    texStartY + (isStateOn ? texDiffY : 0),
                    textureWidth, // width of entire texture icon is in
                    textureHeight, // height of entire texture icon is in
                    // color mask
                    color);
            matrixStack.popPose();
        }
    }
}
