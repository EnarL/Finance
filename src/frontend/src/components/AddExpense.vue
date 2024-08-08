<template>
  <div class="form-card">
    <h2>Add Expense</h2>
    <input v-model="amount" type="number" placeholder="Amount" />
    <input v-model="category" type="text" placeholder="Category" />
    <input v-model="description" type="text" placeholder="Description" />
    <input v-model="date" type="date" placeholder="Date" />
    <button @click="addExpense">Add Expense</button>
  </div>
</template>

<script>
export default {
  data() {
    return {
      amount: '',
      category: '',
      description: '',
      date: new Date().toISOString().split('T')[0], // Default to today's date
      username: localStorage.getItem('username')
    };
  },
  methods: {
    addExpense() {
      if (!this.date) {
        alert('Please enter a valid date.');
        return;
      }
      const newExpense = {
        amount: this.amount,
        category: this.category,
        description: this.description,
        date: this.date,
        username: this.username
      };
      fetch('http://localhost:8080/api/expenses/add', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(newExpense)
      })
          .then(response => response.json())
          .then(data => {
            console.log('Expense added:', data);
          })
          .catch(error => {
            console.error('Error adding expense:', error);
          });
    }
  }
}
</script>

<style scoped>
.form-card {
  background-color: #f9f9f9;
  border-radius: 8px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  padding: 20px;
  max-width: 400px;
  margin: 20px auto;
  text-align: center;
}

h2 {
  margin-bottom: 20px;
  color: #333;
}

input {
  display: block;
  width: calc(100% - 20px);
  margin: 10px auto;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 4px;
  transition: border-color 0.3s ease;
  color:black;
}

input:focus {
  border-color: #007bff;
  outline: none;
}

button {
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 4px;
  padding: 10px 20px;
  cursor: pointer;
  transition: background-color 0.3s ease, transform 0.3s ease;
}

button:hover {
  background-color: #0056b3;
  transform: translateY(-2px);
}
</style>