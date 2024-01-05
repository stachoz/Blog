package com.example.blogjava.crypto;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CoinRepository extends CrudRepository<Coin, Long> {
    Optional<Coin> findCoinByName(String name);
    boolean existsByName(String name);
}
