package lehjr.mpsrecipecreator.client.gui;

import com.lehjr.numina.client.gui.frame.ScrollableFrame;
import com.lehjr.numina.client.gui.geometry.MusePoint2D;
import com.lehjr.numina.client.gui.geometry.Rect;
import com.lehjr.numina.common.string.StringUtils;
import net.minecraft.client.gui.GuiGraphics;

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
    public void render(GuiGraphics gfx, int mouseX, int mouseY, float partialTick) {
        if (this.isEnabled() && this.isVisible()) {
            setCurrentScrollPixels(Math.min(getCurrentScrollPixels(), getMaxScrollPixels()));
            super.preRender(gfx, mouseX, mouseY, partialTick);
            gfx.pose().pushPose();
            gfx.pose().translate(0, (float) -getCurrentScrollPixels(), 0);
            StringUtils.drawLeftAlignedShadowedString(gfx, "FileName: " + title,
                    left() + 4,
                    top() + 12);

            if (recipe.length > 0) {
                for (int index = 0; index < recipe.length; index ++) {
                    StringUtils.drawLeftAlignedShadowedString(gfx, recipe[index],
                            left() + 4,
                            (top() + 12) + (12 * index));
                }
            }
            gfx.pose().popPose();
            super.postRender(gfx, mouseX, mouseY, partialTick);
        }
    }

    public void reset() {
        recipe = new String[0];
        setFileName("");
    }
}