<template>
 <h1>{{msg}}</h1>
</template>

<script>
export default {
  name: 'HelloWorld',
  data(){
    return {
      msg: ''
    }
  },
  mounted() {
    fetch("/api/runs/findall")
        .then((response) => response.json())
        .then((data) => {
          this.msg = data;
        })
        .catch((error) => {
          console.error('Error:', error);
        });
  }
}


</script>


