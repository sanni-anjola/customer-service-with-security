package io.anjola.customerservicejava.controller.auth;

import io.anjola.customerservicejava.model.entity.user.User;
import io.anjola.customerservicejava.payload.APIResponse;
import io.anjola.customerservicejava.payload.auth.LoginRequest;
import io.anjola.customerservicejava.payload.auth.SignUpRequest;
import io.anjola.customerservicejava.service.UserService;
import io.anjola.customerservicejava.util.ApplicationConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping(ApplicationConstants.AUTH_ENDPOINT)
public class AuthController {

    private final UserService userService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest){

        return ResponseEntity.ok(userService.signin(loginRequest));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest){
        if(userService.usernameExists(signUpRequest.getUsername())){
            return new ResponseEntity<>(new APIResponse(false, "Username is already taken!",
                    HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
        }

        if(userService.emailExists(signUpRequest.getEmail())){
            return new ResponseEntity<>(new APIResponse(false, "Email Address already in use!",
                    HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
        }

        User newUser = userService.register(signUpRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/v1/users/{username}")
                .buildAndExpand(newUser.getUsername()).toUri();
        return ResponseEntity.created(location).body(new APIResponse(true, "User registered successfully",
                HttpStatus.CREATED));
    }
}
