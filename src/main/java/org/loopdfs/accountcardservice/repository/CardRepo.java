package org.loopdfs.accountcardservice.repository;

import org.loopdfs.accountcardservice.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepo extends JpaRepository<Card, Long> {

}
