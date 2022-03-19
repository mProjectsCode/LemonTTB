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
    <div class="hello">
        <h1>{{ msg }}</h1>
        <p>
            For a guide and recipes on how to configure / customize this project,<br>
            check out the
            <a href="https://cli.vuejs.org" rel="noopener" target="_blank">vue-cli documentation</a>.
        </p>
        <h3>Installed CLI Plugins</h3>
        <ul>
            <li><a href="https://github.com/vuejs/vue-cli/tree/dev/packages/%40vue/cli-plugin-typescript"
                   rel="noopener" target="_blank">typescript</a></li>
        </ul>
        <h3>Essential Links</h3>
        <ul>
            <li><a href="https://vuejs.org" rel="noopener" target="_blank">Core Docs</a></li>
            <li><a href="https://forum.vuejs.org" rel="noopener" target="_blank">Forum</a></li>
            <li><a href="https://chat.vuejs.org" rel="noopener" target="_blank">Community Chat</a></li>
            <li><a href="https://twitter.com/vuejs" rel="noopener" target="_blank">Twitter</a></li>
            <li><a href="https://news.vuejs.org" rel="noopener" target="_blank">News</a></li>
        </ul>
        <h3>Ecosystem</h3>
        <ul>
            <li><a href="https://router.vuejs.org" rel="noopener" target="_blank">vue-router</a></li>
            <li><a href="https://vuex.vuejs.org" rel="noopener" target="_blank">vuex</a></li>
            <li><a href="https://github.com/vuejs/vue-devtools#vue-devtools" rel="noopener"
                   target="_blank">vue-devtools</a></li>
            <li><a href="https://vue-loader.vuejs.org" rel="noopener" target="_blank">vue-loader</a></li>
            <li><a href="https://github.com/vuejs/awesome-vue" rel="noopener" target="_blank">awesome-vue</a></li>
        </ul>
    </div>
</template>

<script lang="ts">
import {Options, Vue} from 'vue-class-component';

@Options({
    props: {
        msg: String
    }
})
export default class HelloWorld extends Vue {
    msg!: string
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style lang="scss" scoped>
h3 {
    margin: 40px 0 0;
}

ul {
    list-style-type: none;
    padding: 0;
}

li {
    display: inline-block;
    margin: 0 10px;
}

a {
    color: #42b983;
}
</style>
