package org.example.projbd2.exercicio3;

import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Encontra, entre todas as políticas de frete cadastradas no Spring, a que
 * se aplica a um tipo de cliente. Nenhum {@code if/switch} por tipo de
 * cliente existe aqui ou em qualquer outro lugar - cada política sabe dizer
 * por si mesma se é aplicável.
 */
@Component
public class PoliticaFreteResolver {

    private final List<PoliticaFrete> politicas;

    public PoliticaFreteResolver(List<PoliticaFrete> politicas) {
        this.politicas = politicas;
    }

    public PoliticaFrete resolverPara(TipoCliente tipoCliente) {
        return politicas.stream()
                .filter(politica -> politica.aplicavelPara(tipoCliente))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(
                        "Nenhuma política de frete configurada para o tipo de cliente: " + tipoCliente));
    }
}
