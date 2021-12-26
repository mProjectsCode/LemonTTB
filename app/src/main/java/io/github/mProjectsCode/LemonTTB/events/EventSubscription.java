package io.github.mProjectsCode.LemonTTB.events;

import java.util.Objects;

/**
 * The type Event subscription.
 */
public class EventSubscription {
    private final EventListener eventListener;
    private final EventGroup eventGroup;
    private final EventType eventType;

    /**
     * Instantiates a new Event subscription.
     *
     * @param eventSubscriber the event subscriber
     * @param eventType       the event type
     */
    public EventSubscription(EventListener eventSubscriber, EventType eventType) {
        this.eventListener = eventSubscriber;
        this.eventGroup = null;
        this.eventType = eventType;
    }

    /**
     * Instantiates a new Event subscription.
     *
     * @param eventSubscriber the event subscriber
     * @param eventGroup      the event group
     */
    public EventSubscription(EventListener eventSubscriber, EventGroup eventGroup) {
        this.eventListener = eventSubscriber;
        this.eventGroup = eventGroup;
        this.eventType = null;
    }

    /**
     * Gets event listener.
     *
     * @return the event listener
     */
    public EventListener getEventListener() {
        return eventListener;
    }

    /**
     * Gets event group.
     *
     * @return the event group
     */
    public EventGroup getEventGroup() {
        return eventGroup;
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
        if (Objects.equals(eventGroup, null)) {
            if (Objects.equals(event.getEventType(), eventType)) {
                eventListener.onEvent(event);
            }
        } else {
            if (Objects.equals(event.getEventGroup(), eventGroup)) {
                eventListener.onEvent(event);
            }
        }
    }
}
