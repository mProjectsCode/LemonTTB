package io.github.mProjectsCode.LemonTTB.events;

import java.util.Objects;

public class EventSubscription {
    private final EventListener eventListener;
    private final EventType eventType;

    public EventSubscription(EventListener eventSubscriber, EventType eventType) {
        this.eventListener = eventSubscriber;
        this.eventType = eventType;
    }

    public EventListener getEventSubscriber() {
        return eventListener;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void sendEvent(Event event) {
        if (Objects.equals(event.getEventType(), eventType)) {
            eventListener.onEvent(event);
        }
    }
}
