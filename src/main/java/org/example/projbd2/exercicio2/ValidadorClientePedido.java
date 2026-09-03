package org.example.projbd2.exercicio2;

import org.springframework.stereotype.Component;

/** Responsabilidade única: decidir se um cliente pode fazer pedidos. */
@Component
public class ValidadorClientePedido {

    public void validarElegivel(Cliente cliente) {
        if (!cliente.isAtivo()) {
            throw new ClienteInativoException();
        }
    }
}
