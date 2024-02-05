<template>
  <AgendamentoComponente @transferenciaSuccess="handleTransferenciaSuccess" />
  <div class="container">
    <h2>Agendar Transferência</h2>
    <form @submit.prevent="preAgendar">
      <div class="field">
        <input
          id="contaOrigem"
          v-model.trim="transferencia.contaOrigem"
          type="text"
          placeholder="Conta Origem (10 dígitos)"
          @input="validateNumericInput('contaOrigem')"
          maxlength="10"
        />
        <div v-if="validationMessages.contaOrigem" class="validation-message">
          {{ validationMessages.contaOrigem }}
        </div>
      </div>
      <div class="field">
        <input
          id="contaDestino"
          v-model.trim="transferencia.contaDestino"
          type="text"
          placeholder="Conta Destino (10 dígitos)"
          @input="validateNumericInput('contaDestino')"
          maxlength="10"
        />
        <div v-if="validationMessages.contaDestino" class="validation-message">
          {{ validationMessages.contaDestino }}
        </div>
      </div>
      <div class="field">
        <input
          id="dataTransferencia"
          v-model="transferencia.dataTransferencia"
          type="date"
          @blur="validateDate"
          @change="updateDateInputStyle" 
          :class="{'date-has-value': transferencia.dataTransferencia}" 
        />
        <div v-if="validationMessages.dataTransferencia" class="validation-message">
          {{ validationMessages.dataTransferencia }}
        </div>
      </div>
      <div class="field">
        <input
          id="valor"
          v-model="transferencia.valor"
          type="text"
          placeholder="Valor"
          @input="validateCaracterValue"
        />
        <div v-if="validationMessages.valor" class="validation-message">
          {{ validationMessages.valor }}
        </div>
      </div>
      <!--
      <div v-if="taxaCalculada && valorDebitado" class="taxa-info">
        <p>Taxa: R$ {{ taxaCalculada }}</p>
        <p>Valor Debitado (a ser transferido): R$ {{ valorDebitado }}</p>
      </div>
      -->
      <div v-if="errorMessage" class="alert-message">{{ errorMessage }}</div>
      <button type="submit" @click="calcularTaxa">CALCULAR TAXA</button>
    </form>

    <div v-if="showModal" class="modal-overlay" @click="closeModal">
      <div class="modal">
        <p>Confirmar agendamento com os seguintes valores:</p>
        <p>Taxa: R$ {{ taxaCalculada }}</p>
        <p>Valor Debitado: R$ {{ valorDebitado }}</p>

        <div
          class="botoesModal"
          style="display: flex; align-content: center; justify-content: space-evenly"
        >
          <div class="btnOkAgend">
            <button @click="confirmAgendar">OK</button>
          </div>
          <div class="btnCancelarAgend">
            <button @click="closeModal">Cancelar</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import TransferenciaService from "@/services/TransferenciaService";
//import { eventBus } from '@/eventBus.js'; 

