package org.loopdfs.accountcardservice.dto;

import lombok.Data;

@Data
public class AccountResponseDto {
    private Long accountId;

    private String iban;

    private String bicSwift;

    private Long clientId;
}
