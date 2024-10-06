<template>
  <div class="login-container">
    <h2>Login</h2>
    <form @submit.prevent="login">
      <div class="form-group">
        <label for="username">Username:</label>
        <input type="text" v-model="username" id="username" required>
      </div>
      <div class="form-group">
        <label for="password">Password:</label>
        <div class="password-input">
          <input :type="passwordFieldType" v-model="password" id="password" required>
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
    <p v-if="errorMessage">{{ errorMessage }}</p>
    <button class="register-button" @click="goToRegister">Register</button>
  </div>
</template>
<script>
export default{

  name: Login

}
</script>