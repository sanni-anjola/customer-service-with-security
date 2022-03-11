package io.anjola.customerservicejava.controller.admin;

import io.anjola.customerservicejava.exception.NotFoundException;
import io.anjola.customerservicejava.model.entity.user.User;
import io.anjola.customerservicejava.payload.APIResponse;
import io.anjola.customerservicejava.payload.user.UserRegisterRequest;
import io.anjola.customerservicejava.payload.user.UserUpdateRequest;
import io.anjola.customerservicejava.service.UserService;
import io.anjola.customerservicejava.util.ApplicationConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping(ApplicationConstants.ADMIN_ENDPOINT)
public class AdminController {

    private final UserService userService;

    @GetMapping("/users")
    public List<User> getAllUser(){
        return userService.getAllUser();
    }

    @GetMapping("/users/{id}")
    public User getUserId(@PathVariable Long id){
        return userService.getUser(id)
                .orElseThrow(() -> new NotFoundException("User", "id", Long.toString(id)));
    }

    @PostMapping("/users")
    public ResponseEntity<?> insertUser(@Valid @RequestBody UserRegisterRequest userRegisterRequest){
        if(userService.usernameExists(userRegisterRequest.getUsername())){
            return new ResponseEntity<>(new APIResponse(false, "Username is already taken!",
                    HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
        }

        if(userService.emailExists(userRegisterRequest.getEmail())){
            return new ResponseEntity<>(new APIResponse(false, "Email Address already in use!",
                    HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
        }

        User newUser = null;
        try {
            newUser = userService.register(userRegisterRequest);
        }catch (Exception ex){
            return new ResponseEntity<>(new APIResponse(false, "Something went wrong",
                    HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<?> insertUser(@Valid @RequestBody UserUpdateRequest userUpdateRequest,
                                        @PathVariable Long id){
        User user = userService.getUser(id)
                .orElseThrow(() -> new NotFoundException("User", "id", Long.toString(id)));
        if(!user.getEmail().equals(userUpdateRequest.getEmail())){
            if(userService.emailExists(userUpdateRequest.getEmail())){
                return new ResponseEntity<>(new APIResponse(false, "Email Address already in use!",
                        HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
            }
        }

        user.setEmail(userUpdateRequest.getEmail());
        user.setPassword(userUpdateRequest.getPassword());
        user.setName(userUpdateRequest.getName());
        user.setUserDetails(userUpdateRequest.getUserDetails());

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id){
        User user = userService.getUser(id)
                .orElseThrow(() -> new NotFoundException("User", "id", Long.toString(id)));
        userService.deleteUser(user);
        return new ResponseEntity<>(new APIResponse(true, "Account Deleted Successfully",
                HttpStatus.OK), HttpStatus.OK);
    }

}
