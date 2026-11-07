package co.inter.piggies.controller;

import co.inter.piggies.domain.transference.TransferenceRequestDTO;
import co.inter.piggies.domain.transference.TransferenceResponseDTO;
import co.inter.piggies.service.TransferenceService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import jakarta.inject.Inject;

import java.util.List;
import java.util.UUID;

@Controller("/api/transference")
public class TransferencesController {


    private final TransferenceService transferenceService;

    public TransferencesController(TransferenceService transferenceService) {
        this.transferenceService = transferenceService;
    }

    /*
     * GET - api/trasference/
     * Busca todas as transferencias no banco
     */

    @Get
    public HttpResponse<List<TransferenceResponseDTO>> getALlTransferences(){
        return HttpResponse.ok(transferenceService.getAllTransferences());
    }

    /*
     * GET - api/trasference/{id}
     * Busca uma transferencia filtrando pelo ID
     */

    @Get("/{id}")
    public HttpResponse<TransferenceResponseDTO> getTransferenceByID(@PathVariable UUID id){
        return HttpResponse.ok(transferenceService.getTransferenceByID(id));
    }

    /*
    * POST - api/trasference
    * Cria uma transferencia
    */

    @Post
    public HttpResponse <TransferenceResponseDTO> createTransference(@Body TransferenceRequestDTO body){
        return HttpResponse.ok(transferenceService.createTransference(body));
    }
}
