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

package lehjr.powersuits.event;

import com.google.common.eventbus.Subscribe;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class EntityDamageEvent {
    // LivingAttackEvent


    @Subscribe
    public static void handleEntityDamageEvent(LivingDamageEvent event) {
//        System.out.println("entity type: "  + event.getEntity().getClass());

        // todo: control damage based on heat/max heat && whether or not player has full armor and is in lava
        // Note: can cancel here but damage animation/sound still happens. Only way to not have it is potion effects.


//        LivingEntity livingEntityy = event.getEntityLiving();
//        if (livingEntityy instanceof Player) {
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
//        System.out.println("entity type: "  + event.getEntity().getClass());
    }
}
