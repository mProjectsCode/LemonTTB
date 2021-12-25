import { createStore } from 'vuex';

export default createStore({
    state: {
        botStatus: 'offline',
    },
    getters: {
        botStatus(state) {
            return state.botStatus;
        },
    },
    actions: {
    },
    mutations: {
        setBotStatus(state, data) {
            state.botStatus = data;
        },
    },
});
