<template>
    <div id="app">
        <div id="nav">
            <router-link to="/">Home</router-link> |
            <router-link to="/about">About</router-link>
        </div>
        <router-view/>
        {{botStatus}}
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
