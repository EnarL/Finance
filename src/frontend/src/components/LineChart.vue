<template>
  <div class="charts-container">
    <div class="chart-card">

     <canvas id="monthlyIncomesChart"></canvas>
      <canvas id="monthlyExpensesChart"></canvas>
    </div>
  </div>
</template>

<script>
import { Chart, registerables } from 'chart.js';


Chart.register(...registerables);

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: 'ChartsContainer',
  props: {
    expenses: Array,
    incomes: Array
  },
  mounted() {
    this.updateMonthlyExpensesChart();
    this.updateMonthlyIncomesChart();

  },
  watch: {
    expenses: 'updateMonthlyExpensesChart',
    incomes: 'updateMonthlyIncomesChart'

  },
  methods: {
    updateMonthlyExpensesChart() {
      if (this.monthlyExpensesChart) {
        this.monthlyExpensesChart.destroy();
      }
      const ctx = document.getElementById('monthlyExpensesChart').getContext('2d');
      const monthData = this.expenses.reduce((acc, expense) => {
        const month = new Date(expense.date).getMonth();
        if (!acc[month]) {
          acc[month] = 0;
        }
        acc[month] += parseFloat(expense.amount);
        return acc;
      }, {});

      const months = Object.keys(monthData).map(month => new Date(0, month).toLocaleString('default', {month: 'long'}));
      const amounts = Object.values(monthData);

      const gradient = ctx.createLinearGradient(0, 0, 0, 400);
      gradient.addColorStop(0, 'rgba(0, 98, 112, 0.5)'); // Updated color
      gradient.addColorStop(1, 'rgba(0, 98, 112, 0)'); // Updated color

      this.monthlyExpensesChart = new Chart(ctx, {
        type: 'line',
        data: {
          labels: months,
          datasets: [{
            label: 'Expenses',
            data: amounts,
            borderColor: '#006270', // Updated color
            backgroundColor: gradient,
            fill: true,
            tension: 0.4, // Smooth curves
            pointBackgroundColor: '#006270', // Updated color
            pointBorderColor: 'white',
            pointHoverRadius: 5,
            pointHoverBackgroundColor: 'white',
            pointHoverBorderColor: '#006270', // Updated color
            pointRadius: 3,
            pointHitRadius: 10
          }]
        },
        options: {
          scales: {
            x: {
              grid: {
                display: false
              }
            },
            y: {
              beginAtZero: true,
              grid: {
                color: 'rgba(200, 200, 200, 0.2)'
              }
            }
          },
          responsive: true,
          plugins: {
            legend: {
              display: true,
              position: 'top',
              labels: {
                color: '#006270' // Updated color
              }
            },
            title: {
              display: true,
              text: 'Monthly Expenses by Month',
              color: '#006270', // Updated color
              font: {
                size: 18
              }
            },
            tooltip: {
              backgroundColor: 'rgba(0, 98, 112, 0.8)', // Updated color
              titleColor: 'white',
              bodyColor: 'white',
              callbacks: {
                label: function (tooltipItem) {
                  return tooltipItem.label + ': $' + tooltipItem.raw.toFixed(2);
                }
              }
            }
          }
        }
      });
    },
    updateMonthlyIncomesChart(){
      if (this.monthlyIncomesChart) {
        this.monthlyIncomesChart.destroy();
      }
      const ctx = document.getElementById('monthlyIncomesChart').getContext('2d');
      const monthData = this.incomes.reduce((acc, income) => {
        const month = new Date(income.date).getMonth();
        if (!acc[month]) {
          acc[month] = 0;
        }
        acc[month] += parseFloat(income.amount);
        return acc;
      }, {});

      const months = Object.keys(monthData).map(month => new Date(0, month).toLocaleString('default', {month: 'long'}));
      const amounts = Object.values(monthData);

      const gradient = ctx.createLinearGradient(0, 0, 0, 400);
      gradient.addColorStop(0, 'rgba(0, 98, 112, 0.5)'); // Updated color
      gradient.addColorStop(1, 'rgba(0, 98, 112, 0)'); // Updated color

      this.monthlyIncomesChart = new Chart(ctx, {
        type: 'line',
        data: {
          labels: months,
          datasets: [{
            label: 'Incomes',
            data: amounts,
            borderColor: '#006270', // Updated color
            backgroundColor: gradient,
            fill: true,
            tension: 0.4, // Smooth curves
            pointBackgroundColor: '#006270', // Updated color
            pointBorderColor: 'white',
            pointHoverRadius: 5,
            pointHoverBackgroundColor: 'white',
            pointHoverBorderColor: '#006270', // Updated color
            pointRadius: 3,
            pointHitRadius: 10
          }]
        },
        options: {
          scales: {
            x: {
              grid: {
                display: false
              }
            },
            y: {
              beginAtZero: true,
              grid: {
                color: 'rgba(200, 200, 200, 0.2)'
              }
            }
          },
          responsive: true,
          plugins: {
            legend: {
              display: true,
              position: 'top',
              labels: {
                color: '#006270' // Updated color
              }
            },
            title: {
              display: true,
              text: 'Monthly Incomes by Month',
              color: '#006270', // Updated color
              font: {
                size: 18
              }
            },
            tooltip: {
              backgroundColor: 'rgba(0, 98, 112, 0.8)', // Updated color
              titleColor: 'white',
              bodyColor: 'white',
              callbacks: {
                label: function (tooltipItem) {
                  return tooltipItem.label + ': $' + tooltipItem.raw.toFixed(2);
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
  width: 75%;
  margin: 0 auto;

}

.chart-card {

  border-radius: 8px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  transition: transform 0.3s ease;
  padding: 20px;

}

.chart-card:hover {
  transform: translateY(-5px);
}
</style>