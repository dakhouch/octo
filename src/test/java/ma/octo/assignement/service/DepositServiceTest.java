package ma.octo.assignement.service;

import ma.octo.assignement.domain.Account;
import ma.octo.assignement.domain.Audit;
import ma.octo.assignement.domain.Deposit;
import ma.octo.assignement.dto.DepositDto;
import ma.octo.assignement.exceptions.TransactionException;
import ma.octo.assignement.repository.AccountRepository;
import ma.octo.assignement.repository.AuditRepository;
import ma.octo.assignement.repository.DepositRepository;
import ma.octo.assignement.service.accountService.AccountServiceImpl;
import ma.octo.assignement.service.auditService.AuditServiceImpl;
import ma.octo.assignement.service.depositService.DepositServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
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
public class DepositServiceTest {
    @InjectMocks
    AccountServiceImpl accountService;
    @InjectMocks
    DepositServiceImpl depositService;

    @InjectMocks
    AuditServiceImpl auditService;

    @Mock
    DepositRepository depositRepository;
    @Mock
    AccountRepository accountRepository;
    @Mock
    AuditRepository auditRepository;


    @BeforeAll
    public void initMocks(){
        depositService=new DepositServiceImpl();
        accountService=new AccountServiceImpl() ;
        auditService=new AuditServiceImpl();
        accountService.setAccountRepository(accountRepository);
        auditService.setAuditRepository(auditRepository);
        depositService.setAuditService(auditService);
        depositService.setAccountService(accountService);
        depositService.setDepositRepository(depositRepository);
    }
    @Test
    public void test_with_normal_case() {
        //Arrange

        Account compteBeneficiaireAvant = new Account();
        compteBeneficiaireAvant.setNrCompte("010000B025001000");
        compteBeneficiaireAvant.setRib("RIB2");
        compteBeneficiaireAvant.setSolde(BigDecimal.valueOf(140000L));

        BigDecimal soldeBeneficiareAvant = compteBeneficiaireAvant.getSolde();

        DepositDto depositDto=new DepositDto();
        depositDto.setDate(null);
        depositDto.setMotif("null");
        depositDto.setNomEmetteur("anass");
        depositDto.setMontant(BigDecimal.valueOf(10000));
        depositDto.setNrCompteBeneficiaire("010000B025001000");

        //mokito repository
        when(accountRepository.save(any(Account.class))).then(returnsFirstArg());
        when(depositRepository.save(any(Deposit.class))).then(returnsFirstArg());
        when(auditRepository.save(any(Audit.class))).then(returnsFirstArg());
        when(accountRepository.findByNrCompte(compteBeneficiaireAvant.getNrCompte())).thenReturn(compteBeneficiaireAvant);


        //Act

        Deposit deposit = depositService.executeDeposit(depositDto);
        BigDecimal soldeBeneficiaireApres = deposit.getCompteBeneficiaire().getSolde();

        //Assert
        assertThat(soldeBeneficiaireApres).isEqualTo(soldeBeneficiareAvant.add(deposit.getMontant()));
    }
    @Test
    public void test_if_montant_less_than_10() throws Exception{
        //Arrange

        DepositDto depositDto=new DepositDto();
        depositDto.setDate(null);
        depositDto.setMotif("null");
        depositDto.setMontant(BigDecimal.valueOf(9));
        depositDto.setNrCompteBeneficiaire("010000B025001000");

        //Act

        TransactionException thrown = Assertions.assertThrows(TransactionException.class, () -> {
            depositService.executeDeposit(depositDto);
        });
        //assert
        assertThat("Montant minimal de depot non atteint").isEqualTo(thrown.getMessage());
    }

    @Test
    public void test_if_montant_grater_than_maximalSold() throws Exception{
        //Arrange
        DepositDto depositDto=new DepositDto();
        depositDto.setDate(null);
        depositDto.setMotif("null");
        depositDto.setMontant(BigDecimal.valueOf(10000.01));
        depositDto.setNrCompteBeneficiaire("010000B025001000");
        //Act
        TransactionException thrown = Assertions.assertThrows(TransactionException.class, () -> {
            depositService.executeDeposit(depositDto);
        });
        //assert
        assertThat("Montant maximal de depot dépassé").isEqualTo(thrown.getMessage());
    }

    @Test
    public void test_if_sold_equalZero() throws Exception{
        //Arrange
        DepositDto depositDto=new DepositDto();
        depositDto.setDate(null);
        depositDto.setMotif("null");
        depositDto.setMontant(BigDecimal.valueOf(0));
        depositDto.setNrCompteBeneficiaire("010000B025001000");

        //Act

        TransactionException thrown = Assertions.assertThrows(TransactionException.class, () -> {
            depositService.executeDeposit(depositDto);
        });
        //assert
        assertThat("Montant vide").isEqualTo(thrown.getMessage());
    }
}
