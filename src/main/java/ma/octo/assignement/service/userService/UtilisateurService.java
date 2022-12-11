package ma.octo.assignement.service.userService;

import ma.octo.assignement.domain.User;

import java.util.List;

public interface UtilisateurService {
    public List<User> listUtilisateurs();
    public User save(User user);

}
