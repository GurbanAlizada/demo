package com.company.dao.impl.JPA;

import com.company.dao.inter.AbstractDAO;
import com.company.dao.inter.CountryDaoInter;
import com.company.entity.Country;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class CountryDaoImpl extends AbstractDAO implements CountryDaoInter {

    @Override
    public List<Country> getAllCountry() {
        EntityManager entityManager = em();
        String jpql = "select c from Country c";
        TypedQuery<Country> typedQuery = entityManager.createQuery(jpql , Country.class);
        List<Country> countries =  typedQuery.getResultList();
        if(countries.size()>0){
            return  countries;
        }
        return null;
    }
}
