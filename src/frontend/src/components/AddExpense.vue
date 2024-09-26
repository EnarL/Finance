<template>
  <div class="form-card">
    <h2>Add Expense</h2>
    <form @submit.prevent="addExpense">
      <input type="number" v-model="newExpense.amount" placeholder="Amount" required>
      <input type="text" v-model="newExpense.category" placeholder="Category" required>
      <input type="text" v-model="newExpense.description" placeholder="Description" required>
      <input type="date" v-model="newExpense.date" placeholder="Date" required>
      <button type="submit">Add Expense</button>
    </form>
  </div>
</template>

<script>
export default {
  name: 'AddExpense',
  data() {
    return {
      newExpense: {
        amount: null,
        category: '',
        description: '',
        date: new Date().toISOString().split('T')[0],
      }
    };
  },
  methods: {
    addExpense() {
      this.$emit('add-expense', this.newExpense);
      this.resetNewExpense();
    },
    resetNewExpense() {
      this.newExpense = {
        amount: null,
        category: '',
        description: '',
        date: new Date().toISOString().split('T')[0],
      };
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
  max-width:200px;
  cursor: pointer;
  transition: background-color 0.3s ease, transform 0.3s ease;
}

button:hover {
  background-color: #0056b3;
  transform: translateY(-2px);
}
</style>