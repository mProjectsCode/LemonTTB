package io.github.mProjectsCode.LemonTTB.events;

import java.util.UUID;

/**
 * The type Event.
 */
public class Event {
    private UUID id;
    private EventGroup eventGroup;
    private EventType eventType;
    private String name;
    private String payload;
    private String originClass;

    /**
     * Instantiates a new Event.
     *
     * @param eventGroup  the event group
     * @param eventType   the event type
     * @param name        the name
     * @param payload     the payload
     * @param originClass the origin class
     */
    public Event(EventGroup eventGroup, EventType eventType, String name, String payload, String originClass) {
        this.id = UUID.randomUUID();
        this.eventGroup = eventGroup;
        this.eventType = eventType;
        this.name = name;
        this.payload = payload;
        this.originClass = originClass;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public UUID getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(UUID id) {
        this.id = id;
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
     * Sets event group.
     *
     * @param eventGroup the event group
     */
    public void setEventGroup(EventGroup eventGroup) {
        this.eventGroup = eventGroup;
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
     * Sets event type.
     *
     * @param eventType the event type
     */
    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets payload.
     *
     * @return the payload
     */
    public String getPayload() {
        return payload;
    }

    /**
     * Sets payload.
     *
     * @param payload the payload
     */
    public void setPayload(String payload) {
        this.payload = payload;
    }

    /**
     * Gets origin class.
     *
     * @return the origin class
     */
    public String getOriginClass() {
        return originClass;
    }

    /**
     * Sets origin class.
     *
     * @param originClass the origin class
     */
    public void setOriginClass(String originClass) {
        this.originClass = originClass;
    }
}
