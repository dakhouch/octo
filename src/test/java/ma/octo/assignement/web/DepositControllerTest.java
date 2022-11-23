package ma.octo.assignement.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import ma.octo.assignement.dto.DepositDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class DepositControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    public void createTransaction() throws Exception {
        DepositDto transaction = new DepositDto();
        transaction.setDate(null);
        transaction.setMotif("test3");
        transaction.setMontant(BigDecimal.valueOf(10000.00));
        transaction.setNomEmetteur("Anass");
        transaction.setNrCompteBeneficiaire("010000A000001000");
        mockMvc.perform(post("/deposits/executeDeposits").content(asJsonString(transaction)).contentType(MediaType.APPLICATION_JSON)
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