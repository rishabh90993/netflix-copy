package com.example.Netflix.Accesseror;

import com.example.Netflix.Accesseror.Model.WatchHistoryDto;
import com.example.Netflix.Exceptions.DependencyFaliureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.DeleteMapping;

import javax.sql.DataSource;
import java.sql.*;

@Repository
public class WatchHistoryAccessor {

    @Autowired
    private DataSource dataSource;

    public void updateWatchHistory(String profileId,String videoId, int watchtime){
        String query = "UPDATE watchHistory set watchLength = ? , lastWatchedAt = ?" +
                " where profileId= ? and videoId = ?";
        try(Connection connection = dataSource.getConnection()){
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1,watchtime);
            ps.setDate(2,new Date(System.currentTimeMillis()));
            ps.setString(3,profileId);
            ps.setString(4,videoId);

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DependencyFaliureException(e);
        }

    }

    public void insertWatchHistory(String profileId,String videoId, int watchtime, double rating){
        String query = "INSERT into watchHistory values(?,?,?,?,?,?)";
        try(Connection connection = dataSource.getConnection()){
            PreparedStatement ps = connection.prepareStatement(query);
            Date current = new Date(System.currentTimeMillis());

            ps.setString(1,profileId);
            ps.setString(2,videoId);
            ps.setDouble(3,rating);
            ps.setInt(4,watchtime);
            ps.setDate(5,current);
            ps.setDate(6,current);
            ps.execute();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DependencyFaliureException(e);
        }

    }

    public WatchHistoryDto getWatchHistory(String profileId, String videoId){
        String query = "SELECT rating, watchedLength,lastWatchedAt,firstWatchedAt from watchHistory " +
                "where profileId = ? and videoId = ?";
        try(Connection connection = dataSource.getConnection()){
            PreparedStatement ps = connection.prepareStatement(query);
            Date current = new Date(System.currentTimeMillis());

            ps.setString(1,profileId);
            ps.setString(2,videoId);

           ResultSet rs =  ps.executeQuery();

           if(rs.next()){
               WatchHistoryDto watchHistoryDto = WatchHistoryDto.builder()
                       .profileId(profileId)
                       .videoId(videoId)
                       .rating(rs.getDouble(1))
                       .watchedLength(rs.getInt(2))
                       .lastWatchedAt(rs.getDate(3))
                       .firstWatchedAt(rs.getDate(4))
                       .build();
               return watchHistoryDto;
           }

           return null;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DependencyFaliureException(e);
        }

    }

}
