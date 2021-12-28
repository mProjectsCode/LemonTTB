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
                    <span class="badge" v-bind:class="[audioPlayerStatusBadgeClass]">{{
                            audioPlayerStatusBadgeContent
                        }}</span>
                </span>
            </h2>
            <h6 class="">
                Information about the audio player of the bot.
            </h6>
        </div>
        <div class="card-body">
            <div v-if="audioPlayerStatus === 'playing' || audioPlayerStatus === 'paused'">
                <b>Playing</b>
                {{ data.audioPlayerState?.currentTrack?.name }}

                <div class="progress" style="margin-top: 10px; margin-bottom: 15px">
                    <div aria-valuemax="100" aria-valuemin="0" class="progress-bar"
                         role="progressbar" v-bind:aria-valuenow="[getProgress]" v-bind:style="['width: ' + getProgress + '%;']"></div>
                </div>
                <div style="display: flex; align-items: center; gap: 10px">
                    <button class="btn btn-primary" type="button" v-on:click="switchPause()">
                        <span class="material-icons" style="vertical-align: middle">{{
                                data.audioPlayerState?.paused ? 'play_arrow' : 'pause'
                            }}</span>
                    </button>
                    <button class="btn btn-secondary" type="button" v-on:click="skip()">
                        <span class="material-icons" style="vertical-align: middle">skip_next</span>
                    </button>
                    <button class="btn"
                            type="button"
                            v-bind:class="[data.audioPlayerState?.looping ? 'btn-success' : 'btn-secondary']" v-on:click="switchLooping()">
                        <span class="material-icons" style="vertical-align: middle">all_inclusive</span>
                    </button>
                    <span style="flex: 1"></span>
                    <span>
                        {{ getProgressLabel }}
                    </span>
                </div>
                <br>
                <b>Queue</b>
                <div style="margin-bottom: 3px"></div>
                <div v-for="queueEntry in data.audioPlayerState?.queue" class="row" style="margin-top: 2px">
                    <div class="col">
                        {{ queueEntry.name }}
                    </div>
                    <div class="col-auto">
                        {{ new Date(queueEntry.length * 1000).toISOString().substr(14, 5) }}
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
                    return 'bg-success';
                case 'paused':
                    return 'bg-warning';
                case 'connected':
                    return 'bg-warning';
                default:
                    return 'bg-danger';
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
            this.$store.commit('addToFetchOnStartUp', 'api/audioPlayer/getStatus');
        },
        async switchPause() {
            await fetch('api/audioPlayer/switchPause')
        },
        async switchLooping() {
            await fetch('api/audioPlayer/switchLooping')
        },
        async skip() {
            await fetch('api/audioPlayer/skip')
        }
    },
})
export default class AudioPlayerStatus extends Vue {
}
</script>