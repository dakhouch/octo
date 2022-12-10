package ma.octo.assignement.service;

import ma.octo.assignement.domain.Account;
import ma.octo.assignement.exceptions.CompteNonExistantException;
import ma.octo.assignement.exceptions.SoldeDisponibleInsuffisantException;
import ma.octo.assignement.repository.AccountRepository;
import ma.octo.assignement.service.accountService.AccountServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {
    @InjectMocks
    AccountServiceImpl accountService;
    @Mock
    AccountRepository accountRepository;


    @BeforeAll
    public void initMocks(){
        accountService=new AccountServiceImpl() ;
        accountService.setAccountRepository(accountRepository);

    }
    @ParameterizedTest
    @ValueSource(floats = {0,100000,200000})
    public void test_if_sold_retired_from_Account(float montant){
        //Arrange
        when(accountRepository.save(any(Account.class))).then(returnsFirstArg());
        Account compteAvant = new Account();
        BigDecimal montantRetirer=BigDecimal.valueOf(montant);
        compteAvant.setNrCompte("010000A000001000");
        compteAvant.setRib("RIB1");
        compteAvant.setSolde(BigDecimal.valueOf(200000));
        BigDecimal soldeAvant=compteAvant.getSolde();
        //Act
        Account compteApres=accountService.subtractToAccount(compteAvant,montantRetirer);
        BigDecimal soldeApres=compteApres.getSolde();
        //Assert
        assertThat(soldeApres).isEqualTo(soldeAvant.subtract(montantRetirer));
    }
    @ParameterizedTest
    @ValueSource(floats = {0,100000,200000})
    public void test_if_sold_added_to_Account(float montant){
        //Arrange
        when(accountRepository.save(any(Account.class))).then(returnsFirstArg());
        Account compteAvant = new Account();
        BigDecimal montantRetirer=BigDecimal.valueOf(montant);
        compteAvant.setNrCompte("010000A000001000");
        compteAvant.setRib("RIB1");
        compteAvant.setSolde(BigDecimal.valueOf(200000));
        BigDecimal soldeAvant=compteAvant.getSolde();
        //Act
        Account compteApres=accountService.addToAccount(compteAvant,montantRetirer);
        BigDecimal soldeApres=compteApres.getSolde();
        //Assert
        assertThat(soldeApres).isEqualTo(soldeAvant.add(montantRetirer));
    }
    @Test()
    public void test_exception_withdrawing_amount_greaterThan_theBalance() throws Exception{
        //Arrange
        Account compteAvant = new Account();
        BigDecimal montantRetirer=BigDecimal.valueOf(250000);
        compteAvant.setNrCompte("010000A000001000");
        compteAvant.setRib("RIB1");
        compteAvant.setSolde(BigDecimal.valueOf(200000));
        //Act
        SoldeDisponibleInsuffisantException thrown = Assertions.assertThrows(SoldeDisponibleInsuffisantException.class, () -> {
                Account compteApres=accountService.subtractToAccount(compteAvant,montantRetirer);
        });

        //assert
        assertThat("Solde insuffisant pour l'utilisateur").isEqualTo(thrown.getMessage());
    }
    @Test()
    public void test_exception_if_account_is_null() throws Exception{
        //Arrange
        Account compteAvant = null;
        BigDecimal montantRetirer=BigDecimal.valueOf(250000);

        //Act
        CompteNonExistantException thrown = Assertions.assertThrows(CompteNonExistantException.class, () -> {
            Account compteApres=accountService.addToAccount(compteAvant,montantRetirer);
        });

        //assert
        assertThat("Compte Non existant").isEqualTo(thrown.getMessage());
    }
}
