<template>
  <div>
    <h1>Register</h1>
    <button @click="registerWithGoogle">Register with Google</button>
  </div>
</template>

<script>
export default {
  methods: {
    registerWithGoogle() {
      window.location.href = 'http://localhost:8080/oauth2/authorization/google';
    }
  }
}
</script>