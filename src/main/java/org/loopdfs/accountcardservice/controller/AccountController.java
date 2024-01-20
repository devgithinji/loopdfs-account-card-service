package org.loopdfs.accountcardservice.controller;

import jakarta.validation.constraints.Digits;
import lombok.RequiredArgsConstructor;
import org.loopdfs.accountcardservice.dto.AccountResponseDto;
import org.loopdfs.accountcardservice.dto.PaginatedResponse;
import org.loopdfs.accountcardservice.dto.ResponseDto;
import org.loopdfs.accountcardservice.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/accounts", produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
@Validated
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public AccountResponseDto createAccount(@RequestParam("clientId") @Digits(integer = Integer.MAX_VALUE, fraction = 0, message = "Must be a numeric value") Long clientId) {
        return accountService.createAccount(clientId);
    }

    @GetMapping("/{accountId}")
    public AccountResponseDto getAccount(@PathVariable("accountId") Long accountId) {
        return accountService.getAccount(accountId);
    }

    @GetMapping
    public PaginatedResponse<AccountResponseDto> getAccounts(@RequestParam(name = "page", defaultValue = "0") int page,
                                                             @RequestParam(name = "size", defaultValue = "10") int size) {
        return accountService.getAllAccounts(page, size);
    }

    @PutMapping("/{accountId}")
    public AccountResponseDto updateAccount(@PathVariable("accountId") Long accountId,
                                            @RequestParam("clientId") Long clientId) {
        return accountService.updateAccount(accountId, clientId);
    }

    @DeleteMapping("/{accountId}")
    public ResponseDto deleteAccount(@PathVariable("accountId") Long accountId) {
        accountService.deleteAccount(accountId);
        return new ResponseDto(HttpStatus.OK.toString(), "account deleted successfully");
    }


}
