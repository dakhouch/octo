package ma.octo.assignement.service.CompteService;

import ma.octo.assignement.domain.Account;

import ma.octo.assignement.domain.util.EventType;
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
public class CompteServiceImpl implements CompteService{
    Logger LOGGER = LoggerFactory.getLogger(CompteServiceImpl.class);
    @Autowired
    AccountRepository compteRepository;
    @Override
    public List<Account> listComptes() {
        return compteRepository.findAll();
    }

    @Override
    public Account compteParNumC(String numCompte) {
       LOGGER.info("compte num "+numCompte);
        return compteRepository.findByNrCompte(numCompte);
    }

    public  Account handleAccount(Account account, BigDecimal montant,boolean bool) throws CompteNonExistantException,SoldeDisponibleInsuffisantException{
        //compte em et compte be dans un seul condition
        if (account == null) {
            throw new CompteNonExistantException("Compte Non existant");
        }
        //trow exception if solde insufisant
        if (account.getSolde().intValue() - montant.intValue() < 0 && !bool) {
            throw new SoldeDisponibleInsuffisantException("Solde insuffisant pour l'utilisateur");
        }
       if(bool) account.setSolde(account.getSolde().add(montant));
      else{account.setSolde(account.getSolde().subtract(montant));}
      return compteRepository.save(account);
    }


}

