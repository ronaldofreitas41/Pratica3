package co.inter.piggies.Kakfa.Producer;

import co.inter.piggies.Kakfa.Events.TransferenceEvent;
import co.inter.piggies.Kakfa.Topics;
import io.micronaut.configuration.kafka.annotation.KafkaClient;
import io.micronaut.configuration.kafka.annotation.KafkaKey;
import io.micronaut.configuration.kafka.annotation.Topic;

import java.util.UUID;

@KafkaClient
public interface TransferenceProducer {

    /*
    * Por meio do tópico TRANSFERENCES eu envio meus eventos de transferencia
    * A Key passada serve para direcionar eventos de mesma key para a mesma partição do Kafka
    */
    @Topic(Topics.TRANSFERENCES)
     void send(@KafkaKey UUID accountFromId, TransferenceEvent event);
}
