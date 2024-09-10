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
    <button @click="toggleTheme" style="color:white;background-color:#007bff;width:70px;margin:0 auto; display:flex;">Switch Theme</button>
    <h1>Hi, {{ username }}</h1>
    <div class="forms-container">
      <AddExpense @add-expense="addExpense"></AddExpense>
      <AddIncome @add-income="addIncome"></AddIncome>
    </div>
    <TransactionList title="Expenses" :transactions="expenses" :delete-transaction="deleteExpense"></TransactionList>
    <TransactionList title="Income" :transactions="incomes" :delete-transaction="deleteIncome"></TransactionList>
    <ChartsContainer :expenses="expenses" :incomes="incomes"></ChartsContainer>
    <div v-if="loading" class="loading">Loading...</div>
    <div v-if="error" class="error">{{ error }}</div>
  </div>
</template>
<script>
import AddExpense from '@/components/AddExpense.vue';
import AddIncome from '@/components/AddIncome.vue';
import ChartsContainer from '@/components/ChartsContainer.vue';
import TransactionList from "@/components/TransactionList.vue";

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: 'Dashboard',
  components: {
    TransactionList,
    AddExpense,
    AddIncome,
    ChartsContainer,

  },
  data() {
    return {
      expenses: [],
      incomes: [],
      loading: true,
      error: null,
      username: localStorage.getItem('username'),
      id: localStorage.getItem('id'),
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
        const response = await fetch(`http://localhost:8080/api/expenses/findall?username=${username}`);
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
        const response = await fetch(`http://localhost:8080/api/incomes/findall?username=${username}`);
        if (!response.ok) throw new Error('Failed to fetch incomes');
        this.incomes = await response.json();
      } catch (error) {
        console.error('Error fetching incomes:', error);
        throw error;
      }
    },
    addExpense(newExpense) {
      const username = this.username;
      const expenseWithUser = { ...newExpense, username };
      fetch('http://localhost:8080/api/expenses/add', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(expenseWithUser)
      })
          .then(response => {
            if (response.ok) {
              return response.json();
            }
            throw new Error('Network response was not ok.');
          })
          .then(data => {
            this.expenses.push(data);
            this.$store.dispatch('addNotification', `You just added expense: ${data.amount}`);
          })
          .catch(error => {
            console.error('Error adding expense:', error);
          });
    },
    addIncome(newIncome) {
      const username = this.username;
      const incomeWithUser = { ...newIncome, username };
      fetch('http://localhost:8080/api/incomes/add', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(incomeWithUser)
      })
          .then(response => {
            if (response.ok) {
              return response.json();
            }
            throw new Error('Network response was not ok.');
          })
          .then(data => {
            this.incomes.push(data);
          })
          .catch(error => {
            console.error('Error adding income:', error);
          });
    },
    deleteExpense(expenseId) {
      fetch(`http://localhost:8080/api/expenses/delete/${expenseId}`, {
        method: 'DELETE'
      })
          .then(response => {
            if (response.ok) {
              this.expenses = this.expenses.filter(expense => expense.id !== expenseId);
            } else {
              throw new Error('Network response was not ok.');
            }
          })
          .catch(error => {
            console.error('Error deleting expense:', error);
          });
    },
    deleteIncome(incomeId) {
      fetch(`http://localhost:8080/api/incomes/delete/${incomeId}`, {
        method: 'DELETE'
      })
          .then(response => {
            if (response.ok) {
              this.incomes = this.incomes.filter(income => income.id !== incomeId);
            } else {
              throw new Error('Network response was not ok.');
            }
          })
          .catch(error => {
            console.error('Error deleting income:', error);
          });
    },
    toggleTheme() {
      this.isBlackTheme = !this.isBlackTheme;
      localStorage.setItem('isBlackTheme', JSON.stringify(this.isBlackTheme));
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