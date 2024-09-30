// src/App.vue
<template>
  <div id="app">
    <TopBar v-if="showTopBar"></TopBar>
    <SideBar v-if ="showSideBar"></SideBar>
    <router-view />
  </div>
</template>

<script>
import { computed } from 'vue';
import { useRoute } from 'vue-router';
import TopBar from "@/components/TopBar.vue";
import SideBar from "@/components/SideBar.vue";

export default {
  name: 'App',
  components: {SideBar, TopBar },
  setup() {
    const route = useRoute();
    const showTopBar = computed(() => !['/', '/register'].includes(route.path));
    const showSideBar = computed(() => !['/', '/register'].includes(route.path));
    return { showTopBar, showSideBar };
  }
};
</script>

<style scoped>
</style>