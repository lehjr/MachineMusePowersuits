/*
 * Copyright (c) 2021. MachineMuse, Lehjr
 *  All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *      Redistributions of source code must retain the above copyright notice, this
 *      list of conditions and the following disclaimer.
 *
 *     Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 *  ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 *  WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 *  DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 *  ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 *  (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 *  LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 *  ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *  (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *  SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package lehjr.numina.common.math;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.VertexConsumer;
import lehjr.numina.common.base.NuminaLogger;
import net.minecraft.client.gui.GuiGraphics;
import org.joml.Vector4f;

import java.util.Objects;

/**
 * A class representing an RGBA color and various helper functions. Mainly to
 * improve readability elsewhere.
 *
 * @author MachineMuse
 */
public class Color /*implements INBTSerializable<IntTag>*/ {
    // 1/255 for faster math
    public static final float div255 = 0.003921569F;
    // Color values changed to match Java's AWT colors
    public static final Color WHITE = new Color(1F, 1F, 1F, 1F);
    public static final Color LIGHT_GRAY =new Color(0.753F, 0.753F, 0.753F,1F); // Java awt color
    public static final Color GREY =new Color(0.502F, 0.502F, 0.502F,1F); // Java awt color
    public static final Color DARK_GRAY = new Color(0.251F, 0.251F, 0.251F, 1F);
    public static final Color GREY_GUI_BACKGROUND = new Color(0.776F, 0.776F, 0.776F, 1F);
    public static final Color BLACK = new Color(0F, 0F, 0F, 1F);
    public static final Color RED = new Color(1F, 0F, 0F, 1F);
    public static final Color PINK = new Color(1F, 0.686F, 0.686F, 1F);
    public static final Color ORANGE = new Color(1F, 0.7843F, 0F, 1F);
    public static final Color YELLOW = new Color(1F, 1F, 0.0F, 1F);
    public static final Color GREEN = new Color(0.0F, 1F, 0.0F, 1F);
    public static final Color LIGHT_GREEN = new Color(0.5F, 1F, 0.5F, 1F);
    public static final Color DARK_GREEN = new Color(0.0F, 0.8F, 0.2F, 1F);
    public static final Color MAGENTA = new Color(1F, 0F, 1F, 1F);
    public static final Color CYAN = new Color(0F, 1F, 1F, 1F); // AQUA_BLUE
    public static final Color BLUE = new Color(0.0F, 0.0F, 1F, 1F);
    public static final Color LIGHT_BLUE = new Color(0.5F, 0.5F, 1F, 1F);
    public static final Color DARKBLUE = new Color(0.0F, 0.0F, 0.5F, 1F);
    public static final Color PURPLE = new Color(0.6F, 0.1F, 0.9F, 1F);

//    Color parseColor(String colorString) {
//        java.awt.Color.getColor(colorString);
//    }


    /**
     * The RGBA values are stored as floats from 0.0F (nothing) to 1.0F (full
     * saturation/opacity)
     */
    public final float r;
    public final float g;
    public final float b;
    public final float a;

    /**
     * Constructor. Just sets the RGBA values to the parameters.
     */
    public Color(float r, float g, float b, float a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    /**
     * Constructor. Just sets the RGBA values to the parameters.
     */
    public Color(float r, float g, float b) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = 1F;
    }

    public Color(Vector4f vec4fColor)  {
        this.r = vec4fColor.x();
        this.g = vec4fColor.y();
        this.b = vec4fColor.z();
        this.a = vec4fColor.w();
    }

    public Color(int r, int g, int b) {
        this.r = r * div255;
        this.g = g * div255;
        this.b = b * div255;
        this.a = 1F;
    }

    public void setGuiGraphicsColor(GuiGraphics gfx) {
        gfx.setColor(r, g, b, a);
    }

//    public Color fromRGBAHex() {
//
//    }



    // TODO?
    public VertexConsumer addToVertex(VertexConsumer builderIn) {
//        return builderIn.color((int)(r * 255.0F), (int)(g * 255.0F), (int)(b * 255.0F), (int)(a * 255.0F));
        return builderIn.color(r, g, b, a);
    }

    public Vector4f getVec4F() {
        return new Vector4f(r, g, b, a);
    }

    /**
     * Takes colors in the integer format that Minecraft uses, and converts.
     */
    public Color(int c) {
        this.a = (c >> 24 & 0xFF) * div255;
        this.r = (c >> 16 & 0xFF) * div255;
        this.g = (c >> 8 & 0xFF) * div255;
        this.b = (c & 0xFF) * div255;
    }

