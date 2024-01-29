package org.forum.web.forum.helpers;

import org.forum.web.forum.exceptions.AuthorizationException;
import org.forum.web.forum.exceptions.EntityNotFoundException;
import org.forum.web.forum.models.Comment;
import org.forum.web.forum.models.Post;
import org.forum.web.forum.models.User;
import org.forum.web.forum.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationHelper {

    private static final String AUTHORIZATION_HEADER_NAME = "Authorization";
    private static final String INVALID_AUTHENTICATION_ERROR = "Invalid user or password.";
    private static final String AUTHORIZATION_ERROR = "Access denied! You are not allowed to perform this action!";

    private final UserService userService;

    @Autowired
    public AuthenticationHelper(UserService userService) {
        this.userService = userService;
    }

    public User tryGetUser(HttpHeaders headers) {
        if (!headers.containsKey(AUTHORIZATION_HEADER_NAME)) {
            throw new AuthorizationException(INVALID_AUTHENTICATION_ERROR);
        }

        String userInfo = headers.getFirst(AUTHORIZATION_HEADER_NAME);
        String username = getUsername(userInfo);
        String password = getPassword(userInfo);
        return verifyAuthentication(username, password);
    }

    private String getUsername(String userInfo) {
        int firstSpace = userInfo.indexOf(" ");
        if (firstSpace == -1) {
            throw new AuthorizationException(INVALID_AUTHENTICATION_ERROR);
        }

        return userInfo.substring(0, firstSpace);
    }

    private String getPassword(String userInfo) {
        int firstSpace = userInfo.indexOf(" ");
        if (firstSpace == -1) {
            throw new AuthorizationException(INVALID_AUTHENTICATION_ERROR);
        }

        return userInfo.substring(firstSpace + 1);
    }

    private User verifyAuthentication(String username, String password) {
        try {
            User user = userService.getByUsername(username);
            if (!user.getPassword().equals(password)) {
                throw new AuthorizationException(INVALID_AUTHENTICATION_ERROR);
            }
            return user;
        } catch (EntityNotFoundException e) {
            throw new AuthorizationException(INVALID_AUTHENTICATION_ERROR);
        }
    }
    public void checkIfBanned(User user) {
        if (user.getBanStatus()) {
            throw new AuthorizationException("This user is banned!");
        }
    }
    public void checkAuthor(User user, User userToCheck) {
        if (userToCheck.getUserId() != user.getUserId()) {
            throw new AuthorizationException(AUTHORIZATION_ERROR);
        }
    }
    public void checkAuthor(User user, Post post){
        if (user.getUserId() != post.getCreator().getUserId()){
            throw new AuthorizationException(AUTHORIZATION_ERROR);
        }
    }
    public void checkAuthor(User user, Comment comment){
        if (user.getUserId() != comment.getCreator().getUserId()){
            throw new AuthorizationException(AUTHORIZATION_ERROR);
        }
    }

    public void checkAdmin(User user) {
        if (!user.getAdminStatus()) {
            throw new AuthorizationException(AUTHORIZATION_ERROR);
        }
    }
}
