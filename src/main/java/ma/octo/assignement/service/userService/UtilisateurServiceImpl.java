package ma.octo.assignement.service.userService;

import ma.octo.assignement.domain.User;
import ma.octo.assignement.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UtilisateurServiceImpl implements UtilisateurService{
    Logger LOGGER = LoggerFactory.getLogger(UtilisateurServiceImpl.class);

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserRepository utilisateurRepository;
    @Override
    public List<User> listUtilisateurs() {
        LOGGER.info("recuperer les utilisateurs");
        return utilisateurRepository.findAll();
    }

    public void save(User user){
        // validation input


        // encrypt password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // save
        utilisateurRepository.save(user);
    }
}
