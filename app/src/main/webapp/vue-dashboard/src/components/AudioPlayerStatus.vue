<!--
  - This file is part of LemonTTB.
  - (C) Copyright 2021
  - Programmed by Moritz Jung
  -
  - LemonTTB is free software: you can redistribute it and/or modify
  - it under the terms of the GNU General Public License as published by
  - the Free Software Foundation, either version 3 of the License, or
  - (at your option) any later version.
  -
  - LemonTTB is distributed in the hope that it will be useful,
  - but WITHOUT ANY WARRANTY; without even the implied warranty of
  - MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  - GNU General Public License for more details.
  -
  - You should have received a copy of the GNU General Public License
  - along with LemonTTB.  If not, see <https://www.gnu.org/licenses/>.
  -
  -->

<template>
    <div class="card shadow">
        <div class="card-header">
            <h2 style="display: flex; align-items: center; gap: 10px">
                <span>
                    Audio Player Status
                </span>
                <span style="flex: 1">
                    <span class="badge" v-bind:class="[audioPlayerStatusBadgeClass]">
                        {{ audioPlayerStatusBadgeContent }}</span>
                </span>
            </h2>
            <h6 class="">
                Information about the audio player of the bot.
            </h6>
        </div>
        <div class="card-body">
            <div v-if="audioPlayerStatus !== 'disconnected'">
                <b>Connected to </b>
                {{ data.audioPlayerState?.channelName }}
                <b> in </b>
                {{ data.audioPlayerState?.guildName }}
                <br>
                <br>
            </div>

            <div v-if="audioPlayerStatus === 'playing' || audioPlayerStatus === 'paused'">
                <b>Playing</b>
                {{ data.audioPlayerState?.currentTrack?.name }}

                <div class="progress bg-dark" style="margin-top: 10px; margin-bottom: 15px;">
                    <div aria-valuemax="100"
                         aria-valuemin="0"
                         class="progress-bar"
                         role="progressbar"
                         v-bind:aria-valuenow="[getProgress]"
                         v-bind:style="['width: ' + getProgress + '%;']"></div>
                </div>
                <div style="display: flex; align-items: center; gap: 10px">
                    <button class="btn btn-primary" type="button" v-on:click="switchPause()">
                        <span class="material-icons" style="vertical-align: middle">
                            {{ data.audioPlayerState?.paused ? 'play_arrow' : 'pause' }}
                        </span>
                    </button>
                    <button class="btn btn-secondary" type="button" v-on:click="skip()">
                        <span class="material-icons" style="vertical-align: middle">skip_next</span>
                    </button>
                    <button class="btn"
                            type="button"
                            v-bind:class="[data.audioPlayerState?.looping ? 'btn-success' : 'btn-secondary']"
                            v-on:click="switchLooping()">
                        <span class="material-icons" style="vertical-align: middle">all_inclusive</span>
                    </button>
                    <span style="flex: 1"></span>
                    <span>
                        {{ getProgressLabel }}
                    </span>
                </div>
                <br>
                <div style="display: flex; gap: 10px">
                    <div style="flex: 1">
                        <b>Queue</b>
                    </div>
                    <div>
                        <button class="btn btn-danger btn-sm" style="vertical-align: middle; padding: 0px 6px 0px 6px;" type="button" v-on:click="clearQueue">
                            <span class="material-icons" style="vertical-align: middle">clear_all</span>
                        </button>
                    </div>
                </div>
                <div style="margin-bottom: 8px"></div>
                <div v-for="(queueEntry, index) in data.audioPlayerState?.queue" style="margin-top: 2px; display: flex; gap: 10px">
                    <div style="flex: 1">
                        <div style="vertical-align: middle">{{ queueEntry.name }}</div>
                    </div>
                    <div>
                        <div style="vertical-align: middle">{{ new Date(queueEntry.length * 1000).toISOString().substr(14, 5) }}</div>
                    </div>
                    <div>
                        <button class="btn btn-danger btn-sm" style="vertical-align: middle; padding: 0 6px 0 6px;" type="button" v-on:click="removeFromQueue(index)">
                            <span class="material-icons" style="vertical-align: middle">clear</span>
                        </button>
                    </div>
                </div>
            </div>
            <div v-else>
                No track currently playing.
            </div>
        </div>
    </div>
</template>

<script lang="ts">
import {Options, Vue} from "vue-class-component";
import {AudioPlayerEventPayloadData, EventData} from "@/main";

interface Data {
    audioPlayerError?: string;
    audioPlayerState?: AudioPlayerEventPayloadData;

    audioPlayerProgressInterval?: number;
    audioPlayerProgress: number;
    audioPlayerPlaying: boolean;
}

