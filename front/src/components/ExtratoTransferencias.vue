<template>
  <div class="container">
    <h2>Extrato de Transferências</h2>
    <table>
      <thead>
        <tr>
          <th>DT AGEND</th>
          <th>DT TRANSF</th>
          <th>CTA ORIG</th>
          <th>CTA DEST</th>
          <th>VALOR</th>
        </tr>
      </thead>
      <tbody>
        <tr
          v-for="(transferencia, index) in transferencias"
          :key="transferencia.id"
          :class="{
            'linha-branca': index % 2 === 0,
            'linha-azul-claro': index % 2 !== 0,
          }"
        >
          <td>{{ formatarData(transferencia.dataAgendamento) }}</td>
          <td>{{ formatarData(transferencia.dataTransferencia) }}</td>
          <td v-if="transferencia.contaOrigem">{{ transferencia.contaOrigem.numero }}</td>
          <td v-if="transferencia.contaDestino">
            {{ transferencia.contaDestino.numero }}
          </td>
          <td>{{ formatarValor(transferencia.valor) }}</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
export default {
  props: ["transferencias"],
  methods: {
    formatarData(dataString) {
      const data = new Date(dataString);
      const userTimezoneOffset = data.getTimezoneOffset() * 60000;
      const adjustedDate = new Date(data.getTime() + userTimezoneOffset);
      return adjustedDate.toLocaleDateString("pt-BR");
    },
    formatarValor(valor) {
      return parseFloat(valor).toLocaleString("pt-BR", {
        style: "currency",
        currency: "BRL",
      });
    },
  },
};
</script>

<style scoped>
.container {
  max-width: 710px;
  margin: 0 auto;
  padding: 20px;
  background: #fff;
  border-radius: 6px;
  box-shadow: 0 0 15px rgba(0, 0, 0, 0.1);
  background-size: cover;
  margin-top: 20px;
}

h2 {
  text-align: center;
  margin-bottom: 20px;
  color: #333;
  font-weight: normal;
}

table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 20px;
  background: url("../assets/extrato.jpg");
  background-size: cover;
}

th,
td {
  padding: 12px;
  border: 1px solid #ddd;
  text-align: left;
  font-size: 0.9rem;
}

thead th {
  background-color: #b9902edb;
  color: white;
  font-size: 1rem;
}

tbody tr:nth-child(odd) {
  background-color: #fcfcfc;
  opacity: 0.9;
}

tbody tr:nth-child(even) {
  background-color: #f8f8f8;
  opacity: 0.9;
}

th,
td {
  color: #333;
}

@media (max-width: 1024px) {
  .container {
    max-width: 90%;
  }
}

@media (max-width: 768px) {
  .container {
    max-width: 95%;
    padding: 10px;
  }

  table {
    font-size: 0.8rem;
  }

  thead th {
    padding: 8px;
    font-size: 0.9rem;
  }
}

@media (max-width: 480px) {
  h2 {
    font-size: 1.2rem;
  }

  th,
  td {
    padding: 8px;
    font-size: 0.7rem;
  }
}
</style>
