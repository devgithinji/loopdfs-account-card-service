package org.loopdfs.accountcardservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.loopdfs.accountcardservice.dto.AccountResponseDto;
import org.loopdfs.accountcardservice.dto.ErrorResponseDto;
import org.loopdfs.accountcardservice.dto.PaginatedResponse;
import org.loopdfs.accountcardservice.dto.ResponseDto;
import org.loopdfs.accountcardservice.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "Account Endpoints",
        description = "CREATE, UPDATE, DELETE, FETCH AND DELETE endpoints for Account"
)
@RestController
@RequestMapping(value = "/api/accounts", produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
@Validated
public class AccountController {

    private final AccountService accountService;

    @Operation(
            summary = "Create Account REST API",
            description = "REST API endpoint to create account"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Account created Successfully",
                    content = @Content(
                            schema = @Schema(implementation = AccountResponseDto.class)
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
    public AccountResponseDto createAccount(@RequestParam("clientId") Long clientId) {
        return accountService.createAccount(clientId);
    }

    @Operation(
            summary = "Fetch Account REST API",
            description = "REST API endpoint to fetch account"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Ok",
                    content = @Content(
                            schema = @Schema(implementation = AccountResponseDto.class)
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
    @GetMapping("/{accountId}")
    public AccountResponseDto getAccount(@PathVariable("accountId") Long accountId) {
        return accountService.getAccount(accountId);
    }

    @Operation(
            summary = "Fetch Accounts REST API",
            description = "REST API endpoint to fetch all accounts"
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
    public PaginatedResponse<AccountResponseDto> getAccounts(@RequestParam(name = "page", defaultValue = "0") int page,
                                                             @RequestParam(name = "size", defaultValue = "10") int size) {
        return accountService.getAllAccounts(page, size);
    }

    @Operation(
            summary = "Update Account REST API",
            description = "REST API endpoint to update account"
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
    @PutMapping("/{accountId}")
    public AccountResponseDto updateAccount(@PathVariable("accountId") Long accountId,
                                            @RequestParam("clientId") Long clientId) {
        return accountService.updateAccount(accountId, clientId);
    }

    @Operation(
            summary = "Delete Account REST API",
            description = "REST API endpoint to delete account"
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
    @DeleteMapping("/{accountId}")
    public ResponseDto deleteAccount(@PathVariable("accountId") Long accountId) {
        accountService.deleteAccount(accountId);
        return new ResponseDto(HttpStatus.OK.toString(), "account deleted successfully");
    }


}
