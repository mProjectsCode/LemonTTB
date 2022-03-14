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
                    Moderation
                </span>
                <span style="flex: 1">
                    <span class="badge badge-success">Online</span>
                </span>
            </h2>
            <h6 class="">
                Moderate the two table top voice channels.
            </h6>
        </div>
        <div class="card-body">
            <div style="display: flex; gap: 10px">
                <div style="flex: 1">
                    <b>Primary Voice Channel </b>
                    {{ data.voiceChannelState?.primaryChannelName }}
                </div>
            </div>
            <div style="margin-bottom: 8px"></div>
            <div v-for="(entry, index) in data.voiceChannelState?.primaryChannelUsers" style="margin-top: 2px; display: flex; gap: 10px">
                <div>
                    <div style="vertical-align: middle">{{ entry.user.id }}</div>
                </div>
                <div style="flex: 1">
                    <div style="vertical-align: middle">{{ entry.name }}</div>
                </div>
                <div>
                    <button
                            class="btn btn-sm"
                            v-bind:class="[entry.deafened ? 'btn-danger' : 'btn-secondary']"
                            style="vertical-align: middle; padding: 0 6px 0 6px;"
                            type="button"
                            v-on:click="switchDeafMute(entry.user.id)">
                        <span class="material-icons" style="vertical-align: middle">headset_off</span>
                    </button>
                </div>
            </div>
            <br>
            <div style="display: flex; gap: 10px">
                <div style="flex: 1">
                    <b>Secondary Voice Channel </b>
                    {{ data.voiceChannelState?.secondaryChannelName }}
                </div>
            </div>
            <div style="margin-bottom: 8px"></div>
            <div v-for="(entry, index) in data.voiceChannelState?.secondaryChannelUsers" style="margin-top: 2px; display: flex; gap: 10px">
                <div>
                    <div style="vertical-align: middle">{{ entry.user.id }}</div>
                </div>
                <div style="flex: 1">
                    <div style="vertical-align: middle">{{ entry.name }}</div>
                </div>
                <div>
                    <button
                            class="btn btn-sm"
                            v-bind:class="[entry.deafened ? 'btn-danger' : 'btn-secondary']"
                            style="vertical-align: middle; padding: 0 6px 0 6px;"
                            type="button"
                            v-on:click="switchDeafMute(entry.user.id)">
                        <span class="material-icons" style="vertical-align: middle">headset_off</span>
                    </button>
                </div>
            </div>
        </div>
    </div>
</template>

<script lang="ts">
import {Options, Vue} from "vue-class-component";
import {EventData, VoiceChannelPayloadData} from "@/main";

interface Data {
    voiceChannelState?: VoiceChannelPayloadData;
}

@Options({
    name: 'Moderation',

    props: {},

    data() {
        const data: Data = {
            voiceChannelState: undefined
        };
        return {
            data
        };
    },

    computed: {
        audioPlayerStatus() {
            return this.$store.getters.getAudioPlayerStatus
        },
    },

    mounted() {
        this.getStatus();

        this.emitter.on('api-event', (data: EventData) => {
            if (data.eventType == 'VOICE_CHANNEL') {
                console.log('received api event in Moderation.vue');
                this.data.voiceChannelState = data.payload.data;
            }
        });
    },

    methods: {
        async getStatus() {
            if (this.$store.getters.getBotStatus === 'online') {
                await fetch('api/voiceChannel/getStatus');
            } else {
                this.$store.commit('addToFetchOnStartUp', 'api/voiceChannel/getStatus');
            }
        },
        async switchDeafMute(id: string) {
            await fetch('api/voiceChannel/switchDeafMute/' + id);
        }
    },
})
export default class Moderation extends Vue {
}
</script>