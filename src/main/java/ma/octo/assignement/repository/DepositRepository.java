package ma.octo.assignement.repository;

import ma.octo.assignement.domain.Deposit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

public interface DepositRepository extends JpaRepository<Deposit,Long> {
}