    public Color lighten(float amount) {
        float r = Math.min(this.r + amount, 1F);
        float g = Math.min(this.g + amount, 1F);
        float b = Math.min(this.b + amount, 1F);
        return new Color(r, g, b, this.a);
    }

    public Color darken(float amount) {
        float r = Math.max(this.r - amount, 0);
        float g = Math.max(this.g - amount, 0);
        float b = Math.max(this.b - amount, 0);
        return new Color(r, g, b, a);
    }

    public static int getARGBInt(float r, float g, float b, float a) {
        int val = 0;
        val = val | ((int) (a * 255) << 24);
        val = val | ((int) (r * 255) << 16);
        val = val | ((int) (g * 255) << 8);
        val = val | ((int) (b * 255));

        return val;
    }

    public int getAGBRInt() {
        int val = 0;
        val = val | ((int) (a * 255) << 24);
        val = val | ((int) (g * 255) << 16);
        val = val | ((int) (b * 255) << 8);
        val = val | ((int) (r * 255));
        return val;
    }
    
    
    public void setShaderColor() {
        RenderSystem.setShaderColor(r, g, b, a);
    }

    /**
     * Returns a color with RGB set to the same getValue ie. a shade of grey.
     */
    public static Color getGreyscale(float value, float alpha) {
        return new Color(value, value, value, alpha);
    }

    /**
     * Handles RRGGBB and RRGGBBAA hex strings
     *
     * @param hexString
     * @return new color based on getValue or default of white if error
     */
    public static Color fromARGBHexString(String hexString) {
        try {
            if (hexString == null || hexString.isEmpty()) {
                return WHITE;
            }
            hexString = hexString.replaceAll("#", "");
            int parsed = (int) Long.parseLong(hexString, 16);
            // Integer.parse will fail for RGBA strings
            Color color = new Color((int) Long.parseLong(hexString, 16));
            if (color.a == 0) {
                return color.withAlpha(1F);
            }
            return color;
        } catch (Exception e) {
            NuminaLogger.logException("Failed to generate color from Hex: ", e);
        }
        return WHITE;
    }

    /**
     * Returns this color as an int in Minecraft's format (I think)
     * <p>
     * note: full values for RGBA will yield -1
     *
     * @return int getValue of this color
     */
    public int getARGBInt() {
        int val = 0;
        val = val | ((int) (a * 255) << 24);
        val = val | ((int) (r * 255) << 16);
        val = val | ((int) (g * 255) << 8);
        val = val | ((int) (b * 255));
        return val;
    }

    /**
     * Returns a color at interval along a linear gradient from this
     * to target
     */
    public Color interpolate(Color target, float d) {
        float complement = 1 - d;
        return new Color(this.r * complement + target.r * d, this.g * complement + target.g * d, this.b * complement + target.b * d, this.a
                * complement + target.a * d);
    }

    public Color withAlpha(float newalpha) {
        return new Color(this.r, this.g, this.b, newalpha);
    }

    public float[] asFloatArray() {
        return new float[]{r, g, b, a};
    }

    public double[] asDoubleArray() {
        return new double[]{r, g, b, a};
    }

    // format is 0xRRGGBBAA
    public String rgbaHexColor() {
        return hexDigits(r) + hexDigits(g) + hexDigits(b) + (a > 0 ? hexDigits(a) : "");
    }

    public static String hexDigits(float x) {
        int y = (int) (x * 255);
        String hexDigits = "0123456789ABCDEF";
        return hexDigits.charAt(y / 16) + "" + hexDigits.charAt(y % 16);
    }

    @Override
    public String toString() {
        return "Color{" +
                "r=" + r +
                ", g=" + g +
                ", b=" + b +
                ", a=" + a +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Color color = (Color) o;
        return Double.compare(color.r, r) == 0 &&
                Double.compare(color.g, g) == 0 &&
                Double.compare(color.b, b) == 0 &&
                Double.compare(color.a, a) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(r, g, b, a);
    }

    public Color copy() {
        return new Color(this.r, this.g, this.b, this.a);
    }

//    @Override
//    public IntTag serializeNBT() {
//        return IntTag.valueOf(getARGBInt());
//    }
//
//    @Override
//    public void deserializeNBT(IntTag nbt) {
//        if (nbt != null && nbt instanceof IntTag) {
//            int c = nbt.getAsInt();
//            this.a = (c >> 24 & 0xFF) * div255;
//            this.r = (c >> 16 & 0xFF) * div255;
//            this.g = (c >> 8 & 0xFF) * div255;
//            this.b = (c & 0xFF) * div255;
//        }
//    }
}