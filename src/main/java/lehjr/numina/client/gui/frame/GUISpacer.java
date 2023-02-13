package lehjr.numina.client.gui.frame;

import com.mojang.blaze3d.matrix.MatrixStack;
import lehjr.numina.client.gui.gemoetry.DrawableRect;
import lehjr.numina.client.gui.gemoetry.IDrawable;
import lehjr.numina.client.gui.gemoetry.MusePoint2D;
import lehjr.numina.common.config.NuminaSettings;
import lehjr.numina.common.math.Colour;
import net.minecraft.util.text.ITextComponent;

import java.util.List;

/**
 * Just a spacer for a GUI. Height and width are setup to be locked into permanent values
 */
public class GUISpacer extends DrawableRect implements IGuiFrame {
    public GUISpacer(double widthIn, double heightIn) {
        super(MusePoint2D.ZERO, new MusePoint2D(widthIn, heightIn),
                Colour.LIGHT_GREEN, Colour.PURPLE);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        return false;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        return false;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double dWheel) {
        return false;
    }

    @Override
    public void update(double mouseX, double mouseY) {
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float frameTime) {
        if (NuminaSettings.CLIENT_CONFIG.DRAW_GUI_SPACERS.get()) {
            super.render(matrixStack, mouseX, mouseY, frameTime);
        }
    }

    @Override
    public List<ITextComponent> getToolTip(int x, int y) {
        return null;
    }

    @Override
    public void setEnabled(boolean enabled) {

    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public void setVisible(boolean visible) {

    }

    @Override
    public boolean isVisible() {
        return false;
    }

    @Override
    public float getZLevel() {
        return 0;
    }

    @Override
    public IDrawable setZLevel(float zLevel) {
        return this;
    }
}
