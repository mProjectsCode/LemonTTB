import {createApp} from 'vue';
import App from './App.vue';
import router from './router';
import store from './store';

import Toast, {useToast} from "vue-toastification";
import "vue-toastification/dist/index.css";

import 'bootstrap/dist/css/bootstrap.css';
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
