package com.yovvis.example.common.service;

import com.yovvis.example.common.model.User;

/**
 * 获取用户服务
 *
 * @author yovvis
 * @date 2024/3/5
 */
public interface UserService {
    /**
     * 获取用户
     *
     * @param user
     * @return
     */
    User getUser(User user);

    /**
     * 新方法，返回数字
     *
     * @return
     */
    default short getNumber(){
        return 1;
    }
}
