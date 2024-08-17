package com.lehjr.numina.common.capabilities.player.keystates;

public interface IPlayerKeyStates {
    boolean getForwardKeyState();

    void setForwardKeyState(boolean state);

    boolean getReverseKeyState();

    void setReverseKeyState(boolean state);

    boolean getLeftStrafeKeyState();

    void setLeftStrafeKeyState(boolean state);

    boolean getRightStrafeKeyState();

    void setRightStrafeKeyState(boolean state);

    boolean getJumpKeyState();

    void setJumpKeyState(boolean state);

    void setDownKeyState(boolean state);

    boolean getDownKeyState();
}
