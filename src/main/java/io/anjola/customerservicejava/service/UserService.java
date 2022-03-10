package io.anjola.customerservicejava.service;

import io.anjola.customerservicejava.model.entity.user.Role;
import io.anjola.customerservicejava.model.entity.user.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {

    List<User> getAllUser();
    Optional<User> getUser(Long id);
    Optional<User> getUserByUsername(String username);
    Optional<User> getUserByEmail(String email);
    User addUser(User user);
    User updateUser(User user);
    void deleteUser(User user);
    boolean usernameExists(String username);
    boolean emailExists(String email);
    Optional<Role> getUserRole(Long id);
}
