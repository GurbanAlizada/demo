package com.company.dao.impl.JDBC;

import com.company.dao.inter.AbstractDAO;
import com.company.dao.inter.SkillDaoInter;
import com.company.entity.Skill;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SkillDaoImpl extends AbstractDAO implements SkillDaoInter {


    @Override
    public List<Skill> getAllSkills() {
        List<Skill> result = new ArrayList<>();
        try(Connection connection = connect()){
            PreparedStatement preparedStatement = connection.prepareStatement("select * from resumejpa.skill");
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();

            while(resultSet.next()){
                int id = resultSet.getInt("id");
                String skillName = resultSet.getString("name");
                result.add(new Skill(id , skillName));
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
