package com.example.demo_app.transfer.controller;

import com.example.demo_app.account.service.AccountService;
import com.example.demo_app.transfer.dto.MoneyTransferRequest;
import com.example.demo_app.transfer.dto.MoneyTransferResponse;
import com.example.demo_app.transfer.entity.MoneyTransfer;
import com.example.demo_app.transfer.service.MoneyTransferService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/transfer")
@RequiredArgsConstructor
public class MoneyTransferController {

    private final MoneyTransferService moneyTransferService;
    private final AccountService accountService;


    @PostMapping
    ResponseEntity<MoneyTransferResponse> transferBalance(
            @RequestBody @Valid MoneyTransferRequest request,
            @RequestHeader("Idempotency-Key") String idempotencyKey) {
        MoneyTransfer moneyTransfer = moneyTransferService.transfer(
                request.fromAccountNumber(),
                request.toAccountNumber(),
                request.amountToTransfer(),
                UUID.fromString(idempotencyKey));

        MoneyTransferResponse moneyTransferResponse = new MoneyTransferResponse(
                moneyTransfer.getId(),
                moneyTransfer.getTransferId(),
                moneyTransfer.getIdempotencyKey(),
                accountService.transformAccountToDto(moneyTransfer.getFromAccount()),
                accountService.transformAccountToDto(moneyTransfer.getToAccount()),
                moneyTransfer.getStatus(),
                moneyTransfer.getCreatedAt(),
                moneyTransfer.getAmountToTransfer()
        );


        return ResponseEntity.status(HttpStatus.CREATED).body(moneyTransferResponse);
    }

}
