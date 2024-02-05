<!-- src/App.vue -->

<template>
  <div id="app">
    <AgendamentoTransferencia @transferenciaSuccess="addTransferenciaToList" />
    <ExtratoTransferencias :transferencias="transferencias" />
  </div>
</template>

<script>
import AgendamentoTransferencia from './components/AgendamentoTransferencia.vue';
import ExtratoTransferencias from './components/ExtratoTransferencias.vue';
import TransferenciaService from '@/services/TransferenciaService';

export default {
  components: {
    AgendamentoTransferencia,
    ExtratoTransferencias
  },
  data() {
    return {
      transferencias: []
    };
  },
  created() {
    this.carregarTransferencias();
  },
  methods: {
    async carregarTransferencias() {
      try {
        const response = await TransferenciaService.listarTransferencias();
        this.transferencias = response.data;
      } catch (error) {
        console.error('Erro ao buscar transferências:', error);
      }
    },
    addTransferenciaToList(transferencia) {
      this.transferencias.push(transferencia);
    }
  }
};
</script>

<style scoped>

/* Tratamentos específicos para o campo data - INICIO */

input[type="date"] {
 /* -webkit-appearance: none;  Chrome e Safari */
  appearance: none;
  background-color: white;
}
.date-placeholder {
  color: #747474;
}
input::placeholder {
  color: #747474;
}

/* Para garantir a consistência em navegadores que suportam a pseudo-classe ::-webkit-datetime-edit */
input[type="date"]::-webkit-datetime-edit,
input[type="date"]::-webkit-inner-spin-button,
input[type="date"]::-webkit-clear-button {
  font-family: inherit;
  color: #747474;
}

/* Remover a transparência no Mozilla Firefox */
input[type="date"]::-moz-focus-inner {
  border: 0;
}
input[type="date"]:hover,
input[type="date"]:focus {
  color: #000;
}
.date-value {
  color: #000; /* Cor preta para texto quando há valor */
}

#dataTransferencia{
  color:#000000;
}
.date-has-value::-webkit-datetime-edit {
  color: #000; /* Preto */
}


/* Tratamentos específicos para o campo data - FINAL */
</style>