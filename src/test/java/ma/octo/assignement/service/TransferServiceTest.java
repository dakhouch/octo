package ma.octo.assignement.service;

import ma.octo.assignement.domain.Account;
import ma.octo.assignement.domain.Audit;
import ma.octo.assignement.domain.Transfer;
import ma.octo.assignement.dto.TransferDto;
import ma.octo.assignement.exceptions.TransactionException;
import ma.octo.assignement.repository.AccountRepository;
import ma.octo.assignement.repository.AuditRepository;
import ma.octo.assignement.repository.TransferRepository;
import ma.octo.assignement.service.accountService.AccountServiceImpl;
import ma.octo.assignement.service.auditService.AuditServiceImpl;
import ma.octo.assignement.service.transferService.TransferServiceImpl;
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
public class TransferServiceTest {
   @InjectMocks
  TransferServiceImpl transferService;
   @InjectMocks
   AccountServiceImpl accountService;
   @InjectMocks
   AuditServiceImpl auditService;
   @Mock
   AccountRepository accountRepository;
   @Mock
   TransferRepository transferRepository;
   @Mock
    AuditRepository auditRepository;


    @BeforeAll
    public void initMocks(){
        transferService=new TransferServiceImpl();
        accountService=new AccountServiceImpl() ;
        auditService=new AuditServiceImpl();
        accountService.setAccountRepository(accountRepository);
        auditService.setAuditRepository(auditRepository);
        transferService.setAuditService(auditService);
        transferService.setAccountService(accountService);
        transferService.setTransferRepository(transferRepository);
    }

    @Test
    public void test_with_normal_case() {
        //Arrange
        Account compteEmeteurAvant = new Account();
        compteEmeteurAvant.setNrCompte("010000A000001000");
        compteEmeteurAvant.setRib("RIB1");
        compteEmeteurAvant.setSolde(BigDecimal.valueOf(200000L));

        Account compteBeneficiaireAvant = new Account();
        compteBeneficiaireAvant.setNrCompte("010000B025001000");
        compteBeneficiaireAvant.setRib("RIB2");
        compteBeneficiaireAvant.setSolde(BigDecimal.valueOf(140000L));


        BigDecimal soldeEmetteurAvant = compteEmeteurAvant.getSolde();
        BigDecimal soldeBeneficiareAvant = compteBeneficiaireAvant.getSolde();

        TransferDto transferDto = new TransferDto();
        transferDto.setDate(null);
        transferDto.setMotif("null");
        transferDto.setMontant(BigDecimal.valueOf(10000.00));
        transferDto.setNrCompteEmetteur("010000A000001000");
        transferDto.setNrCompteBeneficiaire("010000B025001000");

        //mokito repository
        when(accountRepository.save(any(Account.class))).then(returnsFirstArg());
        when(transferRepository.save(any(Transfer.class))).then(returnsFirstArg());
        when(auditRepository.save(any(Audit.class))).then(returnsFirstArg());
        when(accountRepository.findByNrCompte(compteEmeteurAvant.getNrCompte())).thenReturn(compteEmeteurAvant);
        when(accountRepository.findByNrCompte(compteBeneficiaireAvant.getNrCompte())).thenReturn(compteBeneficiaireAvant);


        //Act

        Transfer transfer = transferService.executeTransfer(transferDto);

        BigDecimal soldeEmeteurApres = transfer.getCompteEmetteur().getSolde();
        BigDecimal soldeBeneficiaireApres = transfer.getCompteBeneficiaire().getSolde();

        //Assert
        assertThat(soldeEmeteurApres).isEqualTo(soldeEmetteurAvant.subtract(transfer.getMontantTransfer()));
        assertThat(soldeBeneficiaireApres).isEqualTo(soldeBeneficiareAvant.add(transfer.getMontantTransfer()));
    }
    @Test
    public void test_if_montant_less_than_10() throws Exception{
        //Arrange

        TransferDto transferDto=new TransferDto();
        transferDto.setDate(null);
        transferDto.setMotif("null");
        transferDto.setMontant(BigDecimal.valueOf(9));
        transferDto.setNrCompteEmetteur("010000A000001000");
        transferDto.setNrCompteBeneficiaire("010000B025001000");

        //Act

        TransactionException thrown = Assertions.assertThrows(TransactionException.class, () -> {
            transferService.executeTransfer(transferDto);
        });
        //assert
        assertThat("Montant minimal de transfer non atteint").isEqualTo(thrown.getMessage());
    }

    @Test
    public void test_if_montant_grater_than_maximalSold() throws Exception{
        //Arrange
        TransferDto transferDto=new TransferDto();
        transferDto.setDate(null);
        transferDto.setMotif("null");
        transferDto.setMontant(BigDecimal.valueOf(10000.01));
        transferDto.setNrCompteEmetteur("010000A000001000");
        transferDto.setNrCompteBeneficiaire("010000B025001000");

        //Act

        TransactionException thrown = Assertions.assertThrows(TransactionException.class, () -> {
            transferService.executeTransfer(transferDto);
        });
        //assert
        assertThat("Montant maximal de transfer dépassé").isEqualTo(thrown.getMessage());
    }

    @Test
    public void test_if_sold_equalZero() throws Exception{
        //Arrange
        TransferDto transferDto=new TransferDto();
        transferDto.setDate(null);
        transferDto.setMotif("null");
        transferDto.setMontant(BigDecimal.valueOf(0));
        transferDto.setNrCompteEmetteur("010000A000001000");
        transferDto.setNrCompteBeneficiaire("010000B025001000");

        //Act

        TransactionException thrown = Assertions.assertThrows(TransactionException.class, () -> {
            transferService.executeTransfer(transferDto);
        });
        //assert
        assertThat("Montant vide").isEqualTo(thrown.getMessage());
    }
}
