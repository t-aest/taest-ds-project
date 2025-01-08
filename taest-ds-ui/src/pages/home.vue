<script setup>

import Header from "@/components/Header.vue";
import {useUserStore} from "../store/user.js";
const user = useUserStore();
</script>

<template>
  <div class="w-full">
    <div class="shadow mb-[10px]">
      <div class="w-[1200px] mx-auto">
        <Header />
      </div>
    </div>
    <router-view></router-view>
  </div>
</template>

<style scoped>

</style>
