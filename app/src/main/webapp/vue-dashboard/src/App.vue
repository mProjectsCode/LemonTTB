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
            // await this.getAllEvents();
            this.source = new EventSource('/api/events/subscribe');
            this.source.onmessage = this.onEventMessage;
        },
        async onEventMessage(event: MessageEvent): Promise<void> {
            const eventData: EventData = JSON.parse(event.data);
            console.log(eventData);
            this.emitter.emit("api-event", eventData);

            this.toast.success(eventData.name + ' status ' + eventData.payload.response, {
                timeout: 5000
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
