package com.example.demo.mapper;

import com.example.demo.entity.User;
import com.example.demo.web.model.request.UpsertUserRequest;
import com.example.demo.web.model.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    UserResponse userToResponse(User user);

    UpsertUserRequest userToRequest(User user);

    User requestToUser(UpsertUserRequest request);

}
