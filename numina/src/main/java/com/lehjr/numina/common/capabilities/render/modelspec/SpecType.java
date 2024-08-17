package com.lehjr.numina.common.capabilities.render.modelspec;

import java.util.Arrays;

public enum SpecType {
    // Obj models rendered as armor
    ARMOR_OBJ_MODEL("ARMOR_OBJ_MODEL"),

    // pretty much what Minecraft already uses
    ARMOR_SKIN("ARMOR_SKIN"),

    // model intended to render in the hand
    HANDHELD_OBJ_MODEL("HANDHELD_OBJ"),

    // java based model that renderes in the hand
    HANDHELD_JAVA_MODEL("HANDHELD_JAVA_MODEL"),
    NONE("NONE");

    String name;

    SpecType(String name) {
        this.name = name;
    }

    public static SpecType getTypeFromName(String nameIn) {
        String finalNameIn = nameIn.toUpperCase().replaceAll("\\s", "");
        return Arrays.stream(values()).filter(spec -> spec.getName().equals(finalNameIn)).findAny().orElse(null);
    }

    public String getName() {
        return this.name;
    }
}
