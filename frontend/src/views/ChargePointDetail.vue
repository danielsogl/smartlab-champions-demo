<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { ApiError, getChargePoint, requestRemoteStart, type ChargePoint } from '@/api'

const props = defineProps<{ id: string }>()

const chargePoint = ref<ChargePoint>()
const message = ref('')

onMounted(async () => {
  chargePoint.value = await getChargePoint(props.id)
})

async function start() {
  message.value = ''
  try {
    const { sessionId } = await requestRemoteStart(props.id)
    message.value = `Session started (${sessionId})`
  } catch (e) {
    message.value = e instanceof ApiError ? `Start refused: ${e.code}` : 'Start failed'
  }
}
</script>

<template>
  <section v-if="chargePoint">
    <h1>{{ chargePoint.id }}</h1>
    <p>{{ chargePoint.location }}</p>
    <p>
      Status: <span class="status">{{ chargePoint.status }}</span>
    </p>
    <button @click="start">
      Remote start
    </button>
    <p
      v-if="message"
      class="message"
      role="status"
    >
      {{ message }}
    </p>
  </section>
</template>
