import { createApp } from 'vue';
import App from './App.vue';
import router from './router';
import store from './store';

import Toast, {useToast} from "vue-toastification";
import "vue-toastification/dist/index.css";

import 'bootstrap/dist/css/bootstrap.css';


const toastOptions = {
    // You can set your default options here
};

const app = createApp(App);

app.use(store);
app.use(router);

app.use(Toast, toastOptions);

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

    methods: {

    },
})

app.mount('#app');