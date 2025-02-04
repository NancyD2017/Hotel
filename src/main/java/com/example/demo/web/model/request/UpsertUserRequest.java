package com.example.demo.web.model.request;

import com.example.demo.entity.RoleType;
import lombok.Data;

@Data
public class UpsertUserRequest {
    private String name;
    private String password;
    private String email;
    private RoleType role;
}
