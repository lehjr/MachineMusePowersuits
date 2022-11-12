//package lehjr.numina.client.gui.clickable;
//
//import com.mojang.blaze3d.matrix.MatrixStack;
//import com.mojang.blaze3d.platform.GlStateManager;
//import com.mojang.blaze3d.systems.RenderSystem;
//import net.minecraft.client.Minecraft;
//import net.minecraft.client.audio.SimpleSound;
//import net.minecraft.client.gui.FontRenderer;
//import net.minecraft.client.gui.widget.button.CheckboxButton;
//import net.minecraft.util.ResourceLocation;
//import net.minecraft.util.SoundEvents;
//import net.minecraft.util.math.MathHelper;
//import net.minecraft.util.text.ITextComponent;
//
//
///**
// * Scales the checkbox size down a bit
// */
//public class Checkbox2 extends CheckboxButton {
//    private final boolean showLabel;
//    private static final ResourceLocation TEXTURE = new ResourceLocation("textures/gui/checkbox.png");
//
//    public Checkbox2(int posX, int posY, int width, ITextComponent message, boolean checked) {
//        this(posX, posY, width, message, checked, true);
//    }
//
//    public Checkbox2(int posX, int posY, int width, ITextComponent message, boolean checked, boolean showLabel) {
//        super(posX, posY, width, 20, message, checked, showLabel);
//        this.showLabel = showLabel;
//    }
//
//    @Override
//    public void renderButton(MatrixStack matrixStack, int mouseX, int mouseY, float pPartialTicks) {
//        Minecraft minecraft = Minecraft.getInstance();
//        minecraft.getTextureManager().bind(TEXTURE);
//        RenderSystem.enableDepthTest();
//        FontRenderer fontrenderer = minecraft.font;
//        RenderSystem.color4f(1.0F, 1.0F, 1.0F, this.alpha);
//        RenderSystem.enableBlend();
//        RenderSystem.defaultBlendFunc();
//        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
//        blit(matrixStack,
//                // posX, posY
//                this.x, this.y + 5,
//                // width, height,
//                10, 10,
//                // image start x (xOffset)
//                this.isHovered() ? 20 : 0.0F,
//                // image start y (yOffset)
//                this.selected() ? 20 : 0.0F,
//                // int uWidth, int vHeight,
//                20, 20,
//                // textureWidth, textureHeight
//                64, 64);
//
//        this.renderBg(matrixStack, minecraft, mouseX, mouseY);
//        if (this.showLabel) {
//            drawString(matrixStack, fontrenderer, this.getMessage(), this.x + 16, this.y + (this.height - 8) / 2, 14737632 | MathHelper.ceil(this.alpha * 255.0F) << 24);
//        }
//    }
//
//    @Override
//    public void onPress() {
//        if (this.visible && this.active) {
//            Minecraft.getInstance().getSoundManager().play(SimpleSound.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
//            super.onPress();
//        }
//    }
//}
