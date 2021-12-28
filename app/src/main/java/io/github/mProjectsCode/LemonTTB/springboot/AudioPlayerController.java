/*
 * This file is part of LemonTTB.
 * (C) Copyright 2021
 * Programmed by Moritz Jung
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

import io.github.mProjectsCode.LemonTTB.App;
import io.github.mProjectsCode.LemonTTB.Logger.Logger;
import io.github.mProjectsCode.LemonTTB.events.Event;
import io.github.mProjectsCode.LemonTTB.events.EventGroup;
import io.github.mProjectsCode.LemonTTB.events.EventHandler;
import io.github.mProjectsCode.LemonTTB.events.EventType;
import io.github.mProjectsCode.LemonTTB.events.payloads.payloads.AudioPlayerPayload;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Audio player controller.
 */
@RestController
@RequestMapping("/api/audioPlayer")
public class AudioPlayerController {
    private static final Logger LOGGER = Logger.getLogger(EventController.class);


    /**
     * Gets status.
     *
     * @return the status
     */
    @ResponseBody
    @RequestMapping(value = "/getStatus", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<String> getStatus() {
        EventHandler.trigger(new Event(
                EventGroup.BOT,
                EventType.AUDIO_PLAYER,
                "Audio Player status request from web interface",
                new AudioPlayerPayload(AudioPlayerPayload.AudioPlayerPayloadResponse.STATUS_REQUEST),
                AudioPlayerController.class.getName()
        ));
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * Switch pause response entity.
     *
     * @return the response entity
     */
    @ResponseBody
    @RequestMapping(value = "/switchPause", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<String> switchPause() {
        App.audioManager.setPaused(!App.audioManager.isPaused());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * Switch looping response entity.
     *
     * @return the response entity
     */
    @ResponseBody
    @RequestMapping(value = "/switchLooping", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<String> switchLooping() {
        App.audioManager.setLooping(!App.audioManager.isLooping());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * Skip response entity.
     *
     * @return the response entity
     */
    @ResponseBody
    @RequestMapping(value = "/skip", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<String> skip() {
        App.audioManager.skipTrack();
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
