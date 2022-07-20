package com.company.dao.impl.JDBC;

import com.company.dao.inter.AbstractDAO;
import com.company.dao.inter.UserSkillDaoInter;
import com.company.entity.Skill;
import com.company.entity.User;
import com.company.entity.UserSkill;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserSkillIDaompl extends AbstractDAO implements UserSkillDaoInter {

    private UserSkill getUserSkill(ResultSet resultSet) throws Exception {

        int userId = resultSet.getInt("id");
        int skillId = resultSet.getInt("skill_id");
        String skillName  = resultSet.getString("skill_name");
        int power = resultSet.getInt("power");

        return new UserSkill(null, new User(userId) ,  new Skill(skillId , skillName) , power);

    }





    @Override
    public List<UserSkill> getAllSkillByUserId(int userId) {
        List<UserSkill> result = new ArrayList<>();

        try(Connection connection = connect()){

            PreparedStatement preparedStatement = connection.prepareStatement("SELECT " +
                    " u.* , " +
                    " us.skill_id , " +
                    " s.name AS skill_name ," +
                    " us.power " +
                    " FROM  " +
                    " resumejpa.userskill us " +
                    "LEFT JOIN resumejpa.user u ON us.user_id = u.id " +
                    "LEFT JOIN resumejpa.skill s ON us.skill_id = s.id  " +
                    "WHERE " +
                    "us.user_id = ?;");
            preparedStatement.setInt(1,userId);
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();

            while(resultSet.next()){

                UserSkill u  = getUserSkill(resultSet);
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
