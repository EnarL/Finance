<template>
  <div class="dashboard">
    <h1>Financial Dashboard</h1>
    <div class="forms-container">
      <div class="form-card">
        <h2>Add Expense</h2>
        <form @submit.prevent="addExpense">
          <input type="text" v-model="newExpense.username" placeholder="Username" required>
          <input type="number" v-model="newExpense.amount" placeholder="Amount" required>
          <input type="text" v-model="newExpense.category" placeholder="Category" required>
          <input type="text" v-model="newExpense.description" placeholder="Description">
          <input type="date" v-model="newExpense.date" placeholder="Date" required>
          <button type="submit">Add Expense</button>
        </form>
      </div>
      <div class="form-card">
        <h2>Add Income</h2>
        <form @submit.prevent="addIncome">
          <input type="text" v-model="newIncome.username" placeholder="Username" required>
          <input type="number" v-model="newIncome.amount" placeholder="Amount" required>
          <input type="text" v-model="newIncome.source" placeholder="Source" required>
          <input type="date" v-model="newIncome.date" placeholder="Date" required>
          <button type="submit">Add Income</button>
        </form>
      </div>
    </div>
    <div class="charts-container">
      <div class="chart-card">
        <h2>Expenses</h2>
        <canvas id="expensesChart"></canvas>
      </div>
      <div class="chart-card">
        <h2>Income</h2>
        <canvas id="incomeChart"></canvas>
      </div>
    </div>
  </div>
</template>

<script>
import { Chart, registerables } from 'chart.js';

// Register all necessary components
Chart.register(...registerables);

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: 'Dashboard',
  data() {
    return {
      expenses: [],
      incomes: [],
      newExpense: {
        username: '',
        amount: '',
        category: '',
        description: '',
        date: ''
      },
      newIncome: {
        username: '',
        amount: '',
        source: '',
        date: ''
      },
      loading: true,
      error: null,
      expenseChart: null,
      incomeChart: null
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
            this.updateExpensesChart();
          });
    },
    async fetchIncomeData() {
      return fetch('http://localhost:8080/api/incomes/findall')
          .then(response => response.json())
          .then(data => {
            this.incomes = data;
            this.updateIncomeChart();
          });
    },
    updateExpensesChart() {
      if (this.expenseChart) {
        this.expenseChart.destroy();
      }
      const ctx = document.getElementById('expensesChart').getContext('2d');
      const categoryData = this.expenses.reduce((acc, expense) => {
        if (!acc[expense.category]) {
          acc[expense.category] = 0;
        }
        acc[expense.category] += parseFloat(expense.amount);
        return acc;
      }, {});

      const categories = Object.keys(categoryData);
      const amounts = Object.values(categoryData);

      this.expenseChart = new Chart(ctx, {
        type: 'pie',
        data: {
          labels: categories,
          datasets: [{
            data: amounts,
            backgroundColor: [
              '#FF6384',
              '#36A2EB',
              '#FFCE56',
              '#4BC0C0',
              '#9966FF',
              '#FF9F40'
            ],
            hoverBackgroundColor: [
              '#FF6384',
              '#36A2EB',
              '#FFCE56',
              '#4BC0C0',
              '#9966FF',
              '#FF9F40'
            ]
          }]
        },
        options: {
          responsive: true,
          plugins: {
            legend: {
              position: 'top',
            },
            title: {
              display: true,
              text: 'Expenses by Category'
            },
            tooltip: {
              callbacks: {
                label: function(tooltipItem) {
                  return tooltipItem.label + ': ' + tooltipItem.raw.toFixed(2);
                }
              }
            }
          }
        }
      });
    },
    updateIncomeChart() {
      if (this.incomeChart) {
        this.incomeChart.destroy();
      }
      const ctx = document.getElementById('incomeChart').getContext('2d');
      const sourceData = this.incomes.reduce((acc, income) => {
        if (!acc[income.source]) {
          acc[income.source] = 0;
        }
        acc[income.source] += parseFloat(income.amount);
        return acc;
      }, {});

      const sources = Object.keys(sourceData);
      const amounts = Object.values(sourceData);

      this.incomeChart = new Chart(ctx, {
        type: 'pie',
        data: {
          labels: sources,
          datasets: [{
            data: amounts,
            backgroundColor: [
              '#FF6384',
              '#36A2EB',
              '#FFCE56',
              '#4BC0C0',
              '#9966FF',
              '#FF9F40'
            ],
            hoverBackgroundColor: [
              '#FF6384',
              '#36A2EB',
              '#FFCE56',
              '#4BC0C0',
              '#9966FF',
              '#FF9F40'
            ]
          }]
        },
        options: {
          responsive: true,
          plugins: {
            legend: {
              position: 'top',
            },
            title: {
              display: true,
              text: 'Income by Source'
            },
            tooltip: {
              callbacks: {
                label: function(tooltipItem) {
                  return tooltipItem.label + ': ' + tooltipItem.raw.toFixed(2);
                }
              }
            }
          }
        }
      });
    },
    addExpense() {
      fetch('http://localhost:8080/api/expenses', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(this.newExpense)
      })
          .then(response => {
            if (!response.ok) {
              throw new Error('Failed to add expense');
            }
            return response.json();
          })
          .then(data => {
            this.expenses.push(data);
            this.updateExpensesChart();
            this.resetNewExpense();
          })
          .catch(error => {
            console.error('Error adding expense:', error);
            // Display error to user or handle as needed
          });
    }
    ,
    addIncome() {
      fetch('http://localhost:8080/api/incomes', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(this.newIncome)
      })
          .then(response => response.json())
          .then(data => {
            this.incomes.push(data);
            this.updateIncomeChart();
            this.resetNewIncome();
          })
          .catch(error => console.error('Error:', error));
    },
    resetNewExpense() {
      this.newExpense = {
        username: '',
        amount: '',
        category: '',
        description: '',
        date: ''
      };
    },
    resetNewIncome() {
      this.newIncome = {
        username: '',
        amount: '',
        source: '',
        date: ''
      };
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

.charts-container {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
  gap: 20px;
  padding: 20px;
  background-color: #f4f4f4;
}

.chart-card {
  background-color: #ffffff;
  border-radius: 8px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  padding: 20px;
  transition: transform 0.3s ease;
}

.chart-card:hover {
  transform: translateY(-5px);
}

canvas {
  max-width: 600px;
  margin: 20px auto;
  box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
  border-radius: 8px;
}

h1 {
  margin-bottom: 2rem;
  color: #333;
}

h2 {
  color: #555;
  margin-bottom: 1.5rem;
}



</style>
