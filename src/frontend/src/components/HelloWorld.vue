<template>
  <table>
    <thead>
    <tr>
      <th>ID</th>
      <th>Title</th>
      <th>Started On</th>
      <th>Completed On</th>
      <th>Distance</th>
      <th>Location</th>
    </tr>
    </thead>
    <tbody>
    <tr v-for="run in msg" :key="run.id">
      <td>{{ run.id }}</td>
      <td>{{ run.title }}</td>
      <td>{{ run.startedOn }}</td>
      <td>{{ run.completedOn }}</td>
      <td>{{ run.distance }}</td>
      <td>{{ run.location }}</td>
    </tr>
    </tbody>
  </table>
</template>

<script>
export default {
  name: 'HelloWorld',
  data() {
    return {
      msg: []
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

<style scoped>
table {
  width: 10%;
  border-collapse: collapse;
}

th, td {
  border: 1px solid #ddd;
  padding: 8px;
}

tr:nth-child(even) {
  background-color: #f2f2f2;
}

th {
  padding-top: 12px;
  padding-bottom: 12px;
  text-align: left;
  background-color: #4CAF50;
  color: white;
}
</style>