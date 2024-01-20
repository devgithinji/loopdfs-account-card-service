package org.loopdfs.accountcardservice.service;

import org.loopdfs.accountcardservice.dto.CardDto;
import org.loopdfs.accountcardservice.dto.CardResponseDto;
import org.loopdfs.accountcardservice.dto.PaginatedResponse;

import java.util.List;

public interface CardService {
    CardResponseDto createCard(CardDto cardDto);

    CardResponseDto getCard(Long cardId);

    PaginatedResponse<CardResponseDto> getAllCards(int page, int size);

    List<CardResponseDto> getAccountCards(Long accountId);

    CardResponseDto updateCard(Long cardId, String cardAlias);

    void deleteCard(Long cardId);
}
