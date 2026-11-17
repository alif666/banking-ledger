package com.example.demo_app.currency.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Entity
@Table(name = "currency")
@Getter
public class CurrencyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull()
    @Pattern(regexp = "[A-Z]{3}")
    @Column(name = "currency_code", unique = true, nullable = false, length = 3)
    private String currencyCode;

    protected CurrencyEntity(){

    }

    public CurrencyEntity(String currencyCode){
        if(currencyCode == null || !currencyCode.matches("[A-Z]{3}")){
            throw new IllegalArgumentException("Currency code must be 3 uppercase letters");
        }
        this.currencyCode = currencyCode;
    }

}
