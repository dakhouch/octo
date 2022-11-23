package ma.octo.assignement.mapper;

import ma.octo.assignement.domain.Transfer;
import ma.octo.assignement.dto.TransferDto;

public class TransferMapper {

    private static TransferDto transferDto;

    public static TransferDto map(Transfer transfer) {
        transferDto = new TransferDto();
        transferDto.setNrCompteEmetteur(transfer.getCompteEmetteur().getNrCompte());
        transferDto.setDate(transfer.getDateExecution());
        transferDto.setMotif(transfer.getMotifTransfer());
        //mapper le compte de beneficiaire
        transferDto.setNrCompteBeneficiaire(transfer.getCompteBeneficiaire().getNrCompte());
        return transferDto;

    }
}
