package ma.octo.assignement.service.AuditService;

import ma.octo.assignement.domain.Audit;
import ma.octo.assignement.domain.util.EventType;
import ma.octo.assignement.dto.DepositDto;
import ma.octo.assignement.dto.TransferDto;
import ma.octo.assignement.repository.AuditRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class AuditServiceImpl implements AuditService {

    Logger LOGGER = LoggerFactory.getLogger(AuditServiceImpl.class);

    @Autowired
    private AuditRepository auditRepository;

    public void auditTransfer(TransferDto transferDto) {
        LOGGER.info("Audit de l'événement {}", EventType.TRANSFER);
        String message="Transfer depuis " + transferDto.getNrCompteEmetteur() + " vers " + transferDto
                .getNrCompteBeneficiaire() + " d'un montant de " + transferDto.getMontant().toString();
        Audit transferAudit = new Audit();
        transferAudit.setEventType(EventType.TRANSFER);
        transferAudit.setMessage(message);
        auditRepository.save(transferAudit);
    }
    public void auditDeposit(DepositDto depositDto) {
        LOGGER.info("Audit de l'événement {}", EventType.DEPOSIT);
        String message="Deposit depuis " + depositDto.getNomEmetteur()+" vers " + depositDto
                .getNrCompteBeneficiaire() + " d'un montant de " + depositDto.getMontant().toString();
        Audit depositAudit = new Audit();
        depositAudit.setEventType(EventType.DEPOSIT);
        depositAudit.setMessage(message);
        auditRepository.save(depositAudit);
    }


}
