package ma.octo.assignement.service.TransferService;

import ma.octo.assignement.domain.Account;
import ma.octo.assignement.domain.Transfer;
import ma.octo.assignement.dto.TransferDto;
import ma.octo.assignement.exceptions.CompteNonExistantException;
import ma.octo.assignement.exceptions.TransactionException;

import java.util.List;

public interface TransferService {
    public List<Transfer> listTransfers();
    public void executerTransfer(TransferDto transferDto, Account compteEm, Account compteBe) throws CompteNonExistantException, TransactionException;

}
