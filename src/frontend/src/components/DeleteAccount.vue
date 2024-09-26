<template>
  <div v-if="show" class="modal-overlay">
    <div class="modal">
      <h2>Confirm Account Deletion</h2>
      <form @submit.prevent="deleteAccount">
        <div class="form-group">
          <label for="password-for-deletion">Password</label>
          <input type="password" id="password-for-deletion" v-model="passwordForDeletion" required />
        </div>
        <div class="button-group">
          <button type="submit" class="primary-button">Confirm</button>
          <button type="button" class="secondary-button" @click="closeModal">Cancel</button>
        </div>
      </form>
    </div>
  </div>
</template>

<script>
export default {
  name: 'DeleteAccount',
  props: {
    show: {
      type: Boolean,
      required: true
    }
  },
  data() {
    return {
      passwordForDeletion: '',
      id: localStorage.getItem('id')
    };
  },
  methods: {
    async deleteAccount() {
      try {
        this.$emit('delete-account', this.passwordForDeletion);
      } catch (error) {
        alert('Failed to delete account');
      }
    },
    closeModal() {
      this.$emit('close');
    }
  }
};
</script>

<style scoped>
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  transition: opacity 0.3s ease;
}

.modal {
  background: white;
  padding: 30px;
  border-radius: 12px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
  max-width: 400px;
  width: 100%;
  animation: slide-down 0.3s ease;
}

@keyframes slide-down {
  from {
    transform: translateY(-20px);
    opacity: 0;
  }
  to {
    transform: translateY(0);
    opacity: 1;
  }
}

h2 {
  margin-bottom: 20px;
  font-size: 24px;
  color: #333;
}

.form-group {
  margin-bottom: 15px;
}

label {
  display: block;
  margin-bottom: 5px;
  font-weight: bold;
  color: #555;
}

input {
  width: 100%;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 4px;
  font-size: 16px;
}

.button-group {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
}

.primary-button {
  background-color: #007bff;
  color: white;
  padding: 10px 20px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.primary-button:hover {
  background-color: #0056b3;
}

.secondary-button {
  background-color: #6c757d;
  color: white;
  padding: 10px 20px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.secondary-button:hover {
  background-color: #5a6268;
}
</style>