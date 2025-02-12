package com.example.demo.service;

import com.example.demo.entity.RoleType;
import com.example.demo.entity.StatisticsUser;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.utils.BeanUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.management.InstanceNotFoundException;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    @Value("${app.kafka.kafkaUser}")
    private String topicName;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    public Optional<User> findById(String id) throws InstanceNotFoundException {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) throw new InstanceNotFoundException();
        else return user;
    }
    public boolean existsByNameAndEmail(String name, String email) throws TypeNotPresentException {
        return userRepository.existsByNameAndEmail(name, email);
    }
    public User save(User user){
        Set<RoleType> roles = user.getRoles();
        if (roles == null || (!roles.contains(RoleType.ROLE_MANAGER) && !roles.contains(RoleType.ROLE_USER)))
            throw new IllegalArgumentException("User must have some roles");
        if (existsByNameAndEmail(user.getName(), user.getEmail())) throw new IllegalStateException(
                "User with name " + user.getName() + " and email " + user.getEmail() + " already exists");

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);
        kafkaTemplate.send(topicName, new StatisticsUser(savedUser.getId()));
        return savedUser;
    }
    public User update(String id, User user) throws InstanceNotFoundException {
        try {
            Optional<User> existedUser = findById(id);
            if (existedUser.get().getRoles() != null) user.setRoles(existedUser.get().getRoles());
            if (existedUser.get().getName() != null) user.setName(existedUser.get().getName());
            if (existedUser.get().getEmail() != null) user.setEmail(existedUser.get().getEmail());
            if (existedUser.get().getId() != null) user.setId(existedUser.get().getId());
            if (existedUser.get().getPassword() != null) user.setPassword(existedUser.get().getPassword());
            return userRepository.save(user);
        } catch (InstanceNotFoundException e){
            throw new InstanceNotFoundException(e.getMessage());
        }
    }
    public void deleteById(String id){
        userRepository.deleteById(id);
    }
}
