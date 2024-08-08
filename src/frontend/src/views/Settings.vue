<script setup>
import { ref } from 'vue';
import { useDark, useToggle } from "@vueuse/core";
import ChangePasswordModel from "@/components/ChangePasswordModel.vue";
import DeleteAccount from "@/components/DeleteAccount.vue";

const isDark = useDark();
const toggleDark = useToggle(isDark);
const showChangePasswordModal = ref(false);
const showDeleteAccountModal = ref(false);

async function getUserId() {
  const username = localStorage.getItem('username');
  if (!username) {
    console.error('Username not found in local storage');
    return null;
  }

  try {
    const response = await fetch(`http://localhost:8080/api/clients/?username=${username}`);
    if (response.ok) {
      const user = await response.json();
      return user.id; // Assuming the response contains the user object with an id field
    } else {
      console.error('Failed to fetch user ID');
      return null;
    }
  } catch (error) {
    console.error('Error fetching user ID:', error);
    return null;
  }
}

async function handlePasswordChange({ currentPassword, newPassword }) {
  try {
    const userId = await getUserId();
    if (!userId) {
      console.error('User ID not found');
      return;
    }

    const response = await fetch(`/api/clients/change/${userId}`, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({ newPassword })
    });

    if (response.ok) {
      console.log('Password changed successfully');
    } else {
      console.error('Failed to change password');
    }
  } catch (error) {
    console.error('Error changing password:', error);
  } finally {
    showChangePasswordModal.value = false;
  }
}

async function handleDeleteAccount(password) {
  try {
    const userId = await getUserId();
    if (!userId) {
      console.error('User ID not found');
      return;
    }

    const response = await fetch(`/api/clients/delete/${userId}`, {
      method: 'DELETE',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${password}` // Assuming password is used for authorization
      }
    });

    if (response.ok) {
      console.log('Account deleted successfully');
    } else {
      console.error('Failed to delete account');
    }
  } catch (error) {
    console.error('Error deleting account:', error);
  } finally {
    showDeleteAccountModal.value = false;
  }
}
</script>

<template>
  <main class="pt-8 flex items-start justify-center bg-slate-700 dark:bg-white min-h-screen">
    <div class="max-w-[900px] shadow-md p-8 rounded-md bg-white dark:bg-slate-700">
      <div class="flex items-start sm:items-center mb-4">
        <h1 class="text-4xl flex-1 dark:text-white">Settings</h1>
      </div>
      <div class="settings-item mb-4">
        <label class="text-md dark:text-white">Theme</label>
        <button @click="toggleDark()" class="py-2 px-4 bg-black text-white rounded-md dark:bg-white dark:text-black">
          {{ isDark ? "Switch to Light Mode" : "Switch to Dark Mode" }}
        </button>
      </div>
      <div class="settings-item mb-4">
        <label class="text-md dark:text-white">Notification Settings</label>
        <button class="py-2 px-4 bg-black text-white rounded-md dark:bg-white dark:text-black">
          Configure Notifications
        </button>
      </div>
      <div class="settings-item mb-4">
        <label class="text-md dark:text-white">Account Settings</label>
        <button class="py-2 px-4 bg-black text-white rounded-md dark:bg-white dark:text-black">
          Manage Account
        </button>
      </div>
      <div class="settings-item mb-4">
        <label class="text-md dark:text-white">Change Password</label>
        <button @click="showChangePasswordModal = true"
                class="py-2 px-4 bg-black text-white rounded-md dark:bg-white dark:text-black">
          Change Password
        </button>
      </div>
      <div class="settings-item mb-4">
        <label class="text-md dark:text-white">Delete Account</label>
        <button @click="showDeleteAccountModal = true"
                class="py-2 px-4 bg-red-600 text-white rounded-md dark:bg-red-800 dark:text-white">
          Delete Account
        </button>
      </div>
    </div>
    <ChangePasswordModel :show="showChangePasswordModal" @close="showChangePasswordModal = false"
                         @change-password="handlePasswordChange"/>
    <DeleteAccount :show="showDeleteAccountModal" @close="showDeleteAccountModal = false"
                   @delete-account="handleDeleteAccount"/>
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
}

.settings-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

button {
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.3s ease, transform 0.3s ease;
}

button:hover {
  background-color: #009394;
  transform: translateY(-2px);
}

button:active {
  background-color: #006270;
  transform: translateY(0);
}

.modal {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
}

.modal-content {
  background: white;
  padding: 20px;
  border-radius: 8px;
  text-align: center;
}

.modal-actions {
  display: flex;
  justify-content: space-around;
  margin-top: 20px;
}

.input {
  width: 100%;
  padding: 10px;
  margin-top: 10px;
  border: 1px solid #ccc;
  border-radius: 4px;
}
</style>