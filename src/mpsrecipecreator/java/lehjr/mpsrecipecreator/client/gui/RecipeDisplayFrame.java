package lehjr.mpsrecipecreator.client.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import lehjr.numina.client.gui.frame.ScrollableFrame;
import lehjr.numina.client.gui.geometry.MusePoint2D;
import lehjr.numina.client.gui.geometry.Rect;
import lehjr.numina.common.string.StringUtils;

public class RecipeDisplayFrame extends ScrollableFrame {
    String[] recipe = new String[0];
    String title;;

    public RecipeDisplayFrame() {
        super(new Rect(MusePoint2D.ZERO, MusePoint2D.ZERO));
        reset();
    }

    public void setFileName(String fileName) {
        title = fileName;
    }

    public void setRecipe(String recipeIn) {
        recipe = recipeIn.split("\n");
    }

    @Override
    public void update(double mouseX, double mouseY) {
        super.update(mouseX, mouseY);
        this.setTotalSize(25 + recipe.length * 12);
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        if (this.isEnabled() && this.isVisible()) {
            setCurrentScrollPixels(Math.min(getCurrentScrollPixels(), getMaxScrollPixels()));
            super.preRender(poseStack, mouseX, mouseY, partialTick);
            poseStack.pushPose();
            poseStack.translate(0, (float) -getCurrentScrollPixels(), 0);
            StringUtils.drawLeftAlignedShadowedString(poseStack, "FileName: " + title,
                    left() + 4,
                    top() + 12);

            if (recipe.length > 0) {
                for (int index = 0; index < recipe.length; index ++) {
                    StringUtils.drawLeftAlignedShadowedString(poseStack, recipe[index],
                            left() + 4,
                            (top() + 12) + (12 * index));
                }
            }
            poseStack.popPose();
            super.postRender(mouseX, mouseY, partialTick);
        }
    }

    public void reset() {
        recipe = new String[0];
        setFileName("");
    }
}