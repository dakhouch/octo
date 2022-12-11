package ma.octo.assignement.web;

import ma.octo.assignement.dto.DepositDto;
import ma.octo.assignement.exceptions.CompteNonExistantException;
import ma.octo.assignement.exceptions.SoldeDisponibleInsuffisantException;
import ma.octo.assignement.exceptions.TransactionException;
import ma.octo.assignement.service.auditService.AuditService;
import ma.octo.assignement.service.accountService.AccountService;
import ma.octo.assignement.service.depositService.DepositService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("deposits")
public class DepositController {
    Logger logger= LoggerFactory.getLogger(DepositController.class);
    @Autowired
    AccountService compteService;
    @Autowired
    DepositService depositService;
    @Autowired
    AuditService auditService;

    @PreAuthorize("hasAuthority('user')")
    @PostMapping("/executeDeposits")
    @ResponseStatus(HttpStatus.CREATED)
    public void createTransaction(@RequestBody DepositDto depositDto)
            throws SoldeDisponibleInsuffisantException, CompteNonExistantException, TransactionException {
     logger.trace("mapping /executeDeposits");
        depositService.executeDeposit(depositDto);
    }

}
