package ma.octo.assignement.service.accountService;

import ma.octo.assignement.domain.Account;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService {
    public List<Account> listComptes();
    public Account getAccountbyNumC(String numCompte);
    public Account addToAccount(Account account, BigDecimal montant);
    public Account subtractToAccount(Account account, BigDecimal montant);
}
