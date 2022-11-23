package ma.octo.assignement.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class AccountRepositoryTest {

  @Autowired
  private AccountRepository transferRepository;

  @Test
  public void findByNrCompte() {
    transferRepository.findByNrCompte("010000B025001000");
  }


}