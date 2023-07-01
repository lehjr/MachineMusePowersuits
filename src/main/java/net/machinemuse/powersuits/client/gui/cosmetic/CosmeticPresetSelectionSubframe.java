package net.machinemuse.powersuits.client.gui.cosmetic;

import net.machinemuse.numina.client.gui.clickable.ClickableItem;
import net.machinemuse.numina.client.gui.clickable.ClickableLabel;
import net.machinemuse.numina.client.gui.geometry.MusePoint2D;
import net.machinemuse.numina.client.gui.geometry.MuseRect;
import net.machinemuse.numina.client.gui.scrollable.ScrollableLabel;
import net.machinemuse.numina.common.item.MuseItemUtils;
import net.machinemuse.numina.common.nbt.MuseNBTUtils;
import net.machinemuse.powersuits.client.gui.common.ItemSelectionFrame;
import net.machinemuse.powersuits.common.item.armor.ItemPowerArmor;
import net.machinemuse.powersuits.common.item.tool.ItemPowerFist;
import net.machinemuse.powersuits.common.network.MPSPackets;
import net.machinemuse.powersuits.common.network.packets.MusePacketCosmeticPreset;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class CosmeticPresetSelectionSubframe extends ScrollableLabel {
    public MuseRect border;
    public boolean open;
    public ItemSelectionFrame itemSelector;
    String name;
    public CosmeticPresetSelectionSubframe(String name, MusePoint2D musePoint2D, ItemSelectionFrame itemSelector, MuseRect border) {
        super(new ClickableLabel(name, musePoint2D), border);
        this.name = name;
        this.itemSelector = itemSelector;
        this.border = border;
        this.open = true;
        this.setMode(0);
    }

    public boolean isValidItem(ClickableItem clickie, EntityEquipmentSlot slot) {
        if (clickie != null) {
            if (clickie.getItem().getItem() instanceof ItemPowerArmor)
                return clickie.getItem().getItem().isValidArmor(clickie.getItem(), slot, Minecraft.getMinecraft().player);
            else if (clickie.getItem().getItem() instanceof ItemPowerFist && slot.getSlotType().equals(EntityEquipmentSlot.Type.HAND))
                return true;
        }
        return false;
    }

    public ClickableItem getSelectedItem() {
        return this.itemSelector.getSelectedItem();
    }

    /**
     * Get's the equipment slot the item is for.
     */
    public EntityEquipmentSlot getEquipmentSlot() {
        ItemStack selectedItem = getSelectedItem().getItem();
        if (!selectedItem.isEmpty() && selectedItem.getItem() instanceof ItemPowerArmor)
            return ((ItemPowerArmor) selectedItem.getItem()).armorType;

        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayer player = mc.player;
        ItemStack heldItem = player.getHeldItemOffhand();

        if (!heldItem.isEmpty() && MuseItemUtils.stackEqualExact(selectedItem, heldItem))
            return EntityEquipmentSlot.OFFHAND;
        return EntityEquipmentSlot.MAINHAND;
    }

    public String getName() {
        return name;
    }

    public NBTTagCompound getItemTag() {
        return MuseNBTUtils.getMuseItemTag(this.getSelectedItem().getItem());
    }


    public MuseRect getBorder() {
        return this.border;
    }

    @Override
    public boolean hitbox(double x, double y) {
        // change the render tag to this ... keep in mind that the render tag for these are just a key to read from the config file
        if(super.hitbox(x, y) && this.getSelectedItem() != null) {
            if (isValidItem(getSelectedItem(), getEquipmentSlot())) {
                MPSPackets.sendToServer(new MusePacketCosmeticPreset(Minecraft.getMinecraft().player, this.getSelectedItem().inventorySlot, this.name));
            }
            return true;
        }
        return false;
    }
}