package ma.octo.assignement.service;

import ma.octo.assignement.domain.Account;
import ma.octo.assignement.dto.DepositDto;
import ma.octo.assignement.service.CompteService.CompteService;
import ma.octo.assignement.service.MoneyDepositService.MoneyDepositService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest
public class MoneyDepositServiceTest {
    @Autowired
    CompteService compteService;
    @Autowired
    MoneyDepositService moneyDepositService;

    @Test
    public void DepositTransactionTest(){
        Account compteBeneficiaireBefore=compteService.compteParNumC("010000B025001000");
        BigDecimal soldeBeBefore=compteBeneficiaireBefore.getSolde();
        DepositDto depositDto=new DepositDto();
        depositDto.setNomEmetteur("saadia");
        depositDto.setDate(null);
        depositDto.setMotif("null");
        depositDto.setMontant(BigDecimal.valueOf(10000.00));
        compteService.handleAccount(compteBeneficiaireBefore,depositDto.getMontant(), true);
        Account compteBeneficiaireAfter=compteService.compteParNumC("010000B025001000");
        moneyDepositService.executeDeposit(depositDto,compteBeneficiaireBefore);
        BigDecimal soldeBeAfter=compteBeneficiaireAfter.getSolde();
// test if soldeEmeteurBeforeTrasfer=soldeEmeteurAfterTransfer+montant
        Assertions.assertEquals(soldeBeBefore.add(depositDto.getMontant()),soldeBeAfter);

    }
}
