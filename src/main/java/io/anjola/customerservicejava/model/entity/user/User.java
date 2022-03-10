package io.anjola.customerservicejava.model.entity.user;

import io.anjola.customerservicejava.model.audit.DateAudit;
import io.anjola.customerservicejava.model.entity.user.Role;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"username"}),
        @UniqueConstraint(columnNames = {"email"})
})
@Data
public class User extends DateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 60)
    private String name;

    @NotBlank
    @Size(max = 20)
    private String username;

    @NaturalId
    @NotBlank
    @Size(max = 60)
    @Email
    private String email;

    @NotBlank
    @Size(max = 100)
    private String password;

    private Boolean isEnabled;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    @Embedded
    private UserDetails userDetails;

    public User() {
    }

    public User(@NotBlank @Size(max = 60) String name, @NotBlank @Size(max = 20) String username,
                @NotBlank @Size(max = 60) @Email String email, @NotBlank @Size(max = 100) String password) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
    }


}
