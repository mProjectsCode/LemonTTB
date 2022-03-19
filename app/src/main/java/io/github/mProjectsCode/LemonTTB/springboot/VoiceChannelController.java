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
import io.github.mProjectsCode.LemonTTB.Logger.Logger;
import io.github.mProjectsCode.LemonTTB.events.Event;
import io.github.mProjectsCode.LemonTTB.events.EventGroup;
import io.github.mProjectsCode.LemonTTB.events.EventHandler;
import io.github.mProjectsCode.LemonTTB.events.EventType;
import io.github.mProjectsCode.LemonTTB.events.payloads.payloads.VoiceChannelPayload;
import net.dv8tion.jda.api.entities.GuildChannel;
import net.dv8tion.jda.api.entities.Member;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * The type Voice channel controller.
 */
@RestController
@RequestMapping("/api/voiceChannel")
public class VoiceChannelController {
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
                EventType.VOICE_CHANNEL,
                "Voice Channel status request from web interface",
                new VoiceChannelPayload(),
                VoiceChannelController.class.getName()
        ));
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * Gets status.
     *
     * @param id the id
     * @return the status
     */
    @ResponseBody
    @RequestMapping(value = "/move/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<String> move(@PathVariable Integer id) {


        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * Gets status.
     *
     * @param id the id
     * @return the status
     */
    @ResponseBody
    @RequestMapping(value = "/switchDeafMute/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<String> switchDeafMute(@PathVariable String id) {
        GuildChannel primaryChannel = App.jda.getGuildChannelById(Config.options.primaryVoiceChannel);
        GuildChannel secondaryChannel = App.jda.getGuildChannelById(Config.options.secondaryVoiceChannel);

        assert primaryChannel != null;
        assert secondaryChannel != null;

        List<Member> primaryChannelMembersWithMatchingId = primaryChannel.getMembers().stream().filter(m -> Objects.equals(m.getId(), id)).toList();
        List<Member> secondaryChannelMembersWithMatchingId = secondaryChannel.getMembers().stream().filter(m -> Objects.equals(m.getId(), id)).toList();

        Member memberToDeafMute = primaryChannelMembersWithMatchingId.size() > 0 ? primaryChannelMembersWithMatchingId.get(0) : null;
        memberToDeafMute = secondaryChannelMembersWithMatchingId.size() > 0 ? secondaryChannelMembersWithMatchingId.get(0) : memberToDeafMute;

        if (!Objects.equals(memberToDeafMute, null)) {
            memberToDeafMute.getGuild().deafen(memberToDeafMute, !memberToDeafMute.getVoiceState().isGuildDeafened()).queue();
            memberToDeafMute.getGuild().mute(memberToDeafMute, !memberToDeafMute.getVoiceState().isGuildMuted()).queue();
        }

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
