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

package lehjr.numina.client.model.item.armor;


import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.common.util.Lazy;

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 10:01 PM, 11/07/13
 */
public class ArmorModelInstance {
    private static final Lazy<HighPolyArmor<LivingEntity>> INSTANCE = Lazy.of(() -> new HighPolyArmor<>(Minecraft.getInstance().getEntityModels().bakeLayer(ModelLayers.PLAYER)));
    private static final Lazy<HighPolyArmor<LivingEntity>> INSTANCE_SLIM = Lazy.of(() -> new HighPolyArmor<>(Minecraft.getInstance().getEntityModels().bakeLayer(ModelLayers.PLAYER_SLIM)));


    private static HighPolyArmor instance = null;

    public static HighPolyArmor getInstance() {
        if (instance == null) {
            instance = INSTANCE.get();
        }
        return instance;

      /*
      builder.put(ModelLayers.PLAYER, LayerDefinition.create(PlayerModel.createMesh(CubeDeformation.NONE, false), 64, 64));
      builder.put(ModelLayers.PLAYER_HEAD, layerdefinition11);
      builder.put(ModelLayers.PLAYER_INNER_ARMOR, layerdefinition3);
      builder.put(ModelLayers.PLAYER_OUTER_ARMOR, layerdefinition1);
      builder.put(ModelLayers.PLAYER_SLIM, LayerDefinition.create(PlayerModel.createMesh(CubeDeformation.NONE, true), 64, 64));
      builder.put(ModelLayers.PLAYER_SLIM_INNER_ARMOR, layerdefinition3);
      builder.put(ModelLayers.PLAYER_SLIM_OUTER_ARMOR, layerdefinition1);
      */







    }

    private static HighPolyArmor instanceSlim = null;

    public static HighPolyArmor getInstanceSlim() {
        if (instanceSlim == null) {
            instanceSlim = INSTANCE_SLIM.get();
        }
        return instanceSlim;
    }
}