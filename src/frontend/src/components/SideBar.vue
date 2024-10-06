<template>
  <nav class="sidebar">
    <ul>
      <li><a href="/dashboard">Dashboard</a></li>
      <li><a href="/Monthly">Monthly Overview</a></li>
      <li><a href="/incomes">Incomes</a></li>
      <li><a href="/expenses">Expenses</a></li>
    </ul>
  </nav>
</template>

<style scoped>
.sidebar {
  width: 200px;
  background-color: #006270;
  padding: 10px;
  height: 100vh;
  position: fixed;
  top: 0;
}

.sidebar ul {
  list-style-type: none;
  padding: 0;
}

.sidebar ul li {
  margin: 20px 0;
}

.sidebar ul li a {
  color: white;
  text-decoration: none;
  font-weight: bold;
}

.sidebar ul li a:hover {
  text-decoration: underline;
}
</style>