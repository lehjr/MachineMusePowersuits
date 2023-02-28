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

package lehjr.numina.common.capabilities.heat;

import lehjr.numina.common.capabilities.CapabilityUpdate;

/**
 * Same as ForgeEnergy/RF, except for heat
 */
public interface IHeatStorage extends CapabilityUpdate {
    /**
     * Adds heat to the storage. Returns quantity of heat that was accepted.
     *
     * @param maxReceive Maximum amount of heat to be inserted.
     * @param simulate   If TRUE, the insertion will only be simulated.
     * @return Amount of heat that was (or would have been, if simulated) accepted by the storage.
     */
    double receiveHeat(double maxReceive, boolean simulate);

    /**
     * Removes heat from the storage. Returns quantity of heat that was removed.
     *
     * @param maxExtract Maximum amount of heat to be extracted.
     * @param simulate   If TRUE, the extraction will only be simulated.
     * @return Amount of heat that was (or would have been, if simulated) extracted from the storage.
     */
    double extractHeat(double maxExtract, boolean simulate);

    /**
     * Returns the amount of heat currently stored.
     */
    double getHeatStored();

    /**
     * Returns the maximum amount of heat that can be stored.
     */
    double getMaxHeatStored();

    /**
     * Returns if this storage can have heat extracted.
     * If this is false, then any calls to extractheat will return 0.
     */
    boolean canExtract();

    /**
     * Used to determine if this storage can receive heat.
     * If this is false, then any calls to receiveHeat will return 0.
     */
    boolean canReceive();

    void setHeatCapacity(double maxHeat);
}