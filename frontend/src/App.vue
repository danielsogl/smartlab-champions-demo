<script setup lang="ts">
import { RouterLink, RouterView } from 'vue-router'
</script>

<template>
  <header>
    <RouterLink to="/">
      Charge Ops
    </RouterLink>
  </header>
  <main>
    <RouterView />
  </main>
</template>
