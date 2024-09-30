<script setup>
import { useDark, useToggle } from "@vueuse/core";
import { ref } from "vue";
import { useStore } from "vuex";
import ChangePassword from "@/components/ChangePassword.vue";
import DeleteAccount from "@/components/DeleteAccount.vue";


const showChangePasswordModel = ref(false);
const showDeleteAccountModel = ref(false);
const isDark = useDark();
const toggleDark = useToggle(isDark);
const store = useStore();

const changePassword = async (currentPassword, newPassword) => {
  try {
    await store.dispatch('user/changePassword', { currentPassword, newPassword });
    showChangePasswordModel.value = false;
  } catch (error) {
    console.error('Error changing password:', error);
  }
};

const deleteAccount = async () => {
  try {
    await store.dispatch('user/deleteUser');
    showDeleteAccountModel.value = false;
  } catch (error) {
    console.error('Error deleting account:', error);
  }
};



</script>

<template>
  <main class="main-container">
    <div class="settings-card">
      <div class="header">
        <h1 class="settings-title">Settings</h1>
      </div>

      <div class="settings-item">
        <label class="label-text">Theme</label>
        <button @click="toggleDark()" class="btn toggle-btn">
          {{ isDark ? "Switch to Dark Mode" : "Switch to Light Mode" }}
        </button>
      </div>

      <div class="settings-item">
        <label class="label-text">Notification Settings</label>
        <button class="btn notification-btn">Configure Notifications</button>
      </div>

      <div class="settings-item">
        <label class="label-text">Account Settings</label>
        <button class="btn account-btn">Manage Account</button>
      </div>

      <div class="settings-item">
        <label class="label-text">Change Password</label>
        <button @click="showChangePasswordModel = true" class="btn change-password-btn">
          Change Password
        </button>
      </div>
      <div class="settings-item">
        <label class="label-text">Delete Account</label>
        <button @click="showDeleteAccountModel = true" class="btn delete-btn">
          Delete Account
        </button>
      </div>
    </div>

    <DeleteAccount :show="showDeleteAccountModel" @close="showDeleteAccountModel = false" @delete-account="deleteAccount"/>
    <ChangePassword :show="showChangePasswordModel" @close="showChangePasswordModel = false" @change-password="changePassword" />
  </main>
</template>


<script>
export default {
  //eslint-disable-next-line vue/multi-word-component-names
  name: 'Settings'
}
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap');

* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
  font-family: 'Roboto', sans-serif;
}

.main-container {
  padding-top: 2rem;
  display: flex;
  justify-content: center;
  background-color: #2e3a46;
  min-height: 100vh;
}

.settings-card {
  max-width: 900px;
  max-height: 600px; /* Restrict height */
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  padding: 2rem;
  border-radius: 8px;
  background-color: #fff;
  overflow-y: auto; /* Enable internal scrolling if content overflows */
}

.header {
  display: flex;
  align-items: flex-start;
  margin-bottom: 1rem;
}

.settings-title {
  font-size: 2.5rem;
  color: #000;
  flex-grow: 1;
}

.settings-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 1rem;
}

.label-text {
  font-size: 1rem;
  color: #000;
}

.btn {
  padding: 0.5rem 1rem;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.3s ease, transform 0.3s ease;
  margin-left: 1rem;
  border: none;
  color: #fff;
}

.toggle-btn {
  background-color: #000;
}
.delete-btn {
  background-color: #e53e3e;
}

.change-password-btn, .account-btn, .notification-btn {
  background-color: #000;
}

.btn:hover {
  background-color: #009394;
  transform: translateY(-2px);
}

.btn:active {
  background-color: #006270;
  transform: translateY(0);
}

input {
  width: 100%;
  margin-bottom: 1rem;
  padding: 0.5rem;
  border: 1px solid #ddd;
  border-radius: 4px;
}

.dark .main-container {
  background-color: #ffffff;
}

.dark .settings-card {
  background-color: #2e3a46;
}

.dark .settings-title, .dark .label-text {
  color: #fff;
}

.dark .toggle-btn {
  background-color: #fff;
  color: #000;
}

.dark .notification-btn, .dark .account-btn, .dark .change-password-btn {
  background-color: #fff;
  color: #000;
}

.dark .delete-btn {
  background-color: #e53e3e;
}

.dark .btn:hover {
  background-color: #009394;
}

.dark .btn:active {
  background-color: #006270;
}
</style>

