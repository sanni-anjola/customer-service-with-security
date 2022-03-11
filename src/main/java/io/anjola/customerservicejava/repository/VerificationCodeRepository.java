package io.anjola.customerservicejava.repository;

import io.anjola.customerservicejava.model.entity.user.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Long> {
    Optional<VerificationCode> findByUser_Id(Long id);
}
