/*
 * This file is part of LemonTTB.
 * (C) Copyright 2021
 * Programmed by Moritz Jung
 *
 * LemonTTB is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * LemonTTB is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with LemonTTB.  If not, see <https://www.gnu.org/licenses/>.
 *
 */

import {createApp} from 'vue';
import App from './App.vue';
import router from './router';
import store from './store';

import Toast, {useToast} from "vue-toastification";
import "vue-toastification/dist/index.css";

import '@forevolve/bootstrap-dark/dist/css/bootstrap-dark.css';
import mitt from "mitt";


const toastOptions = {
    // You can set your default options here
};

const emitter = mitt();

const app = createApp(App);

app.use(store);
app.use(router);

app.use(Toast, toastOptions);
app.config.globalProperties.emitter = emitter;

app.mixin({
    data() {
        return {
            toast: null,
        }
    },

    mounted() {
        // Get toast interface
        this.toast = useToast();
    },

    methods: {},
})

app.mount('#app');

export interface EventData {
    id: string;
    eventGroup: string;
    eventType: string;
    time: string;
    name: string;
    payload: EventPayload;
    originClass: string;
}

export interface EventPayload {
    response: string;
    data: any;
}

export interface AudioPlayerEventPayloadData {
    currentTrack: TrackData;
    queue: TrackData[];
    looping: boolean;
    paused: boolean;
    guildName: String;
    channelName: String;
}

export interface TrackData {
    name: string;
    length: number;
    position: number;
    audioTrackSource: string;
}

export interface VoiceChannelPayloadData {
    guildName: string;
    primaryChannelName: string;
    secondaryChannelName: string;
    primaryChannelUsers: VoiceChannelPayloadDataUser[];
    secondaryChannelUsers: VoiceChannelPayloadDataUser[];
}

export interface VoiceChannelPayloadDataUser {
    user: User;
    name: string;
    muted: boolean;
    deafened: boolean;
}

export interface User {
    defaultPermissions: string[];
    permissions: string[];
    id: string;
}
