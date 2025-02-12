package com.example.demo.web.controller;

import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.UserService;
import com.example.demo.web.model.request.UpsertUserRequest;
import com.example.demo.web.model.response.ErrorResponse;
import com.example.demo.web.model.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.management.InstanceNotFoundException;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_MANAGER')")
    public ResponseEntity<?> findById(@PathVariable String id) {
        try {
            Optional<User> user = userService.findById(id);
            return ResponseEntity.ok().body(user);
        } catch (InstanceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("User with id " + id + " not found"));
        }
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UpsertUserRequest request) {
        try {
            UserResponse user = userMapper.userToResponse(userService.save(userMapper.requestToUser(request)));
            return ResponseEntity.ok(user);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_MANAGER')")
    public ResponseEntity<?> updateUser(@RequestBody UpsertUserRequest request,
                                        @PathVariable String id) {
        try {
            User updatedUser = userService.update(id, userMapper.requestToUser(request));
            return ResponseEntity.ok(userMapper.userToResponse(updatedUser));
        } catch (InstanceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_MANAGER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable String id) {
        userService.deleteById(id);
    }
}
