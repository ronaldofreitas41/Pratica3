package co.inter.piggies.Kakfa.Cossumer;

import co.inter.piggies.Kakfa.Topics;
import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.Topic;

@KafkaListener
public class TransferencesCossumer {

    @Topic(Topics.TRANSFERENCES)

}
