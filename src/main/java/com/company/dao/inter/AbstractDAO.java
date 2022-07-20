package com.company.dao.inter;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public abstract class AbstractDAO {



    public  Connection connect() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/resume" , "root", "1234");


        return connection;
    }



    private static EntityManagerFactory entityManagerFactory = null;

    public EntityManager em(){
        if(entityManagerFactory == null){
            entityManagerFactory= Persistence.createEntityManagerFactory("default");
        }
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        return entityManager;
    }

    public void closeEmf(){
        entityManagerFactory.close();
    }





}