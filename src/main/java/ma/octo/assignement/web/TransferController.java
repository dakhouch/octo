package ma.octo.assignement.web;
import ma.octo.assignement.domain.Account;
import ma.octo.assignement.domain.Transfer;
import ma.octo.assignement.dto.TransferDto;
import ma.octo.assignement.exceptions.CompteNonExistantException;
import ma.octo.assignement.exceptions.SoldeDisponibleInsuffisantException;
import ma.octo.assignement.exceptions.TransactionException;
import ma.octo.assignement.service.AuditService.AuditService;
import ma.octo.assignement.service.CompteService.CompteService;
import ma.octo.assignement.service.TransferService.TransferService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//restcontroller value --> requestmapping value
@RestController
@RequestMapping(value = "/transfers")
class TransferController {

    Logger LOGGER = LoggerFactory.getLogger(TransferController.class);
    @Autowired
    private TransferService transferService;
    @Autowired
    CompteService compteService;
    @Autowired
    AuditService auditTransferService;


    @GetMapping("listOfTransfers")
    List<Transfer> loadAllTransfers() {
        LOGGER.trace("mapping listOfTransfers");
       return transferService.listTransfers();
    }

    @PostMapping("/executeTransfers")
    @ResponseStatus(HttpStatus.CREATED)
    public void createTransaction(@RequestBody TransferDto transferDto)
            throws SoldeDisponibleInsuffisantException, CompteNonExistantException, TransactionException {
        LOGGER.trace("mapping executeTransfers");
        Account compteEm=compteService.compteParNumC(transferDto.getNrCompteEmetteur());
        Account compteBe=compteService.compteParNumC(transferDto.getNrCompteBeneficiaire());
        compteService.handleAccount(compteEm,transferDto.getMontant(),false);
        compteService.handleAccount(compteBe,transferDto.getMontant(),true);
        transferService.executerTransfer(transferDto,compteEm,compteBe);
        auditTransferService.auditTransfer(transferDto);
    }
}