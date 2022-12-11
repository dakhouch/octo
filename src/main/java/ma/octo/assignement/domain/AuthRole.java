package ma.octo.assignement.domain;

import javax.persistence.*;

@Entity
@Table(name = "role")
public class AuthRole {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(length = 60, nullable = false)
    private String role ;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
