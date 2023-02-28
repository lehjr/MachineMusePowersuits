package lehjr.numina.client.gui.geometry;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;

import java.util.List;

public interface IDrawableRect extends IRect, IDrawable {
    default void preRender(PoseStack matrixStack, int mouseX, int mouseY, float frameTIme) {

    }

    default void postRender(int mouseX, int mouseY, float partialTicks) {

    }

    /**
     * @param x mouseX
     * @param y mouseY
     * @return tooltip or null if not returning tooltip;
     */
    default List<Component> getToolTip(int x, int y) {
        return null;
    }
}