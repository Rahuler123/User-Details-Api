package com.user.userDetailsApi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.List;

@RestController
@RequestMapping("/user")
public class userController {

    private String userGivenEmail = "";
    private String userLoginEmail = "";
    private String genderCheck = "";
    private String salutation = "";
    boolean checkUserPassword;
    boolean checkUserMail;
    boolean checkMail;

    @Autowired
    private userService userService;

    @PostMapping("/userCreation")
    public ResponseEntity <String> userCreation(@RequestBody userDetails userDetails) {

        userGivenEmail = userDetails.getEmail();
        
        if(userDetails.getName().equals("") || userDetails.getGender().equals("") || userDetails.getEmail().equals("")) {
        	return ResponseEntity.ok("Fields cannot be null.");
        }

        try {
            checkMail = userService.findByEmail(userGivenEmail);
        } catch (Exception e) {
            return ResponseEntity.ok("Invalid mail address.");
        }
        if (checkMail == true) {
            return ResponseEntity.ok("Given mail id is taken. Try another.");
        } else {
            userService.addUserDetails(userDetails);
            return ResponseEntity.ok("Successfully Created !");
        }
    }

    @PostMapping("/userLogin")
    public ResponseEntity <String> userLogin(@RequestBody userDetails userDetails) {

        userLoginEmail = userDetails.getEmail();

        try {
            checkUserMail = userService.findByEmail(userLoginEmail);
        } catch (Exception e) {
            return ResponseEntity.ok("Invalid mail address.");
        }
        if (checkUserMail == true) {
            userDetails userIdByMail = userService.findByUserEmail(userLoginEmail);
            checkUserPassword = userIdByMail.getPassword().matches(userDetails.getPassword());
            if (checkUserPassword == true) {
                genderCheck = userIdByMail.getGender();
                if (genderCheck.equalsIgnoreCase("male") || genderCheck.equalsIgnoreCase("M")) {
                    salutation = "Mr.";
                } else if (genderCheck.equalsIgnoreCase("female") || genderCheck.equalsIgnoreCase("F")) {
                    salutation = "Ms.";
                }else {
                	 salutation = "";
                }
                return ResponseEntity.ok("Welcome " + salutation + "" + userIdByMail.getName() + " . Your Email Address is " + userIdByMail.getEmail());
            } else {
                return ResponseEntity.ok("Invalid Password.");
            }

        } else {
            return ResponseEntity.ok("Invalid mail address.");
        }
    }

    @GetMapping("/getAllUsers")
    public List <userDetails> getAllDetails() {
        return userService.getAllDetails();
    }

    @DeleteMapping("/deleteUser/{email}")
    public ResponseEntity <String> deleteUserDetails(@PathVariable String email) {

        try {
            checkMail = userService.findByEmail(email);
        } catch (Exception e) {
            return ResponseEntity.ok("Invalid Email address.");
        }
        if (checkMail == true) {

            userDetails userIdByMail = userService.findByUserEmail(email);
            userService.deleteUserDetails(userIdByMail.getId());
            return ResponseEntity.ok("Successfully deleted.");
        } else {
            return ResponseEntity.ok("Couldn't find your account.");
        }
    }
}