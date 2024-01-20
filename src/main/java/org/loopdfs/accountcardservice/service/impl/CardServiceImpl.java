package org.loopdfs.accountcardservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.loopdfs.accountcardservice.dto.CardDto;
import org.loopdfs.accountcardservice.dto.CardResponseDto;
import org.loopdfs.accountcardservice.dto.PaginatedResponse;
import org.loopdfs.accountcardservice.exception.ResourceNotFoundException;
import org.loopdfs.accountcardservice.mapper.CardMapper;
import org.loopdfs.accountcardservice.model.Card;
import org.loopdfs.accountcardservice.repository.AccountRepo;
import org.loopdfs.accountcardservice.repository.CardRepo;
import org.loopdfs.accountcardservice.service.CardService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private final CardRepo cardRepo;
    private final AccountRepo accountRepo;

    @Override
    public CardResponseDto createCard(CardDto cardDto) {
        return accountRepo.findById(cardDto.getAccountId()).map(account -> {
            Card card = CardMapper.cardDtoToCard(cardDto, new Card());
            card.setAccount(account);
            return CardMapper.cardToCardResponseDto(cardRepo.save(card), new CardResponseDto());
        }).orElseThrow(() -> new ResourceNotFoundException("account", "account Id", cardDto.getAccountId().toString()));
    }

    @Override
    public CardResponseDto getCard(Long cardId) {
        return cardRepo.findById(cardId).map(card -> CardMapper.cardToCardResponseDto(card, new CardResponseDto()))
                .orElseThrow(() -> new ResourceNotFoundException("card", "card Id", cardId.toString()));
    }

    @Override
    public PaginatedResponse<CardResponseDto> getAllCards(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);

        Page<Card> cardPage = cardRepo.findAll(pageRequest);

        List<CardResponseDto> cardResponseDtos = cardPage.get()
                .map(card -> CardMapper.cardToCardResponseDto(card, new CardResponseDto()))
                .collect(Collectors.toList());

        return new PaginatedResponse<>(
                cardResponseDtos,
                cardPage.getTotalPages(),
                cardPage.getTotalElements(),
                cardPage.isLast(),
                cardPage.getSize(),
                cardPage.getNumber(),
                cardPage.isFirst(),
                cardPage.getNumberOfElements()
        );
    }

    @Override
    public List<CardResponseDto> getAccountCards(Long accountId) {
        return accountRepo.findById(accountId).map(account -> account.getCards()
                        .stream()
                        .map(card -> CardMapper.cardToCardResponseDto(card, new CardResponseDto()))
                        .collect(Collectors.toList()))
                .orElseThrow(() -> new ResourceNotFoundException("account", "account Id", accountId.toString()));
    }

    @Override
    public CardResponseDto updateCard(Long cardId, String cardAlias) {
        return cardRepo.findById(cardId).map(card -> {
            card.setCardAlias(cardAlias);
            return CardMapper.cardToCardResponseDto(cardRepo.save(card), new CardResponseDto());
        }).orElseThrow(() -> new ResourceNotFoundException("card", "card Id", cardId.toString()));
    }

    @Override
    public void deleteCard(Long cardId) {
        Card card = cardRepo.findById(cardId).orElseThrow(() -> new ResourceNotFoundException("card", "card Id", cardId.toString()));
        cardRepo.deleteById(card.getId());
    }
}
