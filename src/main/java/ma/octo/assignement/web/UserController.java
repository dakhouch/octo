package ma.octo.assignement.web;

import ma.octo.assignement.domain.User;
import ma.octo.assignement.service.UtilisateurService.UtilisateurService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("Users/")
public class UserController {
    Logger logger= LoggerFactory.getLogger(UserController.class);
    @Autowired
    UtilisateurService utilisateurService;

    @GetMapping("listOfUsers")
    List<User> loadAllUtilisateur() {
        logger.trace("mapping /listOfUsers");
        return utilisateurService.listUtilisateurs();
    }
}
