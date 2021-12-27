<template>
    <div>
        <button class="btn btn-primary" v-on:click="getBotStatus(true)">get bot status</button>

        <p>
            The bot is currently
            <span class="badge" v-bind:class="[botStatus === 'online' ? 'bg-success' : 'bg-danger' ]">{{ botStatus === 'online' ? 'Online' : 'Offline'}}</span>
        </p>

        <h2>Actions</h2>

        <p>
            Start or restart the bot
            <br>
            <button class="btn btn-primary" v-on:click="startBot()">{{ botStatus === 'online' ? 'Restart Bot' : 'Start Bot'}}</button>
        </p>

        <p>
            Stop the bot
            <br>
            <button class="btn btn-danger" v-on:click="stopBot()">Stop Bot</button>
        </p>
    </div>
</template>

<script>
    export default {
        name: 'BotStatus',

        props: {

        },

        data() {
            return {
                onlineQueryInterval: null,
            };
        },

        computed: {
            botStatus() {
                return this.$store.getters.getBotStatus
            },
        },

        mounted() {
            this.getBotStatus(false);
            this.onlineQueryInterval = setInterval(() => {
                this.getBotStatus(false);
            }, 5000);
        },

        methods: {
            async getBotStatus(showToast) {
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
            async startBot() {
                await fetch('/api/startUp/startBot')
            },
            async stopBot() {
                await fetch('/api/startUp/stopBot')
            },
        },
    }
</script>