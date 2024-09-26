import axios from 'axios';

const state = {
    username: localStorage.getItem('username'),
    token: localStorage.getItem('token'),
    id: localStorage.getItem('id'),
    loading: false,
    errorMessage: ''
};

const mutations = {
    SET_USER(state, user) {
        state.username = user.username;
        state.token = user.token;
        state.id = user.id;
    },
    CLEAR_USER(state) {
        state.username = null;
        state.token = null;
        state.id = null;
    }
};

const actions = {
    async login({ commit }, user) {
        commit('SET_LOADING', true);
        commit('SET_ERROR_MESSAGE', '');
        try {
            const response = await axios.post('http://localhost:8080/login', user);
            const data = response.data;
            localStorage.setItem('username', data.username);
            localStorage.setItem('token', data.token);
            localStorage.setItem('id', data.id);
            commit('SET_USER', data);
        } catch (error) {
            if (error.response && error.response.status === 401) {
                commit('SET_ERROR_MESSAGE', 'Invalid username or password');
            } else {
                commit('SET_ERROR_MESSAGE', 'An error occurred. Please try again later.');
            }
            throw error;
        } finally {
            commit('SET_LOADING', false);
        }
    },
    logout({ commit }) {
        localStorage.removeItem('username');
        localStorage.removeItem('token');
        localStorage.removeItem('id');
        commit('CLEAR_USER');
    },
    async changePassword({ state }, { currentPassword, newPassword }) {
        try {
            await axios.put(`http://localhost:8080/changepassword/${state.username}`, null, {
                params: {
                    currentPassword,
                    newPassword
                },
                headers: {
                    'Authorization': `Bearer ${state.token}`
                }
            });
        } catch (error) {
            console.error('Error changing password:', error);
            throw error;
        }
    },
    async deleteUser({commit, state }) {
        try {
            const response = await axios.delete(`http://localhost:8080/${state.id}`, {
                headers: {
                    'Authorization': `Bearer ${state.token}`
                }
            });
            if (response.status === 200) {
                commit('CLEAR_USER');
                localStorage.removeItem('username');
                localStorage.removeItem('token');
                localStorage.removeItem('id');
            }
        } catch (error) {
            console.error('Error deleting user:', error);
            throw error;
        }
    },
    async register({ state }, user) {
        try {
            await axios.post('http://localhost:8080/register', user, {
                headers: {
                    'Authorization': `Bearer ${state.token}`
                }
            });
        } catch (error) {
            console.error('Error registering user:', error);
            throw error;
        }
    },
    async assignRole({ state }, { username, role }) {
        try {
            await axios.post(`http://localhost:8080/${username}/role`, null, {
                params: { role },
                headers: {
                    'Authorization': `Bearer ${state.token}`
                }
            });
        } catch (error) {
            console.error('Error assigning role:', error);
            throw error;
        }
    },
    async findAllUsers({ state }) {
        try {
            const response = await axios.get('http://localhost:8080/findall', {
                headers: {
                    'Authorization': `Bearer ${state.token}`
                }
            });
            return response.data;
        } catch (error) {
            console.error('Error fetching all users:', error);
            throw error;
        }
    },
    async findUserById({ state }, id) {
        try {
            const response = await axios.get(`http://localhost:8080/id/${id}`, {
                headers: {
                    'Authorization': `Bearer ${state.token}`
                }
            });
            return response.data;
        } catch (error) {
            console.error('Error fetching user by ID:', error);
            throw error;
        }
    },
    async findUserByUsername({ state }, username) {
        try {
            const response = await axios.get(`http://localhost:8080/username/${username}`, {
                headers: {
                    'Authorization': `Bearer ${state.token}`
                }
            });
            return response.data;
        } catch (error) {
            console.error('Error fetching user by username:', error);
            throw error;
        }
    },
    async updateUser({ state }, { id, user }) {
        try {
            await axios.put(`http://localhost:8080/${id}`, user, {
                headers: {
                    'Authorization': `Bearer ${state.token}`
                }
            });
        } catch (error) {
            console.error('Error updating user:', error);
            throw error;
        }
    }
};

export default {
    namespaced: true,
    state,
    mutations,
    actions
};