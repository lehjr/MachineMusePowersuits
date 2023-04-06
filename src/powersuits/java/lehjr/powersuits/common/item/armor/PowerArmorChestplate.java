/*
 * Copyright (c) 2021. MachineMuse, Lehjr
 *  All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *      Redistributions of source code must retain the above copyright notice, this
 *      list of conditions and the following disclaimer.
 *
 *     Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 *  ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 *  WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 *  DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 *  ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 *  (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 *  LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 *  ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *  (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *  SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package lehjr.powersuits.common.item.armor;

import lehjr.numina.common.capabilities.inventory.modularitem.IModularItem;
import lehjr.powersuits.common.constants.MPSRegistryNames;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ForgeCapabilities;

public class PowerArmorChestplate extends AbstractElectricItemArmor {
    public PowerArmorChestplate() {
        super(EquipmentSlot.CHEST);
    }


    @Override
    public boolean canElytraFly(ItemStack stack, LivingEntity entity) {
        return
                // Flight control module
                entity.getItemBySlot(EquipmentSlot.HEAD).getCapability(ForgeCapabilities.ITEM_HANDLER)
                        .filter(IModularItem.class::isInstance)
                        .map(IModularItem.class::cast)
                        .map(iModularItem -> iModularItem.isModuleOnline(MPSRegistryNames.FLIGHT_CONTROL_MODULE)).orElse(false) &&

                        entity.getItemBySlot(EquipmentSlot.CHEST).getCapability(ForgeCapabilities.ITEM_HANDLER)
                                .filter(IModularItem.class::isInstance)
                                .map(IModularItem.class::cast)
                                .map(iModularItem -> iModularItem.isModuleOnline(MPSRegistryNames.GLIDER_MODULE)).orElse(false) &&

                        entity.getItemBySlot(EquipmentSlot.CHEST).getCapability(ForgeCapabilities.ITEM_HANDLER)
                                .filter(IModularItem.class::isInstance)
                                .map(IModularItem.class::cast)
                                .map(iModularItem -> iModularItem.isModuleOnline(MPSRegistryNames.JETPACK_MODULE)).orElse(false);
    }

    @Override
    public boolean elytraFlightTick(ItemStack stack, LivingEntity entity, int flightTicks) {
//        if (!entity.level.isClientSide && (flightTicks + 1) % 20 == 0) {
//            // drain instead?
//            // stack.hurtAndBreak(1, entity, e -> e.broadcastBreakEvent(net.minecraft.inventory.EquipmentSlot.CHEST));
//        }
        return true;
    }
}
