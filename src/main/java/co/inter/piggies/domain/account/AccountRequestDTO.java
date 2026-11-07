package co.inter.piggies.domain.account;

import io.micronaut.serde.annotation.Serdeable;

import java.math.BigDecimal;
import java.util.UUID;

@Serdeable
public record AccountRequestDTO(
        UUID id,
        BigDecimal initialBalance,
        BigDecimal balance
) {}
