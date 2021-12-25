package io.github.mProjectsCode.LemonTTB.events;

import java.util.Objects;

/**
 * The type Event subscription.
 */
public class EventSubscription {
    private final EventListener eventListener;
    private final EventType eventType;

    /**
     * Instantiates a new Event subscription.
     *
     * @param eventSubscriber the event subscriber
     * @param eventType       the event type
     */
    public EventSubscription(EventListener eventSubscriber, EventType eventType) {
        this.eventListener = eventSubscriber;
        this.eventType = eventType;
    }

    /**
     * Gets event subscriber.
     *
     * @return the event subscriber
     */
    public EventListener getEventSubscriber() {
        return eventListener;
    }

    /**
     * Gets event type.
     *
     * @return the event type
     */
    public EventType getEventType() {
        return eventType;
    }

    /**
     * Send event.
     *
     * @param event the event
     */
    public void sendEvent(Event event) {
        if (Objects.equals(event.getEventType(), eventType)) {
            eventListener.onEvent(event);
        }
    }
}
