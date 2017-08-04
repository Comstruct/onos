/*
 * Copyright 2015-present Open Networking Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.onosproject.event;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.ReferenceCardinality;

/**
 * Basis for components which need to export listener mechanism.
 */
@Component(componentAbstract = true)
public abstract class AbstractListenerManager<E extends Event, L extends EventListener<E>>
    implements ListenerService<E, L> {

    protected final ListenerRegistry<E, L> listenerRegistry = new ListenerRegistry<>();

    @Reference(cardinality = ReferenceCardinality.MANDATORY_UNARY)
    protected EventDeliveryService eventDispatcher;

    @Override
    public void addListener(L listener) {
        listenerRegistry.addListener(listener);
    }

    @Override
    public void removeListener(L listener) {
        listenerRegistry.removeListener(listener);
    }


    /**
     * Safely posts the specified event to the local event dispatcher.
     * If there is no event dispatcher or if the event is null, this method
     * is a noop.
     *
     * @param event event to be posted; may be null
     */
    protected void post(E event) {
        if (event != null && eventDispatcher != null) {
            eventDispatcher.post(event);
        }
    }

}
