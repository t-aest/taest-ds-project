<template>
  <div class="flex h-[calc(100vh-64px)]">
    <!-- 左侧边栏 -->
    <div class="w-64 bg-gray-50 border-r border-gray-200 flex flex-col">
      <!-- 新建聊天按钮 -->
      <div class="p-4">
        <el-button type="primary" class="w-full" @click="createNewChat">
          <el-icon class="mr-2">
            <Plus/>
          </el-icon>
          新建聊天
        </el-button>
      </div>

      <!-- 历史对话列表 -->
      <div class="flex-1 overflow-y-auto">
        <div v-for="chat in chatHistory"
             :key="chat.id"
             :class="['flex items-center p-3 cursor-pointer hover:bg-gray-100 group',
                     activeChatId === chat.id ? 'bg-gray-200' : '']"
             @click="switchChat(chat.id)">
          <el-icon class="mr-2">
            <ChatRound/>
          </el-icon>
          <span class="flex-1 truncate">{{ chat.title }}</span>
          <el-popconfirm
              title="确定要删除这个对话吗？"
              @confirm="deleteChat(chat.id)"
              width="200"
          >
            <template #reference>
              <el-button
                  type="danger"
                  size="small"
                  circle
                  class="opacity-0 group-hover:opacity-100 transition-opacity duration-200"
                  @click.stop
              >
                <el-icon>
                  <Delete/>
                </el-icon>
              </el-button>
            </template>
          </el-popconfirm>
        </div>
      </div>
    </div>

    <!-- 右侧聊天区域 -->
    <div class="flex-1 flex flex-col bg-white">
      <!-- 聊天记录 -->
      <div class="flex-1 overflow-y-auto p-4" ref="messageContainer">
        <template v-if="currentChat">
          <div v-for="(message, index) in currentChat.messages"
               :key="index"
               :class="['flex mb-4', message.role === 'user' ? 'justify-end' : 'justify-start']">
            <div :class="['max-w-[80%] p-3 rounded-lg',
               message.role === 'user' ? 'bg-blue-500 text-white' : 'bg-gray-100']"
                 v-html="message.content.replace(/\n/g, '<br>')">
            </div>
          </div>
        </template>
        <div v-else class="h-full flex items-center justify-center text-gray-400">
          请选择或新建一个对话
        </div>
      </div>

      <!-- 输入区域 -->
      <div class="border-t p-4">
        <div class="flex gap-2">
          <el-input
              v-model="inputMessage"
              type="textarea"
              :rows="3"
              placeholder="输入消息..."
              @keyup.enter.prevent="sendMessage"
              :disabled="!currentChat"
          />
          <el-button
              type="primary"
              @click="sendMessage"
              :disabled="!currentChat || !inputMessage.trim()"
              :loading="loading"
          >
            发送
          </el-button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import {ref, computed, onMounted} from 'vue'
import {Plus, ChatRound, Delete} from '@element-plus/icons-vue'
import {ElMessage} from 'element-plus'
import axios from "axios";
// OpenAI API 密钥
const OPENAI_API_KEY = import.meta.env.VITE_OPENAI_API_KEY;
// 状态
const chatHistory = ref([])
const activeChatId = ref(null)
const inputMessage = ref('')
const loading = ref(false)

// 当前选中的对话
const currentChat = computed(() =>
    chatHistory.value.find(chat => chat.id === activeChatId.value)
)

// 加载历史对话
const loadChatHistory = () => {
  try {
    const saved = localStorage.getItem('chatHistory')
    if (saved) {
      chatHistory.value = JSON.parse(saved)
      // 如果有历史对话，默认选中最新的一个
      if (chatHistory.value.length > 0) {
        activeChatId.value = chatHistory.value[0].id
      }
    }
  } catch (error) {
    console.error('加载历史对话失败:', error)
    ElMessage.error('加载历史对话失败')
  }
}

// 保存历史对话
const saveChatHistory = () => {
  try {
    localStorage.setItem('chatHistory', JSON.stringify(chatHistory.value))
  } catch (error) {
    console.error('保存历史对话失败:', error)
    ElMessage.error('保存历史对话失败')
  }
}

// 创建新对话
const createNewChat = () => {
  const newChat = {
    id: Date.now(),
    title: `新对话 ${chatHistory.value.length + 1}`,
    messages: [],
    createTime: new Date().toISOString()
  }
  chatHistory.value.unshift(newChat) // 添加到列表开头
  activeChatId.value = newChat.id
  saveChatHistory()
}

// 切换对话
const switchChat = (id) => {
  activeChatId.value = id
}

// 删除对话
const deleteChat = (id) => {
  chatHistory.value = chatHistory.value.filter(chat => chat.id !== id)
  if (activeChatId.value === id) {
    activeChatId.value = chatHistory.value[0]?.id || null
  }
  saveChatHistory()
  ElMessage.success('删除成功')
}

// 发送消息
const sendMessage = async () => {
  if (!inputMessage.value.trim() || loading.value) return

  loading.value = true
  try {
    const userInput = inputMessage.value
    inputMessage.value = ''

    // 添加用户消息
    currentChat.value.messages.push({
      role: 'user',
      content: userInput,
      time: new Date().toISOString()
    })

    // 创建AI响应消息
    const aiMessage = {
      role: 'assistant',
      content: `思考中！！！`,
      time: new Date().toISOString()
    }

    currentChat.value.messages.push(aiMessage)
// 调用 OpenAI API
    const response = await fetch('https://api.deepseek.com/chat/completions', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${OPENAI_API_KEY}`
      },
      body: JSON.stringify({
        model: 'deepseek-chat',
        messages: [
          {
            role: 'user',
            content: userInput,
          },
        ],
        stream: true, // 启用流式输出
      }),
    });

    // 处理流式数据
    const reader = response.body.getReader();
    const decoder = new TextDecoder();
    let buffer = '';

    while (true) {
      const { done, value } = await reader.read();
      if (done) break;

      const chunk = decoder.decode(value);
      const lines = chunk.split('\n');

      for (const line of lines) {
        if (line.startsWith('data: ')) {
          const data = line.slice(6);
          if (data === '[DONE]') break;

          const json = JSON.parse(data);
          const content = json.choices[0].delta.content || '';
          buffer += content;
          aiMessage.content = buffer;
        }
      }
    }



    // 保存对话
    saveChatHistory()
    console.log(currentChat.value.messages.length)
    // 更新对话标题
    if (currentChat.value.messages.length === 2) {
      currentChat.value.title = userInput.slice(0, 20) + (userInput.length > 20 ? '...' : '')
      // 保存对话
      saveChatHistory()
    }

  } finally {
    loading.value = false
  }
}

// 组件挂载时加载历史对话
onMounted(() => {
  loadChatHistory()
})
</script>

<style scoped>
/* 自定义滚动条样式 */
::-webkit-scrollbar {
  width: 6px;
}

::-webkit-scrollbar-track {
  background: #f1f1f1;
}

::-webkit-scrollbar-thumb {
  background: #888;
  border-radius: 3px;
}

::-webkit-scrollbar-thumb:hover {
  background: #555;
}

/* 添加过渡效果 */
.group:hover .opacity-0 {
  opacity: 1;
}

.transition-opacity {
  transition: opacity 0.2s ease-in-out;
}
</style>