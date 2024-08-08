<template>
  <div class="form-card">
    <h2>Delete Expense</h2>
    <form @submit.prevent="deleteExpense">
      <input type="text" v-model="expenseId" placeholder="Expense ID" required>
      <button type="submit">Delete Expense</button>
    </form>
  </div>
</template>

<script>
export default {
  name: 'DeleteExpense',
  data() {
    return {
      expenseId: ''
    };
  },
  methods: {
    deleteExpense() {
      this.$emit('delete-expense', this.expenseId);
      this.expenseId = '';
    }
  }
}
</script>

<style scoped>
.form-card {

  border-radius: 8px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  padding: 15px;
  transition: transform 0.3s ease;
}

.form-card:hover {
  transform: translateY(-5px);
}
</style>