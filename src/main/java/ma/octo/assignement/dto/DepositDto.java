package ma.octo.assignement.dto;

import java.math.BigDecimal;
import java.util.Date;

public class DepositDto {
    private String nomEmetteur;
    private String nrCompteBeneficiaire;
    private String motif;
    private BigDecimal montant;
    private Date date;

    public String getNomEmetteur() {
        return nomEmetteur;
    }

    public void setNomEmetteur(String nomEmetteur) {
        this.nomEmetteur = nomEmetteur;
    }

    public String getNrCompteBeneficiaire() {
        return nrCompteBeneficiaire;
    }

    public void setNrCompteBeneficiaire(String nrCompteBeneficiaire) {
        this.nrCompteBeneficiaire = nrCompteBeneficiaire;
    }

    public String getMotif() {
        return motif;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }

    public BigDecimal getMontant() {
        return montant;
    }

    public void setMontant(BigDecimal montant) {
        this.montant = montant;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
