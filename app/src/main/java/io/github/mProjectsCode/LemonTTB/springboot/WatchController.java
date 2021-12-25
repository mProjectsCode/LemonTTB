package io.github.mProjectsCode.LemonTTB.springboot;

import com.google.gson.Gson;
import io.github.mProjectsCode.LemonTTB.App;
import io.github.mProjectsCode.LemonTTB.Logger.Logger;
import io.github.mProjectsCode.LemonTTB.events.Event;
import io.github.mProjectsCode.LemonTTB.events.EventHandler;
import io.github.mProjectsCode.LemonTTB.events.EventListener;
import io.github.mProjectsCode.LemonTTB.events.EventType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The type Watch controller.
 */
@RestController
@RequestMapping("/api/startUp")
public class WatchController implements EventListener {
    private static final Logger LOGGER = Logger.getLogger(TimeController.class);
    /**
     * The Start up event queue.
     */
    public final List<Event> startUpEventQueue = new ArrayList<>();
    private final Gson gson = new Gson();
    private final ExecutorService startUpExecutor = Executors.newSingleThreadExecutor();

    private int eventIndex = 0;

    /**
     * Instantiates a new Watch controller.
     */
    public WatchController() {
        EventHandler.subscribe(EventType.START_UP_EVENT, this);
    }

    /**
     * Start bot response entity.
     *
     * @return the response entity
     */
    @ResponseBody
    @RequestMapping(value = "/startBot", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<String> startBot() {
        App.startBot();
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * Stop bot response entity.
     *
     * @return the response entity
     */
    @ResponseBody
    @RequestMapping(value = "/stopBot", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<String> stopBot() {
        App.shutdownBot("Shut down from web interface");
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * Stream start up events sse emitter.
     *
     * @return the sse emitter
     */
    @CrossOrigin
    @GetMapping("/events")
    public SseEmitter streamStartUpEvents() {
        SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);

        sseEmitter.onCompletion(() -> LOGGER.logInfo("Start up SseEmitter is completed"));

        sseEmitter.onTimeout(() -> LOGGER.logInfo("Start up SseEmitter is timed out"));

        sseEmitter.onError((ex) -> {
            LOGGER.logInfo("Start up SseEmitter got error:");
            LOGGER.logError(ex);
        });

        startUpExecutor.execute(() -> {
            try {
                while (true) {
                    if (eventIndex < startUpEventQueue.size()) {
                        Event event = startUpEventQueue.get(eventIndex);
                        sseEmitter.send(event);
                        eventIndex += 1;
                    } else {
                        Thread.sleep(100L);
                    }
                }
            } catch (InterruptedException e) {
                LOGGER.logWarning("The connection on /api/statUp/events was interrupted");
                sseEmitter.complete();
            } catch (IOException e) {
                LOGGER.logWarning("An io exception occurred on /api/statUp/events");
                sseEmitter.complete();
            } catch (Throwable t) {
                LOGGER.logWarning("An exception occurred on /api/statUp/events");
                sseEmitter.complete();
            }
        });

        return sseEmitter;
    }

    /**
     * Gets all events.
     *
     * @return the all events
     */
    @CrossOrigin
    @GetMapping("/allEvents")
    public List<Event> getAllEvents() {
        return startUpEventQueue;
    }

    /**
     * Is bot online boolean.
     *
     * @return the boolean
     */
    @CrossOrigin
    @GetMapping("/botOnline")
    public boolean isBotOnline() {
        return App.isBotOnline;
    }

    @Override
    public void onEvent(Event event) {
        startUpEventQueue.add(event);
    }
}
