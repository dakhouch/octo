package ma.octo.assignement.service.AuditService;

import ma.octo.assignement.dto.DepositDto;
import ma.octo.assignement.dto.TransferDto;

public interface AuditService {
    public void auditTransfer(TransferDto transferDto);
    public void auditDeposit(DepositDto depositDto);
}
