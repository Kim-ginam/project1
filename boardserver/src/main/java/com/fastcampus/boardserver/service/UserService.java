package com.fastcampus.boardserver.service;

import com.fastcampus.boardserver.dto.UserDTO;
import com.fastcampus.boardserver.exception.DuplicateException;

import javax.xml.datatype.Duration;
import java.util.Date;

public interface UserService {

    void register(UserDTO userProfile);

    UserDTO login(String id, String password);

    boolean isDuplicatedId(String id);

    UserDTO getUserInfo(String userID);

    void updatePassword(String id, String beforepassword, String afterPassword);

    void deleteId(String id, String password);
}
