package co.inter.piggies.domain.transference;

import io.micronaut.serde.annotation.Serdeable;

import java.math.BigDecimal;
import java.util.UUID;

@Serdeable
public record TransferenceResponseDTO(
        UUID id,
        UUID accountFrom,
        UUID accountTo,
        BigDecimal ammount,
        String Status
) { }
