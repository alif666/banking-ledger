package com.example.demo_app.currency.service;

import com.example.demo_app.currency.entity.CurrencyEntity;

public interface CurrencyService {
    CurrencyEntity lookUpCurrencyCode(String currencyCode);
}
