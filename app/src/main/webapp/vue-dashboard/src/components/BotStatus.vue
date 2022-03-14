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
                    Bot Status
                </span>
                <span style="flex: 1">
                    <span class="badge" v-bind:class="[botStatusBadgeClass]">{{ botStatusBadgeContent }}</span>
                </span>
                <button class="btn btn-secondary" type="button" v-on:click="getBotStatus(true)">
                    <span class="material-icons" style="vertical-align: middle">refresh</span>
                </button>
                <button class="btn" type="button"
                        v-bind:class="[data.autoOnlineQuery ? 'btn-success' : 'btn-secondary']" v-on:click="switchAutoOnlineQueryMode()">
                    <span class="material-icons" style="vertical-align: middle">autorenew</span>
                </button>
            </h2>
            <h6 class="">
                Information if the bot is online and buttons to start and stop it.
            </h6>
        </div>
        <div class="card-body">
            <h4>Actions</h4>
            <p>
                <button class="btn"
                        v-bind:class="[botStatus === 'online' ? 'btn-secondary' : 'btn-primary']"
                        v-on:click="startBot()">
                    {{ botStatus === 'online' ? 'Restart Bot' : 'Start Bot' }}
                </button>
            </p>
            <p>
                <button class="btn btn-danger" v-on:click="stopBot()">Stop Bot</button>
            </p>
            <h4>Component Status</h4>
            <div class="container">
                <div class="row">
                    <div class="col-auto">
                        <span class="material-icons"
                              style="vertical-align: middle"
                              v-bind:class="[data.botAudioPlayerOnline ? 'text-success' : 'text-danger']">
                            {{ data.botAudioPlayerOnline ? 'check' : 'clear' }}
                        </span>
                    </div>
                    <div class="col">
                        Audio Player
                    </div>
                </div>
                <div class="row">
                    <div class="col-auto">
                        <span class="material-icons"
                              style="vertical-align: middle"
                              v-bind:class="[data.botUserOnline ? 'text-success' : 'text-danger']">
                            {{ data.botUserOnline ? 'check' : 'clear' }}
                        </span>
                    </div>
                    <div class="col">
                        User Manager
                    </div>
                </div>
                <div class="row">
                    <div class="col-auto">
                        <span class="material-icons"
                              style="vertical-align: middle"
                              v-bind:class="[data.botPermissionsOnline ? 'text-success' : 'text-danger']">
                            {{ data.botPermissionsOnline ? 'check' : 'clear' }}
                        </span>
                    </div>
                    <div class="col">
                        Permission Manager
                    </div>
                </div>
                <div class="row">
                    <div class="col-auto">
                        <span class="material-icons"
                              style="vertical-align: middle"
                              v-bind:class="[data.botNameMappingsOnline ? 'text-success' : 'text-danger']">
                            {{ data.botNameMappingsOnline ? 'check' : 'clear' }}
                        </span>
                    </div>
                    <div class="col">
                        Name Mappings
                    </div>
                </div>
                <div class="row">
                    <div class="col-auto">
                        <span class="material-icons"
                              style="vertical-align: middle"
                              v-bind:class="[data.botJdaOnline ? 'text-success' : 'text-danger']">
                            {{ data.botJdaOnline ? 'check' : 'clear' }}
                        </span>
                    </div>
                    <div class="col">
                        JDA
                    </div>
                </div>
                <div class="row">
                    <div class="col-auto">
                        <span class="material-icons"
                              style="vertical-align: middle"
                              v-bind:class="[data.botConfigValidationOnline ? 'text-success' : 'text-danger']">
                            {{ data.botConfigValidationOnline ? 'check' : 'clear' }}
                        </span>
                    </div>
                    <div class="col">
                        Config Validated
                    </div>
                </div>
            </div>

            <div v-show="data.botStartUpError != undefined">
                <br>
                <h6>Bot start up errors:</h6>
                <p class="text-danger">
                    {{ data.botStartUpError }}
                </p>
            </div>
        </div>
    </div>
</template>

<script lang="ts">
import {Options, Vue} from "vue-class-component";
import {EventData} from "@/main";

interface Data {
    onlineQueryInterval?: number;
    autoOnlineQuery: boolean;

    botStartUpError?: string;

    botAudioPlayerOnline: boolean;
    botUserOnline: boolean;
    botPermissionsOnline: boolean;
    botNameMappingsOnline: boolean;
    botJdaOnline: boolean;
    botConfigValidationOnline: boolean;
}

