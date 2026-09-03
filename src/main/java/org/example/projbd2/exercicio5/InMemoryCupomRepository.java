package org.example.projbd2.exercicio5;

import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryCupomRepository implements CupomRepository {

    private final Map<String, Cupom> cupons = new ConcurrentHashMap<>();

    @Override
    public Optional<Cupom> findByCodigo(String codigo) {
        return Optional.ofNullable(cupons.get(codigo));
    }

    public void salvar(Cupom cupom) {
        cupons.put(cupom.getCodigo(), cupom);
    }
}
