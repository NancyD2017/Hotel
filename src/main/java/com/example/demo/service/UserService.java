package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.utils.BeanUtils;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    public List<User> findAll(){
        return userRepository.findAll();
    }
    public Optional<User> findById(String id) throws EntityNotFoundException {
        return userRepository.findById(id);
    }
    public boolean existsByNameAndEmail(String name, String email) throws EntityNotFoundException {
        return userRepository.existsByNameAndEmail(name, email);
    }
    public Optional<User> findByName(String name) throws EntityNotFoundException {
        return userRepository.findByName(name);
    }
    public User save(User user){
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
