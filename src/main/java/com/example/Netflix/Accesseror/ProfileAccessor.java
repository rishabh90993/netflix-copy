package com.example.Netflix.Accesseror;

import com.example.Netflix.Accesseror.Model.Profile.ProfileDto;
import com.example.Netflix.Accesseror.Model.Profile.ProfileType;
import com.example.Netflix.Exceptions.DependencyFaliureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.UUID;

@Repository
public class ProfileAccessor {

    @Autowired
    private DataSource dataSource;

    public void addProfile(String userId, String profileName,ProfileType type){

        String query = "INSERT INTO profile (profileId, name,type,createdAt,userId) values (? , ? , ? , ? , ? )";

        try(Connection connection = dataSource.getConnection()){
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, UUID.randomUUID().toString());
            ps.setString(2,profileName);
            ps.setString(3,type.name());
            ps.setDate(4,new Date(System.currentTimeMillis()));
            ps.setString(5,userId);

            ps.execute();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DependencyFaliureException(e);
        }

    }

    public void deActivateProfile(final String profileId){

        String query = "DELETE FROM profile where profileId = ?";

        try(Connection connection = dataSource.getConnection()){
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, profileId);

            ps.execute();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DependencyFaliureException(e);
        }

    }

    public ProfileDto getProfile(final String profileId){

        String query = "SELECT name, type, createdAt, userID FROM profile where profileId = ?";

        try(Connection connection = dataSource.getConnection()){
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, profileId);

           ResultSet rs = ps.executeQuery();

           if(rs.next()){
               ProfileDto profileDto = ProfileDto.builder()
                       .profileId(profileId)
                       .name(rs.getString(1))
                       .type(ProfileType.valueOf(rs.getString(2)))
                       .createdAt(rs.getDate(3))
                       .userId(rs.getString(4))
                       .build();
               return profileDto;
           }
        return null;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DependencyFaliureException(e);
        }

    }

}