export default {
  data() {
    return {
      transferencia: {
        contaOrigem: "",
        contaDestino: "",
        valor: "",
        dataTransferencia: "",
        taxaCalculada: null,
        valorDebitado: null,
        errorMessage: "",
      },
      validationMessages: {
        contaOrigem: "",
        contaDestino: "",
        valor: "",
        dataTransferencia: "",
      },
      submitAttempted: false,
      showModal: false,
    };
  },
  computed: {
    isFormValid() {
      return (
        this.transferencia.contaOrigem.length === 10 &&
        this.transferencia.contaDestino.length === 10 &&
        this.transferencia.contaOrigem !== this.transferencia.contaDestino &&
        this.isValidValue(this.transferencia.valor) &&
        this.transferencia.dataTransferencia
      );
    },
  },
  methods: {
    validateNumericInput(field) {
      this.transferencia[field] = this.transferencia[field].replace(/\D/g, "");
      this.validationMessages[field] =
        this.transferencia[field].length !== 10
          ? "Deve conter 10 caracteres numéricos."
          : "";
    },
    validateCaracterValue(event) {
      const validChars = /[^0-9.,]/g;
      event.target.value = event.target.value.replace(validChars, "");
      this.validationMessages.valor = "";
    },
    validateDate() {
      const selectedDate = new Date(this.transferencia.dataTransferencia);
      const currentDate = new Date();
      currentDate.setHours(0, 0, 0, 0);
      if (selectedDate < currentDate) {
        this.validationMessages.dataTransferencia =
          "A data não pode ser menor que a data atual.";
        this.transferencia.dataTransferencia = "";
      } else {
        this.validationMessages.dataTransferencia = "";
      }
    },
    updateDateInputStyle(event) {
      const hasValue = event.target.value.length > 0;
      event.target.classList.toggle("date-value", hasValue);
      event.target.classList.toggle("date-placeholder", !hasValue);
    },
    formatValueOnBlur() {
      if (this.transferencia.valor) {
        this.transferencia.valor = parseFloat(this.transferencia.valor).toFixed(2);
      }
    },
    isValidValue(value) {
      const numValue = Number(value);
      return !isNaN(numValue) && numValue > 0;
    },
    //onBlurValor() {
    //if (this.transferencia.valor && this.transferencia.dataTransferencia) {
    //  this.calcularTaxaEValorDebitado();
    //}
    //},
    async calcularTaxaEValorDebitado() {
      try {
        const response = await TransferenciaService.calcularTaxa({
          valor: this.transferencia.valor,
          dataTransferencia: this.transferencia.dataTransferencia,
        });
        if (response.data.taxa === 0) {
          throw new Error("Período inválido para transferência.");
        }

        this.taxaCalculada = response.data.taxa;
        this.valorDebitado = (
          parseFloat(this.transferencia.valor) - parseFloat(response.data.taxa)
        ).toFixed(2);
        this.transferencia.valor = this.valorDebitado;
        this.errorMessage = ""; 
        this.showModal = true; 
      } catch (error) {
        this.errorMessage = "Transação fora dos padrões aceitos para transferência. A transação não foi efetuada."; // Mensagem de erro específica
        alert(this.errorMessage);
        this.showModal = false; 
      }
    },

    preAgendar() {
      if (this.isFormValid) {
        this.calcularTaxaEValorDebitado(); 
      }
    },
    confirmAgendar() {
      this.agendar();
      this.showModal = false;
    },
    cancelAgendar() {
      this.showModal = false;
    },
    closeModal() {
      this.showModal = false;
    },
async agendar() {
  this.submitAttempted = true;
  if (!this.isFormValid) {
    return;
  }
  try {
    const contaOrigem = { numero: this.transferencia.contaOrigem };
    const contaDestino = { numero: this.transferencia.contaDestino };
    const response = await TransferenciaService.agendarTransferencia({
      contaOrigem,
      contaDestino,
      valor: this.transferencia.valor,
      dataTransferencia: this.transferencia.dataTransferencia,
    });
    console.log("Resposta do servidor:", response);
    alert("Transferência agendada com sucesso!");

    this.$emit('transferenciaSuccess', response.data)
    //eventBus.emit('atualizarExtrato');
    
    this.resetForm();
  } catch (error) {
    console.error("Erro ao agendar transferência:", error);
    alert("Falha ao agendar transferência. Verifique os dados e tente novamente.");
  } finally {
    this.submitAttempted = false;
  }
},

    resetForm() {
      this.transferencia = {
        contaOrigem: "",
        contaDestino: "",
        valor: "",
        dataTransferencia: "",
        taxaCalculada: null,
        valorDebitado: null,
      };
      this.validationMessages = {
        contaOrigem: "",
        contaDestino: "",
        valor: "",
        dataTransferencia: "",
      };
      this.showModal = false;
    },
  },
};
</script>
<style scoped>

.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.6);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
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

.container {
  max-width: 710px;
  margin: 0 auto;
  padding: 20px;
  background: #f9f9f9 url("../assets/pen_big.jpg") no-repeat center center;
  background-size: cover;
  border-radius: 6px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  transition: background 0.4s ease-in-out;
  margin-top: 20px;
}

.container:hover {
  background-image: url("../assets/pen_big_hover.jpg");
}

h2 {
  text-align: center;
  margin-bottom: 40px;
  color: #333;
  font-weight: normal;
}
form {
  display: flex;
  flex-direction: column;
  gap: 10px;
}
input,
button {
  padding: 12px;
  border: 1px solid #ddd;
  border-radius: 4px;
  font-size: 1em;
  color:#594109
}

button {
  background-color: #b9902ec2;
  color: ghostwhite;
  font-weight: bold;
  border: none;
  margin-top: 10px;
  cursor: pointer;
  transition: background-color 0.2s ease-in-out;
}
button:hover {
  background-color: #b9902edb;
  outline: none;
  border: none;
}

input {
  width: 50%;
}
input:focus {
  border: 1px solid #1c1d22;
  outline: none;
}

.alert-message {
  color: red;
  font-size: 0.9em;
  margin-top: 10px;
  background: #ffffffa1;
  padding: 5px;
}
.validation-message {
  color: #ff3860;
  font-size: 0.9em;
  margin-top: 4px;
}

@media (max-width: 768px) {
  .container {
    max-width: 95%;
    padding: 15px;
  }
  h2 {
    font-size: 1.5rem;
  }
  input,
  button {
    font-size: 0.9rem;
  }
}

@media (max-width: 480px) {
  form {
    gap: 5px;
  }

  input,
  button {
    padding: 10px;
    font-size: 0.8rem;
  }
}

/* Tratamentos específicos para o campo data - INICIO */

input[type="date"] {
 /* -webkit-appearance: none;  Chrome e Safari */
  appearance: none;
}
.date-placeholder {
  color: #473306;
}
input::placeholder {
  color: #473306;
}

/* Para garantir a consistência em navegadores que suportam a pseudo-classe ::-webkit-datetime-edit */
input[type="date"]::-webkit-datetime-edit,
input[type="date"]::-webkit-inner-spin-button,
input[type="date"]::-webkit-clear-button {
  font-family: inherit;
  color: #473306;
}

/* Remover a transparência no Mozilla Firefox */
input[type="date"]::-moz-focus-inner {
  border: 0;
}
input[type="date"]:hover,
input[type="date"]:focus {
  color: #473306;
}
.date-value {
  color: #000; /* Cor preta para texto quando há valor */
  color: #473306;
}

#dataTransferencia{
  color:#000000;
  color: #473306;
}
.date-has-value::-webkit-datetime-edit {
  color: #473306; 
}


/* Tratamentos específicos para o campo data - FINAL */
</style>
