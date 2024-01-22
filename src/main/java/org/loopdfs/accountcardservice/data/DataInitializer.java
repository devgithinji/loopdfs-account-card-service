package org.loopdfs.accountcardservice.data;

import lombok.RequiredArgsConstructor;
import org.loopdfs.accountcardservice.model.Account;
import org.loopdfs.accountcardservice.model.Card;
import org.loopdfs.accountcardservice.model.CardType;
import org.loopdfs.accountcardservice.repository.AccountRepo;
import org.loopdfs.accountcardservice.repository.CardRepo;
import org.loopdfs.accountcardservice.util.BICGenerator;
import org.loopdfs.accountcardservice.util.IBANGenerator;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final AccountRepo accountRepo;
    private final CardRepo cardRepo;

    @Override
    public void run(String... args) throws Exception {
        cardRepo.deleteAll();
        accountRepo.deleteAll();

        Account account = new Account(IBANGenerator.getIBAN(), BICGenerator.getBIC(), 567L);
        Account account2 = new Account(IBANGenerator.getIBAN(), BICGenerator.getBIC(), 600L);
        accountRepo.saveAll(List.of(account, account2));

        Card card = new Card("Travel Card", account, CardType.VIRTUAL);
        Card card2 = new Card("Shopping Card", account, CardType.PHYSICAL);
        Card card3 = new Card("Gaming Card", account2, CardType.VIRTUAL);
        Card card4 = new Card("HealthCare Card", account2, CardType.PHYSICAL);

        cardRepo.saveAll(List.of(card, card2, card3, card4));
    }
}
