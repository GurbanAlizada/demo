package com.company.dao.impl.JPA;

import com.company.dao.inter.AbstractDAO;
import com.company.dao.inter.EmploymentHistoryDaoInter;
import com.company.entity.EmploymentHistory;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class EmploymentHistoryDaoImpl extends AbstractDAO implements EmploymentHistoryDaoInter {


    @Override
    public List<EmploymentHistory> getAllEmploymentHistoryByUserId(int userId) {
        EntityManager entityManager  = em();
        String jpql = "select e from EmploymentHistory  e  where e.id = :id";
        TypedQuery<EmploymentHistory> typedQuery = entityManager.createQuery(jpql , EmploymentHistory.class);
        typedQuery.setParameter("id" , userId);
        List<EmploymentHistory> employmentHistories = typedQuery.getResultList();
        entityManager.close();
        if(employmentHistories.size()>0)
        return employmentHistories;
        return null;
    }


}



