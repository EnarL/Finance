<template>
  <div class="form-card">
    <h2>Register</h2>
    <p v-if="successMessage" class="success-message">{{ successMessage }}</p>
    <p v-if="errorMessage" class="error-message">{{ errorMessage }}</p>
    <form @submit.prevent="handleRegister">
      <input type="text" v-model="username" placeholder="Username" required>
      <input type="email" v-model="email" placeholder="Email" required>
      <input type="password" v-model="password" placeholder="Password" required>
      <button type="submit" :disabled="loading">Register</button>
    </form>

    <button @click="goToLogin">Go to Login</button>
  </div>
</template>


<script>
import { mapActions, mapState } from 'vuex';

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: 'Register',
  data() {
    return {
      username: '',
      email: '',
      password: '',
      successMessage: '',
    };
  },
  computed: {
    ...mapState('user', {
      loading: state => state.loading,
      errorMessage: state => state.errorMessage,
    }),
  },
  methods: {
    ...mapActions('user', ['register']),

    async handleRegister() {
      try {
        await this.register({
          username: this.username,
          email: this.email,
          password: this.password
        });

        this.successMessage = 'Registration successful! Please log in.';
        this.errorMessage = '';

        this.username = '';
        this.email = '';
        this.password = '';


        setTimeout(() => {
          this.$router.push('/');
        }, 3000);
      } catch (error) {
        console.error('Registration failed:', error);
        this.successMessage = '';
      }
    },

    goToLogin() {
      this.$router.push('/');
    }
  }
};
</script>

<style scoped>
.form-card {
  background-color: #ffffff;
  border-radius: 8px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  padding: 15px;
  transition: transform 0.3s ease;
}

.form-card:hover {
  transform: translateY(-5px);
}

input {
  display: block;
  width: 100%;
  margin-bottom: 1rem;
  padding: 0.5rem;
  border: 1px solid #ddd;
  border-radius: 4px;
}

button {
  padding: 0.5rem 1rem;
  border: none;
  border-radius: 4px;
  background-color: #007bff;
  color: white;
  cursor: pointer;
}

button:hover {
  background-color: #0056b3;
}
</style>
