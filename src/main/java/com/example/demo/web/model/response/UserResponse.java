package com.example.demo.web.model.response;

import com.example.demo.entity.RoleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private String id;
    private String name;
    private String password;
    private String email;
    private Set<RoleType> roles;
}
