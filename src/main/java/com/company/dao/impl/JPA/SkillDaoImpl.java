package com.company.dao.impl.JPA;

import com.company.dao.inter.AbstractDAO;
import com.company.dao.inter.SkillDaoInter;
import com.company.entity.Skill;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class SkillDaoImpl extends AbstractDAO implements SkillDaoInter {


    @Override
    public List<Skill> getAllSkills() {
        EntityManager entityManager = em();
        String jpql = "select s from Skill s";
        TypedQuery<Skill> typedQuery = entityManager.createQuery(jpql , Skill.class);
        List<Skill> skills =  typedQuery.getResultList();
        return  skills;
    }


}
