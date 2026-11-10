<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { RouterLink } from 'vue-router'
import { listChargePoints, type ChargePoint } from '@/api'

const chargePoints = ref<ChargePoint[]>([])

onMounted(async () => {
  chargePoints.value = await listChargePoints()
})
</script>

<template>
  <h1>Charge points</h1>
  <table>
    <thead>
      <tr>
        <th>ID</th>
        <th>Location</th>
        <th>Status</th>
      </tr>
    </thead>
    <tbody>
      <tr
        v-for="cp in chargePoints"
        :key="cp.id"
      >
        <td>
          <RouterLink :to="{ name: 'charge-point', params: { id: cp.id } }">
            {{ cp.id }}
          </RouterLink>
        </td>
        <td>{{ cp.location }}</td>
        <td><span class="status">{{ cp.status }}</span></td>
      </tr>
    </tbody>
  </table>
</template>
