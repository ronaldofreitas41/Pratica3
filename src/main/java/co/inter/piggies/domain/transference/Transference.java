package co.inter.piggies.domain.transference;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "transferences")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transference {

    @Id
    @GeneratedValue
    private UUID id;
    private UUID accountFrom;
    private UUID accountTo;
    private BigDecimal ammount;
    private String status;

}
