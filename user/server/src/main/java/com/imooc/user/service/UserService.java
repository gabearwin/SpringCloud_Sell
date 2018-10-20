package com.imooc.user.service;

import com.imooc.user.dataobject.UserInfo;

public interface UserService {
    UserInfo findByOpenid(String openid);
}
