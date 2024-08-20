package com.lehjr.numina.common.map;

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 4:30 AM, 29/04/13
 * <p>
 * Ported to Java by lehjr on 11/8/16.
 */
public class NuminaRegistry<T> extends NuminaBiMap<String, T> {
    public T put(final String name, final T elem) {
        return this.putName(name, elem);
    }

    public String put(final T elem, final String name) {
        return this.putElem(elem, name);
    }
}
