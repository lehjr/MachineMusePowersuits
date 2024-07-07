package lehjr.numina.common.capability.player.keystates;

import lehjr.numina.common.utils.MathUtils;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.ByteTag;
import net.neoforged.neoforge.common.util.INBTSerializable;

public class PlayerKeyStateStorage implements IPlayerKeyStates , INBTSerializable<ByteTag> {
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
                ", rightStrafeKeyState: " + getRightStrafeKeyState() +
                ", downKeyState: " + getDownKeyState() +
                ", jumpKeyState: " + getJumpKeyState();
    }

    @Override
    public ByteTag serializeNBT(HolderLookup.Provider provider) {
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
    public void deserializeNBT(HolderLookup.Provider provider, ByteTag nbt) {
        if (nbt instanceof ByteTag) {
            byte byteOut = ((ByteTag) nbt).getAsByte();
            boolean[] boolArray = MathUtils.byteToBooleanArray(byteOut);

            setForwardKeyState(boolArray[0]);
            setReverseKeyState(boolArray[1]);
            setLeftStrafeKeyState(boolArray[2]);
            setRightStrafeKeyState(boolArray[3]);
            setDownKeyState(boolArray[4]);
            setJumpKeyState(boolArray[5]);
        }
    }
}
