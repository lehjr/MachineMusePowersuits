package com.lehjr.numina.client.config;

import com.lehjr.numina.common.math.Color;

public interface IMeterConfig {
    default float getDebugValue() {
        return 0;
    }

    default Color getGlassColor() {
        return Color.WHITE;
    }

    default Color getBarColor() {
        return Color.WHITE;
    }
}