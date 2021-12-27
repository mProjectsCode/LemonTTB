import {createStore} from 'vuex';

export default createStore({
    state: {
        botStatus: 'offline' as string,
        eventSource: null as unknown as EventSource
    },
    getters: {
        getBotStatus(state): string {
            return state.botStatus;
        },
    },
    actions: {},
    mutations: {
        setBotStatus(state, data: string) {
            state.botStatus = data;
        },
    },
});
