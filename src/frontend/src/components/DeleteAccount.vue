<template>
  <div v-if="show" class="modal-overlay">
    <div class="modal">
      <h2>Confirm Account Deletion</h2>
      <p>Please enter your password to confirm:</p>
      <input type="password" v-model="passwordForDeletion" class="input" />
      <div class="modal-actions">
        <button @click="deleteAccount" class="py-2 px-4 bg-red-600 text-white rounded-md">Confirm</button>
        <button @click="closeModal" class="py-2 px-4 bg-gray-600 text-white rounded-md">Cancel</button>
      </div>
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
      const clientsId = localStorage.getItem('id');
      try {
        const response = await fetch(`http://localhost:8080/api/clients/delete/${clientsId}`, {
          method: 'DELETE',
          headers: {
            'Authorization': `Bearer ${this.passwordForDeletion}`
          }
        });
        if (response.ok) {
          localStorage.removeItem('id');
          localStorage.removeItem('username');
          await this.$router.push('/');
        } else {
          throw new Error('Network response was not ok.');
        }
      } catch (error) {
        console.error('Error deleting account:', error);
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