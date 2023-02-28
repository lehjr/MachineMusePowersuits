//package lehjr.numina.client.gui.clickable;
//
//import lehjr.numina.client.gui.geometry.MusePoint2D;
//import lehjr.numina.client.gui.geometry.Rect;
//import lehjr.numina.common.math.Color;
//import net.minecraft.resources.ResourceLocation;
//
//public class AbstractTexturedButton extends Button {
//    ResourceLocation textureLocation;
//    double texStartX;
//    double texStartY;
//    double textureWidth;
//    double textureHeight;
//    double iconWidth;
//    double iconHeight;
//
//    public AbstractTexturedButton(double left, double top, double right, double bottom, boolean growFromMiddle,
//                                  Color backgroundColourEnabled,
//                                  Color backgroundColourDisabled,
//                                  Color borderColourEnabled,
//                                  Color borderColourDisabled,
//                                  double textureWidth,
//                                  double textureHeight,
//                                  ResourceLocation textureLocation) {
//        super(left, top, right, bottom, growFromMiddle, backgroundColourEnabled, backgroundColourDisabled, borderColourEnabled, borderColourDisabled);
//        this.textureWidth = this.iconWidth = textureWidth;
//        this.textureHeight = this.iconHeight = textureHeight;
//        this.textureLocation = textureLocation;
//        this.texStartX = 0;
//        this.texStartY = 0;
//    }
//
//    public AbstractTexturedButton(double left, double top, double right, double bottom,
//                                  Color backgroundColourEnabled,
//                                  Color backgroundColourDisabled,
//                                  Color borderColourEnabled,
//                                  Color borderColourDisabled,
//                                  double textureWidth,
//                                  double textureHeight,
//                                  ResourceLocation textureLocation) {
//        super(left, top, right, bottom, backgroundColourEnabled, backgroundColourDisabled, borderColourEnabled, borderColourDisabled);
//        this.textureWidth = textureWidth;
//        this.textureHeight = textureHeight;
//        this.textureLocation = textureLocation;
//        this.texStartX = 0;
//        this.texStartY = 0;
//    }
//
//    public AbstractTexturedButton(MusePoint2D ul, MusePoint2D br,
//                                  Color backgroundColourEnabled,
//                                  Color backgroundColourDisabled,
//                                  Color borderColourEnabled,
//                                  Color borderColourDisabled,
//                                  double textureWidth,
//                                  double textureHeight,
//                                  ResourceLocation textureLocation) {
//        super(ul, br, backgroundColourEnabled, backgroundColourDisabled, borderColourEnabled, borderColourDisabled);
//        this.textureWidth = textureWidth;
//        this.textureHeight = textureHeight;
//        this.textureLocation = textureLocation;
//        this.texStartX = 0;
//        this.texStartY = 0;
//    }
//
//    public AbstractTexturedButton(Rect ref,
//                                  Color backgroundColourEnabled,
//                                  Color backgroundColourDisabled,
//                                  Color borderColourEnabled,
//                                  Color borderColourDisabled,
//                                  double textureWidth,
//                                  double textureHeight,
//                                  ResourceLocation textureLocation) {
//        super(ref, backgroundColourEnabled, backgroundColourDisabled, borderColourEnabled, borderColourDisabled);
//        this.textureWidth = textureWidth;
//        this.textureHeight = textureHeight;
//        this.textureLocation = textureLocation;
//        this.texStartX = 0;
//        this.texStartY = 0;
//    }
//
//    public AbstractTexturedButton setTextureStartX(double xOffset) {
//        this.texStartX = xOffset;
//        return this;
//    }
//
//    public AbstractTexturedButton setTextureStartY(double yOffset) {
//        this.texStartY = yOffset;
//        return this;
//    }
//
//    public AbstractTexturedButton setIconWidth(double iconWidth) {
//        this.iconWidth = iconWidth;
//        return this;
//    }
//
//    public AbstractTexturedButton setIconHeight(double iconHeight) {
//        this.iconHeight = iconHeight;
//        return this;
//    }
//
//    public AbstractTexturedButton setTextureWidth(double textureWidth) {
//        this.textureWidth = textureWidth;
//        return this;
//    }
//
//    public AbstractTexturedButton setTextureHeight(double textureHeight) {
//        this.textureHeight = textureHeight;
//        return this;
//    }
//}
