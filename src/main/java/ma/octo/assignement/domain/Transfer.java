package ma.octo.assignement.domain;


import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "transfer")
public class Transfer {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(precision = 16, scale = 2, nullable = false)
  private BigDecimal montantTransfer;

  @Column
  @Temporal(TemporalType.TIMESTAMP)
  private Date dateExecution;

  @ManyToOne

  private Account compteEmetteur;

  @ManyToOne
  private Account compteBeneficiaire;

  @Column(length = 200)
  private String motifTransfer;

  public BigDecimal getMontantTransfer() {
    return montantTransfer;
  }

  public void setMontantTransfer(BigDecimal montantTransfer) {
    this.montantTransfer = montantTransfer;
  }

  public Date getDateExecution() {
    return dateExecution;
  }

  public void setDateExecution(Date dateExecution) {
    this.dateExecution = dateExecution;
  }

  public Account getCompteEmetteur() {
    return compteEmetteur;
  }

  public void setCompteEmetteur(Account compteEmetteur) {
    this.compteEmetteur = compteEmetteur;
  }

  public Account getCompteBeneficiaire() {
    return compteBeneficiaire;
  }

  public void setCompteBeneficiaire(Account compteBeneficiaire) {
    this.compteBeneficiaire = compteBeneficiaire;
  }

  public String getMotifTransfer() {
    return motifTransfer;
  }

  public void setMotifTransfer(String motifTransfer) {
    this.motifTransfer = motifTransfer;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }
}
