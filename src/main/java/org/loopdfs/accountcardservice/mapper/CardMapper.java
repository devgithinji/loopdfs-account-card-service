package org.loopdfs.accountcardservice.mapper;

import org.loopdfs.accountcardservice.dto.CardDto;
import org.loopdfs.accountcardservice.dto.CardResponseDto;
import org.loopdfs.accountcardservice.model.Card;
import org.loopdfs.accountcardservice.model.CardType;

public class CardMapper {

    public static Card cardDtoToCard(CardDto cardDto, Card card) {
        card.setCardAlias(cardDto.getCardAlias());
        card.setCardType(CardType.valueOf(cardDto.getCardType()));
        return card;
    }

    public static CardResponseDto cardToCardResponseDto(Card card, CardResponseDto cardResponseDto) {
        cardResponseDto.setId(card.getId());
        cardResponseDto.setCardAlias(card.getCardAlias());
        cardResponseDto.setCardType(card.getCardType().name());
        cardResponseDto.setAccountId(card.getAccount().getAccountId());
        return cardResponseDto;
    }
}
