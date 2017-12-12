package com.management.rd.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.management.rd.dao.IUserDao;
import com.management.rd.pojo.User;
import com.management.rd.service.IUserService;



@Service("userService")
public class UserServiceImpl implements IUserService {
    @Resource  
    private IUserDao userDao;
    
    @Override
    public List<User> listUser() {
        List<User> userList = userDao.listUser();
        return userList;
    }

}
