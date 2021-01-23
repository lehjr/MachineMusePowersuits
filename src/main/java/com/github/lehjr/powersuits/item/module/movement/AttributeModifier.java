//package com.github.lehjr.powersuits.item.module.movement;
//
//import net.minecraft.inventory.EquipmentSlotType;
//import net.minecraft.nbt.CompoundNBT;
//
//import java.util.Objects;
//
///**
// * Author: MachineMuse (Claire Semple)
// * Created: 10:10 AM, 8/7/13
// *
// * Created by leon on 10/18/16.
// */
//public class AttributeModifier {
//    final int operation;
//    final UUID uuid;
//    final double amount;
//    final String attributeName;
//    final String id;
//    final EquipmentSlotType slot;
//
//    public AttributeModifier(int operation, UUID uuid, double amount, String attributeName, String id, EquipmentSlotType slotIn) {
//        this.operation = operation;
//        this.uuid = uuid;
//        this.amount = amount;
//        this.attributeName = attributeName;
//        this.id = id;
//        this.slot = slotIn;
//    }
//
//    public AttributeModifier(CompoundNBT nbt) {
//        this.operation = nbt.getInteger("Operation");
//        this.uuid = new UUID(nbt);
//        this.amount = nbt.getDouble("Amount");
//        this.attributeName = nbt.getString("AttributeName");
//        this.id = nbt.getString("Name");
//        this.slot = EquipmentSlotType.fromString(!Objects.equals(nbt.getString("Slot"), "") ? nbt.getString("Slot").toLowerCase() : "legs" );
//    }
//
//    public CompoundNBT toNBT(CompoundNBT nbt) {
//        nbt.setInteger("Operation", operation);
//        uuid.toNBT(nbt);
//        nbt.setDouble("Amount", amount);
//        nbt.setString("AttributeName", attributeName);
//        nbt.setString("Name", id);
//        nbt.setString("Slot", slot.getName());
//        return nbt;
//    }
//
//    public CompoundNBT toNBT() {
//        return toNBT(new CompoundNBT());
//    }
//}