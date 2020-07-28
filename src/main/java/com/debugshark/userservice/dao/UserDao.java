package com.debugshark.userservice.dao;

import com.debugshark.userservice.models.User;

import java.util.List;

public interface UserDao {
public boolean save(User user);
public User getByEmailId(String EmailId);
public boolean update(User user);
public boolean deleteById(int id);
public List<User> getAll();
public boolean updatePassword(int id, String password);
}

