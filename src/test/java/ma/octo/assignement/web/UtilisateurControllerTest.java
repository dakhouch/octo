package ma.octo.assignement.web;


import ma.octo.assignement.service.UtilisateurService.UtilisateurService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UtilisateurControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    UtilisateurService utilisateurService;
    @Test
    public void getAllUtilisateur() throws Exception {
        mockMvc.perform(get("/Users/listOfUsers")).andExpect(status().isOk());
    }
}