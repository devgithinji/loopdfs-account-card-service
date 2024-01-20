package org.loopdfs.accountcardservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.loopdfs.accountcardservice.dto.AccountResponseDto;
import org.loopdfs.accountcardservice.dto.PaginatedResponse;
import org.loopdfs.accountcardservice.exception.ResourceNotFoundException;
import org.loopdfs.accountcardservice.mapper.AccountMapper;
import org.loopdfs.accountcardservice.model.Account;
import org.loopdfs.accountcardservice.repository.AccountRepo;
import org.loopdfs.accountcardservice.service.AccountService;
import org.loopdfs.accountcardservice.util.IBANGenerator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepo accountRepo;
    private final Random random = new Random();
    private static final String[] bics = {"EB456KE", "SB789TZ", "IM587UG", "NA678MQ"};

    @Override

    public AccountResponseDto createAccount(Long clientId) {
        Account account = accountRepo.save(new Account(IBANGenerator.getIBAN(), getBIC(), clientId));

        return AccountMapper.accountToAccountResponseDto(account);
    }


    @Override
    public AccountResponseDto getAccount(Long accountId) {

        return accountRepo.findById(accountId)
                .map(AccountMapper::accountToAccountResponseDto)
                .orElseThrow(() -> new ResourceNotFoundException("account", "accountId", accountId.toString()));
    }

    @Override
    public PaginatedResponse<AccountResponseDto> getAllAccounts(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Account> accountPage = accountRepo.findAll(pageRequest);

        List<AccountResponseDto> accountResponseDtos = accountPage.get()
                .map(AccountMapper::accountToAccountResponseDto)
                .collect(Collectors.toList());

        return new PaginatedResponse<>(
                accountResponseDtos,
                accountPage.getTotalPages(),
                accountPage.getTotalElements(),
                accountPage.isLast(),
                accountPage.getSize(),
                accountPage.getNumber(),
                accountPage.isFirst(),
                accountPage.getNumberOfElements()
        );
    }


    @Override
    public AccountResponseDto updateAccount(Long accountId, Long clientId) {
        return accountRepo.findById(accountId).map(account -> {
            account.setClientId(clientId);
            return AccountMapper.accountToAccountResponseDto(accountRepo.save(account));
        }).orElseThrow(() -> new ResourceNotFoundException("account", "account Id", accountId.toString()));
    }

    @Override
    public void deleteAccount(Long accountId) {
        Account account = accountRepo.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("account", "account Id", accountId.toString()));
        accountRepo.deleteById(account.getAccountId());
    }

    private String getBIC() {
        int randoIndex = random.nextInt(bics.length);
        return bics[randoIndex];
    }
}
