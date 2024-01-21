package org.loopdfs.accountcardservice.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.loopdfs.accountcardservice.dto.CardDto;
import org.loopdfs.accountcardservice.dto.CardResponseDto;
import org.loopdfs.accountcardservice.dto.PaginatedResponse;
import org.loopdfs.accountcardservice.model.Account;
import org.loopdfs.accountcardservice.model.Card;
import org.loopdfs.accountcardservice.model.CardType;
import org.loopdfs.accountcardservice.repository.AccountRepo;
import org.loopdfs.accountcardservice.repository.CardRepo;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CardServiceImplTest {

    @Mock
    private CardRepo cardRepo;
    @Mock
    private AccountRepo accountRepo;
    @InjectMocks
    private CardServiceImpl cardService;

    private Card card;

    private Account account;

    @BeforeEach
    void setUp() {
        account = new Account(1L, IBANGenerator.getIBAN(), BICGenerator.getBIC(), 5678L);

        card = new Card(1L, "Travel Card", account, CardType.PHYSICAL);
    }


    @DisplayName("junit test for create card")
    @Test
    void givenCardDto_whenCreateCard_thenReturnCardResponseDto() {
        //given  - precondition or setup
        Long accountId = 1L;
        given(accountRepo.findById(accountId)).willReturn(Optional.of(account));
        given(cardRepo.save(any(Card.class))).willReturn(card);

        //when - action or the behaviour that we are going to test
        CardDto cardDto = new CardDto();
        cardDto.setCardAlias("Travel Card");
        cardDto.setCardType("PHYSICAL");
        cardDto.setAccountId(accountId);
        CardResponseDto cardResponseDto = cardService.createCard(cardDto);

        //then - verify the output
        assertThat(cardResponseDto).isNotNull();
        assertThat(cardResponseDto.getId()).isEqualTo(accountId);
    }


    @DisplayName("junit test for get card")
    @Test
    void givenCardId_whenGetCard_thenReturnCardResponseDto() {
        //given  - precondition or setup
        Long cardId = 1L;
        given(cardRepo.findById(cardId)).willReturn(Optional.of(card));

        //when - action or the behaviour that we are going to test
        CardResponseDto cardResponseDto = cardService.getCard(cardId);

        //then - verify the output
        assertThat(cardResponseDto.getId()).isEqualTo(cardId);
    }

    @DisplayName("junit test for get all cards ")
    @Test
    void givenCards_whenGetAllCards_thenReturnAllCards() {
        //given  - precondition or setup
        Card cardTwo = new Card(2L, "Shopping Card", account, CardType.VIRTUAL);
        List<Card> cards = List.of(card, cardTwo);
        int page = 0;
        int size = 10;

        PageRequest pageRequest = PageRequest.of(page, size);

        Page<Card> cardPage = new PageImpl<>(cards, pageRequest, cards.size());
        given(cardRepo.findAll(pageRequest)).willReturn(cardPage);
        //when - action or the behaviour that we are going to test
        PaginatedResponse<CardResponseDto> cardPaginatedResponse = cardService.getAllCards(page, size);

        //then - verify the output
        assertThat(cardPaginatedResponse.getContent().size()).isEqualTo(cards.size());
    }


    @DisplayName("junit test for all account cards")
    @Test
    void givenAccountId_whenGetAllAccountCards_thenReturnAllAccountCards() {
        //given  - precondition or setup
        Long accountId = 1L;
        Card cardTwo = new Card(2L, "Shopping Card", account, CardType.VIRTUAL);
        List<Card> cards = List.of(card, cardTwo);
        account.setCards(cards);

        given(accountRepo.findById(accountId)).willReturn(Optional.of(account));

        //when - action or the behaviour that we are going to test
        List<CardResponseDto> cardResponseDtos = cardService.getAccountCards(accountId);

        //then - verify the output
        assertThat(cardResponseDtos.size()).isEqualTo(cards.size());

        cardResponseDtos.forEach(cardResponseDto -> {
            assertThat(cardResponseDto.getAccountId()).isEqualTo(accountId);
        });
    }

    @DisplayName("junit test for updateCard")
    @Test
    void givenCardIdAndCardAlias_whenUpdatingCard_thenReturnCardResponseDto() {
        //given  - precondition or setup
        Long cardId = 1L;
        given(cardRepo.findById(cardId)).willReturn(Optional.of(card));
        String newAlias = "Gaming Card";
        Card newCard = new Card(card.getId(), newAlias, card.getAccount(), card.getCardType());
        given(cardRepo.save(any(Card.class))).willReturn(newCard);

        //when - action or the behaviour that we are going to test
        CardResponseDto cardResponseDto = cardService.updateCard(cardId, newAlias);

        //then - verify the output
        assertThat(cardResponseDto.getCardAlias()).isEqualTo(newAlias);
    }

    @DisplayName("junit test for delete card")
    @Test
    void givenCardId_whenDeleteCard_thenDeleteCard() {
        //given  - precondition or setup
        Long cardId = 1L;
        given(cardRepo.findById(cardId)).willReturn(Optional.of(card));
        willDoNothing().given(cardRepo).deleteById(cardId);

        //when - action or the behaviour that we are going to test
        cardService.deleteCard(cardId);

        //then - verify the output
        verify(cardRepo, times(1)).deleteById(cardId);
    }

}