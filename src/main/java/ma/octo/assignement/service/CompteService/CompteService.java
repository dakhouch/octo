package ma.octo.assignement.service.CompteService;

import ma.octo.assignement.domain.Account;
import ma.octo.assignement.domain.util.EventType;

import java.math.BigDecimal;
import java.util.List;

public interface CompteService {
    public List<Account> listComptes();
    public Account compteParNumC(String numCompte);
    public Account handleAccount(Account account, BigDecimal montant, boolean bool);
}
