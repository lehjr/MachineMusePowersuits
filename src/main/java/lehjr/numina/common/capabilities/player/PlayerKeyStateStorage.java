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

package lehjr.numina.common.capabilities.player;

import lehjr.numina.common.capabilities.CapabilityUpdate;
import lehjr.numina.common.math.MathUtils;
import net.minecraft.nbt.ByteTag;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

public class PlayerKeyStateStorage implements IPlayerKeyStates, INBTSerializable<ByteTag>, CapabilityUpdate {
    LazyOptional<PlayerKeyStateStorage> instance = LazyOptional.of(()-> this);


    private boolean forwardKeyState = false;
    private boolean reverseKeyState = false;
    private boolean strafeLeftKeyState = false;
    private boolean strafeRightKeyState = false;
    private boolean downKeyState = false;
    private boolean jumpKeyState = false;

    public PlayerKeyStateStorage() {
        this.forwardKeyState = false;
        this.reverseKeyState = false;
        this.strafeLeftKeyState = false;
        this.strafeRightKeyState = false;
        this.downKeyState = false;
        this.jumpKeyState = false;
    }

    public PlayerKeyStateStorage(
            boolean forwardKeyState,
            boolean reverseKeyState,
            boolean strafeLeftKeyState,
            boolean strafeRightKeyState,
            boolean downKeyState,
            boolean jumpKeyState) {
        this.forwardKeyState = forwardKeyState;
        this.reverseKeyState = reverseKeyState;
        this.strafeLeftKeyState = strafeLeftKeyState;
        this.strafeRightKeyState = strafeRightKeyState;
        this.downKeyState = downKeyState;
        this.jumpKeyState = jumpKeyState;
    }

    public PlayerKeyStateStorage(byte packedKeyStates) {
        boolean[] boolArray = MathUtils.byteToBooleanArray(packedKeyStates);
        this.forwardKeyState = boolArray[0];
        this.reverseKeyState = boolArray[1];
        this.strafeLeftKeyState = boolArray[2];
        this.strafeRightKeyState = boolArray[3];
        this.downKeyState = boolArray[4];
        this.jumpKeyState = boolArray[5];
    }

    @Override
    public boolean getForwardKeyState() {
        return forwardKeyState;
    }

    @Override
    public void setForwardKeyState(boolean state) {
        this.forwardKeyState = state;
    }

    @Override
    public boolean getReverseKeyState() {
        return reverseKeyState;
    }

    @Override
    public void setReverseKeyState(boolean state) {
        this.reverseKeyState = state;
    }

    @Override
    public boolean getLeftStrafeKeyState() {
        return strafeLeftKeyState;
    }

    @Override
    public void setLeftStrafeKeyState(boolean state) {
        this.strafeLeftKeyState = state;
    }

    @Override
    public boolean getRightStrafeKeyState() {
        return strafeRightKeyState;
    }

    @Override
    public void setRightStrafeKeyState(boolean state) {
        this.strafeRightKeyState = state;
    }

    @Override
    public boolean getJumpKeyState() {
        return jumpKeyState;
    }

    @Override
    public void setJumpKeyState(boolean state) {
        this.jumpKeyState = state;
    }

    @Override
    public void setDownKeyState(boolean state) {
        this.downKeyState = state;
    }

    @Override
    public boolean getDownKeyState() {
        return downKeyState;
    }

    @Override
    public String toString() {
        return "forwardKeyState: " + getForwardKeyState() +
                ", reverseKeyState: " + getReverseKeyState() +
                ", leftStrafeKeyState: " + getLeftStrafeKeyState() +
                ",  rightStrafeKeyState: " + getRightStrafeKeyState() +
                ", downKeyState: " + getDownKeyState() +
                ", jumpKeyState: " + getJumpKeyState();
    }

    @Override
    public ByteTag serializeNBT() {
        boolean[] boolArray = new boolean[]{
                getForwardKeyState(),
                getReverseKeyState(),
                getLeftStrafeKeyState(),
                getRightStrafeKeyState(),
                getDownKeyState(),
                getJumpKeyState(),
                false,
                false
        };
        byte byteOut = MathUtils.boolArrayToByte(boolArray);
        return ByteTag.valueOf(byteOut);
    }

    @Override
    public void deserializeNBT(ByteTag nbt) {
        if (nbt instanceof ByteTag) {
            byte byteOut = nbt.getAsByte();
            boolean[] boolArray = MathUtils.byteToBooleanArray(byteOut);

            setForwardKeyState(boolArray[0]);
            setReverseKeyState(boolArray[1]);
            setLeftStrafeKeyState(boolArray[2]);
            setRightStrafeKeyState(boolArray[3]);
            setDownKeyState(boolArray[4]);
            setJumpKeyState(boolArray[5]);
        }
    }

    @Override
    public void loadCapValues() {

    }

    @Override
    public void onValueChanged() {

    }
}