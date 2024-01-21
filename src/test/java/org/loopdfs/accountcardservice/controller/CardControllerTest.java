package org.loopdfs.accountcardservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.loopdfs.accountcardservice.dto.CardDto;
import org.loopdfs.accountcardservice.dto.CardResponseDto;
import org.loopdfs.accountcardservice.dto.PaginatedResponse;
import org.loopdfs.accountcardservice.mapper.CardMapper;
import org.loopdfs.accountcardservice.model.Account;
import org.loopdfs.accountcardservice.model.Card;
import org.loopdfs.accountcardservice.model.CardType;
import org.loopdfs.accountcardservice.service.CardService;
import org.loopdfs.accountcardservice.util.BICGenerator;
import org.loopdfs.accountcardservice.util.IBANGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CardController.class)
class CardControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CardService cardService;

    @Autowired
    private ObjectMapper objectMapper;

    private Card card;

    private Account account;

    private CardResponseDto cardResponseDto;


    @BeforeEach
    void setUp() {

        account = new Account(1L, IBANGenerator.getIBAN(), BICGenerator.getBIC(), 4567L);
        card = new Card(1L, "Travel Card", account, CardType.VIRTUAL);

        cardResponseDto = new CardResponseDto();
        cardResponseDto.setId(card.getId());
        cardResponseDto.setCardAlias(card.getCardAlias());
        cardResponseDto.setAccountId(card.getAccount().getAccountId());
        cardResponseDto.setCardType(card.getCardType().name());
    }

    // junit test for
    @DisplayName("junit test for create card")
    @Test
    void givenCardDto_whenCreateCard_thenReturnCardResponseDto() throws Exception {
        //given  - precondition or setup
        CardDto cardDto = new CardDto();
        cardDto.setAccountId(account.getAccountId());
        cardDto.setCardType(card.getCardType().name());
        cardDto.setCardAlias(card.getCardAlias());

        given(cardService.createCard(cardDto)).willReturn(cardResponseDto);

        //when - action or the behaviour that we are going to test
        ResultActions response = mockMvc.perform(post("/api/cards")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(cardDto)));

        //then - verify the output
        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(card.getId().intValue())));
    }

    @DisplayName("junit test for get card")
    @Test
    void givenCardId_whenGetCard_thenReturnCardResponseDto() throws Exception {
        //given  - precondition or setup
        Long cardId = 1L;
        given(cardService.getCard(cardId)).willReturn(cardResponseDto);

        //when - action or the behaviour that we are going to test
        ResultActions response = mockMvc.perform(get("/api/cards/{cardId}", cardId.intValue())
                .contentType(MediaType.APPLICATION_JSON));

        //then - verify the output
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(card.getId().intValue())))
                .andExpect(jsonPath("$.cardAlias", is(card.getCardAlias())));
    }


    @DisplayName("junit test for get all cards")
    @Test
    void givenPageAndSize_whenGetCards_thenReturnCardResponseDtoPaginatedResponse() throws Exception {
        //given  - precondition or setup
        int page = 0;
        int size = 10;

        Card card2 = new Card(2L, "Shopping Card", account, CardType.PHYSICAL);


        List<CardResponseDto> cardResponseDtos = Stream.of(card, card2).map(CardMapper::cardToCardResponseDto).toList();

        PaginatedResponse<CardResponseDto> cardResponseDtoPaginatedResponse = new PaginatedResponse<>(
                cardResponseDtos,
                1,
                cardResponseDtos.size(),
                true,
                size,
                page,
                true,
                cardResponseDtos.size()
        );

        given(cardService.getAllCards(page, size)).willReturn(cardResponseDtoPaginatedResponse);

        //when - action or the behaviour that we are going to test
        ResultActions response = mockMvc.perform(get("/api/cards")
                .contentType(MediaType.APPLICATION_JSON));

        //then - verify the output
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.size()", is(cardResponseDtos.size())));
    }


    @DisplayName("junit test for get account cards")
    @Test
    void givenAccountId_whenGetAccountCards_thenReturnCardsList() throws Exception {
        //given  - precondition or setup
        Card card2 = new Card(2L, "Shopping Card", account, CardType.PHYSICAL);

        List<CardResponseDto> cardResponseDtos = Stream.of(card, card2).map(CardMapper::cardToCardResponseDto).toList();
        Long accountId = 1L;
        given(cardService.getAccountCards(accountId)).willReturn(cardResponseDtos);

        //when - action or the behaviour that we are going to test
        ResultActions response = mockMvc.perform(get("/api/cards/account/{accountId}", accountId)
                .contentType(MediaType.APPLICATION_JSON));

        //then - verify the output
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(cardResponseDtos.size())));
    }

    @DisplayName("junit test for update card")
    @Test
    void givenCardIdAndCardAlias_whenUpdateCard_thenReturnCardResponseDto() throws Exception {
        //given  - precondition or setup
        Long cardId = 1L;
        String newCardAlias = "Gaming card";
        cardResponseDto.setCardAlias(newCardAlias);
        given(cardService.updateCard(cardId, newCardAlias)).willReturn(cardResponseDto);

        //when - action or the behaviour that we are going to test
        ResultActions response = mockMvc.perform(put("/api/cards/{cardId}", cardId)
                .contentType(MediaType.APPLICATION_JSON)
                .param("cardAlias", newCardAlias));

        //then - verify the output
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cardAlias", is(newCardAlias)));
    }


    @DisplayName("junit test for delete card")
    @Test
    void givenCardId_whenDeleteCard_thenReturnResponseDto() throws Exception {
        //given  - precondition or setup
        Long cardId = 1L;
        willDoNothing().given(cardService).deleteCard(cardId);

        //when - action or the behaviour that we are going to test
        ResultActions response = mockMvc.perform(delete("/api/cards/{cardId}", cardId)
                .contentType(MediaType.APPLICATION_JSON));

        //then - verify the output
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusMsg", is("Card deleted successfully")));
    }

}