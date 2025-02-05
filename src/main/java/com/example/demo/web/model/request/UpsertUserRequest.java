package com.example.demo.web.model.request;

import com.example.demo.entity.RoleType;
import lombok.Data;

import java.util.Set;

@Data
public class UpsertUserRequest {
    private String name;
    private String password;
    private String email;
    private Set<RoleType> roles;
}
