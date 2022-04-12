package com.ps.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.NamingException;

import com.ps.domain.User;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserRepository{

    private static final Logger logger=LogManager.getLogger();

    public User save(User user){
        logger.debug("about to insert into DB: "+user );

        String sql="INSERT INTO user VALUES(?,?,?,?,?,?)";
        try(
            Connection connection=DBConnectionManager.getDBConnection();
            PreparedStatement ps=connection.prepareStatement(sql)
        ){
            ps.setString(1, user.getId());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getHashedPassword());
            ps.setString(4, user.getFirstName());
            ps.setString(5, user.getLastName());
            ps.setString(6, user.getMobileNumber());

            int x=ps.executeUpdate();
            if(x>0){
                return user;
            }

        }catch(SQLException | NamingException e){
            logger.catching(e);
            logger.error("Error inserting user to DB: "+user);
            throw new RuntimeException("Error inserting into DB");
        }
        return null;
    }

    public User findById(String userId){
        logger.debug("about to fetch user from DB: "+userId);
        String sql="SELECT * FROM user where id=?";
        User user=null;
        try(
            Connection connection=DBConnectionManager.getDBConnection();
            PreparedStatement statement=connection.prepareStatement(sql);
        ){
            statement.setString(1, userId);

            try(ResultSet rs=statement.executeQuery();){
                user= new User();
                if(rs.next()){
                    user.setId(rs.getString("id"));
                    user.setEmail(rs.getString("email"));
                    user.setHashedPassword(rs.getString("password"));
                    user.setFirstName(rs.getString("first_name"));
                    user.setLastName(rs.getString("last_name"));
                    user.setMobileNumber(rs.getString("mobile_number"));
                }
            }

        }catch(SQLException | NamingException e){
            logger.catching(e);
            logger.error("Error getting by User Id"+userId);
            throw new RuntimeException("Error getting user by id: "+userId);
        }
        return user;
    }

    public User findByMail(String email){
        logger.debug("about to fetch user from DB: "+email);
        String sql="SELECT * FROM user where email=?";
        User user=null;
        try(
            Connection connection=DBConnectionManager.getDBConnection();
            PreparedStatement statement=connection.prepareStatement(sql);
        ){
            statement.setString(1, email);

            try(ResultSet rs=statement.executeQuery();){
                user= new User();
                if(rs.next()){
                    user.setId(rs.getString("id"));
                    user.setEmail(rs.getString("email"));
                    user.setHashedPassword(rs.getString("hashed_password"));
                    user.setFirstName(rs.getString("first_name"));
                    user.setLastName(rs.getString("last_name"));
                    user.setMobileNumber(rs.getString("mobile_number"));
                }
            }
        }catch(SQLException | NamingException e){
            logger.catching(e);
            logger.error("Error getting by User mail"+email);
            throw new RuntimeException("Error getting user by mail: "+email);
        }
        return user;
    }
    
}