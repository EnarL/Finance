<template>
  <div class="form-card">
    <h2>Monthly bar chart: {{ totalExpenses }}</h2>
    <input v-model="month" type="number" placeholder="Month (1-12)" min="1" max="12" />
    <button @click="fetchTotalExpenses">Fetch Expenses</button>
  </div>
</template>

<script>
export default {
  name: 'ExpenseSummary',
  data() {
    return {
      totalExpenses: 0,
      username: localStorage.getItem('username'),
      month: new Date().getMonth() + 1 // Default to current month
    };
  },
  methods: {
    fetchTotalExpenses() {
      fetch(`http://localhost:8080/api/expenses/sum?username=${this.username}&month=${this.month}`)
          .then(response => response.json())
          .then(data => {
            this.totalExpenses = data;
          })
          .catch(error => {
            console.error('Error fetching total expenses:', error);
          });
    }
  }
}
</script>

<style scoped>
.form-card {

  border-radius: 8px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  padding: 20px;
  transition: transform 0.3s ease;
}

.form-card:hover {
  transform: translateY(-5px);
}

input {
  display: block;
  width: 100%;
  margin-bottom: 1rem;
  padding: 0.5rem;
  border: 1px solid #ddd;
  border-radius: 4px;
  transition: border-color 0.3s ease;
}

input:focus {
  border-color: #007bff;
}

button {
  padding: 0.5rem 1rem;
  border: none;
  border-radius: 4px;


  cursor: pointer;
  transition: background-color 0.3s ease, transform 0.3s ease;
}

button:hover {
  background-color: #0056b3;
  transform: translateY(-2px);
}

button:active {
  background-color: #004494;
  transform: translateY(0);
}
</style>