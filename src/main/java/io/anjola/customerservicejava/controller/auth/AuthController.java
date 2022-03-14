package io.anjola.customerservicejava.controller.auth;

import io.anjola.customerservicejava.exception.ApplicationException;
import io.anjola.customerservicejava.exception.NotFoundException;
import io.anjola.customerservicejava.model.entity.user.User;
import io.anjola.customerservicejava.model.entity.user.VerificationCode;
import io.anjola.customerservicejava.payload.APIResponse;
import io.anjola.customerservicejava.payload.auth.LoginRequest;
import io.anjola.customerservicejava.payload.auth.SignUpRequest;
import io.anjola.customerservicejava.service.UserService;
import io.anjola.customerservicejava.util.ApplicationConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@Validated
@Slf4j
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
        /*try {
            newUser =
        }catch (Exception ex){
            log.info("Signup --> {}", ex.getMessage());
            return new ResponseEntity<>(new APIResponse(false, "Something went wrong",
                    HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
        }*/

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/v1/users/{username}")
                .buildAndExpand(newUser.getUsername()).toUri();
        return ResponseEntity.created(location).body(new APIResponse(true, "User registered successfully",
                HttpStatus.CREATED));
    }

    @GetMapping("{id}/verify")
    public ResponseEntity<?> activateUser(@PathVariable(value = "id") Long id, @RequestParam(value = "code") String code){
        User user = userService.getUser(id)
                .orElseThrow(() -> new NotFoundException("User", "id", Long.toString(id)));

        VerificationCode savedCode = userService.getCode(user.getId());
        if(Boolean.FALSE.equals(user.getIsEnabled())){
            if(savedCode.getCode().equals(code)) {
                user.setIsEnabled(true);
                userService.deleteVerification(savedCode);
            }
            return ResponseEntity.ok("Your verification process is successful");
        }

        return ResponseEntity.ok(null);

    }
}
