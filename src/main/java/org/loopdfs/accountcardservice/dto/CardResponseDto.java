package org.loopdfs.accountcardservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(
        name = "CardResponseDto",
        description = "Data Transfer Object (DTO) for Card details information"
)
public class CardResponseDto {
    @Schema(
            description = "Unique identifier (ID) of the card",
            example = "1"
    )
    private Long id;

    @Schema(
            description = "Card Alias",
            example = "Travel Card"
    )
    private String cardAlias;

    @Schema(
            description = "Account ID to which the card is linked",
            example = "2432432"
    )
    private Long accountId;

    @Schema(
            description = "Card Type (PHYSICAL or VIRTUAL)",
            example = "PHYSICAL"
    )
    private String cardType;
}
