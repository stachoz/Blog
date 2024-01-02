package com.example.blogjava.crypto;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Coin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private BigDecimal currentPrice;
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime dateTime;

    public Coin(String name, BigDecimal currentPrice) {
        this.name = name;
        this.currentPrice = currentPrice;
    }

    public Coin(){}

    @PreUpdate
    public void preUpdate(){
        this.dateTime = LocalDateTime.now();
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(BigDecimal currentPrice) {
        this.currentPrice = currentPrice;
    }
}
