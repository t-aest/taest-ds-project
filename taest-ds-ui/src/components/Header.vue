<script setup>
import { ref } from "vue";
import { Search } from "@element-plus/icons-vue";
import { useModalStore } from "@/store/modal.js";
import { useUserStore } from "@/store/user.js";
import { useRouter } from 'vue-router';
const input = ref("");
const modal = useModalStore();
const user = useUserStore();
</script>

<template>
  <div class="flex items-center gap-2">
    <img
      class="h-16 w-36"
      src="@/assets/title.jpg"
    />
    <div class="flex items-center justify-between flex-[1]">
      <router-link to="/home/wel">首页</router-link>
      <router-link to="/home/option">物品查询</router-link>
      <router-link to="/home/chat">AI助手</router-link>
      <a href="#">工具Mod TODO</a>
      <a href="#">
        <span>角色介绍 TODO</span>
        <el-icon>
          <ArrowDownBold />
        </el-icon>
      </a>
    </div>
  </div>
</template>
