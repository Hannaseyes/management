package com.management.rd.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.management.rd.pojo.User;


@Repository
public interface IUserDao {
    public List<User> listUser();
}
