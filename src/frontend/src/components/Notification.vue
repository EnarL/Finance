
<template>
  <div class="notification" v-if="show">
    {{ message }}
  </div>
</template>

<script>
export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: 'Notification',
  props: {
    message: {
      type: String,
      required: true
    }
  },
  data() {
    return {
      show: true
    };
  },
  mounted() {
    setTimeout(() => {
      this.show = false;
      this.$emit('close');
    }, 3000); // Hide notification after 3 seconds
  }
};
</script>

<style scoped>
.notification {
  background-color: #007bff;
  color: white;
  padding: 10px;
  border-radius: 4px;
  margin-bottom: 10px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}
</style>