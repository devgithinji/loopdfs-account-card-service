package org.loopdfs.accountcardservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.loopdfs.accountcardservice.validation.ValidCardType;

@Data
public class CardDto {
    @NotBlank(message = "card alias required")
    private String cardAlias;
    @ValidCardType(message = "Invalid card type. {PHYSICAL or VIRTUAL}.")
    @NotBlank(message = "card type is required")
    private String cardType;
    @NotNull(message = "account id required")
    private Long accountId;
}
