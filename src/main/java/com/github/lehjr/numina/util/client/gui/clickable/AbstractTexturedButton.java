package com.github.lehjr.numina.util.client.gui.clickable;

import com.github.lehjr.numina.util.client.gui.gemoetry.MusePoint2D;
import com.github.lehjr.numina.util.client.gui.gemoetry.RelativeRect;
import com.github.lehjr.numina.util.math.Colour;
import net.minecraft.util.ResourceLocation;

public class AbstractTexturedButton extends Button {
    ResourceLocation textureLocation;
    double texStartX;
    double texStartY;
    double textureWidth;
    double textureHeight;
    double iconWidth;
    double iconHeight;

    public AbstractTexturedButton(double left, double top, double right, double bottom, boolean growFromMiddle,
                                  Colour backgroundColourEnabled,
                                  Colour backgroundColourDisabled,
                                  Colour borderColourEnabled,
                                  Colour borderColourDisabled,
                                  double textureWidth,
                                  double textureHeight,
                                  ResourceLocation textureLocation) {
        super(left, top, right, bottom, growFromMiddle, backgroundColourEnabled, backgroundColourDisabled, borderColourEnabled, borderColourDisabled);
        this.textureWidth = this.iconWidth = textureWidth;
        this.textureHeight = this.iconHeight = textureHeight;
        this.textureLocation = textureLocation;
        this.texStartX = 0;
        this.texStartY = 0;
    }

    public AbstractTexturedButton(double left, double top, double right, double bottom,
                                  Colour backgroundColourEnabled,
                                  Colour backgroundColourDisabled,
                                  Colour borderColourEnabled,
                                  Colour borderColourDisabled,
                                  double textureWidth,
                                  double textureHeight,
                                  ResourceLocation textureLocation) {
        super(left, top, right, bottom, backgroundColourEnabled, backgroundColourDisabled, borderColourEnabled, borderColourDisabled);
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;
        this.textureLocation = textureLocation;
        this.texStartX = 0;
        this.texStartY = 0;
    }

    public AbstractTexturedButton(MusePoint2D ul, MusePoint2D br,
                                  Colour backgroundColourEnabled,
                                  Colour backgroundColourDisabled,
                                  Colour borderColourEnabled,
                                  Colour borderColourDisabled,
                                  double textureWidth,
                                  double textureHeight,
                                  ResourceLocation textureLocation) {
        super(ul, br, backgroundColourEnabled, backgroundColourDisabled, borderColourEnabled, borderColourDisabled);
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;
        this.textureLocation = textureLocation;
        this.texStartX = 0;
        this.texStartY = 0;
    }

    public AbstractTexturedButton(RelativeRect ref,
                                  Colour backgroundColourEnabled,
                                  Colour backgroundColourDisabled,
                                  Colour borderColourEnabled,
                                  Colour borderColourDisabled,
                                  double textureWidth,
                                  double textureHeight,
                                  ResourceLocation textureLocation) {
        super(ref, backgroundColourEnabled, backgroundColourDisabled, borderColourEnabled, borderColourDisabled);
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;
        this.textureLocation = textureLocation;
        this.texStartX = 0;
        this.texStartY = 0;
    }

    public AbstractTexturedButton setTextureStartX(double xOffset) {
        this.texStartX = xOffset;
        return this;
    }

    public AbstractTexturedButton setTextureStartY(double yOffset) {
        this.texStartY = yOffset;
        return this;
    }

    public AbstractTexturedButton setIconWidth(double iconWidth) {
        this.iconWidth = iconWidth;
        return this;
    }

    public AbstractTexturedButton setIconHeight(double iconHeight) {
        this.iconHeight = iconHeight;
        return this;
    }

    public AbstractTexturedButton setTextureWidth(double textureWidth) {
        this.textureWidth = textureWidth;
        return this;
    }

    public AbstractTexturedButton setTextureHeight(double textureHeight) {
        this.textureHeight = textureHeight;
        return this;
    }
}
