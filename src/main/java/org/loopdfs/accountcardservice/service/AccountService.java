package org.loopdfs.accountcardservice.service;

import org.loopdfs.accountcardservice.dto.AccountResponseDto;
import org.loopdfs.accountcardservice.dto.PaginatedResponse;

public interface AccountService {
    AccountResponseDto createAccount(Long clientId);

    AccountResponseDto getAccount(Long accountId);

    PaginatedResponse<AccountResponseDto> getAllAccounts(int page, int size);

    AccountResponseDto updateAccount(Long accountId, Long clientId);

    void deleteAccount(Long accountId);
}
