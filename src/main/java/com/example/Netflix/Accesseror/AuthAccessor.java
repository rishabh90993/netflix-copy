package com.example.Netflix.Accesseror;

import com.example.Netflix.Accesseror.Model.Auth.AuthDto;
import com.example.Netflix.Exceptions.DependencyFaliureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@Repository
public class AuthAccessor {

   @Autowired
    DataSource dataSource;

   /** it will store token for a user, if successfull ends, otherwise throws Exception**/
    public void storeToken(String userID,String token){
        String query = "INSERT INTO auth (authId,token,userId) values(?, ?, ?)";
        String uuId = UUID.randomUUID().toString();

        try(Connection connection = dataSource.getConnection()){
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1,uuId);
            ps.setString(2,token);
            ps.setString(3,userID);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DependencyFaliureException(e);
        }

    }

    public AuthDto getAuthByToken(String token){
        String query = "Select authId,token,userID from auth where token = ?";


        try(Connection connection = dataSource.getConnection()){
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1,token);
            ResultSet resultSet = ps.executeQuery();
            if(resultSet.next()){
                AuthDto dto = AuthDto.builder()
                        .authId(resultSet.getString(1))
                        .token(resultSet.getString(2))
                        .userId(resultSet.getString(3))
                        .build();
                return dto;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DependencyFaliureException(e);
        }
        return null;
    }


}
