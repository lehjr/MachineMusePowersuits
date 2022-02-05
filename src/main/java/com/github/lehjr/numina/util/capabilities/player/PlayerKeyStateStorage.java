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

package com.github.lehjr.numina.util.capabilities.player;

public class PlayerKeyStateStorage implements IPlayerKeyStates {
    private boolean forwardKeyState = false;
    private boolean strafeKeyState = false;
    private boolean downKeyState = false;
    private boolean jumpKeyState = false;
    private boolean sneakKeyState = false;

    @Override
    public void setForwardKeyState(boolean state) {
        this.forwardKeyState = state;
    }

    @Override
    public boolean getForwardKeyState() {
        return this.forwardKeyState;
    }

    @Override
    public void setStrafeKeyState(boolean state) {
        this.strafeKeyState = state;
    }

    @Override
    public boolean getStrafeKeyState() {
        return this.strafeKeyState;
    }

    @Override
    public void setDownKeyState(boolean state) {
        this.downKeyState = state;
    }

    @Override
    public boolean getDownKeyState() {
        return this.downKeyState;
    }

    @Override
    public void setJumpKeyState(boolean state) {
        this.jumpKeyState = state;
    }

    @Override
    public boolean getJumpKeyState() {
        return this.jumpKeyState;
    }

    @Override
    public void setSneakKeyState(boolean state) {
        this.sneakKeyState = state;
    }

    @Override
    public boolean getSneakKeyState() {
        return this.sneakKeyState;
    }
}