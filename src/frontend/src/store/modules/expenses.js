import axios from 'axios';

const state = {
    expenses: [],
    loading: false,
    error: null
};

const mutations = {
    SET_EXPENSES(state, expenses) {
        state.expenses = expenses;
    },
    SET_LOADING(state, loading) {
        state.loading = loading;
    },
    SET_ERROR(state, error) {
        state.error = error;
    },
    ADD_EXPENSE(state, expense) {
        state.expenses.push(expense);
    },
    DELETE_EXPENSE(state, expenseId) {
        state.expenses = state.expenses.filter(expense => expense.id !== expenseId);
    }
};

const actions = {
    async fetchExpenses({ commit, rootState }) {
        commit('SET_LOADING', true);
        try {
            const response = await axios.get(`http://localhost:8080/expenses/findall?username=${rootState.user.username}`, {
                headers: {
                    'Authorization': `Bearer ${rootState.user.token}`
                }
            });
            commit('SET_EXPENSES', response.data);

        } catch (error) {
            commit('SET_ERROR', 'Failed to fetch expenses');
            console.error('Error fetching expenses:', error);
        } finally {
            commit('SET_LOADING', false);
        }
    },
    async addExpense({ dispatch, rootState }, newExpense) {
        const expenseWithUser = { ...newExpense, username: rootState.user.username };
        try {
             await axios.post('http://localhost:8080/expenses/add', expenseWithUser, {
                headers: {
                    'Authorization': `Bearer ${rootState.user.token}`,
                    'Content-Type': 'application/json'
                }
            });
            await dispatch('fetchExpenses')
        //    doesnt work because the controller is void and therefore response is empty
            //    commit('ADD_EXPENSE', response.data);
        } catch (error) {
            console.error('Error adding expense:', error);
        }
    },
    async deleteExpense({ commit, rootState }, expenseId) {
        try {
            const response = await axios.delete(`http://localhost:8080/expenses/delete/${expenseId}`, {
                headers: {
                    'Authorization': `Bearer ${rootState.user.token}`
                }
            });
            if (response.status === 204) {
                commit('DELETE_EXPENSE', expenseId);
            }
        } catch (error) {
            console.error('Error deleting expense:', error);
        }
    }
};

export default {
    namespaced: true,
    state,
    mutations,
    actions
};