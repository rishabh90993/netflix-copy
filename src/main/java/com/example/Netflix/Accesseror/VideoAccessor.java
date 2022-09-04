package com.example.Netflix.Accesseror;

import com.example.Netflix.Accesseror.Model.Video.VideoDto;
import com.example.Netflix.Exceptions.DependencyFaliureException;
import com.example.Netflix.Exceptions.VideoNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class VideoAccessor {

    @Autowired
    DataSource dataSource;

    public VideoDto getVideoByVideoId(final String videoId){
        String query = "Select name,seriesId,showId,rating,releaseDate,totalLength from video where videoId = ?";
        try(Connection connection = dataSource.getConnection()){
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1,videoId);

            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                VideoDto videoDto = VideoDto.builder()
                        .videoId(videoId)
                        .name(rs.getString(1))
                        .seriesId(rs.getString(2))
                        .showId(rs.getString(3))
                        .rating(rs.getDouble(4))
                        .releaseDate(rs.getDate(5))
                        .totalLength(rs.getInt(6))
                        .build();
                return videoDto;
            }
            throw new VideoNotFoundException("Video "+ videoId+ " not found");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DependencyFaliureException(e);
        }


    }

}
