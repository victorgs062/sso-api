package br.com.mtashop.sso_api.util;

import br.com.mtashop.sso_api.repository.UsuarioRepository;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;

@Component
public class PublicIdGenerator {

    private final UsuarioRepository repository;

    public PublicIdGenerator(UsuarioRepository repository) {
        this.repository = repository;
    }

    public Integer generateUniquePublicId() {
        for (int attempts = 0; attempts < 20; attempts++) {
            int candidate = ThreadLocalRandom.current().nextInt(10_000, Integer.MAX_VALUE);
            if (!repository.existsByPublicId(candidate)) {
                return candidate;
            }
        }
        int candidate = 10_000;
        while (repository.existsByPublicId(candidate)) candidate++;
        return candidate;
    }
}