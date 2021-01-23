package com.github.lehjr.powersuits.event;

import com.google.common.eventbus.Subscribe;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class EntityDamageEvent {
    // LivingAttackEvent


    @Subscribe
    public static void handleEntityDamageEvent(LivingDamageEvent event) {
        System.out.println("entity type: "  + event.getEntity().getClass());

        // todo: control damage based on heat/max heat && whether or not player has full armor and is in lava
        // Note: can cancel here but damage animation/sound still happens. Only way to not have it is potion effects.


//        LivingEntity livingEntityy = event.getEntityLiving();
//        if (livingEntityy instanceof PlayerEntity) {
//            System.out.println("source: " + event.getSource().getDamageType());
//            if (event.getSource().isFireDamage()) {
//                event.setCanceled(true);
//            }
//        }
    }

    /**
     * Use this instead of the above method.
     * * @param event
     */
    @SubscribeEvent
    public static void entityAttackEventHandler(LivingAttackEvent event) {
        System.out.println("entity type: "  + event.getEntity().getClass());
    }
}
