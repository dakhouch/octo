package ma.octo.assignement.service.transferService;

import ma.octo.assignement.domain.Transfer;
import ma.octo.assignement.dto.TransferDto;
import ma.octo.assignement.exceptions.CompteNonExistantException;
import ma.octo.assignement.exceptions.TransactionException;

import java.util.List;

public interface TransferService {
    public List<Transfer> listTransfers();
    public Transfer executeTransfer(TransferDto transferDto) throws CompteNonExistantException, TransactionException;

}
