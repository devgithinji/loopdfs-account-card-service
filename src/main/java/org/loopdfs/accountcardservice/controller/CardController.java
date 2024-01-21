package org.loopdfs.accountcardservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.loopdfs.accountcardservice.dto.*;
import org.loopdfs.accountcardservice.service.CardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "Card Endpoints",
        description = "CREATE, UPDATE, DELETE, FETCH AND DELETE endpoints for Card"
)
@RestController
@RequestMapping(value = "/api/cards", produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
@Validated
public class CardController {
    private final CardService cardService;

    @Operation(
            summary = "Create Card REST API",
            description = "REST API endpoint to create card"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Card created Successfully"
            ),
            @ApiResponse(
                    responseCode = "422",
                    description = "Unprocessable Entity",
                    content = @Content(
                            schema = @Schema(implementation = UnprocessableEntityErrorDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad request",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public CardResponseDto createCard(@Valid @RequestBody CardDto cardDto) {
        return cardService.createCard(cardDto);
    }

    @Operation(
            summary = "Fetch Card REST API",
            description = "REST API endpoint to fetch card"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Ok"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad request",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Resource Not Found",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @GetMapping("/{cardId}")
    public CardResponseDto getCard(@PathVariable("cardId") Long cardId) {
        return cardService.getCard(cardId);
    }

    @Operation(
            summary = "Fetch All Cards REST API",
            description = "REST API endpoint to fetch all cards"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Ok"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad request",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Resource Not Found",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @GetMapping
    public PaginatedResponse<CardResponseDto> getAllCards(@RequestParam(name = "page", defaultValue = "0") int page,
                                                          @RequestParam(name = "size", defaultValue = "10") int size) {
        return cardService.getAllCards(page, size);
    }

    @Operation(
            summary = "Fetch All Cards From Specific Account REST API",
            description = "REST API endpoint to fetch all cards from specific account"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Ok"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad request",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Resource Not Found",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @GetMapping("/account/{accountId}")
    public List<CardResponseDto> getAccountCards(@PathVariable("accountId") Long accountId) {
        return cardService.getAccountCards(accountId);
    }

    @Operation(
            summary = "Update Card REST API",
            description = "REST API endpoint to update card"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Ok"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad request",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Resource Not Found",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @PutMapping("/{cardId}")
    public CardResponseDto updateCard(@PathVariable("cardId") Long cardId, @RequestParam("cardAlias") String cardAlias) {
        return cardService.updateCard(cardId, cardAlias);
    }

    @Operation(
            summary = "Delete Card REST API",
            description = "REST API endpoint to delete card"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Ok"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad request",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Resource Not Found",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @DeleteMapping("/{cardId}")
    public ResponseDto deleteCard(@PathVariable("cardId") Long cardId) {
        cardService.deleteCard(cardId);
        return new ResponseDto(HttpStatus.OK.toString(), "Card deleted successfully");
    }
}
