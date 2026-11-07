package co.inter.piggies.repository;

import co.inter.piggies.domain.account.Account;
import io.micronaut.data.jpa.repository.JpaRepository;
import jakarta.data.repository.Query;
import jakarta.data.repository.Repository;

import java.math.BigDecimal;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {
    @Query("UPDATE Account a SET a.balance = a.balance - :amount " +
            "WHERE a.accountid = :id AND a.balance >= :amount")
    int debit(UUID id, BigDecimal amount);   // devolve quantas linhas mudou (0 = saldo insuficiente)

    @Query("UPDATE Account a SET a.balance = a.balance + :amount WHERE a.accountid = :id")
    int credit(UUID id, BigDecimal amount);
}
