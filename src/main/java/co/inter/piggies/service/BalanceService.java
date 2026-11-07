package co.inter.piggies.service;

import co.inter.piggies.kakfa.events.TransferenceEvent;
import co.inter.piggies.repository.AccountRepository;
import co.inter.piggies.repository.TransferenceRepository;
import co.inter.piggies.domain.transference.Transference;
import jakarta.inject.Singleton;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class BalanceService {

    private static final Logger LOG = LoggerFactory.getLogger(BalanceService.class);

    private final AccountRepository accountRepository;
    private final TransferenceRepository transferenceRepository;

    public BalanceService(AccountRepository accountRepository,
                          TransferenceRepository transferenceRepository) {
        this.accountRepository = accountRepository;
        this.transferenceRepository = transferenceRepository;
    }

    @Transactional
    public void process(TransferenceEvent event) {
        // 1. A transferência existe?
        Transference transference = transferenceRepository.findById(event.id()).orElse(null);
        if (transference == null) {
            LOG.warn("Transferência {} desconhecida, ignorando", event.id());
            return;
        }

        // 2. Idempotência: se já foi processada, não faz nada
        if (!"Pending".equals(transference.getStatus())) {
            LOG.info("Transferência {} já processada ({}), ignorando", event.id(), transference.getStatus());
            return;
        }

        // 3. As duas contas existem?
        if (!accountRepository.existsById(event.from()) || !accountRepository.existsById(event.to())) {
            transference.setStatus("Rejected");      // motivo: ACCOUNT_NOT_FOUND
            transferenceRepository.update(transference);
            return;
        }

        // 4. Tenta debitar (atômico)
        int debited = accountRepository.debit(event.from(), event.ammount());
        if (debited == 0) {
            transference.setStatus("Rejected");      // motivo: INSUFFICIENT_FUNDS
            transferenceRepository.update(transference);
            return;
        }

        // 5. Credita e conclui (tudo na mesma transação)
        accountRepository.credit(event.to(), event.ammount());
        transference.setStatus("Completed");
        transferenceRepository.update(transference);
    }
}