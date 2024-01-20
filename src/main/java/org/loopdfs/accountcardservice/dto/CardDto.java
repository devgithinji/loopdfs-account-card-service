package org.loopdfs.accountcardservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.loopdfs.accountcardservice.validation.ValidCardType;

@Data
public class CardDto {
    @NotBlank(message = "card alias required")
    private String cardAlias;
    @ValidCardType(message = "Invalid card type. {PHYSICAL or VIRTUAL}.")
    @NotBlank(message = "card type is required")
    private String cardType;
    @NotBlank(message = "account id required")
    private Long accountId;
}
