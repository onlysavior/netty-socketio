/**
 * Copyright 2012 Nikita Koksharov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.corundumstudio.socketio.store;

import com.corundumstudio.socketio.parser.JsonSupport;
import com.corundumstudio.socketio.store.pubsub.PubSubListener;
import com.corundumstudio.socketio.store.pubsub.PubSubMessage;
import com.corundumstudio.socketio.store.pubsub.PubSubStore;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class MemoryPubSubStore implements PubSubStore {
    private ConcurrentHashMap<String, Set<PubSubListener<?>>> eventBus = new ConcurrentHashMap<String, Set<PubSubListener<?>>>();

    public MemoryPubSubStore() {
    }

    @Override
    public void publish(String name, PubSubMessage msg) {
        Set<PubSubListener<?>> listeners = eventBus.get(name);
        if (listeners != null) {
            Iterator<PubSubListener<?>> iterator = listeners.iterator();
            while (iterator.hasNext()) {
                ClassDefinedLinstener<?> classDefinedLinstener = (ClassDefinedLinstener)iterator.next();
                classDefinedLinstener.onMessage(msg);
            }
        }
    }

    @Override
    public <T extends PubSubMessage> void subscribe(String name, PubSubListener<T> listener, Class<T> clazz) {
        Set<PubSubListener<?>> listeners = eventBus.get(name);
        if (listeners == null) {
            listeners = new LinkedHashSet<PubSubListener<?>>();
            listeners.add(new ClassDefinedLinstener<T>(clazz, listener));
            eventBus.put(name, listeners);
        } else {
            listeners.add(new ClassDefinedLinstener<Object>(clazz, listener));
        }
    }

    @Override
    public void unsubscribe(String name) {
        eventBus.remove(name);
    }

    @Override
    public void shutdown() {
    }

    class ClassDefinedLinstener<T> implements PubSubListener<T> {
        private Class clazz;
        private PubSubListener target;

        ClassDefinedLinstener(Class clazz, PubSubListener target) {
            this.clazz = clazz;
            this.target = target;
        }

        @Override
        public void onMessage(Object data) {
            target.onMessage((T)data);
        }
    }
}
