package ma.octo.assignement.repository;

import ma.octo.assignement.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
  Account findByNrCompte(String nrCompte);
}
