package lehjr.numina.common.item;

import lehjr.numina.common.container.slot.SlotBackgrounds;
import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlag;
import net.minecraft.world.item.SmithingTemplateItem;

import java.util.List;

public class SmithingTemplateModularItem extends SmithingTemplateItem {

    public SmithingTemplateModularItem (
        Component appliesTo,
        Component ingredients,
        Component upgradeDescription,
        Component baseSlotDescription,
        Component additionsSlotDescription,
        List<ResourceLocation> baseSlotEmptyIcons,
        List<ResourceLocation> additionalSlotEmptyIcons,
        FeatureFlag... requiredFeatures) {

        super(appliesTo, // text
            ingredients, // text
            upgradeDescription, // text
            baseSlotDescription, // text
            additionsSlotDescription, // text
            baseSlotEmptyIcons, // slot icons
            additionalSlotEmptyIcons,
            requiredFeatures);

        // Block breaking modules don't need any special settings saved, but modular items do



//        super(GuiText.QuartzTools.text().withStyle(DESCRIPTION_FORMAT),
//            Component.translatable(Util.makeDescriptionId("item", AEItemIds.FLUIX_CRYSTAL)).withStyle(DESCRIPTION_FORMAT),
//            Component.translatable(Util.makeDescriptionId("item", AEItemIds.FLUIX_UPGRADE_SMITHING_TEMPLATE)).withStyle(TITLE_FORMAT),
//            GuiText.PutAQuartzTool.text(),
//            GuiText.PutAFluixBlock.text(),
//            List.of(SlotBackgrounds.EMPTY_SLOT_PICKAXE, SlotBackgrounds.EMPTY_SLOT_AXE, SlotBackgrounds.EMPTY_SLOT_HOE, SlotBackgrounds.EMPTY_SLOT_SHOVEL),
//            List.of(EMPTY_SLOT_BLOCK),
//            new FeatureFlag[0]);
//
    }
}
