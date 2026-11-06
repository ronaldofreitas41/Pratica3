package co.inter.piggies.controller;


import co.inter.piggies.domain.account.AccountRequestDTO;
import co.inter.piggies.domain.account.AccountResponseDTO;
import co.inter.piggies.service.AccountService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller("/api/account")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @Get
    public HttpResponse<List<AccountResponseDTO>> getallAccounts (){
        return HttpResponse.ok(accountService.getAccounts());
    }

    @Get("/{id}")
    public HttpResponse<AccountResponseDTO> getAccountByID (@PathVariable UUID id){
        return HttpResponse.ok(accountService.getAccountByID(id));
    }

    @Post
    public HttpResponse<AccountResponseDTO> createAccount(@Body AccountRequestDTO body){
        return HttpResponse.created(accountService.CreateAccount(body));
    }

}
