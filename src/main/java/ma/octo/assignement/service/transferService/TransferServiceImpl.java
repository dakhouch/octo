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
import java.util.List;

@Service
public class TransferServiceImpl implements TransferService {
    Logger LOGGER = LoggerFactory.getLogger(TransferServiceImpl.class);
    private static final int MONTANT_MAXIMAL =10000 ;
    @Autowired
    TransferRepository transferRepository;
    @Autowired
    AccountService compteService;
    @Autowired
    AuditService auditService;

    @Override
    public List<Transfer> listTransfers() {
        return transferRepository.findAll();
    }
    public void executerTransfer(TransferDto transferDto) throws CompteNonExistantException, TransactionException{
        LOGGER.info("transfer de montant : "+transferDto.getMontant().toString());
        //exceptions
        //remplace eqaul by ==
        if (transferDto.getMontant() == null || transferDto.getMontant().intValue() == 0) {
            throw new TransactionException("Montant vide");
        }else if (transferDto.getMontant().intValue() < 10) {
            throw new TransactionException("Montant minimal de transfer non atteint");
        } else if (transferDto.getMontant().intValue() > MONTANT_MAXIMAL) {
            throw new TransactionException("Montant maximal de transfer dépassé");
        }
        //  <0 -> ==0
        if (transferDto.getMotif().length() ==0 || transferDto.getMotif()==null) {
            throw new TransactionException("Motif vide");
        }
       //get accounts
        Account compteEm=compteService.compteParNumC(transferDto.getNrCompteEmetteur());
        Account compteBe=compteService.compteParNumC(transferDto.getNrCompteBeneficiaire());
      //handle acounts
        compteService.handleAccount(compteEm,transferDto.getMontant(),false);
        compteService.handleAccount(compteBe,transferDto.getMontant(),true);

        Transfer transfer=new Transfer();
        transfer.setCompteBeneficiaire(compteBe);
        transfer.setCompteEmetteur(compteEm);
        transfer.setDateExecution(transferDto.getDate());
        transfer.setMotifTransfer(transferDto.getMotif());
        transfer.setMontantTransfer(transferDto.getMontant());
        transferRepository.save(transfer);
        auditService.auditTransfer(transferDto);
    }
}
