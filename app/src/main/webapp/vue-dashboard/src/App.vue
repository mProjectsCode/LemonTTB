<template>
    <div id="app">
        <div id="main">
            <div class="p-5 bg-primary text-white text-center">
                <h1>LemonTTB</h1>
                <p>LemonTTB is a Discord Bot designed to assist the GM during tabletop sessions, that use Discord for
                    communication.</p>
            </div>

            <nav class="navbar navbar-expand-sm bg-dark navbar-dark">
                <div class="container-fluid">
                    <ul class="navbar-nav">
                        <li class="nav-item">
                            <a class="nav-link" href="https://github.com/mProjectsCode/LemonTTB">GitHub</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="#">About LemonTTB</a>
                        </li>
                    </ul>
                </div>
            </nav>

            <router-view/>

            <footer class="mt-5 p-4 bg-dark text-white text-center" style="z-index: 11">
                <p>LemonTTB - GPL-3.0 License</p>
            </footer>
        </div>
    </div>
</template>

<script lang="ts">
import {Options, Vue} from 'vue-class-component';
import {EventData} from './main'


@Options({
    data() {
        return {
            source: null as unknown as EventSource,
        }
    },

    mounted() {

        this.subscribeToEvents();
    },

    computed: {
        botStatus(): boolean {
            return this.$store.getters.getBotStatus
        },
    },

    methods: {
        async getAllEvents(): Promise<void> {
            const data = await fetch('api/events/getAll')
            const eventDataArray: EventData[] = await data.json();

            eventDataArray.forEach((eventData: EventData) => {
                this.emitter.emit("api-event", eventData);
            })
        },
        async subscribeToEvents(): Promise<void> {
            await fetch('api/events/unsubscribe');
            await this.getAllEvents();
            this.source = new EventSource('/api/events/subscribe');
            this.source.onmessage = this.onEventMessage;
        },
        async onEventMessage(event: MessageEvent): Promise<void> {
            const eventData: EventData = JSON.parse(event.data);
            console.log(eventData);
            this.emitter.emit("api-event", eventData);

            this.toast.success(eventData.name + ' status ' + eventData.payload.response, {
                timeout: 10000
            });
        }
    }
})
export default class App extends Vue {
}
</script>

<style lang="scss">
#main {

}
</style>
