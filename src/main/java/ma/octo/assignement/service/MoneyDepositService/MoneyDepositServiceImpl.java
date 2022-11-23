package ma.octo.assignement.service.MoneyDepositService;
import ma.octo.assignement.domain.Account;
import ma.octo.assignement.domain.MoneyDeposit;
import ma.octo.assignement.dto.DepositDto;
import ma.octo.assignement.exceptions.CompteNonExistantException;
import ma.octo.assignement.exceptions.TransactionException;
import ma.octo.assignement.repository.MoneyDepositRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MoneyDepositServiceImpl implements MoneyDepositService {
    private static final int MONTANT_MAXIMAL =10000 ;
    Logger LOGGER = LoggerFactory.getLogger(MoneyDepositServiceImpl.class);
   @Autowired
    MoneyDepositRepository moneyDepositRepository;
    public MoneyDeposit executeDeposit(DepositDto depositDto, Account compteBe) throws TransactionException {
        LOGGER.info("depot de montant : "+depositDto.getMontant().toString());
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
        MoneyDeposit deposit = new MoneyDeposit();
            deposit.setMontant(depositDto.getMontant());
            deposit.setDateExecution(depositDto.getDate());
            deposit.setMotifDeposit(depositDto.getMotif());
            deposit.setCompteBeneficiaire(compteBe);
           return moneyDepositRepository.save(deposit);

    }
}
