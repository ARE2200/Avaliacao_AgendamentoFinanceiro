
 <!-- 
<template>
  <div class="modal-overlay" @click.self="$emit('cancel')">
    <div class="modal">
      <p>Confirmar agendamento com os seguintes valores:</p>
      <p>Taxa: R$ {{ taxa }}</p>
      <p>Valor Debitado: R$ {{ valorDebitado }}</p>
      <button @click="$emit('confirm')">OK</button>
      <button @click="$emit('cancel')">Cancelar</button>
    </div>
  </div>
</template>
-->

<template>
  <!-- Previa -->
  <div v-if="showModal" class="modal-overlay">
    <div class="modal">
      <p>Confirmar agendamento com os seguintes valores:</p>
      <p>Taxa: R$ {{ taxaCalculada }}</p>
      <p>Valor Debitado: R$ {{ valorDebitado }}</p>
      <button @click="confirmAgendar">OK</button>
      <button @click="cancelAgendar">Cancelar</button>
    </div>
  </div>
</template>


<script>

export default {
  props: {
    taxa: Number,
    valorDebitado: Number
  },

  methods: {

    preAgendar() {
      if (this.isFormValid) {
        this.calcularTaxaEValorDebitado();
        this.showModal = true;
      } else {
      }
    },
    confirmAgendar() {
      this.agendar();
      this.showModal = false;
    },
    cancelAgendar() {
      this.showModal = false;
    },
    async agendar() {
      
    },
  }
};
</script>

<style>
/* Estilos para o modal-overlay e modal */
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
}


.modal {
  background: #fff;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2);
  z-index: 1001;
  display: block !important;
  width: 325px;
  height: 245px;
  position: sticky;
}

</style>
