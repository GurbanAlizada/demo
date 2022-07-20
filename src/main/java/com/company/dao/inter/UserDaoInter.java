package com.company.dao.inter;


import com.company.entity.User;

import java.util.List;

public interface UserDaoInter {

 public   List<User> getAllUser();

    List<User> getUsersWithParams(String name , String surname );

    User getByEmailAndPassword(String email , String password) ;

    User getById(int userId);

   boolean updateUser(User u);

    boolean removeUser(int id);

    boolean addUser(User u);

    List<User> getUserLike(String param);

    User findByName(String name);


}
