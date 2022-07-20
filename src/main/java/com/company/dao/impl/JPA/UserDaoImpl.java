package com.company.dao.impl.JPA;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.company.dao.inter.AbstractDAO;
import com.company.dao.inter.UserDaoInter;
import com.company.entity.User;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class UserDaoImpl extends AbstractDAO implements UserDaoInter {


   public User findByName(String name){
        EntityManager entityManager = em();
        String sql  = "select u from User u where u.name = :name";
       TypedQuery<User> typedQuery = entityManager.createQuery(sql , User.class);
       typedQuery.setParameter("name" ,name);
       User user = typedQuery.getSingleResult();
       entityManager.close();
       return user;
    }


    @Override
    public List<User> getUsersWithParams(String name, String surname) {
            EntityManager entityManager = em();
            String jpql = "select u from User u left join fetch u.nationalityId " +
                    " left join fetch u.birthPlaceId " +
                    " where 1=1 ";
            if (name != null && !name.trim().isEmpty()){
                jpql += " and  u.name = :name ";
            }
            if (surname != null && !surname.trim().isEmpty()){
                jpql += " and u.surname = :surname ";
            }
          TypedQuery<User> typedQuery = entityManager.createQuery(jpql , User.class);
            if (name != null && !name.trim().isEmpty()){
                typedQuery.setParameter("name" , name);
            }
            if (surname != null  && !surname.trim().isEmpty()){
               typedQuery.setParameter("surname" , surname);
            }
         List<User> users = typedQuery.getResultList();
            closeEmf();
        return users;
    }



    public User getByEmailAndPassword(String email , String password) {
        EntityManager entityManager = em();
        String query = "select u from User u where u.email = :email and u.password =:password ";
        TypedQuery<User> typedQuery = entityManager.createQuery(query , User.class);
        typedQuery.setParameter("email" ,email);
        typedQuery.setParameter("password" , password);
        List<User> users = typedQuery.getResultList();
        entityManager.close();
        closeEmf();
        if(users.size()==1){
            return users.get(0);
        }
        return null;
    }



    @Override
    public List<User> getAllUser() {
        EntityManager entityManager = em();
        String query = "select u from User u ";
        String query2 =  "select u from User u left join fetch u.nationalityId left join fetch u.birthPlaceId";
        TypedQuery<User> typedQuery = entityManager.createQuery(query2 , User.class);
        List<User> users = typedQuery.getResultList();
        entityManager.close();
        closeEmf();
        return users;
    }


    @Override
    public boolean updateUser(User u) {
            EntityManager entityManager = em();
            entityManager.getTransaction().begin();
            u.setPassword(cryp.hashToString(4,  u.getPassword().toCharArray()));
                            entityManager.merge(u);
            entityManager.getTransaction().commit();
            entityManager.close();
        return true;
    }


    @Override
    public boolean removeUser(int id) {
            EntityManager entityManager = em();
            User u = entityManager.find(User.class, id);
            entityManager.getTransaction().begin();
                            entityManager.remove(u);
            entityManager.getTransaction().commit();
            entityManager.close();
        return true;
    }


    private BCrypt.Hasher cryp = BCrypt.withDefaults();
    @Override
    public boolean addUser(User u) {
        EntityManager entityManager = em();
        entityManager.getTransaction().begin();
        u.setPassword(cryp.hashToString(4,  u.getPassword().toCharArray()));
                     entityManager.persist(u);
        entityManager.getTransaction().commit();
        entityManager.close();
        return false;
    }

    @Override
    public List<User> getUserLike(String param) {
        EntityManager entityManager = em();
        String jpql = "select u from User u where u.name like CONCAT('%', :param, '%') ";
       TypedQuery<User> typedQuery = entityManager.createQuery(jpql , User.class);
        //TypedQuery<User> typedQuery = entityManager.createNamedQuery( "find", User.class);
        typedQuery.setParameter("param" , param);
        List<User> list = typedQuery.getResultList();
        return list;
    }


    @Override
    public User getById(int userId) {
       EntityManager entityManager = em();
                      User user = entityManager.find(User.class , userId);
       entityManager.close();
       return user;
    }


}
