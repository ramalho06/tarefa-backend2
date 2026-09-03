package org.example.projbd2.exercicio1;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ClienteCadastroServiceTest {

    private final ClienteRepository clienteRepository = mock(ClienteRepository.class);
    private final ClienteCadastroService service = new ClienteCadastroService(clienteRepository);

    @Test
    void deveCadastrarClienteAtivoComDataDeCadastroDefinida() {
        Cliente cliente = new Cliente();
        cliente.setNome("Maria");
        cliente.setEmail("maria@email.com");
        when(clienteRepository.existsByEmail("maria@email.com")).thenReturn(false);
        when(clienteRepository.save(cliente)).thenReturn(cliente);

        Cliente salvo = service.cadastrar(cliente);

        assertThat(salvo.isAtivo()).isTrue();
        assertThat(salvo.getDataCadastro()).isNotNull();
    }

    @Test
    void deveImpedirCadastroComEmailJaExistente() {
        Cliente cliente = new Cliente();
        cliente.setNome("Maria");
        cliente.setEmail("maria@email.com");
        when(clienteRepository.existsByEmail("maria@email.com")).thenReturn(true);

        assertThatThrownBy(() -> service.cadastrar(cliente))
                .isInstanceOf(EmailJaCadastradoException.class)
                .hasMessage("Email já cadastrado");
    }
}
