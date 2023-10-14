package com.spring.boot.service;



import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.boot.dao.UserRepository;
import com.spring.boot.model.Users;

@Service
public class SaveData {

     @Autowired
    private UserRepository userRepository;


   
    public void saveData(String userName) {

        Optional<Users> userOptional = userRepository.findByUserName(userName);

        if (userOptional.isPresent()) {
            Users user = userOptional.get();
            user.setEmail(userName);

            userRepository.save(user);
    
      
    
    }
}
}
    

