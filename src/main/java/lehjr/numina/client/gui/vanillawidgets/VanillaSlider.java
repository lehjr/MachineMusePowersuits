//package lehjr.numina.client.gui.vanillawidgets;
//
//import lehjr.numina.client.gui.gemoetry.IDrawable;
//import lehjr.numina.client.gui.gemoetry.Rect;
//import net.minecraft.client.GameSettings;
//import net.minecraft.client.settings.SliderPercentageOption;
//import net.minecraft.util.text.ITextComponent;
//import net.minecraft.util.text.TranslationTextComponent;
//
//import java.util.function.BiConsumer;
//import java.util.function.BiFunction;
//import java.util.function.Function;
//
///**
// *
// */
//public class VanillaSlider extends Rect, IDrawable {
//
//    public static final SliderPercentageOption FOV = new SliderPercentageOption(
//            "options.fov", /* label translation key */
//            30.0D, /* Min value */
//            110.0D, /* max value */
//            1.0F,  /* steps */
//            // getter
//            (gameSettings) -> {
//        return gameSettings.fov;
//    },
//       // setter
//            (p_216612_0_, p_216612_1_) -> { // setter
//        p_216612_0_.fov = p_216612_1_;
//        },
//           // to string
//
//            (p_216590_0_, p_216590_1_) -> { // to string
//        double d0 = p_216590_1_.get(p_216590_0_);
//        if (d0 == 70.0D) {
//            return p_216590_1_.genericValueLabel(new TranslationTextComponent("options.fov.min"));
//        } else {
//            return d0 == p_216590_1_.getMaxValue() ? p_216590_1_.genericValueLabel(new TranslationTextComponent("options.fov.max")) : p_216590_1_.genericValueLabel((int)d0);
//        }
//    });
//
//    public SliderPercentageOption(String p_i51155_1_, double p_i51155_2_, double p_i51155_4_, float p_i51155_6_, Function<GameSettings, Double> p_i51155_7_, BiConsumer<GameSettings, Double> p_i51155_8_, BiFunction<GameSettings, SliderPercentageOption, ITextComponent> p_i51155_9_) {
//        super(p_i51155_1_);
//        this.minValue = p_i51155_2_;
//        this.maxValue = p_i51155_4_;
//        this.steps = p_i51155_6_;
//        this.getter = p_i51155_7_;
//        this.setter = p_i51155_8_;
//        this.toString = p_i51155_9_;
//    }
//
//    /**
//     * slider background
//     *---------------------
//     * top left: 0, 46
//     * bottom left: 0, 66
//     * bottom right: 200, 66
//     * top right: 200, 46
//     * (height 20, width 200)
//     * ======================
//     * slider knob is rendered in 2 parts
//     *----------------------------------
//     * top left: 0, 66
//     * bottom left: 0, 86
//     * bottom right: 200, 86
//     * top right: 200, 66
//     *
//     */
//
//
//
//
//}
