<template>
  <div class="user-icon">
    <span @click="toggleDropdown">{{ username }}</span>
    <div v-if="showDropdown" class="dropdown-menu">
      <button @click="logout">Logout</button>
      <button @click="openSettings">Settings</button>
    </div>
  </div>
</template>

<script>
export default {
  name: 'UserIcon',
  data() {
    return {
      username: localStorage.getItem('username'),
      showDropdown: false
    };
  },
  methods: {
    toggleDropdown() {
      this.showDropdown = !this.showDropdown;
    },
    logout() {
      localStorage.removeItem('username');
      localStorage.removeItem('id');
      this.$router.push('/');
    },
    openSettings() {
      this.$router.push('/settings');
    }
  }
}
</script>

<style scoped>
.user-icon {
  position: relative;
  background-color: #007bff;
  color: white;
  padding: 5px 10px;
  border-radius: 50px;
  font-weight: bold;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  display: flex;
  align-items: center;
  gap: 10px;
  cursor: pointer;
}

.dropdown-menu {
  position: absolute;
  top: 100%;
  right: 0;
  background-color: white;
  color: black;
  border: 1px solid #ddd;
  border-radius: 4px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  margin-top: 5px;
  z-index: 1000;
}

.dropdown-menu button {
  display: block;
  width: 100%;
  background: none;
  border: none;
  padding: 10px;
  text-align: left;
  cursor: pointer;
}

.dropdown-menu button:hover {
  background-color: #f1f1f1;
}
</style>