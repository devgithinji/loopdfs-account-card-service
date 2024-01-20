package org.loopdfs.accountcardservice.service;

import org.loopdfs.accountcardservice.dto.CardDto;
import org.loopdfs.accountcardservice.dto.CardResponseDto;

import java.util.List;

public interface CardService {
    CardResponseDto createCard(CardDto cardDto);

    CardResponseDto getCard(Long cardId);

    List<CardResponseDto> getAllCards();

    List<CardResponseDto> getAccountCards(Long accountId);

    CardResponseDto updateCard(String cardAlias);

    void deleteCard(Long cardId);
}
