package ma.octo.assignement.service.MoneyDepositService;

import ma.octo.assignement.domain.Account;
import ma.octo.assignement.domain.MoneyDeposit;
import ma.octo.assignement.dto.DepositDto;

public interface MoneyDepositService {
    public MoneyDeposit executeDeposit(DepositDto depositDto, Account compteBe);
}
