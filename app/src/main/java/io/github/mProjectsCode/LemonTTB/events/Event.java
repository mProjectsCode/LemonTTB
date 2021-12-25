package io.github.mProjectsCode.LemonTTB.events;

import java.util.UUID;

public class Event {
    private UUID id;
    private EventType eventType;
    private String name;
    private String payload;
    private String originClass;

    public Event(EventType eventType, String name, String payload, String originClass) {
        this.id = UUID.randomUUID();
        this.eventType = eventType;
        this.name = name;
        this.payload = payload;
        this.originClass = originClass;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public String getOriginClass() {
        return originClass;
    }

    public void setOriginClass(String originClass) {
        this.originClass = originClass;
    }
}
