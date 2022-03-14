package io.anjola.customerservicejava.service;

import io.anjola.customerservicejava.config.MailConfig;
import io.anjola.customerservicejava.exception.ApplicationException;
import io.anjola.customerservicejava.model.entity.user.Role;
import io.anjola.customerservicejava.model.entity.user.User;
import io.anjola.customerservicejava.model.entity.user.VerificationCode;
import io.anjola.customerservicejava.payload.auth.JwtAuthenticationResponse;
import io.anjola.customerservicejava.payload.auth.LoginRequest;
import io.anjola.customerservicejava.payload.auth.SignUpRequest;
import io.anjola.customerservicejava.payload.user.UserRegisterRequest;
import io.anjola.customerservicejava.repository.RoleRepository;
import io.anjola.customerservicejava.repository.UserRepository;
import io.anjola.customerservicejava.repository.VerificationCodeRepository;
import io.anjola.customerservicejava.security.jwt.JwtTokenProvider;
import io.anjola.customerservicejava.util.MailUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.utility.RandomString;
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
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final VerificationCodeRepository verificationCodeRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final MailConfig mail;


    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    public Optional<User> getUser(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User addUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    public boolean usernameExists(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    public Optional<Role> getUserRole(Long id) {
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

    public User register(SignUpRequest signUpRequest) {
        return getUser(signUpRequest.getName(), signUpRequest.getUsername(), signUpRequest.getEmail(), signUpRequest.getPassword(), signUpRequest.getRole());
    }


    public User register(UserRegisterRequest userRegisterRequest) throws Exception {
        return getUser(userRegisterRequest.getName(), userRegisterRequest.getUsername(), userRegisterRequest.getEmail(), userRegisterRequest.getPassword(), userRegisterRequest.getRole());
    }

    private User getUser(String name, String username, String email, String password, Long role2) {
        User user = new User(name, username,
                email, password);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role role = getUserRole(role2)
                .orElseThrow(() -> new ApplicationException("User Role not set."));
        user.setRoles(Collections.singleton(role));

        String randomCode = RandomString.make(64);
        VerificationCode verificationCode = new VerificationCode();
        verificationCode.setUser(user);
        verificationCode.setCode(randomCode);

        user.setIsEnabled(false);
        String siteUrl = "http://localhost:8080/api/v1/auth/";

        verificationCodeRepository.save(verificationCode);

        try {
            mail.sendEmail(user.getEmail(), "Verification Mail", MailUtil.verificationMail(siteUrl, user.getId(), user.getName(), randomCode));
        } catch (Exception e) {
            log.error("Error sending email", e);
        }

        return user;//addUser(user);
    }

    public void deleteVerification(VerificationCode code) {
        verificationCodeRepository.delete(code);
    }


    public VerificationCode getCode(Long id) {
        return verificationCodeRepository.findByUser_Id(id)
                .orElseThrow(() -> new ApplicationException("Please try again"));
    }

    public VerificationCode save(VerificationCode code) {
        return verificationCodeRepository.save(code);
    }
}
