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

package com.github.lehjr.numina.event;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.ModLifecycleEvent;

import java.util.function.Consumer;

/**
 * Shamelessly ripped from Just Enough Items
 */
public class EventBusHelper {
	private static IEventBus getInstance() {
		return MinecraftForge.EVENT_BUS;
	}

//	public static <T extends Event> void addListener(Class<T> eventType, Consumer<T> listener) {
//		IEventBus eventBus = getInstance();
//		eventBus.addListener(EventPriority.NORMAL, false, eventType, listener);
//	}

	public static <T extends Event> void addListener(IEventBus eventBus, Class<T> eventType, Consumer<T> listener) {
		eventBus.addListener(EventPriority.NORMAL, false, eventType, listener);
	}

//	public static <T extends Event> void removeListener(Consumer<T> listener) {
//		IEventBus eventBus = getInstance();
//		eventBus.unregister(listener);
//	}

	public static <T extends ModLifecycleEvent> void addLifecycleListener(IEventBus eventBus, Class<T> eventType, Consumer<T> listener) {
		eventBus.addListener(EventPriority.NORMAL, false, eventType, listener);
	}

//	public static void register(Object object) {
//		IEventBus eventBus = getInstance();
//		eventBus.register(object);
//	}
//
//	public static void unregister(Object object) {
//		IEventBus eventBus = getInstance();
//		eventBus.unregister(object);
//	}
//
//	public static void post(Event event) {
//		IEventBus eventBus = getInstance();
//		eventBus.post(event);
//	}
}
