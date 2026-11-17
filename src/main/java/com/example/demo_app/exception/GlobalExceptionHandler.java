package com.example.demo_app.exception;

import com.example.demo_app.account.exception.AccountNotFoundException;
import com.example.demo_app.account.exception.AccountNotPositiveException;
import com.example.demo_app.account.exception.AccountOwnershipException;
import com.example.demo_app.account.exception.InsufficientFundsException;
import com.example.demo_app.auth.exception.DefaultRoleNotFoundException;
import com.example.demo_app.auth.exception.EmailAlreadyRegisteredException;
import com.example.demo_app.auth.exception.UserNotFoundException;
import com.example.demo_app.currency.exception.UnsupportedCurrencyCodeException;
import com.example.demo_app.dto.ResponseDto;
import com.example.demo_app.transfer.exception.*;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<ResponseDto> handleAccountNotFoundException(AccountNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ResponseDto(
                        404,
                        exception.getMessage(),
                        Instant.now()
                ));
    }
    @ExceptionHandler(AccountSourceNotValidException.class)
    public ResponseEntity<ResponseDto> handleAccountSourceNotValidException(AccountSourceNotValidException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ResponseDto(
                        409,
                        exception.getMessage(),
                        Instant.now()
                ));
    }
    @ExceptionHandler(AccountNotActiveException.class)
    public ResponseEntity<ResponseDto> handleAccountNotActiveException(AccountNotActiveException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ResponseDto(
                        409,
                        exception.getMessage(),
                        Instant.now()
                ));
    }
    @ExceptionHandler(AccountNotPositiveException.class)
    public ResponseEntity<ResponseDto> handleAccountNotPositiveException(AccountNotPositiveException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ResponseDto(
                        400,
                        exception.getMessage(),
                        Instant.now()
                ));
    }
    @ExceptionHandler(InsufficientFundsException.class)
    public ResponseEntity<ResponseDto> handleInsufficientFundException(InsufficientFundsException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ResponseDto(
                        409,
                        exception.getMessage(),
                        Instant.now()
                ));
    }
    @ExceptionHandler(IdempotencyKeyConflictException.class)
    public ResponseEntity<ResponseDto> handleIdempotencyKeyConflictException(IdempotencyKeyConflictException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ResponseDto(
                        409,
                        exception.getMessage(),
                        Instant.now()
                ));
    }
    @ExceptionHandler(AccountOwnershipException.class)
    public ResponseEntity<ResponseDto> handleAccountOwnershipException(Exception exception) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ResponseDto(
                        403,
                        exception.getMessage(),
                        Instant.now()
                ));
    }
    @ExceptionHandler(TransactionMismatchException.class)
    public ResponseEntity<ResponseDto> handleTransactionMismatchException(TransactionMismatchException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ResponseDto(
                        409,
                        exception.getMessage(),
                        Instant.now()
                ));
    }
    @ExceptionHandler(OptimisticLockingFailureException.class)
    public ResponseEntity<ResponseDto> handleOptimisticEntityLockException(OptimisticLockingFailureException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ResponseDto(
                        409,
                        "Optimistic Lock Conflict",
                        Instant.now()
                ));
    }
    @ExceptionHandler(UnsupportedCurrencyMatchBetweenAccountsException.class)
    public ResponseEntity<ResponseDto> handleUnsupportedCurrencyMatchBetweenAccountsException(Exception exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ResponseDto(
                        409,
                        exception.getMessage(),
                        Instant.now()
                ));
    }
    @ExceptionHandler(UnsupportedSameAccountException.class)
    public ResponseEntity<ResponseDto> handleUnsupportedSameAccountException(Exception exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ResponseDto(
                        409,
                        exception.getMessage(),
                        Instant.now()
                ));
    }
    @ExceptionHandler(UnsupportedCurrencyCodeException.class)
    public ResponseEntity<ResponseDto> handleUnsupportedCurrencyCodeException(Exception exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ResponseDto(
                        400,
                        exception.getMessage(),
                        Instant.now()
                ));
    }
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ResponseDto> handleIllegalStateException(IllegalStateException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ResponseDto(
                        400,
                        exception.getMessage(),
                        Instant.now()
                ));
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseDto> handleIllegalArugmentException(IllegalArgumentException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ResponseDto(
                        400,
                        exception.getMessage(),
                        Instant.now()
                ));
    }
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ResponseDto> handleUserNotFoundException(Exception exception) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ResponseDto(
                        401,
                        exception.getMessage(),
                        Instant.now()
                ));
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        String message = exception.getBindingResult().getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ResponseDto(
                        400,
                        message,
                        Instant.now()
                ));
    }
    @ExceptionHandler(EmailAlreadyRegisteredException.class)
    public ResponseEntity<ResponseDto> handleEmailAlreadyRegisteredException(Exception exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ResponseDto(
                        409,
                        exception.getMessage(),
                        Instant.now()
                ));
    }
    @ExceptionHandler(DefaultRoleNotFoundException.class)
    public ResponseEntity<ResponseDto> handleDefaultRoleNotFoundException(Exception exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ResponseDto(
                        409,
                        exception.getMessage(),
                        Instant.now()
                ));
    }

}
