<template>
  <div id="app">
    <header>

      <Dashboard></Dashboard>
    </header>
  </div>
</template>

<script>

import Dashboard from "@/views/Dashboard.vue";
export default {
  name: 'App',
  components: {Dashboard}
}
</script>

<style scoped>
#app {
  text-align: center;
  margin-top: 50px;
}

header h1 {
  font-size: 2em;
}
</style>