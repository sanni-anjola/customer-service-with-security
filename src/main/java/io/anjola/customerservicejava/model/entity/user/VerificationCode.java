package io.anjola.customerservicejava.model.entity.user;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class VerificationCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;

    @OneToOne(cascade = CascadeType.ALL)
    User user;
}
