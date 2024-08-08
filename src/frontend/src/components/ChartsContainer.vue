<template>
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
</template>

<script>
import { Chart, registerables } from 'chart.js';

Chart.register(...registerables);

export default {
  name: 'ChartsContainer',
  props: {
    expenses: Array,
    incomes: Array
  },
  mounted() {
    this.updateExpensesChart();
    this.updateIncomeChart();
  },
  watch: {
    expenses: 'updateExpensesChart',
    incomes: 'updateIncomeChart'
  },
  methods: {
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
            ], borderColor: 'transparent'
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
            ],
            borderColor: 'transparent'

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
    }
  }
}
</script>

<style scoped>
.charts-container {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
  gap: 20px;
  padding: 20px;

}

.chart-card {

  border-radius: 8px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  padding: 15px;

  transition: transform 0.3s ease;
}

.chart-card:hover {
  transform: translateY(-5px);
}
</style>
