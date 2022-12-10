package ma.octo.assignement.service.transferService;

import ma.octo.assignement.domain.Account;
import ma.octo.assignement.domain.Transfer;
import ma.octo.assignement.dto.TransferDto;
import ma.octo.assignement.exceptions.CompteNonExistantException;
import ma.octo.assignement.exceptions.TransactionException;
import ma.octo.assignement.repository.TransferRepository;
import ma.octo.assignement.service.auditService.AuditService;
import ma.octo.assignement.service.accountService.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
public class TransferServiceImpl implements TransferService {
    Logger LOGGER = LoggerFactory.getLogger(TransferServiceImpl.class);
    private static final int MONTANT_MAXIMAL =10000 ;

    TransferRepository transferRepository;
    AccountService accountService;
    AuditService auditService;
    @Autowired
    public void setTransferRepository(TransferRepository transferRepository) {
        this.transferRepository = transferRepository;
    }
    @Autowired
    public void setAccountService(AccountService compteService) {
        this.accountService = compteService;
    }
    @Autowired
    public void setAuditService(AuditService auditService) {
        this.auditService = auditService;
    }

    @Override
    public List<Transfer> listTransfers() {
       return transferRepository.findAll();
    }
    @Override
    @Transactional
    public Transfer executeTransfer(TransferDto transferDto) throws CompteNonExistantException, TransactionException{
        LOGGER.info("transfer de montant : "+transferDto.getMontant().toString());
        //exceptions
        //remplace eqaul by ==
        if (transferDto.getMontant().intValue() == 0) {
            throw new TransactionException("Montant vide");
        }else if (transferDto.getMontant().floatValue() < 10) {
            throw new TransactionException("Montant minimal de transfer non atteint");
        } else if (transferDto.getMontant().floatValue() > MONTANT_MAXIMAL) {
            throw new TransactionException("Montant maximal de transfer dépassé");
        }
        //  <0 -> ==0
        if (transferDto.getMotif().length() ==0 || transferDto.getMotif()==null) {
            throw new TransactionException("Motif vide");
        }
       //get accounts
        Account compteEmetteur=accountService.getAccountbyNumC(transferDto.getNrCompteEmetteur());
        Account compteBenefeciaire=accountService.getAccountbyNumC(transferDto.getNrCompteBeneficiaire());
      //handle acounts
        accountService.subtractToAccount(compteEmetteur,transferDto.getMontant());
        accountService.addToAccount(compteBenefeciaire,transferDto.getMontant());

        Transfer transfer=new Transfer();
        transfer.setCompteBeneficiaire(compteBenefeciaire);
        transfer.setCompteEmetteur(compteEmetteur);
        transfer.setDateExecution(transferDto.getDate());
        transfer.setMotifTransfer(transferDto.getMotif());
        transfer.setMontantTransfer(transferDto.getMontant());
        auditService.auditTransfer(transferDto);
        return transferRepository.save(transfer);
    }

}
