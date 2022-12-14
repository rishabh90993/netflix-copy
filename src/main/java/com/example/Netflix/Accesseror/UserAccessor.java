package com.example.Netflix.Accesseror;

import com.example.Netflix.Accesseror.Model.User.UserDto;
import com.example.Netflix.Accesseror.Model.User.UserRole;
import com.example.Netflix.Accesseror.Model.User.UserState;
import com.example.Netflix.Accesseror.Model.User.VerificationStatus;
import com.example.Netflix.Exceptions.DependencyFaliureException;
import org.apache.catalina.Role;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

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

    public UserDto getUserByPhoneNo(String phoneNo){
        String query = "Select userId, name, email, password,phoneNo,state, role, emailVerificationStatus, phoneVerificationStatus from user where phoneNO = ?";

        try(Connection connection = dataSource.getConnection()){
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1,phoneNo);

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
                        .emailVerificationStatus(VerificationStatus.valueOf(resultSet.getString(8)))
                        .phoneVerificationStatus(VerificationStatus.valueOf(resultSet.getString(9)))
                        .build();
                return userDto;
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DependencyFaliureException(e);
        }

    }

    public UserDto addUser(UserDto user){
        String query = "INSERT INTO user(userId,name,email,password,phoneNo,state,role) values (? ,? ,? ,? ,? ,? ,?)";

        try(Connection connection = dataSource.getConnection()){
            PreparedStatement ps = connection.prepareStatement(query);

            ps.setString(1, user.getUserID());
            ps.setString(2,user.getName());
            ps.setString(3,user.getEmail());
            ps.setString(4,user.getPassword());
            ps.setString(5,user.getPhoneNo());
            ps.setString(6,user.getState().name());
            ps.setString(7,user.getRole().name());

            ps.executeUpdate();

            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DependencyFaliureException(e);
        }

    }

    public void updateUserRole(String userId, UserRole userRole){
        String query = "UPDATE user set role = ? where userId = ?";

        try(Connection connection = dataSource.getConnection()){
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1,userRole.name());
            ps.setString(2,userId);

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DependencyFaliureException(e);
        }
    }

    public void updateEmailVerificationStatus(String userId, VerificationStatus verificationStatus){
        String query = "UPDATE user set emailVerificationStatus = ? where userId = ?";

        try(Connection connection = dataSource.getConnection()){
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1,verificationStatus.name());
            ps.setString(2,userId);

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DependencyFaliureException(e);
        }
    }

    public void updatePhoneVerificationStatus(String userId, VerificationStatus verificationStatus){
        String query = "UPDATE user set phoneVerificationStatus = ? where userId = ?";

        try(Connection connection = dataSource.getConnection()){
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1,verificationStatus.name());
            ps.setString(2,userId);

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DependencyFaliureException(e);
        }
    }

}
