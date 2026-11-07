package co.inter.piggies.kakfa.cossumer;

import co.inter.piggies.kakfa.events.TransferenceEvent;
import co.inter.piggies.kakfa.Topics;
import co.inter.piggies.service.BalanceService;
import io.micronaut.configuration.kafka.annotation.KafkaKey;
import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.OffsetReset;
import io.micronaut.configuration.kafka.annotation.Topic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

@KafkaListener(groupId = "balance-service", offsetReset = OffsetReset.EARLIEST)
public class TransferenceCossumer {

    private static final Logger LOG = LoggerFactory.getLogger(TransferenceCossumer.class);
    private final BalanceService balanceService;

    public TransferenceCossumer(BalanceService balanceService) {
        this.balanceService = balanceService;
    }

    @Topic(Topics.TRANSFERENCES)
    void onTransference(@KafkaKey UUID accountFromId, TransferenceEvent event) {
        LOG.info("Recebida transferência {} da conta {}", event.id(), accountFromId);
        balanceService.process(event);
    }
}
