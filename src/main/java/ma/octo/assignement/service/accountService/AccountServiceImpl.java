package ma.octo.assignement.service.accountService;

import ma.octo.assignement.domain.Account;

import ma.octo.assignement.exceptions.CompteNonExistantException;
import ma.octo.assignement.exceptions.SoldeDisponibleInsuffisantException;
import ma.octo.assignement.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {
    private Logger LOGGER = LoggerFactory.getLogger(AccountServiceImpl.class);

    private AccountRepository accountRepository;
    @Override
    public List<Account> listComptes() {
        return accountRepository.findAll();
    }

    @Override
    public Account getAccountbyNumC(String numCompte) {
       LOGGER.info("compte num "+numCompte);
        return accountRepository.findByNrCompte(numCompte);
    }

    public Account addToAccount(Account account, BigDecimal montant) throws CompteNonExistantException,SoldeDisponibleInsuffisantException{
        //compte em et compte be dans un seul condition
        if (account == null) {
            throw new CompteNonExistantException("Compte Non existant");
        }
        LOGGER.info("ajouter le montant "+montant.floatValue()+"au compte num "+account.getNrCompte());

        account.setSolde(account.getSolde().add(montant));

        return accountRepository.save(account);
    }
    @Override
    public Account subtractToAccount(Account account, BigDecimal montant) throws CompteNonExistantException,SoldeDisponibleInsuffisantException{
        //compte em et compte be dans un seul condition
        if (account == null) {
            throw new CompteNonExistantException("Compte Non existant");
        }
        LOGGER.info("soustracter le montant "+montant.floatValue()+"au compte num "+account.getNrCompte());
        //trow exception if solde insufisant
        if (account.getSolde().floatValue() - montant.floatValue() < 0) {
            throw new SoldeDisponibleInsuffisantException("Solde insuffisant pour l'utilisateur");
        }
         account.setSolde(account.getSolde().subtract(montant));
        return accountRepository.save(account);
    }
    @Autowired
    public void setAccountRepository(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }
}

