/*
 * Copyright (c) 2019 MachineMuse, Lehjr
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.github.lehjr.numina.util.capabilities.module.powermodule;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.config.ModConfig;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

public interface IConfig {
    double getBasePropertyDoubleOrDefault(
            EnumModuleCategory category,
            @Nonnull ItemStack module,
            String propertyName,
            double baseVal);

    double getTradeoffPropertyDoubleOrDefault(
            EnumModuleCategory category,
            @Nonnull ItemStack module,
            String tradeoffName,
            String propertyName,
            double multiplier);

    int getTradeoffPropertyIntegerOrDefault(
            EnumModuleCategory category,
            @Nonnull ItemStack module,
            String tradeoffName,
            String propertyName,
            int multiplier);

    boolean isModuleAllowed(EnumModuleCategory category, @Nonnull ItemStack module);

    void setServerConfig(@Nullable ModConfig serverConfig);

    Optional<ModConfig> getModConfig();

    default boolean isLoadingDone() {
        return getModConfig().map(config ->config.getSpec().isLoaded()).orElse(false);
    }
}