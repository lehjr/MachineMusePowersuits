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

package lehjr.numina.util.nbt.propertymodifier;

import lehjr.numina.util.nbt.MuseNBTUtils;
import net.minecraft.nbt.CompoundNBT;

public class PropertyModifierIntLinearAdditive extends PropertyModifierLinearAdditive {
    protected int roundTo = 1;
    protected int offset = 0;

    public PropertyModifierIntLinearAdditive(String tradeoffName, double multiplier, int roundTo, int offset) {
        super(tradeoffName, multiplier);
        this.roundTo = roundTo;
        this.offset = offset;
    }

    @Override
    public double applyModifier(CompoundNBT moduleTag, double value) {
        long result = (long) (value + multiplier * MuseNBTUtils.getDoubleOrZero(moduleTag, tradeoffName));
        return Double.valueOf(roundWithOffset(result, roundTo, offset));
    }

    public double getScaledDouble(CompoundNBT moduleTag, double value) {
        double scaledVal = applyModifier(moduleTag, value);
        double ret = (scaledVal - value)/multiplier;
        MuseNBTUtils.setDoubleOrRemove(moduleTag, tradeoffName, ret);
        return ret;
    }

    public long roundWithOffset(double input, int roundTo, int offset) {
        return Math.round((input + offset) / roundTo) * roundTo - offset;
    }

    public int getRoundTo() {
        return roundTo;
    }

    public int getOffset() {
        return offset;
    }
}