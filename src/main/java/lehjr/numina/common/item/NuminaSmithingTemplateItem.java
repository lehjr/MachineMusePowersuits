package lehjr.numina.common.item;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.SmithingTemplateItem;

import java.util.List;

public class NuminaSmithingTemplateItem extends SmithingTemplateItem {
    public NuminaSmithingTemplateItem(Component appliesTo, Component ingredients, Component upgradeDescription,
                                      Component baseSlotDescription, Component additionsSlotDescription,
                                      List<ResourceLocation> baseSlotEmptyIcons, List<ResourceLocation> additionalSlotEmptyIcons) {
        super(appliesTo, ingredients, upgradeDescription, baseSlotDescription, additionsSlotDescription, baseSlotEmptyIcons, additionalSlotEmptyIcons);
    }

    @Override
    public String getDescriptionId() {
        return this.getOrCreateDescriptionId();
    }
}
