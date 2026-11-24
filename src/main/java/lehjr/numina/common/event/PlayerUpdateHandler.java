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

package lehjr.numina.common.event;

import lehjr.numina.common.capabilities.inventory.modechanging.IModeChangingItem;
import lehjr.numina.common.capabilities.inventory.modularitem.IModularItem;
import lehjr.numina.common.config.NuminaCommonConfig;
import lehjr.numina.common.player.PlayerUtils;
import lehjr.numina.common.registration.NuminaCapabilities;
import lehjr.numina.common.utils.HeatUtils;
import lehjr.numina.common.utils.ItemUtils;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class PlayerUpdateHandler {
    @SubscribeEvent(priority = EventPriority.NORMAL)
    public static void onPlayerUpdate(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        NonNullList<ItemStack> modularItems = NonNullList.create();
        Level level = player.level();
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            ItemStack modularItem = ItemUtils.getItemFromEntitySlot(player, slot);
            if(modularItem.isEmpty()) {
                continue;
            }

            IModularItem iModularItemCap = NuminaCapabilities.getModularItemOrModeChangingCapability(modularItem);
            if(iModularItemCap != null) {
                modularItems.add(modularItem);
                if(slot.isArmor() || iModularItemCap instanceof IModeChangingItem) {
                    iModularItemCap.tick(player, level, modularItem);
                }
            }
        }
        //  Done this way so players can let their stuff cool in their inventory without having to equip it,
        // allowing it to cool off enough to not take damage
        if (!modularItems.isEmpty()) {
            // Heat update
            HeatUtils.PlayerHeat playerHeat = HeatUtils.getPlayerHeat(player);

            if (playerHeat.currentHeat() >= 0 && !level.isClientSide) { // only apply serverside so change is not applied twice

                // cooling value adjustment. Too much or too little cooling makes the heat system useless.
                double coolPlayerAmount = (PlayerUtils.getPlayerCoolingBasedOnMaterial(player) * 0.55);  // cooling value adjustment. Too much or too little cooling makes the heat system useless.

                if (coolPlayerAmount > 0) {
                    HeatUtils.coolPlayer(player, coolPlayerAmount);
                }

                if (playerHeat.currentHeat() < playerHeat.maxHeat() * 0.95) {
                    player.clearFire();
                }
            }
        }
    }

    private static final String INVENTORY_BACKUP_KEY = "numina:inventory_backup";

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onPlayerDeath(LivingDeathEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player) || !NuminaCommonConfig.keepModularItemsOnDeath) {
            return;
        }
        if (player.serverLevel().getGameRules().getBoolean(GameRules.RULE_KEEPINVENTORY)) {
            return;
        }

        ListTag savedItems = new ListTag();
        Inventory inventory = player.getInventory();

        for (int slot = 0; slot < inventory.getContainerSize(); slot++) {
            ItemStack stack = inventory.getItem(slot);
            if (!stack.isEmpty() && shouldPreserveItem(stack)) {
                CompoundTag itemTag = stack.save(new CompoundTag());
                itemTag.putByte("Slot", (byte) slot);
                savedItems.add(itemTag);
                inventory.setItem(slot, ItemStack.EMPTY);
            }
        }

        if (!savedItems.isEmpty()) {
            // Forge automatically copies Player.PERSISTED_NBT_TAG to the new player on respawn
            CompoundTag persisted = player.getPersistentData().getCompound(Player.PERSISTED_NBT_TAG);
            persisted.put(INVENTORY_BACKUP_KEY, savedItems);
            player.getPersistentData().put(Player.PERSISTED_NBT_TAG, persisted);
        }
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        if (!event.isWasDeath() || !(event.getEntity() instanceof ServerPlayer newPlayer)) {
            return;
        }

        newPlayer.getCapability(NuminaCapabilities.PLAYER_KEYSTATES).ifPresent(ks -> ks.setStates((byte) 0));

        if (!NuminaCommonConfig.keepModularItemsOnDeath) {
            return;
        }

        CompoundTag persisted = newPlayer.getPersistentData().getCompound(Player.PERSISTED_NBT_TAG);
        if (persisted.contains(INVENTORY_BACKUP_KEY, Tag.TAG_LIST)) {
            ListTag savedItems = persisted.getList(INVENTORY_BACKUP_KEY, Tag.TAG_COMPOUND);
            Inventory newInventory = newPlayer.getInventory();

            for (int i = 0; i < savedItems.size(); i++) {
                CompoundTag itemTag = savedItems.getCompound(i);
                int slot = itemTag.getByte("Slot") & 255;
                ItemStack restored = ItemStack.of(itemTag);

                if (!restored.isEmpty() && slot < newInventory.getContainerSize()) {
                    newInventory.setItem(slot, restored);
                }
            }

            persisted.remove(INVENTORY_BACKUP_KEY);
        }
    }

    private static boolean shouldPreserveItem(ItemStack stack) {
        return NuminaCapabilities.getModularItemOrModeChangingCapability(stack) != null;
    }


    /**
     * Use this instead of the above method.
     * * @param event
     */
    @SubscribeEvent(priority = EventPriority.NORMAL)
    public void entityAttackEventHandler(LivingAttackEvent event) {
        // if damage is from overheat damage, just let it happen
//        if (event.getSource() == HeatUtils.overheatDamage) { // FIXME?
//            return;
//        }

        if (event.getSource().is(DamageTypeTags.IS_FIRE)) {
            HeatUtils.heatEntity(event);
        }
    }
}
