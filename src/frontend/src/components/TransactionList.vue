<template>
  <div class="transaction-list">
    <h2>{{ title }}</h2>
    <input v-model="filterText" placeholder="Filter transactions" />
    <input type="date" v-model="startDate" placeholder="Start date" />
    <input type="date" v-model="endDate" placeholder="End date" />
    <ul>
      <li v-for="transaction in filteredTransactions" :key="transaction.id">
        <span>{{ transaction.date }} - {{ transaction.category || transaction.source }}: {{ transaction.amount }}</span>
        <div class="buttons">
          <button @click="deleteTransaction(transaction.id)">Delete</button>
          <button @click="toggleEditTransaction(transaction)">Edit</button>
        </div>
        <div v-if="selectedTransaction && selectedTransaction.id === transaction.id">
          <h3>Edit Transaction</h3>
          <form @submit.prevent="updateTransaction">
            <input v-if="isExpense" v-model="selectedTransaction.description" placeholder="Description" />
            <input v-model="selectedTransaction.amount" placeholder="Amount" />
            <input v-if="!isExpense" v-model="selectedTransaction.source" placeholder="Source" />
            <button type="submit">Update</button>
          </form>
        </div>
      </li>
    </ul>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: 'TransactionList',
  props: {
    title: String,
    transactions: Array,
    deleteTransaction: Function,
    isExpense: Boolean
  },
  data() {
    return {
      filterText: '',
      startDate: '',
      endDate: '',
      selectedTransaction: null
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
  },
  methods: {
    toggleEditTransaction(transaction) {
      if (this.selectedTransaction && this.selectedTransaction.id === transaction.id) {
        this.selectedTransaction = null;
      } else {
        this.selectedTransaction = { ...transaction };
      }
    },
    async updateTransaction() {
      const endpoint = this.isExpense ? 'expenses' : 'incomes';
      try {
        await axios.put(`http://localhost:8080/${endpoint}/update/${this.selectedTransaction.id}`, this.selectedTransaction, {
          headers: {
            'Authorization': `Bearer ${localStorage.getItem('token')}`
          }
        });
        console.log("Transaction updated successfully!");
        this.selectedTransaction = null; // Clear the form
        this.$emit('update'); // Emit an event to refresh the list
      } catch (error) {
        console.error("There was an error updating the transaction!", error);
        console.log(this.selectedTransaction);
      }
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
  flex-direction: column;
  padding: 10px 0;
  border-bottom: 1px solid #ddd;
}

.transaction-list li:last-child {
  border-bottom: none;
}

.transaction-list span {
  flex-grow: 1;
  text-align: left;
}

.transaction-list .buttons {
  display: flex;
  gap: 10px;
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