///*
// * Copyright (c) 2021. MachineMuse, Lehjr
// *  All rights reserved.
// *
// * Redistribution and use in source and binary forms, with or without
// * modification, are permitted provided that the following conditions are met:
// *
// *      Redistributions of source code must retain the above copyright notice, this
// *      list of conditions and the following disclaimer.
// *
// *     Redistributions in binary form must reproduce the above copyright notice,
// *     this list of conditions and the following disclaimer in the documentation
// *     and/or other materials provided with the distribution.
// *
// *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
// *  ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
// *  WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
// *  DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
// *  ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
// *  (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
// *  LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
// *  ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
// *  (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
// *  SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
// */
//
//package lehjr.powersuits.client.gui.modding.cosmetic;
//
//import lehjr.numina.common.capabilities.render.ModelSpecNBTCapability;
//import lehjr.numina.common.capabilities.render.modelspec.EnumSpecType;
//import lehjr.numina.client.gui.clickable.ClickableItem;
//import lehjr.numina.client.gui.clickable.ClickableLabel;
//import lehjr.numina.client.gui.gemoetry.MusePoint2D;
//import lehjr.numina.client.gui.gemoetry.RelativeRect;
//import lehjr.numina.client.gui.gemoetry.RelativeRect;
//import lehjr.numina.util.client.gui.scrollable.ScrollableLabel;
//import lehjr.numina.common.tags.MuseNBTUtils;
//import lehjr.powersuits.client.gui.common.ItemSelectionFrame;
//import lehjr.powersuits.item.armor.AbstractElectricItemArmor;
//import net.minecraft.client.Minecraft;
//import net.minecraft.entity.player.PlayerEntity;
//import net.minecraft.inventory.EquipmentSlotType;
//import net.minecraft.item.ItemStack;
//import net.minecraft.nbt.CompoundNBT;
//
//public class CosmeticPresetSelectionSubframe extends ScrollableLabel {
//    public RelativeRect border;
//    public boolean open;
//    public ItemSelectionFrame itemSelector;
//    String name;
//    Minecraft minecraft;
//
//    public CosmeticPresetSelectionSubframe(String name, MusePoint2D point, ItemSelectionFrame itemSelector, RelativeRect border) {
//        super(new ClickableLabel(name, point), border);
//        this.name = name;
//        this.itemSelector = itemSelector;
//        this.border = border;
//        this.open = true;
//        this.setMode(ClickableLabel.JustifyMode.LEFT);
//        minecraft = Minecraft.getInstance();
//    }
//
//    public boolean isValidItem(ClickableItem clickie, EquipmentSlotType slot) {
//        if (clickie != null) {
//            clickie.getStack().getCapability(ModelSpecNBTCapability.RENDER).map(iModelSpecNBT -> {
//                EnumSpecType specType = iModelSpecNBT.getSpecType();
//
//                if (iModelSpecNBT.getSpecType().equals(EnumSpecType.HANDHELD) && slot.getType().equals(EquipmentSlotType.Group.HAND)) {
//                    return true;
//                }
//                if (specType.equals(EnumSpecType.ARMOR_MODEL) || specType.equals(EnumSpecType.ARMOR_MODEL)
//                        && slot.getType().equals(EquipmentSlotType.Group.ARMOR)) {
//                    return true;
//                }
//                return false;
//            }).orElse(false);
//        }
//        return false;
//    }
//
//    public ClickableItem getSelectedItem() {
//        return this.itemSelector.getSelectedItem();
//    }
//
//    /**
//     * Get's the equipment itemSlot the item is for.
//     */
//    public EquipmentSlotType getEquipmentSlot() {
//        ItemStack selectedItem = getSelectedItem().getStack();
//        if (!selectedItem.isEmpty() && selectedItem.getItem() instanceof AbstractElectricItemArmor)
//            return selectedItem.getEquipmentSlot();
//        PlayerEntity player = minecraft.player;
//        ItemStack heldItem = player.getOffhandItem();
//
//        if (!heldItem.isEmpty() && ItemStack.matches(selectedItem, heldItem))
//            return EquipmentSlotType.OFFHAND;
//        return EquipmentSlotType.MAINHAND;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public CompoundNBT getItemTag() {
//        return MuseNBTUtils.getMuseItemTag(this.getSelectedItem().getStack());
//    }
//
//    public RelativeRect getBorder() {
//        return this.border;
//    }
//
//    @Override
//    public boolean hitbox(double x, double y) {
//        // change the render tag to this ... keep in mind that the render tag for these are just a key to read from the config file
//        if(super.hitbox(x, y) && this.getSelectedItem() != null) {
//            if (isValidItem(getSelectedItem(), getEquipmentSlot())) {
////                MPSPackets.CHANNEL_INSTANCE.sendToServer(new MusePacketCosmeticPreset(this.getSelectedItem().getSlotIndex(), this.name));
//            }
//            return true;
//        }
//        return false;
//    }
//}