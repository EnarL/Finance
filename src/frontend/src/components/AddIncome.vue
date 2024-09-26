<template>
  <div class="form-card">
    <h2>Add Income</h2>
    <form @submit.prevent="addIncome">
      <input type="number" v-model="newIncome.amount" placeholder="Amount" required>
      <input type="text" v-model="newIncome.source" placeholder="Source" required>
      <input type="date" v-model="newIncome.date" placeholder="Date" required>
      <button type="submit">Add Income</button>
    </form>
  </div>
</template>

<script>
export default {
  name: 'AddIncome',
  data() {
    return {
      newIncome: {
        amount: null,
        source: '',
        date: new Date().toISOString().split('T')[0]
      }
    };
  },
  methods: {
    addIncome() {
      this.$emit('add-income', this.newIncome);
      this.resetNewIncome();
    },
    resetNewIncome() {
      this.newIncome = {
        amount: null,
        source: '',
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
