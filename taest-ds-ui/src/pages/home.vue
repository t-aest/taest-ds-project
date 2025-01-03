<script setup>


import {useUserStore} from "../store/user.js";
const user = useUserStore();
</script>

<template>
  <div>饥荒攻略</div>
  <span>{{ user.token }}</span>
</template>

<style scoped>

</style>
