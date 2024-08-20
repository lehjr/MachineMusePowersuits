package com.lehjr.numina.common.tags;

import com.lehjr.numina.common.base.NuminaLogger;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;

import javax.annotation.Nullable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Workaround class to access static CompoundTag.getTagMap()
 *
 * @author MachineMuse
 */
public class NBTTagAccessor extends CompoundTag {
    public static Method mTagAccessor;

    /**
     * Accesses the package-visible
     * <p/>
     * <pre>
     * Map CompoundTag.getTagMap(CompoundTag tag)
     * </pre>
     * <p/>
     * Will likely need to be updated every time the obfuscation changes.
     *
     * @return
     * @throws SecurityException
     * @throws NoSuchMethodException
     */
    public static Method getTagAccessor() throws NoSuchMethodException, SecurityException {
        if (mTagAccessor == null) {
            try {
                mTagAccessor = CompoundTag.class.getDeclaredMethod("getTagMap", CompoundTag.class);
                mTagAccessor.setAccessible(true);
                return mTagAccessor;
            } catch (NoSuchMethodException e) {
                mTagAccessor = CompoundTag.class.getDeclaredMethod("a", CompoundTag.class);
                mTagAccessor.setAccessible(true);
                return mTagAccessor;
            }
        } else {
            return mTagAccessor;
        }
    }

    @Nullable
    public static Map getMap(CompoundTag nbt) {
        try {
            return (Map) getTagAccessor().invoke(nbt, nbt);
        } catch (Exception e) {
            e.printStackTrace();
        }
        NuminaLogger.logger.error("Unable to access nbt tag map!");
        return null;
    }

    public static List<CompoundTag> getValues(CompoundTag nbt) {
        Set<String> keyset = nbt.getAllKeys();
        ArrayList<CompoundTag> a = new ArrayList<>(keyset.size());
        for (String key : keyset) {
            Tag c = nbt.get(key);
            if (c instanceof CompoundTag) {
                a.add((CompoundTag) c);
            }
        }
        return a;
    }
}
