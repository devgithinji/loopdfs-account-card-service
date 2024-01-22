package org.loopdfs.accountcardservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.loopdfs.accountcardservice.dto.AccountResponseDto;
import org.loopdfs.accountcardservice.dto.PaginatedResponse;
import org.loopdfs.accountcardservice.mapper.AccountMapper;
import org.loopdfs.accountcardservice.model.Account;
import org.loopdfs.accountcardservice.service.AccountService;
import org.loopdfs.accountcardservice.util.BICGenerator;
import org.loopdfs.accountcardservice.util.IBANGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccountController.class)
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    @Autowired
    private ObjectMapper objectMapper;

    private Account account;
    private Long clientId = 4567L;
    private AccountResponseDto accountResponseDto;

    @BeforeEach
    void setUp() {
        account = new Account(1L, IBANGenerator.getIBAN(), BICGenerator.getBIC(), clientId);

        accountResponseDto = new AccountResponseDto();
        accountResponseDto.setAccountId(account.getAccountId());
        accountResponseDto.setIban(account.getIban());
        accountResponseDto.setBicSwift(account.getBicSwift());
        accountResponseDto.setClientId(clientId);
    }

    @DisplayName("junit test for creating Account")
    @Test
    void givenClientId_whenCreatingAccount_thenReturnAccountResponseDto() throws Exception {
        //given  - precondition or setup
        given(accountService.createAccount(clientId)).willAnswer(invocation -> accountResponseDto);

        //when - action or the behaviour that we are going to test
        ResultActions response = mockMvc.perform(post("/api/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .param("clientId", clientId.toString()));


        //then - verify the output
        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.accountId", is(accountResponseDto.getAccountId().intValue())))
                .andExpect(jsonPath("$.iban", is(accountResponseDto.getIban())))
                .andExpect(jsonPath("$.bicSwift", is(accountResponseDto.getBicSwift())))
                .andExpect(jsonPath("$.clientId", is(accountResponseDto.getClientId().intValue())));

    }


    @DisplayName("junit test for get account")
    @Test
    void givenAccountId_whenGetAccount_thenReturnAccountResponseDto() throws Exception {
        //given  - precondition or setup
        Long accountId = 1L;
        given(accountService.getAccount(accountId)).willAnswer(invocation -> accountResponseDto);
        //when - action or the behaviour that we are going to test
        ResultActions response = mockMvc.perform(get("/api/accounts/{accountId}", accountId.intValue())
                .contentType(MediaType.APPLICATION_JSON));

        //then - verify the output
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountId", is(accountResponseDto.getAccountId().intValue())))
                .andExpect(jsonPath("$.iban", is(accountResponseDto.getIban())))
                .andExpect(jsonPath("$.bicSwift", is(accountResponseDto.getBicSwift())))
                .andExpect(jsonPath("$.clientId", is(accountResponseDto.getClientId().intValue())));
    }


    @DisplayName("junit test for get all accounts")
    @Test
    void givenPageAndSize_whenGetAccounts_thenReturnAccountResponseDtoPaginatedResponse() throws Exception {
        //given  - precondition or setup
        int page = 0;
        int size = 10;
        Account account2 = new Account(2L, IBANGenerator.getIBAN(), BICGenerator.getBIC(), 345L);
        List<Account> accounts = List.of(account, account2);

        List<AccountResponseDto> accountResponseDtos = accounts.stream()
                .map(AccountMapper::accountToAccountResponseDto)
                .collect(Collectors.toList());

        PaginatedResponse<AccountResponseDto> paginatedResponse = new PaginatedResponse<>(
                accountResponseDtos,
                1,
                accountResponseDtos.size(),
                true,
                size,
                page,
                true,
                accountResponseDtos.size()
        );
        given(accountService.getAllAccounts(page, size)).willAnswer(invocationOnMock -> paginatedResponse);

        //when - action or the behaviour that we are going to test
        ResultActions response = mockMvc.perform(get("/api/accounts").contentType(MediaType.APPLICATION_JSON));

        //then - verify the output
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.size()", is(accountResponseDtos.size())));

    }


    @DisplayName("junit test for account update")
    @Test
    void givenAccountIdAndClientId_whenUpdateAccount_thenReturnAccountResponseDto() throws Exception {
        //given  - precondition or setup
        Long accountId = 1L;
        Long newClientId = 3578L;

        accountResponseDto.setClientId(newClientId);
        given(accountService.updateAccount(accountId, newClientId)).willReturn(accountResponseDto);

        //when - action or the behaviour that we are going to test
        ResultActions response = mockMvc.perform(put("/api/accounts/{accountId}", accountId)
                .contentType(MediaType.APPLICATION_JSON)
                .param("clientId", newClientId.toString()));

        //then - verify the output
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.clientId", is(newClientId.intValue())));
    }


    @DisplayName("junit test for account delete ")
    @Test
    void givenAccountId_whenDeleteAccount_thenReturnResponseDto() throws Exception {
        //given  - precondition or setup
        Long accountId = 1L;
        willDoNothing().given(accountService).deleteAccount(accountId);
        //when - action or the behaviour that we are going to test
        ResultActions response = mockMvc.perform(delete("/api/accounts/{accountId}", accountId.intValue()));

        //then - verify the output
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusMsg", is("account deleted successfully")));
    }
}