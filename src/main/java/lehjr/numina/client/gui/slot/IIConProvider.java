package lehjr.numina.client.gui.slot;

import com.mojang.blaze3d.vertex.PoseStack;
import lehjr.numina.common.math.Color;

public interface IIConProvider {
        void drawIconAt(PoseStack matrixStack, double posX, double posY, Color color);
}
