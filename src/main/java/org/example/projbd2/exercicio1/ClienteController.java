package org.example.projbd2.exercicio1;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller = fronteira HTTP.
 * <p>
 * Antes: validava campos, checava duplicidade de e-mail, definia estado
 * inicial e persistia - tudo no mesmo método.
 * Depois: apenas recebe a requisição, delega a regra de negócio ao serviço
 * e traduz o resultado em uma resposta HTTP. Os erros de negócio viram
 * HTTP 400 automaticamente via {@code ApiExceptionHandler}.
 */
@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteCadastroService clienteCadastroService;

    public ClienteController(ClienteCadastroService clienteCadastroService) {
        this.clienteCadastroService = clienteCadastroService;
    }

    @PostMapping
    public ResponseEntity<Cliente> cadastrar(@Valid @RequestBody Cliente cliente) {
        Cliente salvo = clienteCadastroService.cadastrar(cliente);
        return ResponseEntity.ok(salvo);
    }
}
