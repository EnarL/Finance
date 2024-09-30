<template>
  <div class="transaction-list">
    <h2>{{ title }}</h2>
    <input v-model="filterText" placeholder="Filter transactions" />
    <input type="date" v-model="startDate" placeholder="Start date" />
    <input type="date" v-model="endDate" placeholder="End date" />
    <ul>
      <li v-for="transaction in filteredTransactions" :key="transaction.id">
        <span>{{ transaction.date }} - {{ transaction.category || transaction.source }}: {{ transaction.amount }}</span>
        <button @click="deleteTransaction(transaction.id)">Delete</button>
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  name: 'TransactionList',
  props: {
    title: String,
    transactions: Array,
    deleteTransaction: Function
  },
  data() {
    return {
      filterText: '',
      startDate: '',
      endDate: ''
    };
  },
  computed: {
    filteredTransactions() {
      return this.transactions.filter(transaction => {
        const searchText = this.filterText.toLowerCase();
        const transactionDate = new Date(transaction.date);
        const start = this.startDate ? new Date(this.startDate) : null;
        const end = this.endDate ? new Date(this.endDate) : null;

        const matchesText = (transaction.category && transaction.category.toLowerCase().includes(searchText)) ||
            (transaction.source && transaction.source.toLowerCase().includes(searchText));
        const matchesDate = (!start || transactionDate >= start) && (!end || transactionDate <= end);

        return matchesText && matchesDate;
      });
    }
  }
}
</script>

<style scoped>
.transaction-list {
  border-radius: 8px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  padding: 15px;
  width: 100%;
  margin-bottom: 20px;
}

.transaction-list input {
  width: 100%;
  padding: 10px;
  margin-bottom: 15px;
  border: 1px solid #ddd;
  border-radius: 4px;
}

.transaction-list ul {
  list-style-type: none;
  padding: 0;
}

.transaction-list li {
  display: flex;
  justify-content: space-between;
  padding: 10px 0;
  border-bottom: 1px solid #ddd;
}

.transaction-list li:last-child {
  border-bottom: none;
}

.transaction-list button {
  background-color: #ff4d4d;
  color: white;
  border: none;
  border-radius: 4px;
  padding: 5px 10px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.transaction-list button:hover {
  background-color: #cc0000;
}
</style>