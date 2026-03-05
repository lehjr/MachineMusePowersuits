package lehjr.numina.common.container.slot;

import lehjr.numina.client.gui.NuminaIcons;
import lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import lehjr.numina.common.math.Color;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Pair;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.items.IItemHandler;

public class CategoryIconSlotItemHandler extends IconSlotItemHandler{
    final ModuleCategory category;
    public CategoryIconSlotItemHandler(IItemHandler itemHandler, int parent, int index, int xPosition, int yPosition, ModuleCategory category) {
        super(itemHandler, parent, index, xPosition, yPosition);
        this.category = category;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void drawIconAt(PoseStack matrixStack, double posX, double posY, Color color) {
        Pair<ResourceLocation, ResourceLocation> iconPair = SlotBackgrounds.getIconLocationPairForCategory(category);
        if(iconPair != null) {
            NuminaIcons.DrawableIcon icon = NuminaIcons.getIcon(iconPair.getSecond());
            if (icon != null) {
                icon.renderIconScaledWithColor(matrixStack, posX, posY, 16, 16, Color.WHITE);
            }
        }
    }
}
