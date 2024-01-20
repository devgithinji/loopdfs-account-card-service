package org.loopdfs.accountcardservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccountResponseDto {
    private Long accountId;

    private String iban;

    private String bicSwift;

    private Long clientId;
}
