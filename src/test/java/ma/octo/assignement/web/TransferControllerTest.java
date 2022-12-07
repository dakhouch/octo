package ma.octo.assignement.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import ma.octo.assignement.dto.TransferDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.math.BigDecimal;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TransferControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Test
    public void getAllTransfers() throws Exception {
     mockMvc.perform(get("/transfers/listOfTransfers")).andExpect(status().isOk());
    }
    @Test
    public void createTransaction() throws Exception {
        TransferDto transaction=new TransferDto();
        transaction.setNrCompteBeneficiaire("010000A000001000");
        transaction.setNrCompteEmetteur("010000B025001000");
        transaction.setMontant(BigDecimal.valueOf(10000.00));
        transaction.setMotif("test2");
        transaction.setDate(null);
        mockMvc.perform(post("/transfers/executeTransfers").content(asJsonString(transaction)).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());
    }
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
