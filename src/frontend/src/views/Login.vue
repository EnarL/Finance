<template>
  <div class="login-container">
    <h2>Login</h2>
    <form @submit.prevent="handleLogin">
      <div class="form-group">
        <label for="username">Username:</label>
        <input type="text" v-model="username" id="username" required />
      </div>
      <div class="form-group">
        <label for="password">Password:</label>
        <div class="password-input">
          <input :type="passwordFieldType" v-model="password" id="password" required />
          <span @click="togglePasswordVisibility" class="toggle-password">
            {{ passwordFieldType === 'password' ? 'Show' : 'Hide' }}
          </span>
        </div>
      </div>
      <button type="submit" :disabled="loading">
        <span v-if="loading" class="spinner"></span>
        <span v-else>Login</span>
      </button>
    </form>

    <!-- Error message -->
    <p v-if="errorMessage">{{ errorMessage }}</p>

    <button class="register-button" @click="goToRegister">Register</button>
  </div>
</template>

<script>
import { mapActions, mapState } from 'vuex';

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: 'Login',
  data() {
    return {
      username: '',
      password: '',
      passwordFieldType: 'password',
    };
  },
  computed: {
    // Map Vuex state to local computed properties
    ...mapState('user', {
      loading: state => state.loading,
      errorMessage: state => state.errorMessage,
    }),
  },
  methods: {
    // Map Vuex actions to component methods
    ...mapActions('user', ['login']),

    async handleLogin() {
      try {
        await this.login({username: this.username, password: this.password});

        await this.$router.push('/dashboard');
      } catch (error) {
        console.error('Login failed:', error);  // Error is already handled in Vuex
      }
    },

    togglePasswordVisibility() {
      this.passwordFieldType = this.passwordFieldType === 'password' ? 'text' : 'password';
    },

    goToRegister() {
      this.$router.push('/register');
    }
  }
};
</script>

<style scoped>
.login-container {
  min-width: 400px;
  margin: 0 auto;
  padding: 30px;
  border: 1px solid #ccc;
  border-radius: 10px;
  box-shadow: 0 0 15px rgba(0, 0, 0, 0.1);
  background-color: #f9f9f9;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
}

h2 {
  text-align: center;
  margin-bottom: 20px;
  color: #333;
}

.form-group {
  margin-bottom: 15px;
}

label {
  display: block;
  margin-bottom: 5px;
  color: #555;
}

input {
  width: 100%;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
  box-sizing: border-box;
}

.password-input {
  position: relative;
}

.toggle-password {
  position: absolute;
  right: 10px;
  top: 50%;
  transform: translateY(-50%);
  cursor: pointer;
  color: #007BFF;
}

button {
  width: 100%;
  padding: 10px;
  background-color: #007BFF;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  font-size: 16px;
}

button:disabled {
  background-color: #ccc;
  cursor: not-allowed;
}

button:hover:not(:disabled) {
  background-color: #0056b3;
}

.spinner {
  border: 2px solid #f3f3f3;
  border-top: 2px solid #007BFF;
  border-radius: 50%;
  width: 14px;
  height: 14px;
  animation: spin 1s linear infinite;
}

@keyframes spin {
  0% { transform: rotate(0deg); }
  100% { transform: rotate(360deg); }
}

p {
  color: red;
  text-align: center;
  margin-top: 10px;
}

.register-button {
  margin-top: 10px;
  background-color: #28a745;
}

.register-button:hover {
  background-color: #218838;
}
</style>
