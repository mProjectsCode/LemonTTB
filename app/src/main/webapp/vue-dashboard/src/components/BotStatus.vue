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
                <button class="btn" v-bind:class="[autoOnlineQuery ? 'btn-success' : 'btn-secondary']" type="button" v-on:click="switchAutoOnlineQueryMode()">
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
                              v-bind:class="[botAudioPlayerOnline ? 'text-success' : 'text-danger']">
                            {{ botAudioPlayerOnline ? 'check' : 'clear' }}
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
                              v-bind:class="[botUserOnline ? 'text-success' : 'text-danger']">
                            {{ botUserOnline ? 'check' : 'clear' }}
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
                              v-bind:class="[botPermissionsOnline ? 'text-success' : 'text-danger']">
                            {{ botPermissionsOnline ? 'check' : 'clear' }}
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
                              v-bind:class="[botNameMappingsOnline ? 'text-success' : 'text-danger']">
                            {{ botNameMappingsOnline ? 'check' : 'clear' }}
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
                              v-bind:class="[botJdaOnline ? 'text-success' : 'text-danger']">
                            {{ botJdaOnline ? 'check' : 'clear' }}
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
                              v-bind:class="[botConfigValidationOnline ? 'text-success' : 'text-danger']">
                            {{ botConfigValidationOnline ? 'check' : 'clear' }}
                        </span>
                    </div>
                    <div class="col">
                        Config Validated
                    </div>
                </div>
            </div>

            <div v-show="botStartUpError != null">
                <br>
                <h6>Bot start up errors:</h6>
                <p class="text-danger">
                    {{ botStartUpError }}
                </p>
            </div>
        </div>
    </div>
</template>

<script lang="ts">
import {Options, Vue} from "vue-class-component";
import {EventData} from "@/main";

@Options({
    name: 'BotStatus',

    props: {},

    data() {
        return {
            onlineQueryInterval: null as unknown as number,
            autoOnlineQuery: false,

            botStartUpError: null as unknown as String,

            botAudioPlayerOnline: false,
            botUserOnline: false,
            botPermissionsOnline: false,
            botNameMappingsOnline: false,
            botJdaOnline: false,
            botConfigValidationOnline: false,
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
                    return 'bg-success';
                case 'starting':
                    return 'bg-warning';
                default:
                    return 'bg-danger';
            }
        },
        botStatusBadgeContent() {
            const botStatus = this.$store.getters.getBotStatus;
            if (this.botStartUpError) {
                return 'Error';
            } else {
                return botStatus.charAt(0).toUpperCase() + botStatus.slice(1);
            }
        },
    },

    mounted() {
        this.getBotStatus(false);

        this.emitter.on('api-event', (data: EventData) => {
            console.log('received api event in BotStatus.vue');
            if (data.eventType == 'START_UP_EVENT') {
                switch (data.name) {
                    case 'AUDIO_PLAYER':
                        this.botAudioPlayerOnline = true;
                        break;
                    case 'USERS':
                        this.botUserOnline = true;
                        break;
                    case 'PERMISSIONS':
                        this.botPermissionsOnline = true;
                        break;
                    case 'NAME_MAPPINGS':
                        this.botNameMappingsOnline = true;
                        break;
                    case 'JDA':
                        this.botJdaOnline = true;
                        break;
                    case 'CONFIG_VALIDATION':
                        this.botConfigValidationOnline = true;
                        break;
                }
            } else if (data.eventType == 'BOT_STATUS') {
                if (data.name == 'STOP') {
                    this.$store.commit('setBotStatus', 'offline')
                } else if (data.name == 'STARTING') {
                    this.$store.commit('setBotStatus', 'starting')

                    this.botStartUpError = null;

                    this.botAudioPlayerOnline = false;
                    this.botUserOnline = false;
                    this.botPermissionsOnline = false;
                    this.botNameMappingsOnline = false;
                    this.botJdaOnline = false;
                    this.botConfigValidationOnline = false;
                } else if (data.name == 'ONLINE') {
                    this.$store.commit('setBotStatus', 'online')
                }
            } else if (data.eventType == 'BOT_ERROR') {
                this.botStartUpError = data.payload.data;
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
                    if (showToast) {
                        this.toast.success('Bot is online', {
                            timeout: 3000
                        });
                    }
                } else {
                    this.$store.commit('setBotStatus', 'offline');
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
            this.autoOnlineQuery = !this.autoOnlineQuery;

            if (this.autoOnlineQuery) {
                await this.startAutoOnlineQuery();
            } else {
                await this.stopAutoOnlineQuery();
            }
        },
        async startAutoOnlineQuery() {
            this.onlineQueryInterval = setInterval(() => {
                this.getBotStatus(false);
            }, 5000);
        },
        async stopAutoOnlineQuery() {
            clearInterval(this.onlineQueryInterval)
        }
    },
})
export default class BotStatus extends Vue {
}
</script>