/*
 * This file is part of LemonTTB.
 * (C) Copyright 2021-2022
 * Developed by Moritz Jung
 *
 * LemonTTB is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * LemonTTB is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with LemonTTB.  If not, see <https://www.gnu.org/licenses/>.
 *
 */

package io.github.mProjectsCode.LemonTTB.springboot;

import com.google.gson.Gson;
import io.github.mProjectsCode.LemonTTB.Logger.Logger;
import io.github.mProjectsCode.LemonTTB.events.Event;
import io.github.mProjectsCode.LemonTTB.events.EventGroup;
import io.github.mProjectsCode.LemonTTB.events.EventHandler;
import io.github.mProjectsCode.LemonTTB.events.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The type Event controller.
 */
@RestController
@RequestMapping("/api/events")
public class EventController implements EventListener {
    private static final Logger LOGGER = Logger.getLogger(EventController.class);

    private final List<Event> eventQueue = new ArrayList<>();
    private final Gson gson = new Gson();
    private final ExecutorService startUpExecutor = Executors.newSingleThreadExecutor();

    private SseEmitter sseEmitter;
    private int eventIndex = 0;

    /**
     * Instantiates a new Watch controller.
     */
    public EventController() {
        EventHandler.subscribe(EventGroup.BOT, this);
    }

    /**
     * Stream start up events to sse emitter.
     * It is highly recommended to call /api/events/unsubscribe first, otherwise strange things might happen.
     *
     * @return the sse emitter
     */
    @CrossOrigin
    @GetMapping("/subscribe")
    public SseEmitter streamStartUpEvents() {
        sseEmitter = new SseEmitter(Long.MAX_VALUE);

        sseEmitter.onCompletion(() -> LOGGER.logInfo("Start up SseEmitter is completed"));

        sseEmitter.onTimeout(() -> LOGGER.logInfo("Start up SseEmitter is timed out"));

        sseEmitter.onError((ex) -> {
            LOGGER.logInfo("Start up SseEmitter got error:");
            LOGGER.logError(ex);
        });

        startUpExecutor.execute(() -> {
            try {
                while (true) {
                    if (eventIndex < eventQueue.size()) {
                        while (eventIndex < eventQueue.size()) {
                            Event event = eventQueue.get(eventIndex);
                            LOGGER.logDebug("Event controller send event");
                            sseEmitter.send(event);
                            eventIndex += 1;
                        }
                    } else {
                        Thread.sleep(100L);
                    }
                }
            } catch (InterruptedException e) {
                LOGGER.logWarning("The connection on /api/events/subscribe was interrupted");
                sseEmitter.complete();
            } catch (IOException e) {
                LOGGER.logWarning("An io exception occurred on /api/events/subscribe");
                sseEmitter.complete();
            } catch (Throwable t) {
                LOGGER.logWarning("An exception occurred on /api/events/subscribe");
                sseEmitter.complete();
            }
        });

        return sseEmitter;
    }

    /**
     * Unsubscribe response entity.
     *
     * @return the response entity
     */
    @RequestMapping("/unsubscribe")
    public ResponseEntity<String> unsubscribe() {
        if (!Objects.equals(sseEmitter, null)) {
            sseEmitter.complete();
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * Gets all events.
     *
     * @return the all events
     */
    @CrossOrigin
    @GetMapping("/getAll")
    public List<Event> getAllEvents() {
        return eventQueue;
    }


    @Override
    public void onEvent(Event event) {
        eventQueue.add(event);
        LOGGER.logDebug("Event controller received event");
    }
}
