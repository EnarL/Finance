<template>
  <div class="form-card">
    <h2>Delete Income</h2>
    <form @submit.prevent="deleteIncome">
      <input type="text" v-model="incomeId" placeholder="Income ID" required>
      <button type="submit">Delete Income</button>
    </form>
  </div>
</template>

<script>
export default {
  name: 'DeleteIncome',
  data() {
    return {
      incomeId: ''
    };
  },
  methods: {
    deleteIncome() {
      this.$emit('delete-income', this.incomeId);
      this.incomeId = '';
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