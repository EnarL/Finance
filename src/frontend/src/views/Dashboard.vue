<template>
  <div class="dashboard">
    <h1>Financial Dashboard</h1>
    <div class="forms-container">
      <AddExpense @add-expense="addExpense"></AddExpense>
      <AddIncome @add-income="addIncome"></AddIncome>
      <DeleteExpense @delete-expense="deleteExpense"></DeleteExpense>
      <DeleteIncome @delete-income="deleteIncome"></DeleteIncome>
    </div>
    <ChartsContainer :expenses="expenses" :incomes="incomes"></ChartsContainer>
  </div>
</template>

<script>
import AddExpense from '@/components/AddExpense.vue';
import AddIncome from '@/components/AddIncome.vue';
import DeleteExpense from '@/components/DeleteExpense.vue';
import DeleteIncome from '@/components/DeleteIncome.vue';
import ChartsContainer from '@/components/ChartsContainer.vue';

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: 'Dashboard',
  components: {
    AddExpense,
    AddIncome,
    DeleteExpense,
    DeleteIncome,
    ChartsContainer
  },
  data() {
    return {
      expenses: [],
      incomes: [],
      loading: true,
      error: null
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
      return fetch('http://localhost:8080/api/expenses/findall')
          .then(response => response.json())
          .then(data => {
            this.expenses = data;
          });
    },
    async fetchIncomeData() {
      return fetch('http://localhost:8080/api/incomes/findall')
          .then(response => response.json())
          .then(data => {
            this.incomes = data;
          });
    },
    addExpense(newExpense) {
      fetch('http://localhost:8080/api/expenses/add', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(newExpense)
      })
          .then(response => {
            if (response.ok && response.status === 200) {
              return response.json();
            }
            throw new Error('Network response was not ok.');
          })
          .then(data => {
            this.expenses.push(data);
          })
          .catch(error => {
            console.error('Error adding expense:', error);
          });
    },
    addIncome(newIncome) {
      fetch('http://localhost:8080/api/incomes/add', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(newIncome)
      })
          .then(response => {
            if (response.ok && response.status === 200) {
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
            if (response.ok && response.status === 200) {
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
            if (response.ok && response.status === 200) {
              this.incomes = this.incomes.filter(income => income.id !== incomeId);
            } else {
              throw new Error('Network response was not ok.');
            }
          })
          .catch(error => {
            console.error('Error deleting income:', error);
          });
    }
  }
}
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap');

.dashboard {
  text-align: center;
  padding: 20px;
  font-family: 'Roboto', sans-serif;
}
.forms-container {
  display: flex;
  justify-content: center;
  gap: 20px;
}
h1 {
  margin-bottom: 2rem;
  color: #333;
}
input {
  display: block;
  width: 100%;
  margin-bottom: 1rem;
  padding: 0.5rem;
  border: 1px solid #ddd;
  border-radius: 4px;
}

button {
  padding: 0.5rem 1rem;
  border: none;
  border-radius: 4px;
  background-color: #007bff;
  color: white;
  cursor: pointer;
}

button:hover {
  background-color: #0056b3;
}
</style>