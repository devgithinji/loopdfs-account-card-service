package org.loopdfs.accountcardservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.loopdfs.accountcardservice.dto.CardDto;
import org.loopdfs.accountcardservice.dto.CardResponseDto;
import org.loopdfs.accountcardservice.repository.CardRepo;
import org.loopdfs.accountcardservice.service.CardService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private final CardRepo cardRepo;

    @Override
    public CardResponseDto createCard(CardDto cardDto) {
        return null;
    }

    @Override
    public CardResponseDto getCard(Long cardId) {
        return null;
    }

    @Override
    public List<CardResponseDto> getAllCards() {
        return null;
    }

    @Override
    public List<CardResponseDto> getAccountCards(Long accountId) {
        return null;
    }

    @Override
    public CardResponseDto updateCard(String cardAlias) {
        return null;
    }

    @Override
    public void deleteCard(Long cardId) {

    }
}
