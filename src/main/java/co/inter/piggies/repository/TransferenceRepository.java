package co.inter.piggies.repository;

import co.inter.piggies.domain.transference.Transference;
import io.micronaut.data.jpa.repository.JpaRepository;
import jakarta.data.repository.Repository;

import java.util.UUID;

@Repository
public interface TransferenceRepository extends JpaRepository<Transference, UUID> {
}
