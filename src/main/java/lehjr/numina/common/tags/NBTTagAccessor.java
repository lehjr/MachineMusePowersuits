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

package lehjr.numina.common.tags;

import lehjr.numina.common.base.NuminaLogger;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;

import javax.annotation.Nullable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Workaround class to access static CompoundNBT.getTagMap()
 *
 * @author MachineMuse
 */
public class NBTTagAccessor extends CompoundNBT {
    public static Method mTagAccessor;

    /**
     * Accesses the package-visible
     * <p/>
     * <pre>
     * Map CompoundNBT.getTagMap(CompoundNBT tag)
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
                mTagAccessor = CompoundNBT.class.getDeclaredMethod("getTagMap", CompoundNBT.class);
                mTagAccessor.setAccessible(true);
                return mTagAccessor;
            } catch (NoSuchMethodException e) {
                mTagAccessor = CompoundNBT.class.getDeclaredMethod("a", CompoundNBT.class);
                mTagAccessor.setAccessible(true);
                return mTagAccessor;
            }
        } else {
            return mTagAccessor;
        }
    }

    @Nullable
    public static Map getMap(CompoundNBT nbt) {
        try {
            return (Map) getTagAccessor().invoke(nbt, nbt);
        } catch (Exception e) {
            e.printStackTrace();
        }
        NuminaLogger.logger.error("Unable to access nbt tag map!");
        return null;
    }

    public static List<CompoundNBT> getValues(CompoundNBT nbt) {
        Set<String> keyset = nbt.getAllKeys();
        ArrayList<CompoundNBT> a = new ArrayList<>(keyset.size());
        for (String key : keyset) {
            INBT c = nbt.get(key);
            if (c instanceof CompoundNBT) {
                a.add((CompoundNBT) c);
            }
        }
        return a;
    }
}