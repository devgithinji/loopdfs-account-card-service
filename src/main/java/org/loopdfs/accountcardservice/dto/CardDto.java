package org.loopdfs.accountcardservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.loopdfs.accountcardservice.validation.ValidCardType;

@Data
@Schema(
        name = "CardDto",
        description = "Data Transfer Object (DTO) for Card creation information"
)
public class CardDto {
    @Schema(
            description = "A user-friendly alias for the card",
            example = "Travel Card"
    )
    @NotBlank(message = "Card alias is required")
    private String cardAlias;

    @Schema(
            description = "Type of the card, accepts either PHYSICAL or VIRTUAL",
            example = "PHYSICAL"
    )
    @ValidCardType(message = "Invalid card type. Must be either PHYSICAL or VIRTUAL.")
    @NotBlank(message = "Card type is required")
    private String cardType;

    @Schema(
            description = "The unique identifier (ID) of the account to which the card is linked",
            example = "2432432"
    )
    @NotNull(message = "Account ID is required")
    private Long accountId;
}
