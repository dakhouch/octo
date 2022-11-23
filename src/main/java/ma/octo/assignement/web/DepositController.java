package ma.octo.assignement.web;

import ma.octo.assignement.domain.Account;
import ma.octo.assignement.dto.DepositDto;
import ma.octo.assignement.exceptions.CompteNonExistantException;
import ma.octo.assignement.exceptions.SoldeDisponibleInsuffisantException;
import ma.octo.assignement.exceptions.TransactionException;
import ma.octo.assignement.service.AuditService.AuditService;
import ma.octo.assignement.service.CompteService.CompteService;
import ma.octo.assignement.service.MoneyDepositService.MoneyDepositService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("deposits")
public class DepositController {
    Logger logger= LoggerFactory.getLogger(DepositController.class);
    @Autowired
    CompteService compteService;
    @Autowired
    MoneyDepositService depositService;
    @Autowired
    AuditService auditService;

    @PostMapping("/executeDeposits")
    @ResponseStatus(HttpStatus.CREATED)
    public void createTransaction(@RequestBody DepositDto depositDto)
            throws SoldeDisponibleInsuffisantException, CompteNonExistantException, TransactionException {
     logger.trace("mapping /executeDeposits");
        Account compteBe=compteService.compteParNumC(depositDto.getNrCompteBeneficiaire());
        compteService.handleAccount(compteBe,depositDto.getMontant(),true);
        depositService.executeDeposit(depositDto,compteBe);
        auditService.auditDeposit(depositDto);
    }

}
