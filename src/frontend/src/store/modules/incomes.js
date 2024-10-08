import axios from 'axios';

const state = {
    incomes: [],
    loading: false,
    error: null
};

const mutations = {
    SET_INCOMES(state, incomes) {
        state.incomes = incomes;
    },
    SET_LOADING(state, loading) {
        state.loading = loading;
    },
    SET_ERROR(state, error) {
        state.error = error;
    },
    ADD_INCOME(state, income) {
        state.incomes.push(income);
    },
    DELETE_INCOME(state, incomeId) {
        state.incomes = state.incomes.filter(income => income.id !== incomeId);
    }
};

const actions = {
    async fetchIncomes({ commit, rootState }) {
        commit('SET_LOADING', true);
        try {
            const token = rootState.user.token; // Ensure token is retrieved correctly
            const response = await axios.get(`http://localhost:8080/incomes/findall?username=${rootState.user.username}`, {
                headers: {
                    'Authorization': `Bearer ${token}`
                }
            });
            commit('SET_INCOMES', response.data);
        } catch (error) {
            commit('SET_ERROR', 'Failed to fetch incomes');
            console.error('Error fetching incomes:', error);
        } finally {
            commit('SET_LOADING', false);
        }
    },
    async addIncome({ dispatch, rootState }, newIncome) {
        const incomeWithUser = { ...newIncome, username: rootState.user.username };
        try {
            const token = rootState.user.token; // Ensure token is retrieved correctly
            await axios.post('http://localhost:8080/incomes/add', incomeWithUser, {
                headers: {
                    'Authorization': `Bearer ${token}`,
                    'Content-Type': 'application/json'
                }
            });
            await dispatch('fetchIncomes');
        } catch (error) {
            console.error('Error adding income:', error);
        }
    },
    async deleteIncome({ commit, rootState }, incomeId) {
        try {
            const token = rootState.user.token; // Ensure token is retrieved correctly
            const response = await axios.delete(`http://localhost:8080/incomes/delete/${incomeId}`, {
                headers: {
                    'Authorization': `Bearer ${token}`
                }
            });

            if (response.status === 204) {
                commit('DELETE_INCOME', incomeId);
            }
        } catch (error) {
            console.error('Error deleting income:', error);
        }
    }
};

export default {
    namespaced: true,
    state,
    mutations,
    actions
};