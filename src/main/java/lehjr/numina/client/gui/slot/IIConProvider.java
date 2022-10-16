package lehjr.numina.client.gui.slot;

import com.mojang.blaze3d.matrix.MatrixStack;
import lehjr.numina.common.math.Colour;

public interface IIConProvider {
        void drawIconAt(MatrixStack matrixStack, double posX, double posY, Colour colour);
}
