<template>
  <div id="app">
    <div class="welcome">
      <h1>Welcome to My Personal Finance Application</h1>
      <p>Manage your income, expenses, and savings all in one place.</p>
    </div>
    <div class="links">
      <router-link to="/income">Income</router-link>
      <router-link to="/expenses">Expenses</router-link>
      <router-link to="/savings">Savings</router-link>
    </div>
  </div>
</template>

<script>
export default {
  name: 'App'
}
</script>

<style scoped>
#app {
  font-family: Arial, sans-serif;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}

.welcome {
  background-color: #3498db;
  color: white;
  padding: 20px;
  border-radius: 5px;
}

.links {
  display: flex;
  justify-content: space-around;
  margin-top: 20px;
}

.router-link-exact-active {
  color: #42b983;
}
</style>

