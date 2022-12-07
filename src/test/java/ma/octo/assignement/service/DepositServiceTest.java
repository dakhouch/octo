package ma.octo.assignement.service;

import ma.octo.assignement.domain.Account;
import ma.octo.assignement.dto.DepositDto;
import ma.octo.assignement.service.accountService.AccountService;
import ma.octo.assignement.service.depositService.DepositService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest
public class DepositServiceTest {
    @Autowired
    AccountService accountService;
    @Autowired
    DepositService depositService;

    @Test
    public void DepositTransactionTest(){
        //before
        Account compteBeneficiaireBefore=accountService.compteParNumC("010000B025001000");
        BigDecimal soldeBeBefore=compteBeneficiaireBefore.getSolde();

        DepositDto depositDto=new DepositDto();
        depositDto.setNomEmetteur("saadia");
        depositDto.setNrCompteBeneficiaire("010000B025001000");
        depositDto.setDate(null);
        depositDto.setMotif("null");
        depositDto.setMontant(BigDecimal.valueOf(10000));

        depositService.executeDeposit(depositDto);
        //after
        Account compteBeneficiaireAfter=accountService.compteParNumC("010000B025001000");
        BigDecimal soldeBeAfter=compteBeneficiaireAfter.getSolde();

// test if soldeEmeteurBeforeTrasfer=soldeEmeteurAfterTransfer+montant
        Assertions.assertEquals(soldeBeBefore,soldeBeAfter.subtract(depositDto.getMontant()));
        Assertions.assertEquals(1,depositService.listDeposit().size());
    }
}
