package org.loopdfs.accountcardservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(
        name = "AccountResponseDto",
        description = "Data Transfer Object (DTO) for Account details information"
)
public class AccountResponseDto {
    @Schema(
            description = "Unique identifier (ID) of the account",
            example = "1"
    )
    private Long accountId;

    @Schema(
            description = "International Bank Account Number (IBAN) of the account",
            example = "GB00NWBK107787294"
    )
    private String iban;

    @Schema(
            description = "Bank Identifier Code (BIC/SWIFT) of the account",
            example = "EB456KE"
    )
    private String bicSwift;

    @Schema(
            description = "Client ID associated with the account",
            example = "5678"
    )
    private Long clientId;
}
