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
            const response = await axios.get(`http://localhost:8080/incomes/findall?username=${rootState.user.username}`, {
                headers: {
                    'Authorization': `Bearer ${rootState.user.token}`
                }
            });
            commit('SET_INCOMES', response.data)

        } catch (error) {
            commit('SET_ERROR', 'Failed to fetch incomes');
            console.error('Error fetching incomes:', error);
        } finally {
            commit('SET_LOADING', false);
        }
    },
    async addIncome({dispatch, rootState }, newIncome) {
        const incomeWithUser = { ...newIncome, username: rootState.user.username };
        try {
            await axios.post('http://localhost:8080/incomes/add', incomeWithUser, {
                headers: {
                    'Authorization': `Bearer ${rootState.user.token}`,
                    'Content-Type': 'application/json'
                }
            });
          //  doesnt work because the controller is void and therefore response is empty
            //commit('ADD_INCOME', response.data);
        await dispatch('fetchIncomes')
        } catch (error) {
            console.error('Error adding income:', error);
        }
    },
    async deleteIncome({ commit, rootState }, incomeId) {
        try {
            const response = await axios.delete(`http://localhost:8080/incomes/delete/${incomeId}`, {
                headers: {
                    'Authorization': `Bearer ${rootState.user.token}`
                }
            });

            if (response.status === 204){
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