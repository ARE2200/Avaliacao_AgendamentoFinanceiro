import axios from 'axios';

const API_URL = 'http://localhost:8085/transferencias';

class TransferenciaService {
  agendarTransferencia(transferencia) {
    console.log('Enviando requisição POST para:', API_URL, 'com dados:', transferencia);
    return axios.post(`${API_URL}/agendar`, transferencia);
  }

  listarTransferencias() {
    console.log('Enviando requisição GET para:', API_URL);
    return axios.get(API_URL);
  }

  // Modifique este método para se parecer com os outros, sem usar async/await diretamente
  calcularTaxa(dadosCalculo) {
    console.log('Enviando requisição POST para calcular taxa:', `${API_URL}/calcular-taxa`, 'com dados:', dadosCalculo);
    return axios.post(`${API_URL}/calcular-taxa`, dadosCalculo);
  }
}

export default new TransferenciaService();
