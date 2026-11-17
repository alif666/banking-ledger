package com.example.demo_app.currency.service.impl;

import com.example.demo_app.currency.entity.CurrencyEntity;
import com.example.demo_app.currency.exception.UnsupportedCurrencyCodeException;
import com.example.demo_app.currency.repository.CurrencyRepository;
import com.example.demo_app.currency.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CurrencyServiceImpl implements CurrencyService {

    private final CurrencyRepository currencyRepository;

    @Override
    @Transactional(readOnly = true)
    public CurrencyEntity lookUpCurrencyCode(String currencyCode) {
        return currencyRepository.findByCurrencyCode(currencyCode).orElseThrow(()-> new UnsupportedCurrencyCodeException());
    }
}
