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

package lehjr.numina.common.capabilities.render.modelspec;

import lehjr.numina.common.base.NuminaLogger;
import lehjr.numina.common.constants.TagConstants;
import lehjr.numina.common.map.NuminaRegistry;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;

import javax.annotation.Nullable;
import java.util.stream.Stream;

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 7:44 AM, 4/28/13
 * <p>
 * Ported to Java by lehjr on 11/8/16.
 * <p>
 * Note: make sure to have null checks in place.
 */
public class NuminaModelSpecRegistry extends NuminaRegistry<SpecBase> {
    private static volatile NuminaModelSpecRegistry INSTANCE;

    private NuminaModelSpecRegistry() {
    }

    public static NuminaModelSpecRegistry getInstance() {
        if (INSTANCE == null) {
            synchronized (NuminaModelSpecRegistry.class) {
                if (INSTANCE == null) {
                    INSTANCE = new NuminaModelSpecRegistry();
                }
            }
        }
        return INSTANCE;
    }

    public Iterable<SpecBase> getSpecs() {
        return this.elems();
    }

    public Stream<SpecBase> getSpecsAsStream() {
        return this.elemsAsStream();
    }

    public NonNullList<SpecBase> getSpecsForArmorEquipmentSlot(EquipmentSlot slot) {
        NonNullList<SpecBase> ret = NonNullList.create();
        if (slot.isArmor()) {
            this.elemsAsStream().filter(specBase -> specBase.hasArmorEquipmentSlot(slot)).forEach(specBase -> ret.add(specBase));
        }
        return ret;
    }

    public NonNullList<SpecBase> getSpecsForHand(HumanoidArm arm, LivingEntity entity) {
        NonNullList<SpecBase> ret = NonNullList.create();
            this.elemsAsStream().filter(specBase -> specBase.elemsAsStream().filter(partSpecBase -> partSpecBase.isForHand(arm, entity)).findFirst().isPresent())
                    .forEach(specBase -> ret.add(specBase));
        return ret;

    }

    public Iterable<String> getNames() {
        return this.names();
    }

    public SpecBase getModel(CompoundTag nbt) {
        return get(nbt.getString(TagConstants.MODEL));
    }

    public PartSpecBase getPart(CompoundTag nbt, SpecBase model) {
        if (model == null) return null;
        return model.get(nbt.getString(TagConstants.PART));
    }

    public PartSpecBase getPart(CompoundTag nbt) {
        return getPart(nbt, getModel(nbt));
    }

    public boolean addPart(PartSpecBase part) {
        try {
            String baseName = part.spec.getOwnName();

            NuminaLogger.logDebug("adding part < " + part.partName +" > for model < " + baseName +" >" );

            SpecBase base = get(baseName);
            base.put(part, part.partName);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    @Nullable
    public CompoundTag getSpecTag(CompoundTag renderTag, PartSpecBase spec) {
        String name = makeName(spec);
        return (renderTag.contains(name)) ? (renderTag.getCompound(name)) : null;
    }

    public String makeName(PartSpecBase spec) {
        return spec.spec.getOwnName() + "." + spec.partName;
    }
}