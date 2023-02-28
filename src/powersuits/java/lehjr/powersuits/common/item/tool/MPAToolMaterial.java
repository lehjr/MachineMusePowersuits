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
//package lehjr.powersuits.common.item.tool;
//
//import net.minecraft.util.LazyLoadedValue;
//import net.minecraft.world.item.Items;
//import net.minecraft.world.item.crafting.Ingredient;
//
//import java.util.function.Supplier;
//
//public enum MPAToolMaterial implements Material {
//    EMPTY_TOOL(0, 0, 0, 0.0F, 0, () -> {
//        return Ingredient.of(Items.AIR);
//    });
//
//    private final int harvestLevel;
//    private final int maxUses;
//    private final float efficiency;
//    private final float attackDamage;
//    private final int enchantability;
//    private final LazyLoadedValue<Ingredient> repairMaterial;
//
//    private MPAToolMaterial(int harvestLevel, int maxUses, float efficiency, float attackDamage, int enchantibility, Supplier<Ingredient> repairMaterial) {
//        this.harvestLevel = harvestLevel;
//        this.maxUses = maxUses;
//        this.efficiency = efficiency;
//        this.attackDamage = attackDamage;
//        this.enchantability = enchantibility;
//        this.repairMaterial = new LazyLoadedValue<Ingredient>(repairMaterial);
//    }
//
//    public int getUses() {
//        return this.maxUses;
//    }
//
//    public float getSpeed() {
//        return this.efficiency;
//    }
//
//    public float getAttackDamageBonus() {
//        return this.attackDamage;
//    }
//
//    public int getLevel() {
//        return this.harvestLevel;
//    }
//
//    public int getEnchantmentValue() {
//        return this.enchantability;
//    }
//
//    public Ingredient getRepairIngredient() {
//        return (Ingredient)this.repairMaterial.get();
//    }
//}
