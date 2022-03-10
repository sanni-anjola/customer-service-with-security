package io.anjola.customerservicejava.repository;

import io.anjola.customerservicejava.model.entity.user.Role;
import io.anjola.customerservicejava.model.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(RoleName roleName);

}
