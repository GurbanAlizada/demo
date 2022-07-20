package com.company.dao.impl.JDBC;

import com.company.dao.inter.AbstractDAO;
import com.company.dao.inter.CountryDaoInter;
import com.company.entity.Country;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CountryDaoImpl extends AbstractDAO implements CountryDaoInter {


    @Override
    public List<Country> getAllCountry() {
       List<Country> result = new ArrayList<>();
        try(Connection connection = connect()){
            PreparedStatement preparedStatement = connection.prepareStatement("select * from resumejpa.country");
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();
            while(resultSet.next()){
                int id = resultSet.getInt("id");
                String countryName = resultSet.getString("country_name");
                result.add(new Country(id , countryName , null));
            }
            return result;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


        return null;
    }
}
