<script setup>
import {ref, onMounted} from "vue";
import ItemDescription from "@/components/ItemDescription.vue";
import axios from 'axios';
const selectedValue = ref([])
const options = ref([]) //
const itemDescriptionRef = ref(null) // 添加对子组件的引用
// 处理选择变化
const handleChange = (value) => {

  console.log(value)
  console.log(value[0])
  if (!value.length) return

  // 获取完整的选中路径
  const path = value
  // 获取最后选中的值
  const lastValue = path[path.length - 1]
  // 调用子组件的方法
  if (itemDescriptionRef.value) {
    itemDescriptionRef.value.updateItem(lastValue)
  }
  console.log(lastValue)
}

// 获取选项数据的函数
const fetchOptions = async () => {
  try {
    const response = await axios.get('/api/test/get') // 替换为你的实际API地址
    options.value = response.data // 将接口返回的数据赋值给options
  } catch (error) {
    console.error('获取选项数据失败:', error)
    // 可以在这里添加错误处理逻辑
  }
}

// 在组件挂载时获取数据
onMounted(() => {
  fetchOptions()
})
</script>

<template>

  <div class="common-layout">

    <div class="m-4">
      <el-cascader
          v-model="selectedValue"
          placeholder="输入物品名称"
          :options="options"
          filterable
          :show-all-levels="false"
          @change = "handleChange"
      />
    </div>
    <ItemDescription ref="itemDescriptionRef" />
  </div>



</template>

<style scoped>
/* 方案一：设置固定高度 */
:deep(.el-cascader-panel) {
  height: auto !important; /* 自适应高度 */
  max-height: none !important; /* 移除最大高度限制 */
}

:deep(.el-cascader-menu) {
  height: auto !important; /* 自适应高度 */
  max-height: none !important; /* 移除最大高度限制 */
}

:deep(.el-scrollbar__wrap) {
  height: auto !important; /* 自适应高度 */
  max-height: 417px; /* 移除最大高度限制 */
}
</style>