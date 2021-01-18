package com.github.lehjr.numina.client.event;

import com.github.lehjr.numina.config.ModuleConfig;
import com.github.lehjr.numina.config.NuminaSettings;
import com.github.lehjr.numina.util.capabilities.module.powermodule.IConfig;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class LogoutEventHandler {
    @SubscribeEvent
    public void onPlayerLogout(ClientPlayerNetworkEvent.LoggedInEvent event) {
        IConfig moduleConfig = NuminaSettings.getModuleConfig();
        if (moduleConfig instanceof ModuleConfig) {
            ((ModuleConfig) moduleConfig).writeMissingConfigValues();
        }
    }
}
