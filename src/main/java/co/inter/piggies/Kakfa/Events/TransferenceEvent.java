package co.inter.piggies.Kakfa.Events;

import io.micronaut.serde.annotation.Serdeable;

import java.math.BigDecimal;
import java.util.UUID;

@Serdeable
public record TransferenceEvent(
    UUID id,
    UUID from,
    UUID to,
    BigDecimal ammount,
    String status
) { }
