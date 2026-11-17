package com.example.demo_app.account.entity;

import com.example.demo_app.account.exception.AccountNotPositiveException;
import com.example.demo_app.account.exception.InsufficientFundsException;
import com.example.demo_app.auth.entity.UserEntity;
import com.example.demo_app.currency.entity.CurrencyEntity;
import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "account")
@Getter
public class Account {

    @Version
    @Column(name="version", nullable = false)
    private Long version;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_number", nullable = false, unique = true, length = 34)
    private String accountNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "currency_id", nullable = false)
    private CurrencyEntity currency;

    @Column(name = "balance", precision = 19, scale = 4, nullable = false)
    private BigDecimal balance = BigDecimal.ZERO;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity userEntity;

    @Column(name = "account_type", nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    private AccountType accountType;


    @PrePersist
    protected void initializeCreatedAt() {
        if (createdAt == null) {
            createdAt = Instant.now();

        }
        if (updatedAt == null) {
            updatedAt = Instant.now();
        }
    }


    @PreUpdate
    protected void initializeUpdatedAt() {
        updatedAt = Instant.now();
    }

    protected Account() {

    }

    public Account(String accountNumber, CurrencyEntity currency, UserEntity userEntity, AccountType accountType) {
        this.accountNumber = accountNumber;
        this.currency = currency;
        this.userEntity = userEntity;
        this.accountType = accountType;
    }

    public void debit(BigDecimal amount) {
        if (amount == null || amount.signum() <= 0) {
            throw new AccountNotPositiveException();
        }
        if (balance.compareTo(amount) < 0) {
            throw new InsufficientFundsException();
        }
        balance = balance.subtract(amount);
    }

    public void credit(BigDecimal amount) {
        if (amount == null || amount.signum() <= 0) {
            throw new AccountNotPositiveException();
        }
        balance = balance.add(amount);
    }
}
