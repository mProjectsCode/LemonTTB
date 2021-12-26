<template>
    <div id="app">
        <div id="nav">
            <router-link to="/">Home</router-link> |
            <router-link to="/about">About</router-link>
        </div>
        <router-view/>
        <p>{{botStatus}}</p>
        <button v-on:click="getBotStatus()">get bot status</button>
        <button v-on:click="startBot()">start bot</button>
        <button v-on:click="stopBot()">stop bot</button>
    </div>
</template>

<script>
    import { useToast } from "vue-toastification";

    export default {
        data() {
            return {
                source: null,
            }
        },

        mounted() {
            this.source = new EventSource('http://localhost:8080/api/startUp/events');
            this.source.onmessage = this.eventMessage;
        },

        computed: {
            botStatus() {
                return this.$store.getters.getBotStatus
            },
        },

        methods: {
            async eventMessage(event) {
                console.log(event.data);
                const eventData = JSON.parse(event.data);

                useToast().success(eventData.name + ' status ' + eventData.payload, {
                    timeout: 10000
                });
            },
            async getBotStatus() {
                fetch('http://localhost:8080/api/startUp/botOnline').then(async (data) => {
                    const fetchData = await data.json();
                    console.log(fetchData);
                    if (fetchData) {
                        this.$store.commit('setBotStatus', 'Online');
                    } else {
                        this.$store.commit('setBotStatus', 'Offline')
                    }
                });
            },
            async startBot() {
                fetch('http://localhost:8080/api/startUp/startBot')
            },
            async stopBot() {
                fetch('http://localhost:8080/api/startUp/stopBot')
            },
        },
    }
</script>

<style lang="scss">
#app {
    font-family: Avenir, Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #2c3e50;
}

#nav {
    padding: 30px;

    a {
        font-weight: bold;
        color: #2c3e50;

        &.router-link-exact-active {
            color: #42b983;
        }
    }
}
</style>
