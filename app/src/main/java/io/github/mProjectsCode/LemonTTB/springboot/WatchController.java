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
import io.github.mProjectsCode.LemonTTB.App;
import io.github.mProjectsCode.LemonTTB.Logger.Logger;
import io.github.mProjectsCode.LemonTTB.events.Event;
import io.github.mProjectsCode.LemonTTB.events.EventGroup;
import io.github.mProjectsCode.LemonTTB.events.EventHandler;
import io.github.mProjectsCode.LemonTTB.events.EventType;
import io.github.mProjectsCode.LemonTTB.events.payloads.payloads.ErrorPayload;
import io.github.mProjectsCode.LemonTTB.exceptions.StartUpException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * The type Watch controller.
 */
@RestController
@RequestMapping("/api/startUp")
public class WatchController {
    private static final Logger LOGGER = Logger.getLogger(TimeController.class);
    private final Gson gson = new Gson();

    /**
     * Instantiates a new Watch controller.
     */
    public WatchController() {

    }

    /**
     * Start bot response entity.
     *
     * @return the response entity
     */
    @ResponseBody
    @RequestMapping(value = "/startBot", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<String> startBot() {
        try {
            App.startBot();
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (StartUpException e) {
            EventHandler.trigger(new Event(
                    EventGroup.BOT,
                    EventType.BOT_ERROR,
                    "Bot error on startup",
                    new ErrorPayload(e),
                    WatchController.class.getName()
            ));
            App.shutdownBot("Bot error on startup: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
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
     * Is bot online boolean.
     *
     * @return the boolean
     */
    @CrossOrigin
    @GetMapping("/botOnline")
    public boolean isBotOnline() {
        return App.isBotOnline;
    }
}
