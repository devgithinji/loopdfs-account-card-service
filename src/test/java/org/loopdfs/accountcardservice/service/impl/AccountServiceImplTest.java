package org.loopdfs.accountcardservice.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.loopdfs.accountcardservice.dto.AccountResponseDto;
import org.loopdfs.accountcardservice.dto.PaginatedResponse;
import org.loopdfs.accountcardservice.exception.ResourceNotFoundException;
import org.loopdfs.accountcardservice.model.Account;
import org.loopdfs.accountcardservice.repository.AccountRepo;
import org.loopdfs.accountcardservice.util.BICGenerator;
import org.loopdfs.accountcardservice.util.IBANGenerator;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {

    @Mock
    private AccountRepo accountRepo;
    @InjectMocks
    private AccountServiceImpl accountService;
    private Account account;


    @BeforeEach
    void setUp() {
        account = new Account(1L, IBANGenerator.getIBAN(), BICGenerator.getBIC(), 5678L);
    }

    @DisplayName("junit test for create account")
    @Test
    void givenAccountObject_whenCreateAccount_thenReturnAccountObject() {
        //given  - precondition or setup
        Long clientId = 5678L;
        // account.setAccountId(clientId);
        given(accountRepo.save(any(Account.class))).willReturn(account);

        //when - action or the behaviour that we are going to test
        AccountResponseDto accountResponseDto = accountService.createAccount(clientId);

        //then - verify the output
        assertNotNull(accountResponseDto);
        assertEquals(accountResponseDto.getClientId(), clientId);
    }


    @DisplayName("junit test for get account")
    @Test
    void givenAccountId_whenGetAccount_thenReturnAccount() {
        //given  - precondition or setup
        Long accountId = 1L;
        given(accountRepo.findById(accountId)).willReturn(Optional.of(account));

        //when - action or the behaviour that we are going to test
        AccountResponseDto accountResponseDto = accountService.getAccount(accountId);

        //then - verify the output
        assertNotNull(accountResponseDto);
        assertEquals(accountResponseDto.getAccountId(), accountId);
    }


    @DisplayName("junit test for get account not found")
    @Test
    void givenAccountId_whenGetAccount_thenThrowResourceNotFound() {
        //given  - precondition or setup
        Long accountId = 1L;
        given(accountRepo.findById(accountId)).willReturn(Optional.empty());

        //when - action or the behaviour that we are going to test
        assertThrows(ResourceNotFoundException.class, () -> {
            accountService.getAccount(accountId);
        });
    }


    @DisplayName("junit test for get all accounts")
    @Test
    void givenAccountsList_whenGetAllAccounts_thenReturnAccountsList() {
        //given  - precondition or setup
        Account accTwo = new Account(2L, IBANGenerator.getIBAN(), BICGenerator.getBIC(), 7890L);

        List<Account> accounts = List.of(account, accTwo);

        int page = 0;
        int size = 10;

        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Account> mockPage = new PageImpl<>(accounts, pageRequest, accounts.size());

        given(accountRepo.findAll(pageRequest)).willReturn(mockPage);

        //when - action or the behaviour that we are going to test
        PaginatedResponse<AccountResponseDto> accountResponseDtoPaginatedResponse = accountService.getAllAccounts(page, size);

        //then - verify the output
        assertEquals(accountResponseDtoPaginatedResponse.getContent().size(), accounts.size());
    }


    @DisplayName("junit test for update account")
    @Test
    void givenAccount_whenUpdateAccount_thenReturnAccount() {
        //given  - precondition or setup
        Long accountId = 1L;
        Long newClientId = 8976L;
        given(accountRepo.findById(accountId)).willReturn(Optional.of(account));
        Account newAccount = new Account(account.getAccountId(), account.getIban(), account.getBicSwift(), newClientId);
        given(accountRepo.save(any(Account.class))).willReturn(newAccount);

        //when - action or the behaviour that we are going to test
        AccountResponseDto accountResponseDto = accountService.updateAccount(accountId, newClientId);

        //then - verify the output
        assertEquals(accountResponseDto.getClientId(), newClientId);
    }


    @DisplayName("junit test for delete account")
    @Test
    void givenAccountId_whenDeleteAccount_thenAccountShouldBeDeleted() {
        //given  - precondition or setup
        Long accountId = 1L;
        given(accountRepo.findById(accountId)).willReturn(Optional.of(account));
        willDoNothing().given(accountRepo).deleteById(accountId);

        //when - action or the behaviour that we are going to test
        accountService.deleteAccount(accountId);

        //then - verify the output
        verify(accountRepo, times(1)).deleteById(accountId);
    }
}