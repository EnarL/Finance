import { createStore } from 'vuex';
import expenses from './modules/expenses';
import incomes from './modules/incomes';
import user from './modules/user';

export default createStore({
    modules: {
        expenses,
        incomes,
        user
    }
});