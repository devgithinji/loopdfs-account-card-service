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

    @Column(nullable = false)
    private String bicSwift;

    @Column(nullable = false)
    private Long clientId;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "account", orphanRemoval = true)
    private List<Card> cards = new ArrayList<>();


    public Account(Long accountId, String iban, String bicSwift, Long clientId) {
        this.accountId = accountId;
        this.iban = iban;
        this.bicSwift = bicSwift;
        this.clientId = clientId;
    }

    public Account(String iban, String bicSwift, Long clientId) {
        this.iban = iban;
        this.bicSwift = bicSwift;
        this.clientId = clientId;
    }

}
