package co.inter.piggies.domain.account;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "accounts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Id
    private UUID accountid;

    @Column(precision = 20, scale = 2)
    private BigDecimal initialBalance;

    @Column(precision = 20, scale = 2)
    private BigDecimal balance;

}
