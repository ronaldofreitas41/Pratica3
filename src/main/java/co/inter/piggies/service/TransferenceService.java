package co.inter.piggies.service;

import co.inter.piggies.Kakfa.Events.TransferenceEvent;
import co.inter.piggies.Kakfa.Producer.TransferenceProducer;
import co.inter.piggies.Repository.TransferenceRepository;
import co.inter.piggies.domain.transference.Transference;
import co.inter.piggies.domain.transference.TransferenceRequestDTO;
import co.inter.piggies.domain.transference.TransferenceResponseDTO;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.exceptions.HttpStatusException;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.transaction.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Singleton
public class TransferenceService {

    @Inject
    TransferenceRepository transferenceRepository;

    @Inject
    TransferenceProducer transferenceProducer;

    public TransferenceResponseDTO getTransferenceByID(UUID id) {

        Transference transference = transferenceRepository.findById(id).orElseThrow(
                () -> new HttpStatusException(HttpStatus.NOT_FOUND,
                        "Não foi encontrado uma transferencia com o ID informado")
        );

        return new TransferenceResponseDTO(transference.getId(),
                transference.getAccountFrom(),
                transference.getAccountTo(),
                transference.getAmmount(),
                transference.getStatus());
    }

    public List<TransferenceResponseDTO> getAllTransferences() {

        List<Transference> transferences = transferenceRepository.findAll();

        return transferences.stream().map(
                transference -> new TransferenceResponseDTO(
                        transference.getId(),
                        transference.getAccountFrom(),
                        transference.getAccountTo(),
                        transference.getAmmount(),
                        transference.getStatus()
                )
        ).toList();
    }

    @Transactional
    public TransferenceResponseDTO createTransference(TransferenceRequestDTO body) {

        //1º - Verifico os pre requesitos definidos em regra
        if (body.ammount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new HttpStatusException(HttpStatus.BAD_REQUEST,
                    "O valor da transferencia tem que ser maior que 0");
        }

        if (body.ammount().scale() > 2) {
            throw new HttpStatusException(HttpStatus.BAD_REQUEST,
                    "O seu valor de transferencia pode te somente 2 casas decimais");
        }

        //2º - Crio minha entidade Transferencia e seto o Status dela como Pending
        Transference transference = new Transference();
        transference.setAmmount(body.ammount());
        transference.setAccountFrom(body.accountFrom());
        transference.setAccountTo(body.accountTo());
        transference.setStatus("Pending");

        //3º - Salvo meu dado no banco
        transferenceRepository.save(transference);

        //4º - Crio meu evento
        TransferenceEvent event = new TransferenceEvent(
                transference.getId(),
                transference.getAccountFrom(),
                transference.getAccountTo(),
                transference.getAmmount(),
                transference.getStatus()
        );

        //5º - Chamo meu producer para disparar o evento
        transferenceProducer.send(transference.getAccountFrom(),event);

        //6º - Retorno meu evento salvo
        return new TransferenceResponseDTO(
                transference.getId(),
                transference.getAccountFrom(),
                transference.getAccountTo(),
                transference.getAmmount(),
                transference.getStatus());
    }
}
