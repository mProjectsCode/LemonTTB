<template>
    <div class="container mt-5">
        <BotStatus/>
    </div>
</template>

<script lang="ts">
import {Options, Vue} from 'vue-class-component';
import BotStatus from "@/components/BotStatus.vue"; // @ is an alias to /src

@Options({
    components: {
        BotStatus,
    },
})
export default class Dashboard extends Vue {
}
</script>
