package org.loopdfs.accountcardservice.controller;

import lombok.RequiredArgsConstructor;
import org.loopdfs.accountcardservice.dto.CardDto;
import org.loopdfs.accountcardservice.dto.CardResponseDto;
import org.loopdfs.accountcardservice.dto.PaginatedResponse;
import org.loopdfs.accountcardservice.dto.ResponseDto;
import org.loopdfs.accountcardservice.service.CardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/cards", produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
@Validated
public class CardController {
    private final CardService cardService;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public CardResponseDto createCard(@RequestBody CardDto cardDto) {
        return cardService.createCard(cardDto);
    }

    @GetMapping("/{cardId}")
    public CardResponseDto getCard(@PathVariable("cardId") Long cardId) {
        return cardService.getCard(cardId);
    }

    @GetMapping
    public PaginatedResponse<CardResponseDto> getAllCards(@RequestParam(name = "page", defaultValue = "0") int page,
                                                          @RequestParam(name = "size", defaultValue = "10") int size) {
        return cardService.getAllCards(page, size);
    }

    @GetMapping("/{accountId}")
    public List<CardResponseDto> getAccountCards(@PathVariable("accountId") Long accountId) {
        return cardService.getAccountCards(accountId);
    }

    @PutMapping("/{cardId}")
    public CardResponseDto updateCard(@PathVariable("cardId") Long cardId, @RequestParam("cardAlias") String cardAlias) {
        return cardService.updateCard(cardId, cardAlias);
    }

    @DeleteMapping("/{cardId}")
    public ResponseDto deleteCard(@PathVariable("cardId") Long cardId) {
        cardService.deleteCard(cardId);
        return new ResponseDto(HttpStatus.OK.toString(), "Card deleted successfully");
    }
}
