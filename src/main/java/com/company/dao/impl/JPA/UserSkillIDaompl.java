package com.company.dao.impl.JPA;

import com.company.dao.inter.AbstractDAO;
import com.company.dao.inter.UserSkillDaoInter;
import com.company.entity.UserSkill;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class UserSkillIDaompl extends AbstractDAO implements UserSkillDaoInter {


    @Override
    public List<UserSkill> getAllSkillByUserId(int userId) {
        EntityManager entityManager = em();
        String jpql = "select us from UserSkill us left join fetch us.user left join fetch us.skill where us.id=:id";
        TypedQuery<UserSkill> typedQuery = entityManager.createQuery(jpql , UserSkill.class);
        List<UserSkill> userSkills =  typedQuery.getResultList();
        return  userSkills;
    }


}
