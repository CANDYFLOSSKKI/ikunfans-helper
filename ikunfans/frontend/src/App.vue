<template>
  <div id="app">
    <router-view/>
  </div>
</template>

<script setup>
import {onMounted} from "vue";
onMounted(() => {
  document.getElementById('loadingPage').remove()
});
</script>
<style>
@import "@/style/app.css";
</style>
