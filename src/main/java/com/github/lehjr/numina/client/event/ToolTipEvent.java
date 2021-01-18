package com.github.lehjr.numina.client.event;

import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ToolTipEvent {

    @SubscribeEvent
    public void onToolTipEvent(RenderTooltipEvent.Pre event) {
        // TODO: better handling based on config

        event.setMaxWidth(240);
        // event.setMaxWidth(Math.max(event.getMaxWidth(), settings.getMaxWidth()));
    }
}
