package com.company.dao.impl.JDBC;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.company.dao.inter.AbstractDAO;
import com.company.dao.inter.UserDaoInter;
import com.company.entity.Country;
import com.company.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl extends AbstractDAO implements UserDaoInter {



    private User getUser(ResultSet resultSet) throws Exception {


        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        String surname = resultSet.getString("surname");
        String email = resultSet.getString("email");
        String phone = resultSet.getString("phone");
        String password = resultSet.getString("password");

        int nationalityId = resultSet.getInt("nationality_id");
        int birthplaceId = resultSet.getInt("birth_place_id");
        String nationalityStr = resultSet.getString("nationality");
        String birthplaceStr = resultSet.getString("country_name");
        Date birthDate = resultSet.getDate("date_of_date");

        Country nationality = new Country(nationalityId ,null , nationalityStr);
        Country birthPlace = new Country(birthplaceId ,  birthplaceStr , null);
        return new User(id, name, surname, email, phone , birthDate ,  nationality , birthPlace, password );


    }


    @Override
    public List<User> getUsersWithParams(String name, String surname) {
        List<User> result = new ArrayList<>();
        try (Connection connection = connect()) {
            String sql = " select  " +
                    "  u.*  , " +
                    "  n.nationality  , " +
                    "  c.country_name  " +
                    " from  " +
                    "  resumejpa.user u  " +
                    "  left join resumejpa.country n on u.nationality_id = n.id  " +
                    "  left join resumejpa.country c on u.birth_place_id = c.id  where 1=1 ";

            if (name != null && !name.trim().isEmpty()){
                sql += " and u.name = ? ";
            }
            if (surname != null && !surname.trim().isEmpty()){
                sql += " and u.surname = ? ";
            }
            PreparedStatement statement = connection.prepareStatement(sql);
            int i =1;
            if (name != null && !name.trim().isEmpty()){
                statement.setString(i , name);
                i++;
            }
            if (surname != null  && !surname.trim().isEmpty()){
                statement.setString(i ,surname);
                i++;
            }
            statement.execute();

            ResultSet resultSet = statement.getResultSet();
            while (resultSet.next()) {
                User u = getUser(resultSet);
                result.add(u);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            System.out.println("Veri tabanina baglanamadi");
            System.out.println(throwables);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }



        // select
    @Override
    public List<User> getAllUser() {
        List<User> result = new ArrayList<>();
                try (Connection connection = connect()) {
                    // AutoClose ucun try with resource artiq close etmek lazim deyil
                    Statement statement = connection.createStatement();
                    String sql = " select  " +
                            "  u.*  , " +
                            "  n.nationality  , " +
                            "  c.country_name  " +
                            " from  " +
                            "  resumejpa.user u  " +
                            "  left join resumejpa.country n on u.nationality_id = n.id  " +
                            "  left join resumejpa.country c on u.birth_place_id = c.id  ";
                    statement.execute(sql);


            ResultSet resultSet = statement.getResultSet();
            while (resultSet.next()) {
                 User u = getUser(resultSet);
                  result.add(u);
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            System.out.println("Veri tabanina baglanamadi");
                    System.out.println(throwables);
        } catch (Exception e) {
                    e.printStackTrace();
                }
        return result;
    }

    
    
  


    // select
    
    
    public User getByEmailAndPassword(String email , String password) {

        User result = null;
        try (Connection connection = connect()) { // AutoClose ucun try with resource

             PreparedStatement stmt = connection.prepareStatement("select * from resumejpa.user where email=? and password=?");
             stmt.setString(1,email);
             stmt.setString(2,password);
        
             ResultSet rs = stmt.executeQuery();
           
           while (rs.next()) {
               
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String surname = rs.getString("surname");
        String phone = rs.getString("phone");
        String email1 = rs.getString("email");
      
        String password1 = rs.getString("password");

        User user1= new User(id , name , surname ,  email1 , phone , password1);

               
               
     
               result = user1;
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            System.out.println("Veri tabanina baglanamadi");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;


    }

    
    
        
        
        
   




    // update
    @Override
    public boolean updateUser(User u) {
        try (Connection connection = connect()) {
            // Statement statement = connect().createStatement();
            //  return statement.execute("update user set email = 'example2@gmail.com' where id = 5");
            PreparedStatement preparedStatement = connection.prepareStatement("update resumejpa.user set name = ? , surname = ? , email = ? , phone = ?  where id = ?");
            preparedStatement.setString(1, u.getName());
            preparedStatement.setString(2, u.getSurname());
            preparedStatement.setString(3, u.getEmail());
            preparedStatement.setString(4, u.getPhone());
            preparedStatement.setInt(5, u.getId());

            return preparedStatement.execute();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            System.out.println("Veri tabanina baglanamadi");

        }
        return true;
    }







    // delete
    @Override
    public boolean removeUser(int id) {
        try (Connection connection = connect()) {
            Statement statement = connection.createStatement();
            return statement.execute("delete from resumejpa.user  where id = " + id);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            System.out.println("Veri tabanina baglanamadi");

        }
        return true;
    }




private BCrypt.Hasher cryp = BCrypt.withDefaults();
  
    // insert
    @Override
    public boolean addUser(User u) {
        try (Connection connection = connect()) {
            PreparedStatement preparedStatement = connection.prepareStatement("insert into resumejpa.user(name , surname , email , phone , password) values (? , ? , ?  , ? , ?  )");
            preparedStatement.setString(1, u.getName());
            preparedStatement.setString(2, u.getSurname());
            preparedStatement.setString(3, u.getEmail());
            preparedStatement.setString(4, u.getPhone());
            preparedStatement.setString(5,cryp.hashToString(4,  u.getPassword().toCharArray()));

            return preparedStatement.execute();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            System.out.println("Add user Ex ");

        }
        return false;
    }

    @Override
    public List<User> getUserLike(String param) {
        return null;
    }

    @Override
    public User findByName(String name) {
        return null;
    }


    @Override
    public User getById(int userId) {
        User result = null;
        try (Connection c = connect()) {

            Statement stmt = c.createStatement();
            stmt.execute("select * from resumejpa.user where id=" + userId);
            ResultSet rs = stmt.getResultSet();

            while (rs.next()) {
                
         int id = rs.getInt("id");
        String name = rs.getString("name");
        String surname = rs.getString("surname");
        String email = rs.getString("email");
        String phone = rs.getString("phone");
        String password = rs.getString("password");
               
        User user =  new User(id, name, surname, email, phone , password );

                
                
                result =user;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }



}
