package com.gmail.popovychar.wuportal.service;

import com.gmail.popovychar.wuportal.domain.WUser;
import com.gmail.popovychar.wuportal.exception.domain.*;


import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

//Service that con controller is going to call and will return repository
public interface UserService {
    WUser register(String firstName, String lastName, String username, String email)
            throws UserNotFoundException, UsernameExistException, EmailExistException, MessagingException; //, MessagingException

    List<WUser> getUsers();

    WUser findUserByUsername(String username);

    WUser findUserByEmail(String email);

    WUser addNewUser(String firstName, String lastName, String username, String email, String role,
                     boolean isNonLocked, boolean isActive, MultipartFile profileImage) throws UserNotFoundException,
            UsernameExistException, EmailExistException, IOException, NotAnImageFileException;

    WUser updateUser(String currentUsername, String newFirstName, String newLastName, String newUsername, String newEmail,
                     String role, boolean isNonLocked, boolean isActive, MultipartFile profileImage)
            throws UserNotFoundException, UsernameExistException, EmailExistException, IOException, NotAnImageFileException;

    void deleteUser(String username) throws IOException;

    void resetPassword(String email) throws  EmailNotFoundException, MessagingException; //MessagingException,

    WUser updateProfileImage(String username, MultipartFile profileImage)
            throws UserNotFoundException, UsernameExistException, EmailExistException, IOException, NotAnImageFileException;
}
