<script setup>
import imageSrc from '@/assets/ds.jpg';
</script>

<template>

    <el-card >
      <template #header>Welcome Everyone</template>
      欢迎来到 小仝的饥荒攻略站，大家可以查询全物品介绍、以及角色介绍，也可以跟智能助手聊一聊饥荒。
      <img :src="imageSrc"/>
    </el-card>
</template>

<style scoped>

</style>