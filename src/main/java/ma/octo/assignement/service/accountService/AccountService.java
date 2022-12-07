package ma.octo.assignement.service.accountService;

import ma.octo.assignement.domain.Account;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService {
    public List<Account> listComptes();
    public Account compteParNumC(String numCompte);
    public void handleAccount(Account account, BigDecimal montant, boolean bool);
}
