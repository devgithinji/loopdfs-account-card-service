package org.loopdfs.accountcardservice.repository;

import org.loopdfs.accountcardservice.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepo extends JpaRepository<Account, Long> {

}
