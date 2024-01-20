package org.loopdfs.accountcardservice.mapper;

import org.loopdfs.accountcardservice.dto.AccountResponseDto;
import org.loopdfs.accountcardservice.model.Account;

public class AccountMapper {

    public static Account accountResponseDtoToAccount(AccountResponseDto accountResponseDto, Account account){
        account.setAccountId(accountResponseDto.getAccountId());
        account.setIban(accountResponseDto.getIban());
        account.setBicSwift(accountResponseDto.getBicSwift());
        account.setClientId(accountResponseDto.getClientId());
        return account;
    }

    public static AccountResponseDto accountToAccountResponseDto(Account account, AccountResponseDto accountResponseDto) {
        accountResponseDto.setAccountId(account.getAccountId());
        accountResponseDto.setIban(account.getIban());
        accountResponseDto.setBicSwift(account.getBicSwift());
        accountResponseDto.setClientId(account.getClientId());
        return accountResponseDto;
    }
}
