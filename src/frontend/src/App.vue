// src/App.vue
<template>
  <div id="app">
    <TopBar v-if="showTopBar"></TopBar>
    <router-view />
  </div>
</template>

<script>
import { computed } from 'vue';
import { useRoute } from 'vue-router';
import TopBar from "@/components/TopBar.vue";

export default {
  name: 'App',
  components: { TopBar },
  setup() {
    const route = useRoute();
    const showTopBar = computed(() => !['/', '/register'].includes(route.path));
    return { showTopBar };
  }
};
</script>

<style scoped>
</style>