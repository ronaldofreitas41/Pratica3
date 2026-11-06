package co.inter.piggies.Kakfa;

import io.micronaut.configuration.kafka.annotation.Topic;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Topics {

    public static final String TRANSFERENCES = "transferences";
}
