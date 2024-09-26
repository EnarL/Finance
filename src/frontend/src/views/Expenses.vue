<template>

  <nav class="sidebar">
    <ul>
      <li><a href="/dashboard">Dashboard</a></li>
      <li><a href="/Monthly">Monthly Overview</a></li>
      <li><a href="/incomes">Incomes</a></li>
      <li><a href="/expenses">Expenses</a></li>
    </ul>
  </nav>
  <div :class="['dashboard', { 'black-theme': isBlackTheme }]">

    <MonthlyBarChart :expenses="expenses" :incomes="incomes"></MonthlyBarChart>

  </div>
</template>
<script>


import MonthlyBarChart from "@/components/LineChart.vue";
export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: 'Dashboard',
  components: {
    MonthlyBarChart,
  },
  data() {
    return {
      expenses: [],
      incomes: [],
      loading: true,
      error: null,
      username: localStorage.getItem('username'),
      isBlackTheme: JSON.parse(localStorage.getItem('isBlackTheme')) || false,
    };
  },
  mounted() {
    this.fetchData();
  },
  methods: {
    fetchData() {
      this.loading = true;
      this.error = null;
      Promise.all([this.fetchExpensesData(), this.fetchIncomeData()])
          .then(() => {
            this.loading = false;
          })
          .catch(error => {
            console.error('Error:', error);
            this.error = 'Failed to load data. Please try again later.';
            this.loading = false;
          });
    },
    async fetchExpensesData() {
      const username = this.username;
      try {
        const response = await fetch(`http://localhost:8080/expenses/findall?username=${username}`);
        if (!response.ok) throw new Error('Failed to fetch expenses');
        this.expenses = await response.json();
      } catch (error) {
        console.error('Error fetching expenses:', error);
        throw error;
      }
    },
    async fetchIncomeData() {
      const username = this.username;
      try {
        const response = await fetch(`http://localhost:8080/incomes/findall?username=${username}`);
        if (!response.ok) throw new Error('Failed to fetch incomes');
        this.incomes = await response.json();
      } catch (error) {
        console.error('Error fetching incomes:', error);
        throw error;
      }
    },
    logout() {
      localStorage.removeItem('username');
      this.$router.push('/login');
    }
  }
}
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap');

* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

.dashboard {
  background-color: lightblue;
  text-align: center;
  margin-left:200px;
  font-family: 'Roboto', sans-serif;
  color: #333;
  padding: 20px;
}

.black-theme {
  background-color: black;
  color: white;
}

.forms-container {
  justify-content: center;
  gap: 20px;
  margin-bottom: 2rem;
}

h1 {
  margin-bottom: 2rem;
  color: #006270;
  text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.1);
}

input {
  display: block;
  width: 100%;
  margin-bottom: 1rem;
  border: 1px solid #ddd;
  border-radius: 4px;
  transition: border-color 0.3s ease;
}

input:focus {
  border-color: #009394;
}

button {
  border: none;
  border-radius: 4px;

  cursor: pointer;
  transition: background-color 0.3s ease, transform 0.3s ease;
}

button:hover {
  background-color: #009394;
  transform: translateY(-2px);
}

button:active {
  background-color: #006270;
  transform: translateY(0);
}

.forms-container {

  border-radius: 8px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  padding: 15px;
  transition: transform 0.3s ease, box-shadow 0.3s ease;
}

.forms-container > *:hover {
  transform: translateY(-5px);
  box-shadow: 0 6px 8px rgba(0, 0, 0, 0.15);
}

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