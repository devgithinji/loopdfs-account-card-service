package org.loopdfs.accountcardservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "accounts")
@Getter
@Setter
@ToString(exclude = {"cards"})
@AllArgsConstructor
@NoArgsConstructor
public class Account extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;

    @Column(nullable = false, unique = true)
    private String iban;

    @Column(nullable = false, unique = true)
    private String bicSwift;

    @Column(nullable = false)
    private Long clientId;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "account", orphanRemoval = true)
    private List<Card> cards = new ArrayList<>();

    public void addCard(Card card) {
        card.setAccount(this);
        this.cards.add(card);
    }

    public void removeCard(Card card) {
        card.setAccount(null);
        this.cards.remove(card);
    }
}
