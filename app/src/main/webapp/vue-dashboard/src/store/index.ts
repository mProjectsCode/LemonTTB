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

import {createStore} from 'vuex';

interface Data {
    botStatus: string;
    audioPlayerStatus: string;
    eventSource?: EventSource;
    fetchOnStartUp: string[];
}

const data: Data = {
    botStatus: 'offline',
    audioPlayerStatus: 'disconnected',
    eventSource: undefined,
    fetchOnStartUp: [],
}

export default createStore({
    state: {
        data: data,
    },
    getters: {
        getBotStatus(state): string {
            return state.data.botStatus;
        },
        getAudioPlayerStatus(state): string {
            return state.data.audioPlayerStatus;
        },
        getFetchOnStartUp(state): string[] {
            return state.data.fetchOnStartUp;
        },
    },
    actions: {},
    mutations: {
        setBotStatus(state, data: string) {
            state.data.botStatus = data;
        },
        setAudioPlayerStatus(state, data: string) {
            state.data.audioPlayerStatus = data;
        },
        addToFetchOnStartUp(state, data: string) {
            state.data.fetchOnStartUp.push(data);
        },
        clearFetchOnStartUp(state) {
            state.data.fetchOnStartUp = [];
        }
    },
});
