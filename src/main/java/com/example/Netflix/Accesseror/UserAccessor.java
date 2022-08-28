package com.example.Netflix.Accesseror;

import com.example.Netflix.Accesseror.Model.User.UserDto;
import com.example.Netflix.Accesseror.Model.User.UserRole;
import com.example.Netflix.Accesseror.Model.User.UserState;
import com.example.Netflix.Exceptions.DependencyFaliureException;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class UserAccessor {

    @Autowired
    private DataSource dataSource;

    /// Gets User Based on its email if not presents returns null
    public UserDto getUserByEmail(String email){
        String query = "Select userId, name, email, password,phoneNo,state, role from user where email = ?";

        try(Connection connection = dataSource.getConnection()){
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1,email);

            ResultSet resultSet = ps.executeQuery();
            if(resultSet.next()){
                UserDto userDto = UserDto.builder()
                        .userID(resultSet.getString(1))
                        .name(resultSet.getString(2))
                        .email(resultSet.getString(3))
                        .password(resultSet.getString(4))
                        .phoneNo(resultSet.getString(5))
                        .state(UserState.valueOf(resultSet.getString(6)))
                        .role(UserRole.valueOf(resultSet.getString(7)))
                        .build();
                    return userDto;
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DependencyFaliureException(e);
        }

    }

}
