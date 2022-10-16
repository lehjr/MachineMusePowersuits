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

package lehjr.numina.common.map;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import lehjr.numina.common.base.NuminaLogger;

import java.util.Map;

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 4:30 AM, 29/04/13
 * <p>
 * Ported to Java by lehjr on 11/8/16.
 */
public class NuminaBiMap<S, T> {
    private BiMap<S, T> theMap;

    public NuminaBiMap() {
        this.theMap = HashBiMap.create();
    }

    public T get(S name) {
        return this.theMap.get(name);
    }

    public Iterable<T> elems() {
        return theMap.values();
    }

    public Iterable<S> names() {
        return theMap.keySet();
    }

    public T putName(S name, T elem) {
        if (this.theMap.containsKey(name)) {
            NuminaLogger.logError(name + " already a member!");
        } else {
            this.theMap.put(name, elem);
        }
        return elem;
    }

    public S putElem(T elem, S name) {
        if (theMap.containsKey(name)) {
            NuminaLogger.logError(name + " already a member!");
        } else {
            theMap.put(name, elem);
        }
        return name;
    }

    public Map<S,T> apply() {
        return this.theMap;
    }

    public Map<T, S> inverse() {
        return this.theMap.inverse();
    }

    public S getName(T elem) {
        return this.theMap.inverse().get(elem);
    }

    public S removeElem(T elem) {
        return this.theMap.inverse().remove(elem);
    }

    public T removeName(S name) {
        return this.theMap.remove(name);
    }
}