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

package io.github.mProjectsCode.LemonTTB.moderation;

import io.github.mProjectsCode.LemonTTB.App;
import io.github.mProjectsCode.LemonTTB.Config;
import io.github.mProjectsCode.LemonTTB.events.Event;
import io.github.mProjectsCode.LemonTTB.events.EventGroup;
import io.github.mProjectsCode.LemonTTB.events.EventHandler;
import io.github.mProjectsCode.LemonTTB.events.EventType;
import io.github.mProjectsCode.LemonTTB.events.payloads.payloads.VoiceChannelPayload;
import net.dv8tion.jda.api.entities.GuildChannel;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceGuildDeafenEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceGuildMuteEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceUpdateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * The type Voice channel handler.
 */
public class VoiceChannelHandler extends ListenerAdapter {
    @Override
    public void onGuildVoiceUpdate(@NotNull GuildVoiceUpdateEvent event) {
        GuildChannel primaryChannel = App.jda.getGuildChannelById(Config.options.primaryVoiceChannel);
        GuildChannel secondaryChannel = App.jda.getGuildChannelById(Config.options.secondaryVoiceChannel);

        if (Objects.equals(event.getChannelJoined(), primaryChannel) || Objects.equals(event.getChannelJoined(), secondaryChannel) || Objects.equals(event.getChannelLeft(), primaryChannel) || Objects.equals(event.getChannelLeft(), secondaryChannel)) {
            EventHandler.trigger(new Event(
                    EventGroup.BOT,
                    EventType.VOICE_CHANNEL,
                    "User joined voice channel",
                    new VoiceChannelPayload(),
                    VoiceChannelHandler.class.getName()
            ));
        }
    }

    @Override
    public void onGuildVoiceGuildMute(@NotNull GuildVoiceGuildMuteEvent event) {
        GuildChannel primaryChannel = App.jda.getGuildChannelById(Config.options.primaryVoiceChannel);
        GuildChannel secondaryChannel = App.jda.getGuildChannelById(Config.options.secondaryVoiceChannel);

        if (Objects.equals(event.getVoiceState().getChannel(), primaryChannel) || Objects.equals(event.getVoiceState().getChannel(), secondaryChannel)) {
            EventHandler.trigger(new Event(
                    EventGroup.BOT,
                    EventType.VOICE_CHANNEL,
                    "User was muted",
                    new VoiceChannelPayload(),
                    VoiceChannelHandler.class.getName()
            ));
        }
    }

    @Override
    public void onGuildVoiceGuildDeafen(@NotNull GuildVoiceGuildDeafenEvent event) {
        GuildChannel primaryChannel = App.jda.getGuildChannelById(Config.options.primaryVoiceChannel);
        GuildChannel secondaryChannel = App.jda.getGuildChannelById(Config.options.secondaryVoiceChannel);

        if (Objects.equals(event.getVoiceState().getChannel(), primaryChannel) || Objects.equals(event.getVoiceState().getChannel(), secondaryChannel)) {
            EventHandler.trigger(new Event(
                    EventGroup.BOT,
                    EventType.VOICE_CHANNEL,
                    "User was deafened",
                    new VoiceChannelPayload(),
                    VoiceChannelHandler.class.getName()
            ));
        }
    }
}
