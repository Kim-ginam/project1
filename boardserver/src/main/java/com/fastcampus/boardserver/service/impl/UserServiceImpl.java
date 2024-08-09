package com.fastcampus.boardserver.service.impl;

import com.fastcampus.boardserver.dto.UserDTO;
import com.fastcampus.boardserver.exception.DuplicateException;
import com.fastcampus.boardserver.mapper.UserProfileMapper;
import com.fastcampus.boardserver.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

import static com.fastcampus.boardserver.utils.SHA256Util.encryptionSHA256;

@Log4j2
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserProfileMapper userProfileMapper;

    public UserServiceImpl(UserProfileMapper userProfileMapper){
        this.userProfileMapper = userProfileMapper;

    }

    @Override
    public void register(UserDTO userProfile) {
        boolean duplIdResult = isDuplicatedId(userProfile.getUserId());
        if(duplIdResult){
            throw new DuplicateException("중복된 아이디입니다.");
        }
        userProfile.setCreateTime(new Date());
        userProfile.setPassword(encryptionSHA256(userProfile.getPassword()));

        int insertCount = userProfileMapper.register(userProfile);

        if(insertCount != 1){
            log.error("insertMember ERROR {}", userProfile);
            throw new RuntimeException(
                    "insertUser ERROR! 회원가입 매서드를 확인해주세요\n" + "Params : " + userProfile);

        }

    }

    @Override
    public UserDTO login(String id, String password) {
        String cryptoPassword = encryptionSHA256(password);
        UserDTO memberInfo = userProfileMapper.findByIdAndPassword(id, cryptoPassword);

        return memberInfo;
    }

    @Override
    public boolean isDuplicatedId(String id) {
        return userProfileMapper.idCheck(id) == 1;
    }

    @Override
    public UserDTO getUserInfo(String userID) {
        return null;
    }

    @Override
    public void updatePassword(String id, String beforepassword, String afterPassword) {
        String cryptoPassword = encryptionSHA256(beforepassword);
        UserDTO memberInfo = userProfileMapper.findByIdAndPassword(id, cryptoPassword);
        if(memberInfo != null){
            memberInfo.setPassword(encryptionSHA256(afterPassword));
            int insertCount = userProfileMapper.updatePassword(memberInfo);
        }else{
            log.error("updatePassword ERROR! {}", memberInfo);
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");

        }


    }

    @Override
    public void deleteId(String id, String password) {
        String cryptoPassword = encryptionSHA256(password);

        UserDTO memberInfo = userProfileMapper.findByIdAndPassword(id, password);

        if(memberInfo != null){
            int deleteCount = userProfileMapper.deleteUserProfile(id);
        }else{
            log.error("delete ERROR! {}", memberInfo);
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }
    }
}