@Options({
    name: 'AudioPlayerStatus',

    props: {},

    data() {
        const data: Data = {
            audioPlayerError: undefined,
            audioPlayerState: undefined,
            audioPlayerProgressInterval: undefined,
            audioPlayerProgress: 0,
            audioPlayerPlaying: false,
        };
        return {
            data
        };
    },

    computed: {
        audioPlayerStatus() {
            return this.$store.getters.getAudioPlayerStatus
        },
        audioPlayerStatusBadgeClass() {
            const status = this.$store.getters.getAudioPlayerStatus;
            switch (status) {
                case 'playing':
                    return 'badge-success';
                case 'paused':
                    return 'badge-secondary';
                case 'connected':
                    return 'badge-secondary';
                default:
                    return 'badge-danger';
            }
        },
        audioPlayerStatusBadgeContent() {
            const status = this.$store.getters.getAudioPlayerStatus;
            if (this.data.audioPlayerError) {
                return 'Error';
            } else {
                return status.charAt(0).toUpperCase() + status.slice(1);
            }
        },
        getProgress() {
            return (this.data.audioPlayerProgress * 100) / this.data.audioPlayerState?.currentTrack?.length;
        },
        getProgressLabel() {
            const progress = new Date(this.data.audioPlayerProgress * 1000).toISOString().substr(14, 5);
            const length = new Date(this.data.audioPlayerState?.currentTrack?.length * 1000).toISOString().substr(14, 5);
            return progress + ' / ' + length;
        }
    },

    mounted() {
        this.getStatus();

        this.emitter.on('api-event', (data: EventData) => {
            if (data.eventType == 'AUDIO_PLAYER') {
                console.log('received api event in AudioPlayerStatus.vue');
                this.data.audioPlayerState = data.payload.data;


                const connected: boolean = this.data.audioPlayerState.channelName && this.data.audioPlayerState.guildName;
                const hasCurrentTrack: boolean = this.data.audioPlayerState.currentTrack != undefined;
                const isCurrentTrackFinished: boolean = hasCurrentTrack ? this.data.audioPlayerState.currentTrack.position >= this.data.audioPlayerState.currentTrack.length : true;

                if (connected) {
                    if ((data.payload.response == 'STARTED_TRACK' || data.payload.response == 'STATUS_REQUEST') && !isCurrentTrackFinished) {
                        this.data.audioPlayerPlaying = true;
                    } else if (data.payload.response == 'FINISHED_TRACK') {
                        this.data.audioPlayerPlaying = false;
                    }
                } else {
                    this.data.audioPlayerPlaying = false;
                }

                if (connected) {
                    if (this.data.audioPlayerPlaying) {
                        if (this.data.audioPlayerState.paused) {
                            this.$store.commit('setAudioPlayerStatus', 'paused');
                        } else {
                            this.$store.commit('setAudioPlayerStatus', 'playing');
                        }
                    } else {
                        this.$store.commit('setAudioPlayerStatus', 'connected');
                    }
                } else {
                    this.$store.commit('setAudioPlayerStatus', 'disconnected');
                }

                if (hasCurrentTrack) {
                    console.log("has current track")
                    this.data.audioPlayerProgress = this.data.audioPlayerState.currentTrack.position;

                    if (this.data.audioPlayerProgressInterval) {
                        clearInterval(this.data.audioPlayerProgressInterval);
                    }

                    if (!this.data.audioPlayerState.paused && this.data.audioPlayerPlaying) {
                        this.data.audioPlayerProgressInterval = setInterval(() => {
                            this.data.audioPlayerProgress += 1;
                        }, 1000);
                    }
                }
            }
        })
    },

    methods: {
        async getStatus() {
            if (this.$store.getters.getBotStatus === 'online') {
                await fetch('api/audioPlayer/getStatus');
            } else {
                this.$store.commit('addToFetchOnStartUp', 'api/audioPlayer/getStatus');
            }
        },
        async switchPause() {
            await fetch('api/audioPlayer/switchPause');
        },
        async switchLooping() {
            await fetch('api/audioPlayer/switchLooping');
        },
        async skip() {
            await fetch('api/audioPlayer/skip');
        },
        async clearQueue() {
            await fetch('api/audioPlayer/clearQueue');
        },
        async removeFromQueue(i: number) {
            await fetch('api/audioPlayer/removeFromQueue/' + i);
        },
        async joinVC() {
            // TODO: implement this
        },
        async leaveVC() {
            // TODO: implement this
        }
    },
})
export default class AudioPlayerStatus extends Vue {
}
</script>