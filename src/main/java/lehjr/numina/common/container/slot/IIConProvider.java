package lehjr.numina.common.container.slot;

import lehjr.numina.common.math.Color;
import com.mojang.blaze3d.vertex.PoseStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public interface IIConProvider {
    @OnlyIn(Dist.CLIENT)
    void drawIconAt(PoseStack matrixStack, double posX, double posY, Color color);
}
