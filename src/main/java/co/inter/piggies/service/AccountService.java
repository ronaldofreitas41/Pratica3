package co.inter.piggies.service;

import co.inter.piggies.domain.account.Account;
import co.inter.piggies.domain.account.AccountRequestDTO;
import co.inter.piggies.domain.account.AccountResponseDTO;
import co.inter.piggies.Repository.AccountRepository;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.exceptions.HttpStatusException;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.util.List;
import java.util.UUID;

@Singleton
public class AccountService {

    @Inject
    AccountRepository accountRepository;

    public AccountResponseDTO getAccountByID (UUID id){
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new HttpStatusException(HttpStatus.NOT_FOUND, "Conta Não encontrada com esse ID"));

        return new AccountResponseDTO(
                account.getAccountid(),
                account.getInitialBalance()
        );
    }

    public List<AccountResponseDTO> getAccounts (){
        List<Account> accounts = accountRepository.findAll();

        return accounts.stream()
                .map(account -> new AccountResponseDTO(
                        account.getAccountid(),
                        account.getInitialBalance()
                ))
                .toList();
    }

    public AccountResponseDTO CreateAccount(AccountRequestDTO body){

        if(accountRepository.existsById(body.id())){
            throw new HttpStatusException(HttpStatus.CONFLICT,
                    "Conta já existente com o Id: "+body.id());
        }

        Account account = new Account(body.id(), body.initialBalance());
        accountRepository.save(account);

        return new AccountResponseDTO(body.id(),body.initialBalance());

    }
}
