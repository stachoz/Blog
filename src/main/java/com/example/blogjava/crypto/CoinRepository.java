package com.example.blogjava.crypto;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CoinRepository extends CrudRepository<Coin, Long> {
    Optional<Coin> findCoinByName(String name);
}
