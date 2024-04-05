package com.natwest.CapstoneUserService.service;

import com.natwest.CapstoneUserService.dto.RegisterDto;
import com.natwest.CapstoneUserService.dto.UserDto;
import com.natwest.CapstoneUserService.entity.User;
import com.natwest.CapstoneUserService.exception.*;
import com.natwest.CapstoneUserService.repository.UserRepository;
import com.natwest.CapstoneUserService.utility.AppConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SavingAccountClient savingAccountClient;

    public boolean validateEmailId(String email) {
        int n = email.length();
        if (!email.isEmpty() && !email.isBlank()) {
            if (email.endsWith(".com")) {
                String first = "" + email.charAt(0);
                String beforeDot = "" + email.charAt(n - 5);
                if (email.contains("@") && !first.equals("@") && !beforeDot.equals("@")) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean validateName(String name)  {
        if (!name.isBlank() && !name.isEmpty()){
            return true;
        }
        return false;
    }

    public boolean validateMobileNo(Long mobileNo) {
        String mobile = "" + mobileNo;
        if (mobile.length() == 10){
            int n =0;
            for (int i=0 ; i<mobile.length(); i++){
                if (mobile.charAt(i) <='9' && mobile.charAt(i)>= '0') {
                    n++;
                }
            }
            if (n==10){
                return true;
            }
        }
        return false;

    }

    public boolean validatePassword(String password) {
        String specialCharacters = "!@#$%^&*()-_=+[{]}|;:,<.>/?";
        if (password != null && password.length() >= 8) {
            boolean hasUppercase = false;
            boolean hasLowercase = false;
            boolean hasDigit = false;
            boolean hasSpecialChar = false;

            for (char ch : password.toCharArray()) {
                if (Character.isUpperCase(ch)) {
                    hasUppercase = true;
                } else if (Character.isLowerCase(ch)) {
                    hasLowercase = true;
                } else if (Character.isDigit(ch)) {
                    hasDigit = true;
                } else if (specialCharacters.contains(ch +"")) {
                    hasSpecialChar = true;
                }
            }
            return hasUppercase && hasLowercase && hasDigit && hasSpecialChar;
        }
        return false;
    }

    private String generateUserId() {
        return String.valueOf((int) (Math.random() * 100000));
    }


    public UserDto signup(RegisterDto input) throws UserAlreadyExistException, InvalidEmailException, InvalidNameException, InvalidPhoneNoException, InvalidPasswordException {
        if (userRepository.existsById(input.getEmail())){
            throw new UserAlreadyExistException(AppConstant.USER_ALREADY_EXISTS_MESSAGE);
        }
        if (!validateEmailId(input.getEmail())){
            throw new InvalidEmailException(AppConstant.INVALID_EMAIL_MESSAGE);
        } if (!validateName(input.getFullName())){
            throw new InvalidNameException(AppConstant.INVALID_NAME_MESSAGE);
        } if (!validateMobileNo(input.getMobileNumber())){
            throw new InvalidPhoneNoException(AppConstant.INVALID_MOBILENO_MESSAGE);
        } if (!validatePassword(input.getPassword())){
            throw new InvalidPasswordException(AppConstant.INVALID_PASSWORD_MESSAGE);
        } else {
            String userId = generateUserId();
            String password = passwordEncoder.encode(input.getPassword());
            User user = new User(input.getMobileNumber(), userId, input.getFullName(), input.getEmail(), password);
            UserDto userDto = new UserDto(user.getMobileNumber(), user.getFullName(), user.getEmail());
            savingAccountClient.createAccoountNumber(userDto);
            userRepository.save(user);
            return userDto;
        }
    }


    public User getUser(String email) throws UserNotFoundException {
        if (userRepository.existsById(email)){
            return userRepository.findById(email).get();
        }
        else throw new UserNotFoundException(AppConstant.USER_NOT_FOUND_MESSAGE);

    }

    public String removeUser(String email) throws UserNotFoundException {
        if (userRepository.existsById(email)){
            userRepository.delete(userRepository.findById(email).get());
            return "successfully removed";
        }
        else throw new UserNotFoundException(AppConstant.USER_NOT_FOUND_MESSAGE);

    }

}