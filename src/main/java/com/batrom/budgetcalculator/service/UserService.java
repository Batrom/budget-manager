package com.batrom.budgetcalculator.service;

import com.batrom.budgetcalculator.dto.UserDTO;
import com.batrom.budgetcalculator.model.User;
import com.batrom.budgetcalculator.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class UserService {

    public void createUser(final UserDTO userDTO) {
        if (CollectionUtils.isEmpty(userRepository.findByEmailOrLogin(userDTO.getEmail(), userDTO.getLogin()))) {
            final User user = new User();
            userRepository.save(user);
        }
    }

    private UserRepository userRepository;

    @Autowired
    public UserService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
