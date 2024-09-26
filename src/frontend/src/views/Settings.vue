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
        <label class="text-md dark:text-white">Delete Account</label>
        <button @click = "showDeleteAccountModel = true"
            class="py-2 px-4 bg-red-500 text-white rounded-md dark:bg-white dark:text-black">
          Delete Account
        </button>
      </div>
      <div class="settings-item mb-4">
        <label class="text-md dark:text-white">Change Password</label>
        <button @click="showChangePasswordModel = true"
                class="py-2 px-4 bg-black text-white rounded-md dark:bg-white dark:text-black">
          Change Password
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
}

.settings-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 1rem;
}

button {
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.3s ease, transform 0.3s ease;
  margin-left: 1rem;
}

button:hover {
  background-color: #009394;
  transform: translateY(-2px);
}

button:active {
  background-color: #006270;
  transform: translateY(0);
}

form {
  width: 100%;
}

input {
  width: 100%;
  margin-bottom: 1rem;
  padding: 0.5rem;
  border: 1px solid #ddd;
  border-radius: 4px;
}
</style>