package com.company.dao.impl.JDBC;

import com.company.dao.inter.AbstractDAO;
import com.company.dao.inter.EmploymentHistoryDaoInter;
import com.company.entity.EmploymentHistory;
import com.company.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmploymentHistoryDaoImpl extends AbstractDAO implements EmploymentHistoryDaoInter {

    private EmploymentHistory getEmploymentHistory(ResultSet resultSet) throws Exception {

        String header = resultSet.getString("header");
        Date beginDate = resultSet.getDate("begin_date");
        Date endDate = resultSet.getDate("end_date");
        String jobDescription = resultSet.getString("job_description");
        Integer userId = resultSet.getInt("user_id");

        EmploymentHistory emp = new EmploymentHistory(null , header , beginDate , endDate , jobDescription  , new User(userId));
        return emp;
    }


    @Override
    public List<EmploymentHistory> getAllEmploymentHistoryByUserId(int userId) {
        List<EmploymentHistory> result = new ArrayList<>();
        try(Connection connection = connect()){
            PreparedStatement preparedStatement = connection.prepareStatement("select * from resumejpa.employmenthistory where user_id =  ?");
            preparedStatement.setInt(1,userId);
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();

            while(resultSet.next()){
                EmploymentHistory u  = getEmploymentHistory(resultSet);
                result.add(u);
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







