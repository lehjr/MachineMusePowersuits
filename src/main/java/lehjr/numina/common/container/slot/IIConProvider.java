package lehjr.numina.common.container.slot;

import com.mojang.blaze3d.vertex.PoseStack;
import lehjr.numina.common.math.Color;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface IIConProvider {
    @OnlyIn(Dist.CLIENT)
    void drawIconAt(PoseStack matrixStack, double posX, double posY, Color color);
}
