package io.github.mProjectsCode.LemonTTB.springboot.events;

/**
 * The type Start up event.
 */
public class StartUpEvent {
    /**
     * The Component.
     */
    public String component;
    /**
     * The Result.
     */
    public Result result;

    /**
     * Instantiates a new Start up event.
     *
     * @param component the component
     * @param result    the result
     */
    public StartUpEvent(String component, Result result) {
        this.component = component;
        this.result = result;
    }

    /**
     * The enum Result.
     */
    public enum Result {
        /**
         * Error result.
         */
        ERROR,
        /**
         * Warning result.
         */
        WARNING,
        /**
         * Ok result.
         */
        OK
    }
}
