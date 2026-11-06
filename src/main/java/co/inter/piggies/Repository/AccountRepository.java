package co.inter.piggies.Repository;

import co.inter.piggies.domain.account.Account;
import io.micronaut.data.jpa.repository.JpaRepository;
import jakarta.data.repository.Repository;

import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {
}
