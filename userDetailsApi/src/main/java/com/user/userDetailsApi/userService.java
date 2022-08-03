package com.user.userDetailsApi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.List;


@Service
public class userService {
    @Autowired
    private userRepository userRepository;
    
    public List < userDetails > getAllDetails() {
        return userRepository.findAll();
    }

    public userDetails addUserDetails(userDetails userDetails) {
        return userRepository.save(userDetails);
    }

    public userDetails updateUserDetails(int id, userDetails userDetails) {
        userDetails.setId(id);
        return userRepository.save(userDetails);
    }
    
    public void deleteUserDetails(int id) {
        userRepository.deleteById(id);
    }

    public boolean findByEmail(String custEmail) {
        Optional < userDetails > userDetailEmail = userRepository.findByEmail(custEmail);
        if (userDetailEmail.isPresent()) {
            return true;
        }
        return false;
    }

    public userDetails findByUserEmail(String custEmail) {
        userDetails UserDetails = userRepository.findByEmail(custEmail).get();
        return UserDetails;
    }
}