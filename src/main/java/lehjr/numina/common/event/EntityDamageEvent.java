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
//package lehjr.numina.common.event;
//
//import lehjr.numina.common.base.NuminaLogger;
//import lehjr.numina.common.registration.NuminaCapabilities;
//import lehjr.numina.common.utils.HeatUtils;
//import net.minecraft.tags.DamageTypeTags;
//import net.minecraft.world.damagesource.DamageSource;
//import net.minecraft.world.entity.EquipmentSlot;
//import net.minecraft.world.entity.LivingEntity;
//import net.minecraft.world.entity.player.Player;
//import net.neoforged.bus.api.SubscribeEvent;
//import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
//import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
//
//public class EntityDamageEvent {
////    /**
////     * The intention here is to convert the incoming fire damage into heat.
////     * @param event
////     */
////    @SubscribeEvent
////    public static void damageTest(LivingIncomingDamageEvent event) {
////        LivingEntity livingEntity = event.getEntity();
////        DamageSource damageSource = event.getSource();
////        if (damageSource.is(DamageTypeTags.IS_FIRE) && livingEntity instanceof Player player) {
////
////            boolean headCapPresent = NuminaCapabilities.modularItemCapIsPresent(player, EquipmentSlot.HEAD);
////            boolean chestCapPresent = NuminaCapabilities.modularItemCapIsPresent(player, EquipmentSlot.CHEST);
////            boolean legCapPresent = NuminaCapabilities.modularItemCapIsPresent(player, EquipmentSlot.LEGS);
////            boolean footCapPresent = NuminaCapabilities.modularItemCapIsPresent(player, EquipmentSlot.FEET);
////            boolean toolCapPresent = NuminaCapabilities.modeChangingModularItemCapIsPresent(player.getItemBySlot(EquipmentSlot.MAINHAND));
////
////            double damageMult = 1.0;
////            // new damage with netherite plating 0.79999995 (all 4 pieces of armor)
////            damageMult = damageMult - (headCapPresent ? 15 : 0);
////            damageMult = damageMult - (chestCapPresent ? 50 : 0);
////            damageMult = damageMult - (legCapPresent ? 50 : 0);
////            damageMult = damageMult - (footCapPresent ? 5 : 0);
////            damageMult = damageMult - (toolCapPresent ? 10 : 0);
////
////            if (damageMult < 1) {
////                if(damageMult > 0.0F) {
////                    event.getContainer().setNewDamage((float) (damageMult * event.getContainer().getOriginalDamage()));
////                } else {
////                    event.setCanceled(true);
////                }
////                HeatUtils.heatPlayer(livingEntity, event.getContainer().getOriginalDamage() * 0.25);
////            }
////        }
////    }
//
//    @SubscribeEvent
//    public static void handleEntityDamageEvent(LivingDamageEvent.Pre event) {
//        LivingEntity entity = event.getEntity();
//        DamageSource damageSource = event.getSource();
//        float originalDamage = event.getOriginalDamage();
//        float newDamage = event.getNewDamage();
//
//        NuminaLogger.logDebug("entity type: "  + event.getEntity().getClass());
//
//
//
//
//
//        // todo: control damage based on heat/max heat && whether or not player has full armor and is in lava
//        // Note: can cancel here but damage animation/sound still happens. Only way to not have it is potion effects.
//
//
//        if (entity instanceof Player player) {
//            boolean helmetCapPresent = NuminaCapabilities.modularItemCapIsPresent(player, EquipmentSlot.HEAD);
//            boolean chestCapPresent = NuminaCapabilities.modularItemCapIsPresent(player, EquipmentSlot.CHEST);
//            boolean legCapPresent = NuminaCapabilities.modularItemCapIsPresent(player, EquipmentSlot.LEGS);
//            boolean footCapPresent = NuminaCapabilities.modularItemCapIsPresent(player, EquipmentSlot.FEET);
//
//            // reduce damage based on equipped armor items and whether heat is max or not
//            // damage reduction:
//            // head 20%
//            // chest 45%
//            // leggs 30%
//            // feet 5%
//
//
//            if(helmetCapPresent &&
//                chestCapPresent &&
//                legCapPresent &&
//                footCapPresent) {
//                if (damageSource.is(DamageTypeTags.IS_FIRE)) {
//                    NuminaLogger.logDebug("source: " + event.getSource().typeHolder());
//                }
//
//
//
//
//
//
//
//                if(entity.damageSources().inFire() == damageSource) {
//                    NuminaLogger.logDebug("in fire!!!");
//
//                    //            minecraft:in_fire
//                    //            minecraft:on_fire
//                    //            minecraft:lava
//
//                    //            if (event.getSource() == DamageSources.isFireDamage()) {
//                    //                event.setCanceled(true);
//                    //            }
//                    // net.minecraft.world.effect.MobEffects.FIRE_RESISTANCE
//                    //                    p_298903_.put(12, "minecraft:fire_resistance");
//                }
//
//
//                if(entity.damageSources().onFire() == damageSource) {
//                    NuminaLogger.logDebug("on fire!!!");
//
//                }
//                if(entity.damageSources().lava() == damageSource) {
//                    NuminaLogger.logDebug("lava!!!");
//
//                }
//
//
//
//
//
//
//
//
//
//
//
//
//            }
//
//
//
//
//
//            NuminaLogger.logDebug("======================================================================================");
//            NuminaLogger.logDebug("source: " + event.getSource().typeHolder());
//            //            NuminaLogger.logDebug("source kind: " + event.getSource().typeHolder().kind());
//            //            NuminaLogger.logDebug("source unwrap: " + event.getSource().typeHolder().unwrap());
//            //            NuminaLogger.logDebug("source unwrapKey: " + event.getSource().typeHolder().unwrapKey());
//            NuminaLogger.logDebug("source name: " + event.getSource().typeHolder().getDelegate().getRegisteredName());
//
//            NuminaLogger.logDebug("damage original amount:" + event.getOriginalDamage());
//            NuminaLogger.logDebug("damage new amount:" + event.getNewDamage());
//
//            event.setNewDamage(0);
//            NuminaLogger.logDebug("damage new amount after set:" + event.getNewDamage());
//
//            NuminaLogger.logDebug("------------------------------------------------------------------------------------------");
//        }
//    }
//
//    @SubscribeEvent
//    public static void handleEntityDamageEventPost(LivingDamageEvent.Post event) {
//        NuminaLogger.logDebug("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
//        NuminaLogger.logDebug("source: " + event.getSource().typeHolder());
//        //            NuminaLogger.logDebug("source kind: " + event.getSource().typeHolder().kind());
//        //            NuminaLogger.logDebug("source unwrap: " + event.getSource().typeHolder().unwrap());
//        //            NuminaLogger.logDebug("source unwrapKey: " + event.getSource().typeHolder().unwrapKey());
//        NuminaLogger.logDebug("source name: " + event.getSource().typeHolder().getDelegate().getRegisteredName());
//
//        NuminaLogger.logDebug("damage original amount:" + event.getOriginalDamage());
//        NuminaLogger.logDebug("damage new amount:" + event.getNewDamage());
//        NuminaLogger.logDebug("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
//    }
//
//
//
//    /**
//     * Use this instead of the above method.
//     * * @param event
//     */
//    //    @SubscribeEvent
//    //    public static void entityAttackEventHandler(LivingAttackEvent event) {
//    ////        NuminaLogger.logDebug("entity type: "  + event.getEntity().getClass());
//    //    }
//}
