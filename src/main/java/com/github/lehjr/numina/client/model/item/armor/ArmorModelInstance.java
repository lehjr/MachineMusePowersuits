package com.github.lehjr.numina.client.model.item.armor;


/**
 * Author: MachineMuse (Claire Semple)
 * Created: 10:01 PM, 11/07/13
 */
public class ArmorModelInstance {
    private static HighPolyArmor instance = null;

    public static HighPolyArmor getInstance() {
        if (instance == null) {
                instance = new HighPolyArmor();
        }
        return instance;
    }
}