@Options({
    name: 'BotStatus',

    props: {},

    data() {
        const data: Data = {
            onlineQueryInterval: undefined,
            autoOnlineQuery: false,

            botStartUpError: undefined,

            botAudioPlayerOnline: false,
            botUserOnline: false,
            botPermissionsOnline: false,
            botNameMappingsOnline: false,
            botJdaOnline: false,
            botConfigValidationOnline: false,
        };
        return {
            data
        };
    },

    computed: {
        botStatus() {
            return this.$store.getters.getBotStatus
        },
        botStatusBadgeClass() {
            const botStatus = this.$store.getters.getBotStatus;
            switch (botStatus) {
                case 'online':
                    return 'badge-success';
                case 'starting':
                    return 'badge-secondary';
                default:
                    return 'badge-danger';
            }
        },
        botStatusBadgeContent() {
            const botStatus = this.$store.getters.getBotStatus;
            if (this.data.botStartUpError) {
                return 'Error';
            } else {
                return botStatus.charAt(0).toUpperCase() + botStatus.slice(1);
            }
        },
    },

    mounted() {
        this.getBotStatus(false);

        this.emitter.on('api-event', (data: EventData) => {
            if (data.eventType == 'BOT_START_UP') {
                console.log('received api event in BotStatus.vue');
                switch (data.name) {
                    case 'AUDIO_PLAYER':
                        this.data.botAudioPlayerOnline = true;
                        break;
                    case 'USERS':
                        this.data.botUserOnline = true;
                        break;
                    case 'PERMISSIONS':
                        this.data.botPermissionsOnline = true;
                        break;
                    case 'NAME_MAPPINGS':
                        this.data.botNameMappingsOnline = true;
                        break;
                    case 'JDA':
                        this.data.botJdaOnline = true;
                        break;
                    case 'CONFIG_VALIDATION':
                        this.data.botConfigValidationOnline = true;
                        break;
                }
            } else if (data.eventType == 'BOT_STATUS') {
                console.log('received api event in BotStatus.vue');
                if (data.name == 'STOP') {
                    this.getBotStatus(false);
                } else if (data.name == 'STARTING') {
                    this.$store.commit('setBotStatus', 'starting');

                    this.data.botStartUpError = null;

                    this.data.botAudioPlayerOnline = false;
                    this.data.botUserOnline = false;
                    this.data.botPermissionsOnline = false;
                    this.data.botNameMappingsOnline = false;
                    this.data.botJdaOnline = false;
                    this.data.botConfigValidationOnline = false;
                } else if (data.name == 'ONLINE') {
                    this.$store.commit('setBotStatus', 'online');

                    const fetchOnStartUp: string[] = this.$store.getters.getFetchOnStartUp;
                    fetchOnStartUp.forEach((f: string) => {
                        fetch(f);
                    })
                    this.$store.commit('clearFetchOnStartUp');
                }
            } else if (data.eventType == 'BOT_ERROR') {
                console.log('received api event in BotStatus.vue');
                this.data.botStartUpError = data.payload.data;
            }
        })
    },

    methods: {
        async getBotStatus(showToast: boolean): Promise<void> {
            fetch('/api/startUp/botOnline').then(async (data) => {
                const fetchData = await data.json();
                console.log(fetchData);
                if (fetchData) {
                    this.$store.commit('setBotStatus', 'online');

                    this.data.botAudioPlayerOnline = true;
                    this.data.botUserOnline = true;
                    this.data.botPermissionsOnline = true;
                    this.data.botNameMappingsOnline = true;
                    this.data.botJdaOnline = true;
                    this.data.botConfigValidationOnline = true;

                    if (showToast) {
                        this.toast.success('Bot is online', {
                            timeout: 3000
                        });
                    }
                } else {
                    this.$store.commit('setBotStatus', 'offline');
                    this.$store.commit('setAudioPlayerStatus', 'disconnected');

                    this.data.botAudioPlayerOnline = false;
                    this.data.botUserOnline = false;
                    this.data.botPermissionsOnline = false;
                    this.data.botNameMappingsOnline = false;
                    this.data.botJdaOnline = false;
                    this.data.botConfigValidationOnline = false;

                    if (showToast) {
                        this.toast.error('Bot is offline', {
                            timeout: 3000
                        });
                    }
                }
            });
        },
        async startBot(): Promise<void> {
            await fetch('/api/startUp/startBot')
        },
        async stopBot(): Promise<void> {
            await fetch('/api/startUp/stopBot')
        },
        async switchAutoOnlineQueryMode() {
            this.data.autoOnlineQuery = !this.data.autoOnlineQuery;

            if (this.data.autoOnlineQuery) {
                await this.startAutoOnlineQuery();
            } else {
                await this.stopAutoOnlineQuery();
            }
        },
        async startAutoOnlineQuery() {
            this.data.onlineQueryInterval = setInterval(() => {
                this.getBotStatus(false);
            }, 5000);
        },
        async stopAutoOnlineQuery() {
            clearInterval(this.onlineQueryInterval);
        }
    },
})
export default class BotStatus extends Vue {
}
</script>