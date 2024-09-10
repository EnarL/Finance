<template>
  <div class="charts-container">
    <div class="chart-card">
      <h2>Monthly Expenses Bar Chart</h2>
      <canvas id="monthlyExpensesChart"></canvas>
    </div>
  </div>
</template>

<script>
import { Chart, registerables } from 'chart.js';

Chart.register(...registerables);

export default {
  name: 'ChartsContainer',
  props: {
    expenses: Array
  },
  mounted() {
    this.updateMonthlyExpensesChart();

  },
  watch: {
    expenses: 'updateMonthlyExpensesChart'
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

      this.monthlyExpensesChart = new Chart(ctx, {
        type: 'bar',
        data: {
          labels: months,
          datasets: [{
            label: 'Expenses',
            data: amounts,
            backgroundColor: 'darkblue',
           borderColor: 'green',
            borderWidth:1
          }]
        },
        options: {
          scales: {
            y: {
              beginAtZero: true
            }
          },
          responsive: true,
          plugins: {
            legend: {
            //  position: 'top',
              display:false,
            },
            title: {
              display: true,
            //  text: 'Monthly.vue Expenses by Month'
            },
            tooltip: {
              callbacks: {
                label: function (tooltipItem) {
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

  width:75%;
  margin: 0 auto;


}

.chart-card {


  border-radius: 8px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);

  transition: transform 0.3s ease;
}

.chart-card:hover {
  transform: translateY(-5px);
}
</style>