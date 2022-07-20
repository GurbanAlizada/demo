package com.company.test;

import com.company.dao.impl.JDBC.CountryDaoImpl;
import com.company.dao.impl.JDBC.EmploymentHistoryDaoImpl;
import com.company.dao.impl.JDBC.SkillDaoImpl;
import com.company.dao.impl.JDBC.UserSkillIDaompl;
import com.company.dao.impl.JDBC.UserDaoImpl;
import com.company.dao.inter.*;
import com.company.entity.Country;
import com.company.entity.EmploymentHistory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {
    public static void main(String[] args) {

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        UserDaoInter daoJfbc = new com.company.dao.impl.JDBC.UserDaoImpl();
        //CountryDaoInter countryDaoInter = new CountryDaoImpl();


        System.out.println(daoJfbc.getUsersWithParams("Qurban", null));
        //System.out.println(daoJfbc.getByEmailAndPassword("abc", "123"));
      //  System.out.println(daoJfbc.getAllUser());

        UserDaoInter userDaoInterjpa = new com.company.dao.impl.JPA.UserDaoImpl();
        //System.out.println(userDaoInterjpa.getAllUser());
        //System.out.println(userDaoInterjpa.getUsersWithParams("Qurban", null));

        System.out.println(userDaoInterjpa.getUserLike("rb"));

        //  System.out.println(countryDaoInter.getAllCountry());
      //  EmploymentHistoryDaoInter employmentHistoryDaoInter = new EmploymentHistoryDaoImpl();
      //  System.out.println(employmentHistoryDaoInter.getAllEmploymentHistoryByUserId(1));


     //   SkillDaoInter skillDaoInter = new SkillDaoImpl();
      //  System.out.println(skillDaoInter.getAllSkills());

    //    UserSkillDaoInter userSkillDaoInter = new UserSkillIDaompl();
       // System.out.println(userSkillDaoInter.getAllSkillByUserId(1));

        entityManager.close();
        entityManagerFactory.close();



    }
}
