package ma.octo.assignement.service.depositService;
import ma.octo.assignement.domain.Account;
import ma.octo.assignement.domain.Deposit;
import ma.octo.assignement.domain.Transfer;
import ma.octo.assignement.dto.DepositDto;
import ma.octo.assignement.exceptions.TransactionException;
import ma.octo.assignement.repository.DepositRepository;
import ma.octo.assignement.service.auditService.AuditService;
import ma.octo.assignement.service.accountService.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepositServiceImpl implements DepositService {
    private static final int MONTANT_MAXIMAL =10000 ;
    Logger LOGGER = LoggerFactory.getLogger(DepositServiceImpl.class);
   @Autowired
   DepositRepository depositRepository;
   @Autowired
   AccountService accountService;
   @Autowired
    AuditService auditService;

    public List<Deposit> listDeposit() {return depositRepository.findAll();}
    public void executeDeposit(DepositDto depositDto) throws TransactionException {
        LOGGER.info("depot de montant : "+depositDto.getMontant().toString());
        //exceptions
        //remplace eqaul by ==
        if (depositDto.getMontant() == null || depositDto.getMontant().intValue() == 0) {
            throw new TransactionException("Montant vide");
        }else if (depositDto.getMontant().intValue() < 10) {
            throw new TransactionException("Montant minimal de depot non atteint");
        } else if (depositDto.getMontant().intValue() > MONTANT_MAXIMAL) {
            throw new TransactionException("Montant maximal de depot dépassé");
        }
        //  <0 -> ==0
        if (depositDto.getMotif().length() ==0) {
            throw new TransactionException("Motif vide");
        }

        //get account beneficiare
        Account compteBe=accountService.compteParNumC(depositDto.getNrCompteBeneficiaire());
        //handle account
        accountService.handleAccount(compteBe,depositDto.getMontant(),true);

        Deposit deposit = new Deposit();
            deposit.setMontant(depositDto.getMontant());
            deposit.setDateExecution(depositDto.getDate());
            deposit.setMotifDeposit(depositDto.getMotif());
            deposit.setCompteBeneficiaire(compteBe);
           depositRepository.save(deposit);

           //audit
           auditService.auditDeposit(depositDto);

    }
}
