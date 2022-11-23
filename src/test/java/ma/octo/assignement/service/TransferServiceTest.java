package ma.octo.assignement.service;

import ma.octo.assignement.domain.Account;
import ma.octo.assignement.dto.TransferDto;
import ma.octo.assignement.service.CompteService.CompteService;
import ma.octo.assignement.service.TransferService.TransferService;
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
  CompteService compteService;

    @Test
    public void TransferTransactionTest(){
      Account compteEmeteurBefore=compteService.compteParNumC("010000A000001000");
      Account compteBeneficiaireBefore=compteService.compteParNumC("010000B025001000");
      BigDecimal soldeEmBefore=compteEmeteurBefore.getSolde();
      BigDecimal soldeBeBefore=compteBeneficiaireBefore.getSolde();
      TransferDto transferDto=new TransferDto();
      transferDto.setDate(null);
      transferDto.setMotif("null");
      transferDto.setMontant(BigDecimal.valueOf(10000.00));
      compteService.handleAccount(compteBeneficiaireBefore,transferDto.getMontant(), true);
      compteService.handleAccount(compteEmeteurBefore,transferDto.getMontant(),false);
      Account compteEmeteurAfter=compteService.compteParNumC("010000A000001000");
      Account compteBeneficiaireAfter=compteService.compteParNumC("010000B025001000");
    // test if soldeEmeteurBeforeTrasfer=soldeEmeteurAfterTransfer+montant
      Assertions.assertEquals(soldeEmBefore.subtract(transferDto.getMontant()),compteEmeteurAfter.getSolde());
    // test if soldeBeneficiaireBeforeTrasfer+montant=soldeBeneficiaireAfterTransfer
      Assertions.assertEquals(soldeBeBefore.add(transferDto.getMontant()),compteBeneficiaireAfter.getSolde());
    }
}
