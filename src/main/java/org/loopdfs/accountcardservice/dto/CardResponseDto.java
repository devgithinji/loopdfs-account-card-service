package org.loopdfs.accountcardservice.dto;

import lombok.Data;

@Data
public class CardResponseDto {
    private Long id;

    private String cardAlias;

    private Long accountId;

    private String cardType;
}
