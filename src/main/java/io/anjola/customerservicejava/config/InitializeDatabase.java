package io.anjola.customerservicejava.config;

import io.anjola.customerservicejava.model.entity.user.Role;
import io.anjola.customerservicejava.model.enums.RoleName;
import io.anjola.customerservicejava.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class InitializeDatabase {
    private final RoleRepository roleRepository;

    @PostConstruct
    public void initializeData(){
        roleRepository.save(new Role(RoleName.ROLE_ADMIN));
        roleRepository.save(new Role(RoleName.ROLE_USER));
        roleRepository.save(new Role(RoleName.ROLE_CUSTOMER));
    }
}
