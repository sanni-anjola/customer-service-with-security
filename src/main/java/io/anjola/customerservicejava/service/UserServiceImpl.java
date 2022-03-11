package io.anjola.customerservicejava.service;

import io.anjola.customerservicejava.exception.ApplicationException;
import io.anjola.customerservicejava.model.entity.user.Role;
import io.anjola.customerservicejava.model.entity.user.User;
import io.anjola.customerservicejava.payload.auth.JwtAuthenticationResponse;
import io.anjola.customerservicejava.payload.auth.LoginRequest;
import io.anjola.customerservicejava.payload.auth.SignUpRequest;
import io.anjola.customerservicejava.payload.user.UserRegisterRequest;
import io.anjola.customerservicejava.repository.RoleRepository;
import io.anjola.customerservicejava.repository.UserRepository;
import io.anjola.customerservicejava.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private  final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;


    public List<User> getAllUser(){
        return userRepository.findAll();
    }

    public Optional<User> getUser(Long id){
        return userRepository.findById(id);
    }

    public Optional<User> getUserByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public Optional<User> getUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public User addUser(User user){
        return userRepository.save(user);
    }

    public User updateUser(User user){
        return userRepository.save(user);
    }

    public void deleteUser(User user){
        userRepository.delete(user);
    }

    public boolean usernameExists(String username){
        return userRepository.existsByUsername(username);
    }

    public boolean emailExists(String email){
        return userRepository.existsByEmail(email);
    }

    public Optional<Role> getUserRole(Long id){
        return roleRepository.findById(id);
    }

    public JwtAuthenticationResponse signin(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsernameOrEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtTokenProvider.generateToken(authentication);

        return new JwtAuthenticationResponse(jwt, "Bearer");
    }

    public User register(SignUpRequest signUpRequest){
        return getUser(signUpRequest.getName(), signUpRequest.getUsername(), signUpRequest.getEmail(), signUpRequest.getPassword(), signUpRequest.getRole());
    }

    public User register(UserRegisterRequest userRegisterRequest){
        return getUser(userRegisterRequest.getName(), userRegisterRequest.getUsername(), userRegisterRequest.getEmail(), userRegisterRequest.getPassword(), userRegisterRequest.getRole());
    }

    private User getUser(String name, String username, String email, String password, Long role2) {
        User user = new User(name, username,
                email, password);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role role = getUserRole(role2)
                .orElseThrow(() -> new ApplicationException("User Role not set."));
        user.setRoles(Collections.singleton(role));
        user.setIsEnabled(true);

        return addUser(user);
    }
}
