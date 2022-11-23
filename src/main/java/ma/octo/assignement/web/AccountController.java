package ma.octo.assignement.web;

import ma.octo.assignement.domain.Account;
import ma.octo.assignement.service.CompteService.CompteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("accounts/")
public class AccountController {
    Logger logger= LoggerFactory.getLogger(AccountController.class);
    @Autowired
    CompteService compteService;

    @GetMapping("listOfAccounts")
    List<Account> loadAllCompte() {
        logger.trace("mapping /listOfAccounts");
        return compteService.listComptes();
    }
}
