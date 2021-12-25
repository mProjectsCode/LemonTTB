package io.github.mProjectsCode.LemonTTB.springboot.events;

public class StartUpEvent {
    public String component;
    public Result result;

    public enum Result {
        ERROR,
        WARNING,
        OK
    }

    public StartUpEvent(String component, Result result) {
        this.component = component;
        this.result = result;
    }
}
