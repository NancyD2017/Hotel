package com.example.demo.web.controller;

import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.UserService;
import com.example.demo.web.model.request.UpsertUserRequest;
import com.example.demo.web.model.response.ErrorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    //@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_MANAGER')")
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable String id) {
        Optional<User> user = userService.findById(id);
        return user.isPresent()
                ? ResponseEntity.ok().body(user)
                : ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse("User with id " + id + " not found"));
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UpsertUserRequest request) {
        return userService.existsByNameAndEmail(request.getName(), request.getEmail()) ?
                ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(
                                new ErrorResponse("User with name " +
                                        request.getName() + " and email " +
                                        request.getEmail() + " already exists"
                                )) :
                ResponseEntity.ok(userMapper.userToResponse(userService.save(userMapper.requestToUser(request))));
    }

    @PutMapping("/{id}")
    //@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_MANAGER')")
    public ResponseEntity<?> updateUser(@RequestBody UpsertUserRequest request,
                                        @PathVariable String id) {
        User updatedUser = userService.update(id, userMapper.requestToUser(request));
        return (updatedUser == null)
                ? ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse("User with id " + id + " not found"))
                : ResponseEntity.ok(userMapper.userToResponse(updatedUser));
    }

    @DeleteMapping("/{id}")
    //@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_MANAGER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable String id) {
        userService.deleteById(id);
    }

}
