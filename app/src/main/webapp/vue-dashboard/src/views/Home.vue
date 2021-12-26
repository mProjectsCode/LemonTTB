<template>
    <div class="container mt-5">
        <BotStatus/>
    </div>
</template>

<script>
    // @ is an alias to /src
    import BotStatus from '@/components/BotStatus.vue'

    export default {
        name: 'Home',
        components: {
            BotStatus
        }
    }
</script>
