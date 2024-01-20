package org.loopdfs.accountcardservice.mapper;

import org.loopdfs.accountcardservice.dto.AccountResponseDto;
import org.loopdfs.accountcardservice.model.Account;

public class AccountMapper {
        public static AccountResponseDto accountToAccountResponseDto(Account account) {
        AccountResponseDto accountResponseDto = new AccountResponseDto();
        accountResponseDto.setAccountId(account.getAccountId());
        accountResponseDto.setIban(account.getIban());
        accountResponseDto.setBicSwift(account.getBicSwift());
        accountResponseDto.setClientId(account.getClientId());
        return accountResponseDto;
    }
}
