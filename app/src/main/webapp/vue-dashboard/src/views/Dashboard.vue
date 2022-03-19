<!--
  - This file is part of LemonTTB.
  - (C) Copyright 2021-2022
  - Developed by Moritz Jung
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
    <div class="container mt-5">
        <BotStatus/>
        <div v-if="botStatus === 'online'">
            <br>
            <AudioPlayerStatus/>
            <br>
            <Moderation/>
        </div>
    </div>
</template>

<script lang="ts">
import {Options, Vue} from 'vue-class-component';
import BotStatus from "@/components/BotStatus.vue";
import AudioPlayerStatus from "@/components/AudioPlayerStatus.vue";
import Moderation from "@/components/Moderation.vue"; // @ is an alias to /src

@Options({
    components: {
        Moderation,
        AudioPlayerStatus,
        BotStatus,
    },

    computed: {
        botStatus() {
            return this.$store.getters.getBotStatus
        },
    }
})
export default class Dashboard extends Vue {
}
</script>
