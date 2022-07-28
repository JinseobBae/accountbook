<template>
  <div class="modal">
    <div class="overlay" @click="$emit('close-modal')"></div>
    <div class="modal-card">
      <slot />
    </div>
  </div>
</template>

<script>
export default {
  // name: "CategoryAddModal"

}
</script>

<style scoped>
/* Modal */
.modal{
  display: flex;
  align-content: center;
}
.overlay {
  width: 100%;
  height: 100%;
  position: fixed;
  left: 0;
  top: 0;
}
.overlay {
  opacity: 0.5;
  background-color: black;
  z-index: 3;
}
.modal-card {
  position: fixed;
  /*left: 35vw;*/
  /*top : 10vh;*/
  left: 0;
  right: 0;
  top: 0;
  bottom: 0;
  min-width: 30%;
  max-width: 50%;
  margin: 30px auto;
  padding: 20px;
  background-color: white;
  min-height: 5vh;
  max-height: 100%;
  z-index: 10;
  opacity: 1;
}
</style>