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

public class PlayerKeyStateStorage implements IPlayerKeyStates {
    private boolean forwardKeyState = false;
    private boolean reverseKeyState = false;
    private boolean strafeLeftKeyState = false;
    private boolean strafeRightKeyState = false;
    private boolean downKeyState = false;
    private boolean jumpKeyState = false;

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
        return new StringBuilder("forwardKeyState: ").append(getForwardKeyState())
                .append(", reverseKeyState: ").append(getReverseKeyState())
                .append(", leftStrafeKeyState: ").append(getLeftStrafeKeyState())
                .append(",  rightStrafeKeyState: ").append(getRightStrafeKeyState())
                .append(", downKeyState: ").append(getDownKeyState())
                .append(", jumpKeyState: ").append(getJumpKeyState())
                .toString();
    }
}