package ma.octo.assignement.service.depositService;

import ma.octo.assignement.domain.Deposit;
import ma.octo.assignement.domain.Transfer;
import ma.octo.assignement.dto.DepositDto;

import java.util.List;

public interface DepositService {
    public Deposit executeDeposit(DepositDto depositDto);

    public List<Deposit> listDeposit();
}
