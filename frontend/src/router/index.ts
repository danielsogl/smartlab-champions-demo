import { createRouter, createWebHistory } from 'vue-router'
import ChargePointList from '@/views/ChargePointList.vue'
import ChargePointDetail from '@/views/ChargePointDetail.vue'

export default createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    { path: '/', name: 'charge-points', component: ChargePointList },
    { path: '/charge-points/:id', name: 'charge-point', component: ChargePointDetail, props: true },
  ],
})
