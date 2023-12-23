package com.sky.service;

import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;

public interface UserService {
    /**
     * @param userLoginDTO:
     * @return User
     * @author jiayueyuanfang
     * @description 微信登录
     * @date 2023/12/13 23:31
     */

    User wxLogin(UserLoginDTO userLoginDTO);
}
