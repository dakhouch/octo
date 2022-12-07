package ma.octo.assignement.service;

import ma.octo.assignement.domain.Account;
import ma.octo.assignement.dto.TransferDto;
import ma.octo.assignement.service.accountService.AccountService;
import ma.octo.assignement.service.transferService.TransferService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.math.BigDecimal;

@SpringBootTest
public class TransferServiceTest {
   @Autowired
  TransferService transferService;
   @Autowired
   AccountService accountService;

    @Test
    public void TransferTransactionTest(){
      //before
      Account compteEmeteurBefore=accountService.compteParNumC("010000A000001000");
      Account compteBeneficiaireBefore=accountService.compteParNumC("010000B025001000");
      BigDecimal soldeEmBefore=compteEmeteurBefore.getSolde();
      BigDecimal soldeBeBefore=compteBeneficiaireBefore.getSolde();

      TransferDto transferDto=new TransferDto();
      transferDto.setDate(null);
      transferDto.setMotif("null");
      transferDto.setMontant(BigDecimal.valueOf(10000.00));
      transferDto.setNrCompteEmetteur("010000A000001000");
      transferDto.setNrCompteBeneficiaire("010000B025001000");

      transferService.executerTransfer(transferDto);
      //after
      Account compteEmeteurAfter=accountService.compteParNumC("010000A000001000");
      Account compteBeneficiaireAfter=accountService.compteParNumC("010000B025001000");

    // test if soldeEmeteurBeforeTrasfer=soldeEmeteurAfterTransfer+montant
      Assertions.assertEquals(soldeEmBefore.subtract(transferDto.getMontant()),compteEmeteurAfter.getSolde());
    // test if soldeBeneficiaireBeforeTrasfer+montant=soldeBeneficiaireAfterTransfer
      Assertions.assertEquals(soldeBeBefore.add(transferDto.getMontant()),compteBeneficiaireAfter.getSolde());

      //test if transfer was added
      Assertions.assertEquals(2,transferService.listTransfers().size());


    }
}
