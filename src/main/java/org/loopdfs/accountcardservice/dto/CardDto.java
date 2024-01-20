package org.loopdfs.accountcardservice.dto;

import jakarta.validation.constraints.NotBlank;

public class CardDto {
    @NotBlank(message = "card alias required")
    private String cardAlias;
    private String cardType;
    @NotBlank(message = "account id required")
    private Long accountId;


}
