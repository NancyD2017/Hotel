package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.utils.BeanUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    public Optional<User> findById(String id) throws TypeNotPresentException {
        return userRepository.findById(id);
    }
    public boolean existsByNameAndEmail(String name, String email) throws TypeNotPresentException {
        return userRepository.existsByNameAndEmail(name, email);
    }
    public User save(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
    public User update(String id, User user){
        Optional<User> existedUser = findById(id);
        if (existedUser.isPresent()) {
            BeanUtils.copyNonNullProperties(user, existedUser);
            return userRepository.save(user);
        }
        else return null;
    }
    public void deleteById(String id){
        userRepository.deleteById(id);
    }
}
