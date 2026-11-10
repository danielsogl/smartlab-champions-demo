<script setup lang="ts">
import { computed, onMounted, ref } from 'vue'
import {
  ApiError,
  getChargePoint,
  requestRemoteStart,
  type ChargePoint,
  type ChargePointStatus,
} from '@/api'

const props = defineProps<{ id: string }>()

const chargePoint = ref<ChargePoint>()
const message = ref('')

// DEMO-42: the backend checks again on click (AC1, AC2); this only explains why.
const blockedReasons: Partial<Record<ChargePointStatus, string>> = {
  OFFLINE: 'Offline',
  UNKNOWN: 'Offline',
  IN_MAINTENANCE: 'In maintenance',
}
const blockedReason = computed(() => chargePoint.value && blockedReasons[chargePoint.value.status])

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
    <button
      :disabled="!!blockedReason"
      @click="start"
    >
      Remote start
    </button>
    <p
      v-if="blockedReason"
      class="message"
    >
      Remote start not possible: {{ blockedReason }}
    </p>
    <p
      v-if="message"
      class="message"
      role="status"
    >
      {{ message }}
    </p>
  </section>
</template>
