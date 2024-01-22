package org.loopdfs.accountcardservice.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.loopdfs.accountcardservice.model.Account;
import org.loopdfs.accountcardservice.repository.AccountRepo;
import org.loopdfs.accountcardservice.util.BICGenerator;
import org.loopdfs.accountcardservice.util.IBANGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class AccountControllerIT {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private AccountRepo accountRepo;
    @Autowired
    private ObjectMapper objectMapper;

    private Account account;

    private Long clientId = 4567L;

    @BeforeEach
    void setUp() {
        accountRepo.deleteAll();

        account = new Account(1L, IBANGenerator.getIBAN(), BICGenerator.getBIC(), clientId);
    }

    @DisplayName("integration test for creating Account")
    @Test
    void givenClientId_whenCreatingAccount_thenReturnAccountResponseDto() throws Exception {
        //given  - precondition or setup


        //when - action or the behaviour that we are going to test
        ResultActions response = mockMvc.perform(post("/api/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .param("clientId", clientId.toString()));


        //then - verify the output
        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.accountId", CoreMatchers.notNullValue()))
                .andExpect(jsonPath("$.iban", CoreMatchers.notNullValue()))
                .andExpect(jsonPath("$.bicSwift", CoreMatchers.notNullValue()))
                .andExpect(jsonPath("$.clientId", is(clientId.intValue())));

    }

    @DisplayName("integration test for get account")
    @Test
    void givenAccountId_whenGetAccount_thenReturnAccountResponseDto() throws Exception {
        //given  - precondition or setup

        Account savedAccount = accountRepo.save(account);

        //when - action or the behaviour that we are going to test
        ResultActions response = mockMvc.perform(get("/api/accounts/{accountId}", savedAccount.getAccountId().intValue())
                .contentType(MediaType.APPLICATION_JSON));

        //then - verify the output
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountId", is(savedAccount.getAccountId().intValue())))
                .andExpect(jsonPath("$.iban", is(savedAccount.getIban())))
                .andExpect(jsonPath("$.bicSwift", is(savedAccount.getBicSwift())))
                .andExpect(jsonPath("$.clientId", is(savedAccount.getClientId().intValue())));
    }

    @DisplayName("integration test for get all accounts")
    @Test
    void givenPageAndSize_whenGetAccounts_thenReturnAccountResponseDtoPaginatedResponse() throws Exception {
        //given  - precondition or setup
        int page = 0;
        int size = 10;
        Account account2 = new Account(2L, IBANGenerator.getIBAN(), BICGenerator.getBIC(), 345L);
        List<Account> savedAccounts = accountRepo.saveAll(List.of(account, account2));

        //when - action or the behaviour that we are going to test
        ResultActions response = mockMvc.perform(get("/api/accounts").contentType(MediaType.APPLICATION_JSON)
                .param("page", String.valueOf(page))
                .param("size", String.valueOf(size)));

        //then - verify the output
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.size()", is(savedAccounts.size())));

    }


    @DisplayName("integration test for account update")
    @Test
    void givenAccountIdAndClientId_whenUpdateAccount_thenReturnAccountResponseDto() throws Exception {
        //given  - precondition or setup
        Long newClientId = 3578L;

        Account savedAccount = accountRepo.save(account);

        //when - action or the behaviour that we are going to test
        ResultActions response = mockMvc.perform(put("/api/accounts/{accountId}", savedAccount.getAccountId().intValue())
                .contentType(MediaType.APPLICATION_JSON)
                .param("clientId", newClientId.toString()));

        //then - verify the output
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.clientId", is(newClientId.intValue())));
    }

    @DisplayName("integration test for account delete ")
    @Test
    void givenAccountId_whenDeleteAccount_thenReturnResponseDto() throws Exception {
        //given  - precondition or setup
        Account savedAccount = accountRepo.save(account);
        //when - action or the behaviour that we are going to test
        ResultActions response = mockMvc.perform(delete("/api/accounts/{accountId}", savedAccount.getAccountId().intValue()));

        //then - verify the output
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusMsg", is("account deleted successfully")));
    }
}
