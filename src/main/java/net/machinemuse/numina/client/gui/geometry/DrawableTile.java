package net.machinemuse.numina.client.gui.geometry;

import net.machinemuse.numina.client.render.RenderState;
import net.machinemuse.numina.common.math.Colour;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

public class DrawableTile extends MuseRect {
    Colour backgroundColour;
    Colour borderColour;
    float lineWidth = 4.0F;

    public DrawableTile(double left, double top, double right, double bottom, boolean growFromMiddle,
                        Colour backgroundColour,
                        Colour borderColour) {
        super(left, top, right, bottom, growFromMiddle);
        this.backgroundColour = backgroundColour;
        this.borderColour = borderColour;
    }

    public void setLineWidth(float lineWidthIn) {
        lineWidth = lineWidthIn;
    }

    public DrawableTile(double left, double top, double right, double bottom,
                        Colour backgroundColour,
                        Colour borderColour) {
        super(left, top, right, bottom, false);
        this.backgroundColour = backgroundColour;
        this.borderColour = borderColour;
    }

    public DrawableTile(MusePoint2D ul, MusePoint2D br,
                        Colour backgroundColour,
                        Colour borderColour) {
        super(ul, br);
        this.backgroundColour = backgroundColour;
        this.borderColour = borderColour;
    }

    @Override
    public DrawableTile copyOf() {
        return new DrawableTile(super.left(), super.top(), super.right(), super.bottom(), backgroundColour, borderColour);
    }

    @Override
    public DrawableTile setLeft(double value) {
        super.setLeft(value);
        return this;
    }

    @Override
    public DrawableTile setRight(double value) {
        super.setRight(value);
        return this;
    }

    @Override
    public DrawableTile setTop(double value) {
        super.setTop(value);
        return this;
    }

    @Override
    public DrawableTile setBottom(double value) {
        super.setBottom(value);
        return this;
    }

    @Override
    public DrawableTile setWidth(double value) {
        super.setWidth(value);
        return this;
    }

    @Override
    public DrawableTile setHeight(double value) {
        super.setHeight(value);
        return this;
    }

    void vertices() {
        GlStateManager.glVertex3f((float)left(), (float)top(), 1);
        GlStateManager.glVertex3f((float)right(), (float)top(), 1);
        GlStateManager.glVertex3f((float)right(), (float)bottom(), 1);
        GlStateManager.glVertex3f((float)left(), (float)bottom(), 1);
    }

    boolean smoothing = true;
    public void setSmoothing(boolean smoothingIn) {
        smoothing = smoothingIn;
    }

    public void preDraw() {
        RenderState.on2D();
        RenderState.texturelessOn();

        if (smoothing) {
            // makes the lines and radii nicer
            GL11.glEnable(GL11.GL_LINE_SMOOTH);
            GL11.glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_NICEST);
        } else {
            GL11.glDisable(GL11.GL_LINE_SMOOTH);
            GL11.glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_FASTEST);
        }
    }

    public void postDraw() {
        Colour.WHITE.doGL();
        RenderState.texturelessOff();
        RenderState.glowOff();
    }

    public void draw() {
        float lineWidth = GL11.glGetFloat(GL11.GL_LINE_WIDTH);
        boolean smooth  = GL11.glIsEnabled(GL11.GL_LINE_SMOOTH);

        preDraw();

        GL11.glBegin(GL11.GL_POLYGON);
        backgroundColour.doGL();
        vertices();
        GL11.glEnd();

        GL11.glLineWidth(lineWidth);
        GL11.glBegin(GL11.GL_LINE_LOOP);

        borderColour.doGL();
        vertices();
        GL11.glEnd();
        postDraw();

        if (!smooth) {
            GL11.glDisable(GL11.GL_LINE_SMOOTH);
        }
        GL11.glLineWidth(lineWidth);
    }


    public DrawableTile setBackgroundColour(Colour insideColour) {
        this.backgroundColour = insideColour;
        return this;
    }

    public DrawableTile setBorderColour(Colour outsideColour) {
        this.borderColour = outsideColour;
        return this;
    }
}
