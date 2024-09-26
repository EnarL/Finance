<template>
  <div class="top-bar">
    <div class="actions">
      <button @click="goToDashboard" class="home-button">Home</button>
      <div class="icons">
        <span @click="toggleDropdown"><img src="@/assets/notification-bell.svg" alt="notification-bell" class="img"></span>
        <div v-if="showDropdown" class="dropdown-menu">
          <div class="notifications">
            <Notification
                v-for="(notification, index) in notifications"
                :key="index"
                :message="notification"
                @close="removeNotification(notification)"
            />
          </div>
          <TransactionList></TransactionList>
        </div>
      </div>
      <UserIcon></UserIcon>
    </div>
    <div class="notifications">
      <Notification
          v-for="(notification, index) in notifications"
          :key="index"
          :message="notification"
          @close="removeNotification(notification)"
      />
    </div>
  </div>
</template>

<script>
import { mapState, mapActions } from 'vuex';
import UserIcon from '@/components/UserIcon.vue';
import TransactionList from "@/components/TransactionList.vue";
import Notification from "@/components/Notification.vue";

export default {
  name: 'TopBar',
  components: {
    UserIcon,
    TransactionList,
    Notification
  },
  data() {
    return {
      showDropdown: false
    };
  },
  computed: {
    ...mapState(['notifications'])
  },
  methods: {
    ...mapActions(['toggleTheme', 'removeNotification']),
    toggleDropdown() {
      this.showDropdown = !this.showDropdown;
    },
    goToDashboard() {
      this.$router.push('/dashboard');
    }
  }
};
</script>

<style scoped>
.top-bar {
  display: flex;
  justify-content: flex-end;
  align-items: center;
  background-color: #007bff;
  padding: 10px 20px;
  color: white;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.actions {
  display: flex;
  align-items: center;
}

.home-button {
  background: none;
  border: none;
  color: white;
  font-weight: bold;
  cursor: pointer;
  margin-right: 20px;
}

.home-button:hover {
  text-decoration: underline;
}

.icons {
  position: relative;
  padding: 5px 10px;
  border-radius: 50px;
  font-weight: bold;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  display: flex;
  align-items: center;
  gap: 10px;
  cursor: pointer;
  margin-right: 20px; /* Add margin to create space */
}

.img {
  width: 25px; /* Adjust the size as needed */
  height: 25px; /* Adjust the size as needed */
  margin-left: 10px;
}

button {
  background: none;
  border: none;
  cursor: pointer;
  font-weight: bold;
  margin: 0 10px;
}

button:hover {
  text-decoration: underline;
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
  max-height: 300px; /* Optional: Set a max height */
  overflow-y: auto; /* Enable scrolling if content exceeds max height */
  padding: 10px; /* Add padding for better spacing */
  width: 300px; /* Optional: Set a fixed width */
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

.notifications {
  position: fixed;
  top: 10px;
  right: 10px;
  z-index: 1000;
}
</style>