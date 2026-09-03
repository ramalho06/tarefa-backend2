package org.example.projbd2.exercicio1;

import org.springframework.stereotype.Service;

/**
 * Caso de uso "cadastrar cliente".
 * <p>
 * Única responsabilidade: aplicar a regra de negócio do cadastro (e-mail não
 * pode se repetir) e coordenar a persistência. A validação de formato dos
 * campos (nome/e-mail em branco) foi delegada ao Bean Validation, que atua
 * antes mesmo de a requisição chegar aqui - "forma dos dados" e "regra de
 * negócio" são responsabilidades diferentes e agora vivem em lugares
 * diferentes (SRP).
 */
@Service
public class ClienteCadastroService {

    private final ClienteRepository clienteRepository;

    public ClienteCadastroService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public Cliente cadastrar(Cliente cliente) {
        if (clienteRepository.existsByEmail(cliente.getEmail())) {
            throw new EmailJaCadastradoException();
        }

        cliente.definirEstadoInicial();

        return clienteRepository.save(cliente);
    }
}
