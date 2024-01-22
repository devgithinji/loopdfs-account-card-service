package org.loopdfs.accountcardservice.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.loopdfs.accountcardservice.dto.CardDto;
import org.loopdfs.accountcardservice.model.Account;
import org.loopdfs.accountcardservice.model.Card;
import org.loopdfs.accountcardservice.model.CardType;
import org.loopdfs.accountcardservice.repository.AccountRepo;
import org.loopdfs.accountcardservice.repository.CardRepo;
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
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class CardControllerIT {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private AccountRepo accountRepo;
    @Autowired
    private CardRepo cardRepo;
    @Autowired
    private ObjectMapper objectMapper;

    private Card card;

    private Account account;

    @BeforeEach
    void setUp() {
        cardRepo.deleteAll();
        account = new Account(1L, IBANGenerator.getIBAN(), BICGenerator.getBIC(), 567L);
        card = new Card(1L, "Travel Card", account, CardType.VIRTUAL);
    }

    @DisplayName("integration test for create card")
    @Test
    void givenCardDto_whenCreateCard_thenReturnCardResponseDto() throws Exception {
        //given  - precondition or setup
        Account savedAccount = accountRepo.save(account);

        CardDto cardDto = new CardDto();
        cardDto.setAccountId(savedAccount.getAccountId());
        cardDto.setCardType(card.getCardType().name());
        cardDto.setCardAlias(card.getCardAlias());


        //when - action or the behaviour that we are going to test
        ResultActions response = mockMvc.perform(post("/api/cards")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(cardDto)));

        //then - verify the output
        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()));
    }


    @DisplayName("integration test for get card")
    @Test
    void givenCardId_whenGetCard_thenReturnCardResponseDto() throws Exception {
        //given  - precondition or setup
        Long cardId = 1L;
        Account savedAccount = accountRepo.save(account);
        Card savedCard = cardRepo.save(card);


        //when - action or the behaviour that we are going to test
        ResultActions response = mockMvc.perform(get("/api/cards/{cardId}", savedCard.getId().intValue())
                .contentType(MediaType.APPLICATION_JSON));

        //then - verify the output
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(savedCard.getId().intValue())))
                .andExpect(jsonPath("$.cardAlias", is(savedCard.getCardAlias())));
    }

    @DisplayName("integration test for get all cards")
    @Test
    void givenPageAndSize_whenGetCards_thenReturnCardResponseDtoPaginatedResponse() throws Exception {
        //given  - precondition or setup
        accountRepo.save(account);

        int page = 0;
        int size = 10;

        Card card2 = new Card(2L, "Shopping Card", account, CardType.PHYSICAL);

        List<Card> savedCards = cardRepo.saveAll(List.of(card2, card));


        //when - action or the behaviour that we are going to test
        ResultActions response = mockMvc.perform(get("/api/cards")
                .contentType(MediaType.APPLICATION_JSON)
                .param("page", String.valueOf(page))
                .param("size", String.valueOf(size)));

        //then - verify the output
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.size()", is(savedCards.size())));
    }

    @DisplayName("integration test for get account cards")
    @Test
    void givenAccountId_whenGetAccountCards_thenReturnCardsList() throws Exception {
        //given  - precondition or setup
        accountRepo.save(account);

        Card card2 = new Card(2L, "Shopping Card", account, CardType.PHYSICAL);

        List<Card> savedCards = cardRepo.saveAll(List.of(card2, card));


        //when - action or the behaviour that we are going to test
        ResultActions response = mockMvc.perform(get("/api/cards/account/{accountId}", account.getAccountId().intValue())
                .contentType(MediaType.APPLICATION_JSON));

        //then - verify the output
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(savedCards.size())));
    }

    @DisplayName("integration test for update card")
    @Test
    void givenCardIdAndCardAlias_whenUpdateCard_thenReturnCardResponseDto() throws Exception {
        //given  - precondition or setup
        accountRepo.save(account);
        Card savedCard = cardRepo.save(card);
        String newCardAlias = "Gaming card";


        //when - action or the behaviour that we are going to test
        ResultActions response = mockMvc.perform(put("/api/cards/{cardId}", savedCard.getId().intValue())
                .contentType(MediaType.APPLICATION_JSON)
                .param("cardAlias", newCardAlias));

        //then - verify the output
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cardAlias", is(newCardAlias)));
    }


    @DisplayName("integration test for delete card")
    @Test
    void givenCardId_whenDeleteCard_thenReturnResponseDto() throws Exception {
        //given  - precondition or setup
        accountRepo.save(account);
        Card savedCard = cardRepo.save(card);

        //when - action or the behaviour that we are going to test
        ResultActions response = mockMvc.perform(delete("/api/cards/{cardId}", savedCard.getId().intValue())
                .contentType(MediaType.APPLICATION_JSON));

        //then - verify the output
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusMsg", is("Card deleted successfully")));
    }

}
