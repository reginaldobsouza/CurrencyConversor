package service; // Deve estar no mesmo pacote que a classe testada, mas em src/test/java

import api.APIClient;
import model.Conversao;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class) // Para habilitar anotações do Mockito como @Mock
class ConversorServiceTest {

    // MockedStatic é usado para mockar métodos estáticos.
    // Deve ser gerenciado com try-with-resources em cada teste ou com @BeforeEach/@AfterEach.
    private MockedStatic<APIClient> apiClientMockedStatic;

    @BeforeEach
    void setUp() {
        // Inicializa o mock para métodos estáticos da APIClient antes de cada teste
        apiClientMockedStatic = Mockito.mockStatic(APIClient.class);
    }

    @AfterEach
    void tearDown() {
        // Fecha o mock estático após cada teste para evitar interferência entre testes
        if (apiClientMockedStatic != null) {
            apiClientMockedStatic.close();
        }
    }

    @Test
    @DisplayName("Deve converter com sucesso quando API retorna dados válidos")
    void converter_Sucesso() {
        // Arrange
        String moedaBase = "USD";
        String moedaDestino = "BRL";
        double valor = 100.0;
        double taxaEsperada = 5.0;
        double valorConvertidoEsperado = valor * taxaEsperada;

        JSONObject ratesJson = new JSONObject().put(moedaDestino, taxaEsperada);
        JSONObject apiResponseJson = new JSONObject().put("rates", ratesJson).put("base", moedaBase);

        // Configura o mock da APIClient para retornar o JSON esperado
        when(APIClient.obterTaxaCambio(moedaBase)).thenReturn(Optional.of(apiResponseJson));

        // Act
        Optional<Conversao> resultadoOpt = ConversorService.converter(moedaBase, moedaDestino, valor);

        // Assert
        assertTrue(resultadoOpt.isPresent(), "Conversão deveria ser bem-sucedida.");
        Conversao resultado = resultadoOpt.get();
        assertEquals(moedaBase, resultado.getMoedaBase());
        assertEquals(moedaDestino, resultado.getMoedaDestino());
        assertEquals(valor, resultado.getValorOriginal());
        assertEquals(valorConvertidoEsperado, resultado.getValorConvertido(), 0.001); // Delta para comparação de doubles
    }

    @Test
    @DisplayName("Deve retornar Optional vazio se API falhar")
    void converter_FalhaAPI() {
        // Arrange
        when(APIClient.obterTaxaCambio(anyString())).thenReturn(Optional.empty());

        // Act
        Optional<Conversao> resultadoOpt = ConversorService.converter("USD", "BRL", 100.0);

        // Assert
        assertFalse(resultadoOpt.isPresent(), "Deveria retornar Optional vazio em caso de falha da API.");
    }

    @Test
    @DisplayName("Deve retornar Optional vazio se moeda destino não existir nas taxas")
    void converter_MoedaDestinoNaoEncontrada() {
        // Arrange
        JSONObject ratesJson = new JSONObject().put("EUR", 0.9); // Moeda destino "JPY" não está aqui
        JSONObject apiResponseJson = new JSONObject().put("rates", ratesJson).put("base", "USD");
        when(APIClient.obterTaxaCambio("USD")).thenReturn(Optional.of(apiResponseJson));

        // Act
        Optional<Conversao> resultadoOpt = ConversorService.converter("USD", "JPY", 100.0);

        // Assert
        assertFalse(resultadoOpt.isPresent(), "Deveria retornar Optional vazio se moeda destino não for encontrada.");
    }

    @Test
    @DisplayName("Deve retornar Optional vazio se objeto 'rates' estiver ausente na resposta da API")
    void converter_ObjetoRatesAusente() {
        // Arrange
        JSONObject apiResponseJson = new JSONObject().put("base", "USD"); // Sem o objeto "rates"
        when(APIClient.obterTaxaCambio("USD")).thenReturn(Optional.of(apiResponseJson));

        // Act
        Optional<Conversao> resultadoOpt = ConversorService.converter("USD", "BRL", 100.0);

        // Assert
        assertFalse(resultadoOpt.isPresent(), "Deveria retornar Optional vazio se 'rates' estiver ausente.");
    }

    @Test
    @DisplayName("Deve retornar Optional vazio para valor de conversão negativo")
    void converter_ValorNegativo() {
        // Act
        Optional<Conversao> resultadoOpt = ConversorService.converter("USD", "BRL", -10.0);
        // Assert
        assertFalse(resultadoOpt.isPresent(), "Não deveria processar valor negativo.");
    }

    @Test
    @DisplayName("Deve converter corretamente para a mesma moeda (taxa 1.0)")
    void converter_MesmaMoeda() {
        // Act
        Optional<Conversao> resultadoOpt = ConversorService.converter("USD", "USD", 100.0);
        // Assert
        assertTrue(resultadoOpt.isPresent());
        resultadoOpt.ifPresent(conv -> {
            assertEquals(100.0, conv.getValorOriginal());
            assertEquals(100.0, conv.getValorConvertido());
            assertEquals("USD", conv.getMoedaBase());
            assertEquals("USD", conv.getMoedaDestino());
        });
    }

    @Test
    @DisplayName("Deve retornar Optional vazio se taxa for zero ou negativa")
    void converter_TaxaInvalida() {
        // Arrange
        JSONObject ratesJson = new JSONObject().put("BRL", 0.0); // Taxa inválida
        JSONObject apiResponseJson = new JSONObject().put("rates", ratesJson).put("base", "USD");
        when(APIClient.obterTaxaCambio("USD")).thenReturn(Optional.of(apiResponseJson));

        // Act
        Optional<Conversao> resultadoOpt = ConversorService.converter("USD", "BRL", 100.0);

        // Assert
        assertFalse(resultadoOpt.isPresent(), "Deveria retornar Optional vazio para taxa inválida.");
    }

    @Test
    @DisplayName("Deve retornar Optional vazio para moeda base nula")
    void converter_MoedaBaseNula() {
        Optional<Conversao> resultadoOpt = ConversorService.converter(null, "BRL", 100.0);
        assertFalse(resultadoOpt.isPresent());
    }

    @Test
    @DisplayName("Deve retornar Optional vazio para moeda destino nula")
    void converter_MoedaDestinoNula() {
        Optional<Conversao> resultadoOpt = ConversorService.converter("USD", null, 100.0);
        assertFalse(resultadoOpt.isPresent());
    }
}