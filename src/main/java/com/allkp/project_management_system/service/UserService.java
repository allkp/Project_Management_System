package com.allkp.project_management_system.service;

import com.allkp.project_management_system.model.User;

public interface UserService {

    User findUserProfileByJwt(String jwt) throws Exception;

    User findUserByEmail(String email) throws Exception;

    User findUserById(Long userId) throws Exception;

    User updateUsersProjectSize(User user, int number);

}
