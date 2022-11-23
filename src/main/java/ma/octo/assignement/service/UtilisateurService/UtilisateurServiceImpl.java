package ma.octo.assignement.service.UtilisateurService;

import ma.octo.assignement.domain.User;
import ma.octo.assignement.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UtilisateurServiceImpl implements UtilisateurService{
    Logger LOGGER = LoggerFactory.getLogger(UtilisateurServiceImpl.class);
    @Autowired
    UserRepository utilisateurRepository;
    @Override
    public List<User> listUtilisateurs() {
        LOGGER.info("recuperer les utilisateurs");
        return utilisateurRepository.findAll();
    }
}
