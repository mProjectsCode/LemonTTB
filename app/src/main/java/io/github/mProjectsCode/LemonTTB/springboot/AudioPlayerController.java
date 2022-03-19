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

import io.github.mProjectsCode.LemonTTB.App;
import io.github.mProjectsCode.LemonTTB.Config;
import io.github.mProjectsCode.LemonTTB.IOHelper;
import io.github.mProjectsCode.LemonTTB.LemonTTB_Audio.AudioTrackSource;
import io.github.mProjectsCode.LemonTTB.Logger.Logger;
import io.github.mProjectsCode.LemonTTB.commands.Command;
import io.github.mProjectsCode.LemonTTB.events.Event;
import io.github.mProjectsCode.LemonTTB.events.EventGroup;
import io.github.mProjectsCode.LemonTTB.events.EventHandler;
import io.github.mProjectsCode.LemonTTB.events.EventType;
import io.github.mProjectsCode.LemonTTB.events.payloads.payloads.AudioPlayerPayload;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildChannel;
import net.dv8tion.jda.api.entities.Member;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.List;
import java.util.Objects;

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

    /**
     * Remove from queue response entity.
     *
     * @param index the index
     * @return the response entity
     */
    @ResponseBody
    @RequestMapping(value = "/removeFromQueue/{index}", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<String> removeFromQueue(@PathVariable Integer index) {
        App.audioManager.removeFromQueue(index);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * Clear queue response entity.
     *
     * @return the response entity
     */
    @ResponseBody
    @RequestMapping(value = "/clearQueue", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<String> clearQueue() {
        App.audioManager.clearQueue();
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @ResponseBody
    @RequestMapping(value = "/queue", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<String> queue(@RequestParam Boolean local, @RequestParam String link) {
        if (local) {
            File[] paths = IOHelper.findPathsByFileName(link, App.audioPath);
            for (int i = 0; i < paths.length; i++) {
                AudioPlayerController.LOGGER.logTrace("Result " + i + " for search: " + link + " is: " + paths[i].getPath());
            }
            if (paths.length > 0) {
                AudioPlayerController.LOGGER.logDebug("Result for search: " + link + " is: " + paths[0].getPath());
                App.audioManager.loadAndPlayTrack(paths[0].getPath(), AudioTrackSource.LOCAL);
            }
        }
        else {
            App.audioManager.loadAndPlayTrack(link, AudioTrackSource.YOUTUBE);
        }

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @ResponseBody
    @RequestMapping(value = "/join", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<String> join() {
        List<Guild> guilds = App.jda.getGuilds();

        for (int i = 0; i < guilds.size(); i++) {
            // LOGGER.logDebug(guilds.get(i).getName());
            List<GuildChannel> channels = guilds.get(i).getChannels();
            guilds.get(i).loadMembers();

            for (int j = 0; j < channels.size(); j++) {

                if (Objects.equals(channels.get(j).getType(), ChannelType.VOICE)) {
                    // LOGGER.logDebug(channels.get(j).getName());
                    List<Member> members = channels.get(j).getMembers();
                    // LOGGER.logDebug(Integer.toString(members.size()));

                    for (int k = 0; k < members.size(); k++) {
                        // LOGGER.logDebug(members.get(k).getUser().getName());

                        if (Objects.equals(members.get(k).getId(), Config.options.botOwner)) {
                            App.audioManager.connect(channels.get(j), guilds.get(i));
                        }
                    }
                }
            }
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
