<script setup>
import { ref } from 'vue'

// 模拟数据
const mockData = {
  '眼球伞': {
    name: '眼球伞',
    code: 'sad',
    type: '动植物',
    image: 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png',
    description: '这是一个眼球伞的描述...'
  },
  '花伞': {
    name: '花伞',
    code: 'flower',
    type: '植物',
    image: 'https://example.com/flower.png',
    description: '这是一个花伞的描述...这是一个花伞的描述...这是一个花伞的描述...这是一个花伞的描述...这是一个花伞的描述...这是一个花伞的描述...这是一个花伞的描述...这是一个花伞的描述...这是一个花伞的描述...这是一个花伞的描述...这是一个花伞的描述...'
  }
  // 可以添加更多物品数据
}

const itemData = ref({})

// 暴露给父组件调用的方法
const updateItem = (itemDetail) => {
  itemData.value = itemDetail || {}
}

// 暴露方法给父组件
defineExpose({
  updateItem
})
</script>

<template>
  <el-descriptions v-if="itemData.itemName" :column="4" title="物品详情" border>
    <el-descriptions-item
        :span="2"
        label="图片示例"
    >
      <el-image
          :src="itemData.imgUrl"
      />
    </el-descriptions-item>
    <el-descriptions-item :span="2" label="名称">{{ itemData.itemName }}</el-descriptions-item>
    <el-descriptions-item :span="1" label="作弊代码">{{ itemData.cheatCode }}</el-descriptions-item>
    <el-descriptions-item :span="1" label="物品类型">
      <el-tag size="small">{{ itemData.itemType }}</el-tag>
    </el-descriptions-item>
    <el-descriptions-item :span="2" label="todo">todo</el-descriptions-item>
    <el-descriptions-item :span="4" label="描述">
      {{ itemData.itemDesc }}
    </el-descriptions-item>
  </el-descriptions>

</template>

<style scoped>

</style>